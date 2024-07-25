package xyz.etheriamc.skyblock;

import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.CommandCompletions;
import io.github.thatkawaiisam.assemble.Assemble;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.etheriamc.skyblock.acf.ACFResolver;
import xyz.etheriamc.skyblock.handler.ServerHandler;
import xyz.etheriamc.skyblock.listeners.EventListener;
import xyz.etheriamc.skyblock.listeners.PlayerJoinListener;
import xyz.etheriamc.skyblock.islands.IslandManager;
import xyz.etheriamc.skyblock.util.ConfigFile;
import xyz.etheriamc.skyblock.util.adapters.BoardAdapter;
import xyz.etheriamc.skyblock.warp.Warp;
import xyz.etheriamc.skyblock.warp.commands.DeleteWarpCommand;
import xyz.etheriamc.skyblock.warp.commands.SetWarpCommand;
import xyz.etheriamc.skyblock.warp.commands.WarpCommand;
import xyz.etheriamc.skyblock.warp.commands.WarpsCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class Main extends JavaPlugin {
    @Getter private static Main instance;
    private IslandManager islandManager;
    private World islandWorld;
    private ConfigFile scoreboardFile;
    private PaperCommandManager paperCommandManager;
    private ServerHandler serverHandler;

    @Override
    public void onEnable() {
        instance = this;

        paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.registerCommand(new WarpCommand());
        paperCommandManager.registerCommand(new SetWarpCommand());
        paperCommandManager.registerCommand(new DeleteWarpCommand());
        paperCommandManager.registerCommand(new WarpsCommand());

        paperCommandManager.getCommandCompletions().registerCompletion("warps", c -> {
            List<String> toReturn = new ArrayList<>();
            for (Warp warp : getServerHandler().getWarps()) {
                toReturn.add(warp.getName());
            }
            return toReturn;
        });

        loadOthers();
        loadFiles();
        createVoidWorld();

        serverHandler = new ServerHandler();

        islandManager = new IslandManager(this, islandWorld);

        ACFResolver.registerAll();

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
        if (serverHandler != null) {
            serverHandler.saveWarps();
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

    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public class VoidWorldGenerator extends ChunkGenerator {
        @Override
        public byte[] generate(World world, Random random, int x, int z) {
            return new byte[65536];
        }
    }
}