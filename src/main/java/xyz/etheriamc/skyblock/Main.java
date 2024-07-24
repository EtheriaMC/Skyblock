package xyz.etheriamc.skyblock;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.etheriamc.skyblock.commands.CommandHandler;
import xyz.etheriamc.skyblock.listeners.EventListener;
import xyz.etheriamc.skyblock.managers.IslandManager;

import java.util.Random;

public class Main extends JavaPlugin {
    private IslandManager islandManager;
    private World islandWorld;

    @Override
    public void onEnable() {
        createVoidWorld();
        islandManager = new IslandManager(this, islandWorld);

        getCommand("is").setExecutor(new CommandHandler(islandManager));

        getServer().getPluginManager().registerEvents(new EventListener(islandManager), this);
    }

    @Override
    public void onDisable() {
        islandManager.saveIslands();
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

    public class VoidWorldGenerator extends ChunkGenerator {
        @Override
        public byte[] generate(World world, Random random, int x, int z) {
            return new byte[65536];
        }
    }
}