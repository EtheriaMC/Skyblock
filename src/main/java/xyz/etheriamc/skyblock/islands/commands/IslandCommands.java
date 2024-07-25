package xyz.etheriamc.skyblock.islands.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.islands.IslandManager;
import xyz.etheriamc.skyblock.util.CC;

@CommandAlias("is|island")
public class IslandCommands extends BaseCommand {

    private final IslandManager islandManager;

    public IslandCommands() {
        this.islandManager = Main.getInstance().getIslandManager();

        if (this.islandManager == null) {
            Bukkit.getLogger().warning("IslandManager is not available. Check the initialization in the main class.");
        }
    }

    @Default
    @HelpCommand
    public void showHelp(Player player) {
        player.sendMessage(CC.translate("&7&m----------------------------------------"));
        player.sendMessage(CC.translate("&c/is create <name> &7- Create a new island."));
        player.sendMessage(CC.translate("&c/is delete <name> &7- Delete your island."));
        player.sendMessage(CC.translate("&c/is manage &7- Manage your island."));
        player.sendMessage(CC.translate("&c/is home &7- Teleport to your island home."));
        player.sendMessage(CC.translate("&c/is setspawn &7- Set your island's spawn location."));
        player.sendMessage(CC.translate("&c/is visit <player> &7- Visit another player's island."));
        player.sendMessage(CC.translate("&c/is invite <player> &7- Invite a player to your island."));
        player.sendMessage(CC.translate("&c/is accept <player> &7- Accept an island invitation."));
        player.sendMessage(CC.translate("&c/is deny <player> &7- Deny an island invitation."));
        player.sendMessage(CC.translate("&7&m----------------------------------------"));
    }

    @Subcommand("create")
    @Description("Create a new island with a specified name.")
    public void createIsland(Player player, @Name("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cIsland name cannot be empty."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fWhoops! An IslandManager error was found."));
            return;
        }

        player.sendMessage(CC.translate("&b&lEtheriaMC &f● &aCreating your island..."));
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            boolean success = islandManager.createIsland(player, name);
            if (success) {
                player.sendMessage(CC.translate("&b&lEtheriaMC &f● &aIsland created!"));
            } else {
                player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cYou already have an island!"));
            }
        });
    }

    @Subcommand("home|go")
    @Description("Teleport to your island home.")
    public void goHomeIsland(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fWhoops! An IslandManager error was found."));
            return;
        }

        Location spawn = islandManager.getIslandSpawn(player);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &aTeleported to your island!"));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cYou do not have an island. Create one with /is create <name>."));
        }
    }

    @Subcommand("setspawn|setspawnlocation")
    @Description("Set your island's spawn location.")
    public void setSpawn(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fWhoops! An IslandManager error was found."));
            return;
        }

        if (islandManager.getIsland(player) == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cYou do not have an island to set a spawn for."));
            return;
        }

        Location location = player.getLocation();
        islandManager.setIslandSpawn(player, location);
        player.sendMessage(CC.translate("&b&lEtheriaMC &f● &aIsland spawn set to your current location."));
    }

    @Subcommand("visit")
    @Description("Visit another player's island.")
    public void visit(Player player, @Name("target") Player targetPlayer) {
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            player.sendMessage(CC.translate("&cPlayer not found or offline."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fWhoops! An IslandManager error was found."));
            return;
        }

        Location spawn = islandManager.getIslandSpawn(targetPlayer);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &aTeleported to " + targetPlayer.getName() + "'s island."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &c" + targetPlayer.getName() + " does not have an island."));
        }
    }

    @Subcommand("invite")
    @Description("Invite a player to your island.")
    public void invite(Player player, @Name("target") Player targetPlayer) {
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            player.sendMessage(CC.translate("&cPlayer not found or offline."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fWhoops! An IslandManager error was found."));
            return;
        }

        boolean success = islandManager.invitePlayer(player, targetPlayer);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &aInvited " + targetPlayer.getName() + " to your island."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cFailed to invite " + targetPlayer.getName() + " to your island."));
        }
    }

    @Subcommand("accept")
    @Description("Accept an island invitation.")
    public void accept(Player player, @Name("owner") Player owner) {
        if (owner == null || !owner.isOnline()) {
            player.sendMessage(CC.translate("&cPlayer not found or offline."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fWhoops! An IslandManager error was found."));
            return;
        }

        boolean success = islandManager.acceptInvitation(player, owner);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &aYou have joined the island!"));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cYou have no pending invites from " + owner.getName() + "!"));
        }
    }

    @Subcommand("deny|decline")
    @Description("Deny an island invitation.")
    public void deny(Player player, @Name("owner") Player owner) {
        if (owner == null || !owner.isOnline()) {
            player.sendMessage(CC.translate("&cPlayer not found or offline."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fWhoops! An IslandManager error was found."));
            return;
        }

        boolean success = islandManager.denyInvitation(player, owner);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cYou have declined the invitation."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cYou have no pending invites from " + owner.getName() + "!"));
        }
    }

    @Subcommand("delete")
    @Description("Delete your island.")
    public void delete(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fWhoops! An IslandManager error was found."));
            return;
        }

        boolean success = islandManager.deleteIsland(player);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cIsland deleted!"));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cYou do not have an island to delete!"));
        }
    }

    @Subcommand("manage")
    @Description("Manage your island.")
    public void manage(Player player) {
        player.sendMessage(CC.translate("&b&lEtheriaMC &f● &eComing soon..."));
    }
}