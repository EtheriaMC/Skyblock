package xyz.etheriamc.skyblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.islands.IslandManager;
import xyz.etheriamc.skyblock.util.CC;

public class CommandHandler implements CommandExecutor {
    private final IslandManager islandManager;

    public CommandHandler(IslandManager islandManager) {
        this.islandManager = islandManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cOnly players can use this command."));
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
        player.sendMessage(CC.translate("&3&lSkyblock commands"));
        player.sendMessage(CC.translate("&b/is create <name> &7- &fCreate a new island."));
        player.sendMessage(CC.translate("&b/is home &7- &fTeleport to your island home."));
        player.sendMessage(CC.translate("&b/is setspawn &7- &fSet your island's spawn location."));
        player.sendMessage(CC.translate("&b/is visit <player> &7- &fVisit another player's island."));
        player.sendMessage(CC.translate("&b/is invite <player> &7- &fInvite a player to your island."));
        player.sendMessage(CC.translate("&b/is accept <player> &7- &fAccept an island invitation."));
        player.sendMessage(CC.translate("&b/is deny <player> &7- &fDeny an island invitation."));
        player.sendMessage(CC.translate("&b/is delete <name> &7- &fDelete an island."));
        player.sendMessage(CC.translate("&b/is manage &7- &fManage your island."));
    }

    private boolean handleCreateCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /is create <name>"));
            return true;
        }

        String name = args[1];
        if (name == null || name.trim().isEmpty()) {
            player.sendMessage(CC.translate("&cIsland name cannot be empty."));
            return true;
        }

        boolean success = islandManager.createIsland(player, name);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aIsland created successfully!"));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou already have an island or failed to create it."));
        }
        return true;
    }

    private boolean handleHomeCommand(Player player) {
        Location spawn = islandManager.getIslandSpawn(player);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aTeleported to your island!"));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou do not have an island. Create one with /is create <name>."));
        }
        return true;
    }

    private boolean handleSetSpawnCommand(Player player) {
        if (islandManager.getIsland(player) == null) {
            player.sendMessage(CC.translate("&cYou do not have an island to set a spawn for."));
            return true;
        }

        Location location = player.getLocation();
        islandManager.setIslandSpawn(player, location);
        player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aIsland spawn set to your current location."));
        return true;
    }

    private boolean handleVisitCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /is visit <username>"));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return true;
        }

        Location spawn = islandManager.getIslandSpawn(targetPlayer);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aTeleported to " + targetPlayer.getName() + "'s island."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &c" + targetPlayer.getName() + " does not have an island."));
        }
        return true;
    }

    private boolean handleInviteCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /is invite <player>"));
            return true;
        }

        Player invitee = Bukkit.getPlayer(args[1]);
        if (invitee == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return true;
        }

        boolean success = islandManager.invitePlayer(player, invitee);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aInvitation sent to " + invitee.getName() + "."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cFailed to send the invitation."));
        }
        return true;
    }

    private boolean handleAcceptCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /is accept <player>"));
            return true;
        }

        Player owner = Bukkit.getPlayer(args[1]);
        if (owner == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return true;
        }

        boolean success = islandManager.acceptInvitation(player, owner);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aInvitation accepted."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou don't have an invitation from that player."));
        }
        return true;
    }

    private boolean handleDenyCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(CC.translate("&cUsage: /is deny <player>"));
            return true;
        }

        Player owner = Bukkit.getPlayer(args[1]);
        if (owner == null) {
            player.sendMessage(CC.translate("&cPlayer not found."));
            return true;
        }

        boolean success = islandManager.denyInvitation(player, owner);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aInvitation denied."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou don't have an invitation from that player."));
        }
        return true;
    }

    private boolean handleDeleteCommand(Player player, String[] args) {
        if (args.length < 2 && !player.hasPermission("skyblock.command.delete.others")) {
            player.sendMessage(CC.translate("&cUsage: /is delete <name>"));
            return true;
        }

        String targetName = args.length > 1 ? args[1] : player.getName();
        boolean success = islandManager.deleteIsland(targetName);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aIsland deleted."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cFailed to delete the island or island not found."));
        }
        return true;
    }

    private boolean handleManageCommand(Player player) {
        if (!player.hasPermission("skyblock.command.manage")) {
            player.sendMessage(CC.translate("&cYou do not have permission to manage islands."));
            return true;
        }
        player.sendMessage(CC.translate("&b&lEtheriaMC &7● &eComing soon..."));
        return true;
    }
}