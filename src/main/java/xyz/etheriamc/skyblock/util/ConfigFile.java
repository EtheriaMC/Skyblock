package xyz.etheriamc.skyblock.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigFile {
    private final JavaPlugin plugin;
    private final String fileName;
    private FileConfiguration fileConfiguration;
    private File configFile;

    public ConfigFile(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.configFile = new File(plugin.getDataFolder(), fileName);
        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }

        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public FileConfiguration getConfig() {
        return this.fileConfiguration;
    }

    public String getString(String path) {
        return this.fileConfiguration.getString(path);
    }

    public List<String> getStringList(String path) {
        return this.fileConfiguration.getStringList(path);
    }

    public void save() {
        try {
            this.fileConfiguration.save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.configFile);
    }
}