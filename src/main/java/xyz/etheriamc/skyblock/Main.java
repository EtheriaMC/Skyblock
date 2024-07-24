package xyz.etheriamc.skyblock;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.etheriamc.skyblock.commands.ACFResolver;
import xyz.etheriamc.skyblock.listeners.EventListener;
import xyz.etheriamc.skyblock.managers.IslandManager;

import java.util.Random;

@Getter
public class Main extends JavaPlugin {
    @Getter private static Main instance;
    private IslandManager islandManager;
    private World islandWorld;
    private PaperCommandManager paperCommandManager;

    @Override
    public void onEnable() {
        instance = this;
        createVoidWorld();
        islandManager = new IslandManager(this, islandWorld);
        paperCommandManager = new PaperCommandManager(this);
        ACFResolver.registerAll();

        getServer().getPluginManager().registerEvents(new EventListener(islandManager), this);
    }

    @Override
    public void onDisable() {
        instance = null;
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