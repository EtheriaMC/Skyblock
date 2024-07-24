package xyz.etheriamc.skyblock.commands.island;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.managers.IslandManager;
import xyz.etheriamc.skyblock.util.CC;

@CommandAlias("is|island")
public class IslandCommands extends BaseCommand {

    public IslandManager islandManager = Main.getInstance().getIslandManager();

    @Default
    @HelpCommand
    public void showHelp(Player player) {
        player.sendMessage(ChatColor.YELLOW + "Skyblock commands");
        player.sendMessage(ChatColor.GOLD + "/is create <name>" + ChatColor.WHITE + " - Create a new island.");
        player.sendMessage(ChatColor.GOLD + "/is home" + ChatColor.WHITE + " - Teleport to your island home.");
        player.sendMessage(ChatColor.GOLD + "/is setspawn" + ChatColor.WHITE + " - Set your island's spawn location.");
        player.sendMessage(ChatColor.GOLD + "/is visit <player>" + ChatColor.WHITE + " - Visit another player's island.");
        player.sendMessage(ChatColor.GOLD + "/is invite <player>" + ChatColor.WHITE + " - Invite a player to your island.");
        player.sendMessage(ChatColor.GOLD + "/is accept <player>" + ChatColor.WHITE + " - Accept an island invitation.");
        player.sendMessage(ChatColor.GOLD + "/is deny <player>" + ChatColor.WHITE + " - Deny an island invitation.");
        player.sendMessage(ChatColor.GOLD + "/is delete <name>" + ChatColor.WHITE + " - Delete an island.");
        player.sendMessage(ChatColor.GOLD + "/is manage" + ChatColor.WHITE + " - Manage your island.");
    }

    @Subcommand("create")
    public void createIsland(Player player, @Name("name") String name) {
        player.sendMessage(CC.translate("Creating your island..."));
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            if (islandManager.createIsland(player, name)) {
                player.sendMessage(CC.translate("Island created!"));
            } else {
                player.sendMessage(CC.translate("&cYou already have an island!"));
            }
        });
    }

    @Subcommand("home|go")
    public void goHomeIsland(Player player) {
        Location spawn = islandManager.getIslandSpawn(player);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(ChatColor.GREEN + "Teleported to your island!");
        } else {
            throw new InvalidCommandArgument("You do not have an island. Create one with /is create <name>.", false);
        }
    }

    @Subcommand("setspawn|setspawnlocation")
    public void setSpawn(Player player) {
        Location location = player.getLocation();
        islandManager.setIslandSpawn(player, location);
        player.sendMessage(ChatColor.GREEN + "Island spawn set to your current location.");
    }

    @Subcommand("visit")
    public void visit(Player player, @Name("target")Player targetPlayer) {
        Location spawn = islandManager.getIslandSpawn(targetPlayer);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(ChatColor.GREEN + "Teleported to " + targetPlayer.getName() + "'s island.");
        } else {
            player.sendMessage(ChatColor.RED + targetPlayer.getName() + " does not have an island.");
        }
    }

    @Subcommand("invite")
    public void invite(Player player, @Name("target")Player targetPlayer) {
        if(islandManager.invitePlayer(player, targetPlayer)) {
            player.sendMessage(ChatColor.GREEN + "Invited " + targetPlayer.getName() + " to your island.");
        } else {
            throw new InvalidCommandArgument(ChatColor.RED + "Failed to invite " + targetPlayer.getName() + " to your island.", false);
        }
    }

    @Subcommand("accept")
    public void accept(Player player, @Name("owner")Player owner) {
        if(islandManager.acceptInvitation(player, owner)) {
            player.sendMessage(ChatColor.GREEN + "You have joined the island!");
        } else {
            throw new InvalidCommandArgument(ChatColor.RED + "You have no pending invites from " + owner.getName() + "!", false);
        }
    }

    @Subcommand("deny|decline")
    public void deny(Player player, @Name("owner")Player owner) {
        if(islandManager.denyInvitation(player, owner)) {
            player.sendMessage(ChatColor.RED + "You have declined the invitation.");
        } else {
            throw new InvalidCommandArgument(ChatColor.RED + "You have no pending invites from " + owner.getName() + "!", false);
        }
    }

    @Subcommand("delete")
    public void delete(Player player) {
        if(islandManager.deleteIsland(player)) {
            //todo: make a confirmation menu for deletion
            player.sendMessage(ChatColor.RED + "Island deleted!");
        } else {
            throw new InvalidCommandArgument(ChatColor.RED + "You do not have an island to delete!", false);
        }
    }

    @Subcommand("manage")
    public void manage(Player player) {
        //todo: make command
    }
}
