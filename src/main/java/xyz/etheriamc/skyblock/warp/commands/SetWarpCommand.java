package xyz.etheriamc.skyblock.warp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.warp.Warp;
import xyz.etheriamc.skyblock.util.CC;

@CommandAlias("setwarp")
@CommandPermission("skyblock.setwarp")
public class SetWarpCommand extends BaseCommand {

    @Default
    @Description("Set a warp with the specified name.")
    public void setWarp(CommandSender sender, @Name("name") String warpName) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cThis command can only be used by players."));
            return;
        }

        Player player = (Player) sender;
        Warp warp = Main.getInstance().getServerHandler().getWarp(warpName);
        if (warp != null) {
            sender.sendMessage(CC.translate("&b&lEtheriaMC &f● &cA warp with name &b" + warpName + " &calready exists."));
            return;
        }
        warp = new Warp(warpName, player.getLocation());
        Main.getInstance().getServerHandler().addWarp(warp);
        sender.sendMessage(CC.translate("&b&lEtheriaMC &f● &eCreated " + "&f" + warp.getName() + " &ewarp."));
    }

    @HelpCommand
    public void help(CommandSender sender) {
        sender.sendMessage(CC.translate("&cUsage: /setwarp <name>"));
    }
}