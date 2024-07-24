package xyz.etheriamc.skyblock.commands;

import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.commands.island.IslandCommands;

public class ACFResolver {
    public static void registerAll() {
        Main.getInstance().getPaperCommandManager().enableUnstableAPI("help");

        //todo: register formatting stuff

        Main.getInstance().getPaperCommandManager().registerCommand(new IslandCommands());
    }
}