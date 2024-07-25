package xyz.etheriamc.skyblock.warp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.warp.Warp;
import xyz.etheriamc.skyblock.util.CC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandAlias("warp")
@CommandPermission("skyblock.warp")
public class WarpCommand extends BaseCommand {

    @Default
    @Description("Teleport to the specified warp.")
    public void warp(CommandSender sender, @Name("warp") String warpName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cThis command can only be used by players."));
            return;
        }

        Player player = (Player) sender;
        Warp warp = Main.getInstance().getServerHandler().getWarp(warpName);
        if (warp == null) {
            sender.sendMessage(CC.translate("&b&lEtheriaMC &f● &b" + warpName + " &cwarp doesn't exist."));
            return;
        }
        player.teleport(warp.getLocation());
        sender.sendMessage(CC.translate("&b&lEtheriaMC &f● &eTeleported you to warp " + "&f" + warp.getName() + "&e."));
    }

    public List<String> onTabComplete(CommandSender sender, String alias, String[] args) {
        if (args.length == 1) {
            List<String> toReturn = new ArrayList<>();
            for (Warp warp : Main.getInstance().getServerHandler().getWarps()) {
                toReturn.add(warp.getName());
            }
            return toReturn;
        }
        return Collections.emptyList();
    }

    @HelpCommand
    public void help(CommandSender sender) {
        sender.sendMessage(CC.translate("&7&m----------------------------------------"));
        sender.sendMessage(CC.translate("&c/warp <warp> &7- Teleport to a warp"));
        sender.sendMessage(CC.translate("&c/setwarp <warp> &7- Create a warp"));
        sender.sendMessage(CC.translate("&c/delwarp <warp> &7- Delete a warp"));
        sender.sendMessage(CC.translate("&c/warps &7- List of available warps"));
        sender.sendMessage(CC.translate("&7&m----------------------------------------"));
    }
}