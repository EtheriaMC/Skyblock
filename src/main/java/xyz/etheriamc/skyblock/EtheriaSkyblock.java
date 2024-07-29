package xyz.etheriamc.skyblock;

import co.aikar.commands.PaperCommandManager;
import com.mongodb.MongoException;
import io.github.thatkawaiisam.assemble.Assemble;
import lombok.Getter;
import me.vifez.core.Core;
import me.vifez.core.rank.Rank;
import me.vifez.core.rank.RankHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.etheriamc.skyblock.acf.ACFResolver;
import xyz.etheriamc.skyblock.database.mongo.MongoHandler;
import xyz.etheriamc.skyblock.economy.handler.EcoHandler;
import xyz.etheriamc.skyblock.handler.ServerHandler;
import xyz.etheriamc.skyblock.listeners.EventListener;
import xyz.etheriamc.skyblock.listeners.PlayerJoinListener;
import xyz.etheriamc.skyblock.islands.IslandManager;
import xyz.etheriamc.skyblock.pet.commands.PetCommand;
import xyz.etheriamc.skyblock.pet.commands.PetShopCommand;
import xyz.etheriamc.skyblock.pet.listener.UsePetListener;
import xyz.etheriamc.skyblock.pet.petshop.menu.listener.PurchasePetListener;
import xyz.etheriamc.skyblock.profile.ProfileHandler;
import xyz.etheriamc.skyblock.shop.command.ShopCommand;
import xyz.etheriamc.skyblock.shop.listener.PurchaseListener;
import xyz.etheriamc.skyblock.shop.listener.SellListener;
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
public class EtheriaSkyblock extends JavaPlugin {
    @Getter
    public static EtheriaSkyblock instance;
    public IslandManager islandManager;
    public World islandWorld;
    public ConfigFile scoreboardFile, databaseFile;
    public PaperCommandManager paperCommandManager;
    public ServerHandler serverHandler;
    public MongoHandler mongoHandler;
    public ProfileHandler profileHandler;
    public Rank rankManager;

    @Override
    public void onEnable() {
        instance = this;
        loadFiles();

        mongoHandler = new MongoHandler();
        try {
            mongoHandler.setupMongoDB(databaseFile.getString("mongo.uri"));
        } catch (MongoException e) {
            e.printStackTrace();
        }

        paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.registerCommand(new WarpCommand());
        paperCommandManager.registerCommand(new SetWarpCommand());
        paperCommandManager.registerCommand(new DeleteWarpCommand());
        paperCommandManager.registerCommand(new WarpsCommand());
        paperCommandManager.registerCommand(new ShopCommand());
        paperCommandManager.registerCommand(new PetCommand());
        paperCommandManager.registerCommand(new PetShopCommand());

        paperCommandManager.getCommandCompletions().registerCompletion("warps", c -> {
            List<String> toReturn = new ArrayList<>();
            for (Warp warp : getServerHandler().getWarps()) {
                toReturn.add(warp.getName());
            }
            return toReturn;
        });

        loadOthers();
        createVoidWorld();

        serverHandler = new ServerHandler();
        islandManager = new IslandManager(this, islandWorld);
        profileHandler = new ProfileHandler();
        rankManager = fetchRankManager();  // Fetch the RankHandler from Core

        ACFResolver.registerAll();
        registerEconomy();
        getServer().getPluginManager().registerEvents(new EventListener(islandManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PurchaseListener(), this);
        getServer().getPluginManager().registerEvents(new SellListener(), this);
        getServer().getPluginManager().registerEvents(new PurchasePetListener(), this);
        getServer().getPluginManager().registerEvents(new UsePetListener(), this);
    }

    private void loadFiles() {
        saveDefaultConfig();
        this.databaseFile = new ConfigFile(this, "database.yml");
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

        mongoHandler.disconnect();
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

    public Economy getEconomy() {
        return getServer().getServicesManager().getRegistration(Economy.class).getProvider();
    }

    public class VoidWorldGenerator extends ChunkGenerator {
        @Override
        public byte[] generate(World world, Random random, int x, int z) {
            return new byte[65536];
        }
    }

    public void registerEconomy() {
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) {
            getServer().getServicesManager().register(
                    Economy.class,
                    new EcoHandler(),
                    this,
                    ServicePriority.Highest
            );
        }
    }

    private Rank fetchRankManager() {
        if (Bukkit.getPluginManager().isPluginEnabled("Core")) {
            Core corePlugin = (Core) Bukkit.getPluginManager().getPlugin("Core");
            if (corePlugin != null) {
                RankHandler rankHandler = corePlugin.getRankHandler();
                if (rankHandler != null) {
                    return rankHandler.getDefaultRank();
                } else {
                    getLogger().warning("[Skyblock] RankHandler is null");
                }
            } else {
                getLogger().warning("[Skyblock] Glacial instance is null");
            }
        } else {
            getLogger().warning("[Skyblock] Unable to adapt to Glacial");
        }
        return null;
    }
}