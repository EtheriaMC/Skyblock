package xyz.etheriamc.skyblock.commands.island;

import co.aikar.commands.BaseCommand;
<<<<<<< HEAD
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
=======
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.managers.IslandManager;
import xyz.etheriamc.skyblock.util.CC;

@CommandAlias("is|island")
public class IslandCommands extends BaseCommand {

<<<<<<< HEAD
    private final IslandManager islandManager;

    public IslandCommands() {
        // Retrieve IslandManager instance from the main plugin
        this.islandManager = Main.getInstance().getIslandManager();

        // Debugging output to verify initialization
        if (this.islandManager == null) {
            Bukkit.getLogger().warning("IslandManager is not available. Check the initialization in the main class.");
        }
    }
=======
    public IslandManager islandManager = Main.getInstance().getIslandManager();
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9

    @Default
    @HelpCommand
    public void showHelp(Player player) {
<<<<<<< HEAD
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
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cIsland name cannot be empty."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &fWhoops! An IslandManager error was found."));
            return;
        }

        player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aCreating your island..."));
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            boolean success = islandManager.createIsland(player, name);
            if (success) {
                player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aIsland created!"));
            } else {
                player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou already have an island!"));
=======
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
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
            }
        });
    }

    @Subcommand("home|go")
<<<<<<< HEAD
    @Description("Teleport to your island home.")
    public void goHomeIsland(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &fWhoops! An IslandManager error was found."));
            return;
        }

        Location spawn = islandManager.getIslandSpawn(player);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aTeleported to your island!"));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou do not have an island. Create one with /is create <name>."));
=======
    public void goHomeIsland(Player player) {
        Location spawn = islandManager.getIslandSpawn(player);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(ChatColor.GREEN + "Teleported to your island!");
        } else {
            throw new InvalidCommandArgument("You do not have an island. Create one with /is create <name>.", false);
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
        }
    }

    @Subcommand("setspawn|setspawnlocation")
<<<<<<< HEAD
    @Description("Set your island's spawn location.")
    public void setSpawn(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &fWhoops! An IslandManager error was found."));
            return;
        }

        if (islandManager.getIsland(player) == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou do not have an island to set a spawn for."));
            return;
        }

        Location location = player.getLocation();
        islandManager.setIslandSpawn(player, location);
        player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aIsland spawn set to your current location."));
    }

    @Subcommand("visit")
    @Description("Visit another player's island.")
    public void visit(Player player, @Name("target") Player targetPlayer) {
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            player.sendMessage(CC.translate("&cPlayer not found or offline."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &fWhoops! An IslandManager error was found."));
            return;
        }

        Location spawn = islandManager.getIslandSpawn(targetPlayer);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aTeleported to " + targetPlayer.getName() + "'s island."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &c" + targetPlayer.getName() + " does not have an island."));
=======
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
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
        }
    }

    @Subcommand("invite")
<<<<<<< HEAD
    @Description("Invite a player to your island.")
    public void invite(Player player, @Name("target") Player targetPlayer) {
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            player.sendMessage(CC.translate("&cPlayer not found or offline."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &fWhoops! An IslandManager error was found."));
            return;
        }

        boolean success = islandManager.invitePlayer(player, targetPlayer);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aInvited " + targetPlayer.getName() + " to your island."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cFailed to invite " + targetPlayer.getName() + " to your island."));
=======
    public void invite(Player player, @Name("target")Player targetPlayer) {
        if(islandManager.invitePlayer(player, targetPlayer)) {
            player.sendMessage(ChatColor.GREEN + "Invited " + targetPlayer.getName() + " to your island.");
        } else {
            throw new InvalidCommandArgument(ChatColor.RED + "Failed to invite " + targetPlayer.getName() + " to your island.", false);
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
        }
    }

    @Subcommand("accept")
<<<<<<< HEAD
    @Description("Accept an island invitation.")
    public void accept(Player player, @Name("owner") Player owner) {
        if (owner == null || !owner.isOnline()) {
            player.sendMessage(CC.translate("&cPlayer not found or offline."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &fWhoops! An IslandManager error was found."));
            return;
        }

        boolean success = islandManager.acceptInvitation(player, owner);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &aYou have joined the island!"));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou have no pending invites from " + owner.getName() + "!"));
=======
    public void accept(Player player, @Name("owner")Player owner) {
        if(islandManager.acceptInvitation(player, owner)) {
            player.sendMessage(ChatColor.GREEN + "You have joined the island!");
        } else {
            throw new InvalidCommandArgument(ChatColor.RED + "You have no pending invites from " + owner.getName() + "!", false);
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
        }
    }

    @Subcommand("deny|decline")
<<<<<<< HEAD
    @Description("Deny an island invitation.")
    public void deny(Player player, @Name("owner") Player owner) {
        if (owner == null || !owner.isOnline()) {
            player.sendMessage(CC.translate("&cPlayer not found or offline."));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &fWhoops! An IslandManager error was found."));
            return;
        }

        boolean success = islandManager.denyInvitation(player, owner);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou have declined the invitation."));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou have no pending invites from " + owner.getName() + "!"));
=======
    public void deny(Player player, @Name("owner")Player owner) {
        if(islandManager.denyInvitation(player, owner)) {
            player.sendMessage(ChatColor.RED + "You have declined the invitation.");
        } else {
            throw new InvalidCommandArgument(ChatColor.RED + "You have no pending invites from " + owner.getName() + "!", false);
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
        }
    }

    @Subcommand("delete")
<<<<<<< HEAD
    @Description("Delete your island.")
    public void delete(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &fWhoops! An IslandManager error was found."));
            return;
        }

        boolean success = islandManager.deleteIsland(player);
        if (success) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cIsland deleted!"));
        } else {
            player.sendMessage(CC.translate("&b&lEtheriaMC &7● &cYou do not have an island to delete!"));
=======
    public void delete(Player player) {
        if(islandManager.deleteIsland(player)) {
            //todo: make a confirmation menu for deletion
            player.sendMessage(ChatColor.RED + "Island deleted!");
        } else {
            throw new InvalidCommandArgument(ChatColor.RED + "You do not have an island to delete!", false);
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
        }
    }

    @Subcommand("manage")
<<<<<<< HEAD
    @Description("Manage your island.")
    public void manage(Player player) {
        player.sendMessage(CC.translate("&b&lEtheriaMC &7● &eComing soon..."));
=======
    public void manage(Player player) {
        //todo: make command
>>>>>>> a30ef7c6041f4d3cb9952c885f3036894d0bbae9
    }
}