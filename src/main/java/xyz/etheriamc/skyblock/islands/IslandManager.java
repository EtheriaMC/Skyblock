package xyz.etheriamc.skyblock.islands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.etheriamc.skyblock.islands.models.Island;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class IslandManager {
    private Plugin plugin;
    private Map<UUID, Island> islands;
    private File dataFile;
    private Gson gson;
    private World islandWorld;

    private static final int ISLAND_DISTANCE = 1000; // distance between Islands

    public IslandManager(Plugin plugin, World islandWorld) {
        this.plugin = plugin;
        this.islands = new HashMap<>();
        this.dataFile = new File(plugin.getDataFolder(), "islands.json");
        this.gson = new Gson();
        this.islandWorld = islandWorld;
        loadIslands();
    }

    public boolean createIsland(Player player, String name) {
        UUID uuid = player.getUniqueId();
        if (islands.containsKey(uuid)) {
            return false;
        }

        Location islandSpawn = generateIslandLocation();
        Island island = new Island(uuid, name, islandSpawn);
        islands.put(uuid, island);

        player.teleport(islandSpawn);

        saveIslands();
        return true;
    }

    private Location generateIslandLocation() {
        Random random = new Random();
        int x, z;

        do {
            x = (random.nextInt(200) - 100) * ISLAND_DISTANCE;
            z = (random.nextInt(200) - 100) * ISLAND_DISTANCE;
        } while (!isLocationAvailable(x, z));

        Location location = new Location(islandWorld, x, 100, z);

        generateIsland(location);

        return location;
    }

    private boolean isLocationAvailable(int x, int z) {
        for (Island island : islands.values()) {
            Location spawn = island.getSpawnLocation();
            if (spawn.getWorld().equals(islandWorld)) {
                if (spawn.distanceSquared(new Location(islandWorld, x, 0, z)) < ISLAND_DISTANCE * ISLAND_DISTANCE) {
                    return false;
                }
            }
        }
        return true;
    }

    private void generateIsland(Location location) {
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                world.getBlockAt(x + dx, y - 1, z + dz).setType(Material.GRASS);
            }
        }

        world.getBlockAt(x, y, z).setType(Material.LOG);
        for (int dy = 1; dy <= 4; dy++) {
            world.getBlockAt(x, y + dy, z).setType(Material.LOG);
        }
        world.getBlockAt(x, y + 5, z).setType(Material.LEAVES);
        world.getBlockAt(x + 1, y + 5, z).setType(Material.LEAVES);
        world.getBlockAt(x - 1, y + 5, z).setType(Material.LEAVES);
        world.getBlockAt(x, y + 5, z + 1).setType(Material.LEAVES);
        world.getBlockAt(x, y + 5, z - 1).setType(Material.LEAVES);
    }

    public boolean deleteIsland(Player player) {
        UUID uuid = player.getUniqueId();
        if (!islands.containsKey(uuid)) {
            return false;
        }

        islands.remove(uuid);
        saveIslands();
        return true;
    }

    public boolean deleteIsland(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            return deleteIsland(player);
        }
        return false;
    }

    public Island getIsland(Player player) {
        return islands.get(player.getUniqueId());
    }

    public void setIslandSpawn(Player player, Location location) {
        Island island = getIsland(player);
        if (island != null) {
            island.setSpawnLocation(location);
            saveIslands();
        }
    }

    public Location getIslandSpawn(Player player) {
        Island island = getIsland(player);
        if (island != null) {
            return island.getSpawnLocation();
        }
        return null;
    }

    public boolean invitePlayer(Player owner, Player invitee) {
        Island island = getIsland(owner);
        if (island != null && !island.isMember(invitee)) {
            boolean result = island.addInvite(invitee);
            saveIslands();
            return result;
        }
        return false;
    }

    public boolean acceptInvitation(Player player, Player owner) {
        Island island = getIsland(owner);
        if (island != null && island.isInvited(player)) {
            boolean result = island.addMember(player);
            saveIslands();
            return result;
        }
        return false;
    }

    public boolean denyInvitation(Player player, Player owner) {
        Island island = getIsland(owner);
        if (island != null && island.isInvited(player)) {
            boolean result = island.removeInvite(player);
            saveIslands();
            return result;
        }
        return false;
    }

    public Island getIslandByOwnerName(String ownerName) {
        Player owner = Bukkit.getPlayer(ownerName);
        if (owner != null) {
            return getIsland(owner);
        }
        return null;
    }

    public void saveIslands() {
        try (Writer writer = new FileWriter(dataFile)) {
            gson.toJson(islands, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadIslands() {
        if (!dataFile.exists()) {
            return;
        }

        try (Reader reader = new FileReader(dataFile)) {
            Type type = new TypeToken<Map<UUID, Island>>() {}.getType();
            Map<UUID, Island> loadedIslands = gson.fromJson(reader, type);

            for (Island island : loadedIslands.values()) {
                island.setSpawnLocation(island.getSpawnX(), island.getSpawnY(), island.getSpawnZ(), island.getSpawnWorld());
                islands.put(island.getOwner(), island);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}