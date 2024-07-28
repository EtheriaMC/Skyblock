package xyz.etheriamc.skyblock.tokens.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Optional;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.EtheriaAPI;
import xyz.etheriamc.skyblock.profile.Profile;

public class TokenCommand {
    @CommandAlias("tokens")
    public void tokens(Player player, @Name("player") @Optional Profile target) {

        Profile profile = EtheriaAPI.getProfile(target == null ? player.getUniqueId() : target.getUuid());

        if(target == null) {
            player.sendMessage("&aYou have &e" + profile.getTokens() + " &atokens.");
        }else {
            player.sendMessage("&a" + target.getUsername() + " has &e" + profile.getTokens() + " &atokens.");
        }
    }
}
