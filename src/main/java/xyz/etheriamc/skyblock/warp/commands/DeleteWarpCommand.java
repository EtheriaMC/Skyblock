package xyz.etheriamc.skyblock.warp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.warp.Warp;
import xyz.etheriamc.skyblock.util.CC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandAlias("deletewarp|delwarp")
@CommandPermission("skblock.delwarp")
public class DeleteWarpCommand extends BaseCommand {

    @Default
    @Description("Delete a warp with the specified name.")
    public void deleteWarp(CommandSender sender, @Name("warp") String warpName) {
        Warp warp = Main.getInstance().getServerHandler().getWarp(warpName);
        if (warp == null) {
            sender.sendMessage(CC.translate("&b&lEtheriaMC &f● &cNo warp with name &b" + warpName + " &cfound."));
            return;
        }
        Main.getInstance().getServerHandler().removeWarp(warp);
        sender.sendMessage(CC.translate("&b&lEtheriaMC &f● &fDeleted warp " + warp.getName() + "."));
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
}