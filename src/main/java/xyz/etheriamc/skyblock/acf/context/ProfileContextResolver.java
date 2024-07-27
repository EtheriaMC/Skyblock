package xyz.etheriamc.skyblock.acf.context;

import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.contexts.ContextResolver;
import xyz.etheriamc.skyblock.api.EtheriaAPI;
import xyz.etheriamc.skyblock.profile.Profile;

public class ProfileContextResolver implements ContextResolver<Profile, BukkitCommandExecutionContext> {

    @Override
    public Profile getContext(BukkitCommandExecutionContext bukkitCommandExecutionContext) throws InvalidCommandArgument {
        String firstArg = bukkitCommandExecutionContext.popFirstArg();
        Profile profile = EtheriaAPI.getProfile(firstArg);

        if(profile == null) {
            throw new InvalidCommandArgument("No profile with the name \"" + firstArg + "\" found.", false);
        }else {
            return profile;
        }
    }
}
