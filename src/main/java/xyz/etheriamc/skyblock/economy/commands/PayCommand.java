package xyz.etheriamc.skyblock.economy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Name;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.api.EtheriaAPI;
import xyz.etheriamc.skyblock.profile.Profile;
import xyz.etheriamc.skyblock.util.CC;

public class PayCommand extends BaseCommand {
    @CommandAlias("pay")
    @CommandCompletion("@profiles")
    public void pay(Player player, @Name("target") Profile target, @Name("amount") Integer amount) {
        Profile profile = EtheriaAPI.getProfile(player.getUniqueId());
        if (profile.equals(target)) {
            throw new InvalidCommandArgument("You cannot pay yourself.");
        }

        if (amount <= 0) {
            throw new InvalidCommandArgument("You cannot pay 0 or a negative amount.", false);
        }

        if (profile.getBalance() < amount) {
            throw new InvalidCommandArgument("You do not have enough money to pay " + target.getUsername() + " $" + amount + ".", false);
        }

        profile.setBalance(profile.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);

        String playerDisplayName = profile.getUsername();
        String targetDisplayName = target.getUsername();

        player.sendMessage(CC.translate("&eYou have paid &a" + targetDisplayName + " &2$&a" + amount + "&e."));
        Player targetPlayer = Bukkit.getPlayer(target.getUuid());
        if (targetPlayer != null) {
            targetPlayer.sendMessage(CC.translate("&a" + playerDisplayName + " &ehas paid you &2$&a" + amount + "&e."));
        }

        profile.save();
        target.save();
    }
}
