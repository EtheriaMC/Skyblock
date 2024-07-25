package xyz.etheriamc.skyblock.acf;

import co.aikar.commands.PaperCommandManager;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.acf.context.ProfileContextResolver;
import xyz.etheriamc.skyblock.economy.commands.BalanceCommand;
import xyz.etheriamc.skyblock.economy.commands.EconomyCommands;
import xyz.etheriamc.skyblock.economy.commands.PayCommand;
import xyz.etheriamc.skyblock.islands.commands.IslandCommands;
import xyz.etheriamc.skyblock.profile.Profile;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ACFResolver {
    public static void registerAll() {
        PaperCommandManager commandManager = Main.getInstance().getPaperCommandManager();

        if (commandManager == null) {
            throw new IllegalStateException("PaperCommandManager is not initialized.");
        }

        commandManager.getCommandContexts().registerContext(Profile.class, new ProfileContextResolver());
        commandManager.getCommandCompletions().registerCompletion("profiles", context ->
                Main.getInstance().getProfileHandler().getProfiles().stream().map(Profile::getUsername).collect(Collectors.toList()));

        Arrays.asList(
                new IslandCommands(),
                new BalanceCommand(),
                new EconomyCommands(),
                new PayCommand()
        ).forEach(commandManager::registerCommand);
    }
}