package xyz.etheriamc.skyblock.models;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Island {
    private UUID owner;
    private String name;
    private double spawnX, spawnY, spawnZ;
    private String worldName;
    private Set<UUID> members;
    private Set<UUID> invited;

    public Island(UUID owner, String name, Location spawnLocation) {
        this.owner = owner;
        this.name = name;
        this.spawnX = spawnLocation.getX();
        this.spawnY = spawnLocation.getY();
        this.spawnZ = spawnLocation.getZ();
        this.worldName = spawnLocation.getWorld().getName();
        this.members = new HashSet<>();
        this.invited = new HashSet<>();
        members.add(owner);
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public Location getSpawnLocation() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }
        return new Location(world, spawnX, spawnY, spawnZ);
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnX = spawnLocation.getX();
        this.spawnY = spawnLocation.getY();
        this.spawnZ = spawnLocation.getZ();
        this.worldName = spawnLocation.getWorld().getName();
    }

    public void setSpawnLocation(double spawnX, double spawnY, double spawnZ, String spawnWorld) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnZ = spawnZ;
        this.worldName = spawnWorld;
    }

    public double getSpawnX() {
        return spawnX;
    }

    public double getSpawnY() {
        return spawnY;
    }

    public double getSpawnZ() {
        return spawnZ;
    }

    public String getSpawnWorld() {
        return worldName;
    }

    public boolean isMember(Player player) {
        return members.contains(player.getUniqueId());
    }

    public boolean addMember(Player player) {
        return members.add(player.getUniqueId());
    }

    public boolean isInvited(Player player) {
        return invited.contains(player.getUniqueId());
    }

    public boolean addInvite(Player player) {
        return invited.add(player.getUniqueId());
    }

    public boolean removeInvite(Player player) {
        return invited.remove(player.getUniqueId());
    }
}