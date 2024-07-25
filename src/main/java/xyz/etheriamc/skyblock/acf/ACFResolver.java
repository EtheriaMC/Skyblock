package xyz.etheriamc.skyblock.acf;

import co.aikar.commands.PaperCommandManager;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.commands.island.IslandCommands;

public class ACFResolver {
    public static void registerAll() {
        PaperCommandManager paperCommandManager = Main.getInstance().getPaperCommandManager();

        if (paperCommandManager == null) {
            throw new IllegalStateException("PaperCommandManager is not initialized.");
        }

        paperCommandManager.registerCommand(new IslandCommands());
    }
}