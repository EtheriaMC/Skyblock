package xyz.etheriamc.skyblock.acf;

import co.aikar.commands.PaperCommandManager;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.commands.island.IslandCommands;

public class ACFResolver {
    public static void registerAll() {
        PaperCommandManager paperCommandManager = Main.getInstance().getPaperCommandManager();

<<<<<<< HEAD
        if (paperCommandManager == null) {
            throw new IllegalStateException("PaperCommandManager is not initialized.");
        }

        paperCommandManager.registerCommand(new IslandCommands());
    }
}
=======
        paperCommandManager.registerCommand(new IslandCommands());
    }
}
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
