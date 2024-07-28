package xyz.etheriamc.skyblock.economy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Optional;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.EtheriaAPI;
import xyz.etheriamc.skyblock.profile.Profile;
import xyz.etheriamc.skyblock.util.CC;

public class BalanceCommand extends BaseCommand {
    @CommandAlias("balance|bal|money")
    @CommandCompletion("@profiles")
    public void balance(Player player, @Name("target") @Optional Profile target) {
        Profile profile = target != null ? target : EtheriaAPI.getProfile(player.getUniqueId());

        if (target == null) {
            player.sendMessage(CC.translate("&e&lBalance&f: &2$&a" + profile.getBalance()));
        } else {
            String displayName = profile.getUsername() + (profile.getUsername().endsWith("s") ? "'" : "'s");
            player.sendMessage(CC.translate(displayName + " &e&lBalance&f: &2$&a" + profile.getBalance()));
        }
    }
}
