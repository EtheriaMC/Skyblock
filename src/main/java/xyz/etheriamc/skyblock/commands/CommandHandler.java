package xyz.etheriamc.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.managers.IslandManager;

public class CommandHandler implements CommandExecutor {
    private final IslandManager islandManager;

    public CommandHandler(IslandManager islandManager) {
        this.islandManager = islandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            showHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create":
                return handleCreateCommand(player, args);
            case "home":
                return handleHomeCommand(player);
            case "setspawn":
                return handleSetSpawnCommand(player);
            case "visit":
                return handleVisitCommand(player, args);
            case "invite":
                return handleInviteCommand(player, args);
            case "accept":
                return handleAcceptCommand(player, args);
            case "deny":
                return handleDenyCommand(player, args);
            case "delete":
                return handleDeleteCommand(player, args);
            case "manage":
                return handleManageCommand(player);
            default:
                showHelp(player);
                return true;
        }
    }

    private void showHelp(Player player) {
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

    private boolean handleCreateCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /is create <name>");
            return true;
        }

        String name = args[1];
        boolean success = islandManager.createIsland(player, name);
        if (success) {
            player.sendMessage(ChatColor.GREEN + "Island created successfully!");
        } else {
            player.sendMessage(ChatColor.RED + "You already have an island!");
        }
        return true;
    }

    private boolean handleHomeCommand(Player player) {
        Location spawn = islandManager.getIslandSpawn(player);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(ChatColor.GREEN + "Teleported to your island!");
        } else {
            player.sendMessage(ChatColor.RED + "You do not have an island. Create one with /is create <name>.");
        }
        return true;
    }

    private boolean handleSetSpawnCommand(Player player) {
        Location location = player.getLocation();
        islandManager.setIslandSpawn(player, location);
        player.sendMessage(ChatColor.GREEN + "Island spawn set to your current location.");
        return true;
    }

    private boolean handleVisitCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /is visit <username>");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        Location spawn = islandManager.getIslandSpawn(targetPlayer);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(ChatColor.GREEN + "Teleported to " + targetPlayer.getName() + "'s island.");
        } else {
            player.sendMessage(ChatColor.RED + targetPlayer.getName() + " does not have an island.");
        }
        return true;
    }

    private boolean handleInviteCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /is invite <player>");
            return true;
        }

        Player invitee = Bukkit.getPlayer(args[1]);
        if (invitee == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        boolean success = islandManager.invitePlayer(player, invitee);
        if (success) {
            player.sendMessage(ChatColor.GREEN + "Invitation sent to " + invitee.getName() + ".");
        } else {
            player.sendMessage(ChatColor.RED + "You can't invite this player.");
        }
        return true;
    }

    private boolean handleAcceptCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /is accept <player>");
            return true;
        }

        Player owner = Bukkit.getPlayer(args[1]);
        if (owner == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        boolean success = islandManager.acceptInvitation(player, owner);
        if (success) {
            player.sendMessage(ChatColor.GREEN + "Invitation accepted.");
        } else {
            player.sendMessage(ChatColor.RED + "You don't have an invitation from that player.");
        }
        return true;
    }

    private boolean handleDenyCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /is deny <player>");
            return true;
        }

        Player owner = Bukkit.getPlayer(args[1]);
        if (owner == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        boolean success = islandManager.denyInvitation(player, owner);
        if (success) {
            player.sendMessage(ChatColor.GREEN + "Invitation denied.");
        } else {
            player.sendMessage(ChatColor.RED + "You don't have an invitation from that player.");
        }
        return true;
    }

    private boolean handleDeleteCommand(Player player, String[] args) {
        if (args.length < 2 && !player.hasPermission("skyblock.command.delete.others")) {
            player.sendMessage(ChatColor.RED + "Usage: /is delete <name>");
            return true;
        }

        String targetName = args.length > 1 ? args[1] : player.getName();
        boolean success = islandManager.deleteIsland(targetName);
        if (success) {
            player.sendMessage(ChatColor.GREEN + "Island deleted.");
        } else {
            player.sendMessage(ChatColor.RED + "Failed to delete the island.");
        }
        return true;
    }

    private boolean handleManageCommand(Player player) {
        player.sendMessage(ChatColor.YELLOW + "someone implement this plz lol");
        return true;
    }
}