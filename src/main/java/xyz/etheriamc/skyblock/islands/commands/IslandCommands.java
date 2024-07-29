package xyz.etheriamc.skyblock.islands.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.EtheriaSkyblock;
import xyz.etheriamc.skyblock.islands.IslandManager;
import xyz.etheriamc.skyblock.util.CC;
import xyz.etheriamc.skyblock.util.ConfigFile;

@CommandAlias("is|island")
public class IslandCommands extends BaseCommand {

    private final IslandManager islandManager;
    private final ConfigFile messagesConfig;

    public IslandCommands() {
        this.islandManager = EtheriaSkyblock.getInstance().getIslandManager();
        this.messagesConfig = new ConfigFile(EtheriaSkyblock.getInstance(), "messages.yml");

        if (this.islandManager == null) {
            Bukkit.getLogger().warning("IslandManager is not available. Check the initialization in the main class.");
        }
    }

    @Default
    @HelpCommand
    public void showHelp(Player player) {
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.header")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.create")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.delete")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.manage")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.home")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.setspawn")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.visit")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.invite")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.accept")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.deny")));
        player.sendMessage(CC.translate(messagesConfig.getString("messages.help.footer")));
    }

    @Subcommand("create")
    @Description("Create a new island with a specified name.")
    public void createIsland(Player player, @Name("name") String name) {
        if (name == null || name.trim().isEmpty()) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_name_empty")));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_manager_error")));
            return;
        }

        player.sendMessage(CC.translate(messagesConfig.getString("messages.success.creating_island")));
        Bukkit.getScheduler().runTask(EtheriaSkyblock.getInstance(), () -> {
            boolean success = islandManager.createIsland(player, name);
            if (success) {
                player.sendMessage(CC.translate(messagesConfig.getString("messages.success.island_created")));
            } else {
                player.sendMessage(CC.translate(messagesConfig.getString("messages.success.already_have_island")));
            }
        });
    }

    @Subcommand("home|go")
    @Description("Teleport to your island home.")
    public void goHomeIsland(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_manager_error")));
            return;
        }

        Location spawn = islandManager.getIslandSpawn(player);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(CC.translate(messagesConfig.getString("messages.success.teleported_to_island")));
        } else {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.no_island")));
        }
    }

    @Subcommand("setspawn|setspawnlocation")
    @Description("Set your island's spawn location.")
    public void setSpawn(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_manager_error")));
            return;
        }

        if (islandManager.getIsland(player) == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.no_island_to_set_spawn")));
            return;
        }

        Location location = player.getLocation();
        islandManager.setIslandSpawn(player, location);
        player.sendMessage(CC.translate(messagesConfig.getString("messages.success.island_spawn_set")));
    }

    @Subcommand("visit")
    @Description("Visit another player's island.")
    public void visit(Player player, @Name("target") Player targetPlayer) {
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.player_not_found")));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_manager_error")));
            return;
        }

        Location spawn = islandManager.getIslandSpawn(targetPlayer);
        if (spawn != null) {
            player.teleport(spawn);
            player.sendMessage(CC.translate(messagesConfig.getString("messages.success.teleported_to_target_island").replace("{target}", targetPlayer.getName())));
        } else {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.no_island")));
        }
    }

    @Subcommand("invite")
    @Description("Invite a player to your island.")
    public void invite(Player player, @Name("target") Player targetPlayer) {
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.player_not_found")));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_manager_error")));
            return;
        }

        boolean success = islandManager.invitePlayer(player, targetPlayer);
        if (success) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.success.invited_player").replace("{target}", targetPlayer.getName())));
        } else {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.success.failed_to_invite").replace("{target}", targetPlayer.getName())));
        }
    }

    @Subcommand("accept")
    @Description("Accept an island invitation.")
    public void accept(Player player, @Name("owner") Player owner) {
        if (owner == null || !owner.isOnline()) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.player_not_found")));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_manager_error")));
            return;
        }

        boolean success = islandManager.acceptInvitation(player, owner);
        if (success) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.success.joined_island")));
        } else {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.no_pending_invites").replace("{owner}", owner.getName())));
        }
    }

    @Subcommand("deny|decline")
    @Description("Deny an island invitation.")
    public void deny(Player player, @Name("owner") Player owner) {
        if (owner == null || !owner.isOnline()) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.player_not_found")));
            return;
        }

        if (islandManager == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_manager_error")));
            return;
        }

        boolean success = islandManager.denyInvitation(player, owner);
        if (success) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.success.declined_invitation")));
        } else {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.no_pending_invites").replace("{owner}", owner.getName())));
        }
    }

    @Subcommand("delete")
    @Description("Delete your island.")
    public void delete(Player player) {
        if (islandManager == null) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.island_manager_error")));
            return;
        }

        boolean success = islandManager.deleteIsland(player);
        if (success) {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.success.island_deleted")));
        } else {
            player.sendMessage(CC.translate(messagesConfig.getString("messages.errors.no_island_to_delete")));
        }
    }

    @Subcommand("manage")
    @Description("Manage your island.")
    public void manage(Player player) {
        player.sendMessage(CC.translate(messagesConfig.getString("messages.coming_soon")));
    }
}