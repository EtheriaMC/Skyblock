package xyz.etheriamc.skyblock.economy.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.profile.Profile;
import xyz.etheriamc.skyblock.util.CC;

@CommandAlias("eco|econ|economy")
public class EconomyCommands extends BaseCommand {

    @HelpCommand
    @Default
    public void helCommand(Player player) {
        player.sendMessage(CC.translate("&7&m----------------------------------------"));
        player.sendMessage(CC.translate("&c/eco set <name> <balance> &7- Sets the balance of a player."));
        player.sendMessage(CC.translate("&c/eco reset <name> &7- Resets balance of a player."));
        player.sendMessage(CC.translate("&c/eco add <name> &7- Adds to player balance."));
        player.sendMessage(CC.translate("&c/eco remove <name> &7- Removes money from a player balance."));
        player.sendMessage(CC.translate("&7&m----------------------------------------"));
    }

    @Subcommand("set")
    @CommandPermission("etheria.command.ecoset")
    @CommandCompletion("@profiles")
    public void ecoset(Player player, @Name("target")Profile profile, @Name("amount")int amount) {

        if(amount < 0) {
            throw new InvalidCommandArgument("Balances cannot be less than 0!", false);
        }

        profile.setBalance(amount);
        player.sendMessage(CC.translate("&aSuccessfully set " + profile.getUsername() + "'s balance to " + amount));
    }

    @Subcommand("reset")
    @CommandPermission("etheria.command.ecoreset")
    @CommandCompletion("@profiles")
    public void resetBalance(CommandSender sender, @Name("target")Profile profile) {
        String name = (sender instanceof Player) ? sender.getName() : "CONSOLE";
        if(profile.getBalance() == 1000) {
            throw new InvalidCommandArgument(profile.getUsername() + "'s balance is already $1000!", false);
        }

        profile.setBalance(1000);
        sender.sendMessage(CC.translate("&aSuccessfully reset " + profile.getUsername() + "'s balance to $1000!"));
    }

    @Subcommand("give|add")
    @CommandPermission("etheria.command.economy.give")
    @CommandCompletion("@profiles")
    public void give(CommandSender sender, @Name("target")Profile profile, @Name("amount") int amount) {
        String name = (sender instanceof Player) ? sender.getName() : "CONSOLE";
        profile.setBalance(profile.getBalance() + amount);
        sender.sendMessage(CC.translate("&aSuccessfully added &e" + amount + "&f to &e" + profile.getUsername() + "'s balance."));
    }

    @Subcommand("take|remove")
    @CommandPermission("etheria.command.economy.add")
    @CommandCompletion("@profiles")
    public void take(CommandSender sender, @Name("target") Profile profile, @Name("amount") int amount) {
        String name = (sender instanceof Player) ? sender.getName() : "CONSOLE";

        if (amount > profile.getBalance()) {
            throw new InvalidCommandArgument("Cannot remove " + amount + " from " + profile.getUsername() + " as their balance is only " + profile.getBalance() + "!", false);
        }

        profile.setBalance(profile.getBalance() - amount);
        sender.sendMessage(CC.translate("&aSuccessfully removed &e" + amount + "&f from &e" + profile.getUsername() + "."));
    }
}
