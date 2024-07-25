package xyz.etheriamc.skyblock.warp;

import xyz.etheriamc.skyblock.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;

public class Warp {

    private final String name;
    private final Location location;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public void save() {
        FileConfiguration config = Main.getInstance().getConfig();
        ConfigurationSection warpsSection = config.getConfigurationSection("warps");

        if (warpsSection == null) {
            warpsSection = config.createSection("warps");
        }

        ConfigurationSection warpSection = warpsSection.createSection(name);
        if (warpSection != null) {
            warpSection.set("world", location.getWorld() != null ? location.getWorld().getName() : "world"); // Check for null world
            warpSection.set("x", location.getX());
            warpSection.set("y", location.getY());
            warpSection.set("z", location.getZ());
            warpSection.set("yaw", location.getYaw());
            warpSection.set("pitch", location.getPitch());

            Main.getInstance().saveConfig();
        }
    }

    public void delete() {
        FileConfiguration config = Main.getInstance().getConfig();
        ConfigurationSection warpsSection = config.getConfigurationSection("warps");

        if (warpsSection != null) {
            warpsSection.set(name, null);
            Main.getInstance().saveConfig();
        }
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public static Warp load(String name) {
        FileConfiguration config = Main.getInstance().getConfig();
        ConfigurationSection warpsSection = config.getConfigurationSection("warps");

        if (warpsSection == null) {
            return null;
        }

        ConfigurationSection warpSection = warpsSection.getConfigurationSection(name);

        if (warpSection == null) {
            return null;
        }

        String worldName = warpSection.getString("world");
        double x = warpSection.getDouble("x");
        double y = warpSection.getDouble("y");
        double z = warpSection.getDouble("z");
        float yaw = (float) warpSection.getDouble("yaw");
        float pitch = (float) warpSection.getDouble("pitch");

        if (worldName != null) {
            Location location = new Location(Main.getInstance().getServer().getWorld(worldName), x, y, z, yaw, pitch);
            return new Warp(name, location);
        }
        return null;
    }
}