package xyz.etheriamc.skyblock;

import io.github.thatkawaiisam.assemble.Assemble;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.etheriamc.skyblock.commands.CommandHandler;
import xyz.etheriamc.skyblock.listeners.EventListener;
import xyz.etheriamc.skyblock.listeners.PlayerJoinListener;
import xyz.etheriamc.skyblock.managers.IslandManager;
import xyz.etheriamc.skyblock.util.ConfigFile;
import xyz.etheriamc.skyblock.util.adapters.BoardAdapter;

import java.util.Random;

public class Main extends JavaPlugin {
    private IslandManager islandManager;
    private World islandWorld;

    private ConfigFile scoreboardFile;

    @Override
    public void onEnable() {
        loadOthers();
        loadFiles();
        createVoidWorld();
        islandManager = new IslandManager(this, islandWorld);

        getCommand("is").setExecutor(new CommandHandler(islandManager));

        getServer().getPluginManager().registerEvents(new EventListener(islandManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    private void loadFiles() {
        saveDefaultConfig();

        this.scoreboardFile = new ConfigFile(this, "scoreboard.yml");
    }

    private void loadOthers() {
        new Assemble(this, new BoardAdapter(this));
    }

    @Override
    public void onDisable() {
        if (islandManager != null) {
            islandManager.saveIslands();
        }
    }

    private void createVoidWorld() {
        WorldCreator worldCreator = new WorldCreator("skyblock_world");
        worldCreator.type(WorldType.FLAT);
        worldCreator.generator(new VoidWorldGenerator());
        islandWorld = Bukkit.createWorld(worldCreator);
    }

    public World getIslandWorld() {
        return islandWorld;
    }

    public IslandManager getIslandManager() {
        return islandManager;
    }

    public ConfigFile getScoreboardFile() {
        return scoreboardFile;
    }

    public class VoidWorldGenerator extends ChunkGenerator {
        @Override
        public byte[] generate(World world, Random random, int x, int z) {
            return new byte[65536];
        }
    }
}