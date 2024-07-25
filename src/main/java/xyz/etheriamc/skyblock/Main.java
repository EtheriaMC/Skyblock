package xyz.etheriamc.skyblock;

import co.aikar.commands.PaperCommandManager;
import io.github.thatkawaiisam.assemble.Assemble;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.etheriamc.skyblock.acf.ACFResolver;
import xyz.etheriamc.skyblock.commands.island.IslandCommands;
import xyz.etheriamc.skyblock.listeners.EventListener;
import xyz.etheriamc.skyblock.listeners.PlayerJoinListener;
import xyz.etheriamc.skyblock.managers.IslandManager;
import xyz.etheriamc.skyblock.util.ConfigFile;
import xyz.etheriamc.skyblock.util.adapters.BoardAdapter;

import java.util.Random;

@Getter
public class Main extends JavaPlugin {
    @Getter private static Main instance;
    private IslandManager islandManager;
    private World islandWorld;
    private ConfigFile scoreboardFile;
    public PaperCommandManager paperCommandManager;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize PaperCommandManager
        paperCommandManager = new PaperCommandManager(this);

        // Load other necessary components
        loadOthers();
        loadFiles();
        createVoidWorld();

        // Initialize IslandManager
        islandManager = new IslandManager(this, islandWorld);

        // Register commands
        ACFResolver.registerAll();

        // Register events
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
        instance = null;
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

    public PaperCommandManager getPaperCommandManager() {
        return paperCommandManager;
    }

    public class VoidWorldGenerator extends ChunkGenerator {
        @Override
        public byte[] generate(World world, Random random, int x, int z) {
            return new byte[65536];
        }
    }
}