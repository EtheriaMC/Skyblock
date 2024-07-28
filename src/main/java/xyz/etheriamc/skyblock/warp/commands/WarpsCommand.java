package xyz.etheriamc.skyblock.warp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.EtheriaSkyblock;
import xyz.etheriamc.skyblock.warp.Warp;
import xyz.etheriamc.skyblock.util.CC;

import java.util.List;
import java.util.stream.Collectors;

@CommandAlias("warps")
@CommandPermission("skyblock.warp.list")
public class WarpsCommand extends BaseCommand {

    @Default
    @Description("List all available warps.")
    public void listWarps(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cThis command can only be used by players."));
            return;
        }

        Player player = (Player) sender;
        List<Warp> warps = EtheriaSkyblock.getInstance().getServerHandler().getWarps();

        if (warps.isEmpty()) {
            player.sendMessage(CC.translate("&b&lEtheriaMC &f● &cThere are no warps, run /setwarp <warp> to create one."));
            return;
        }

        String warpList = warps.stream()
                .map(warp -> "&e" + warp.getName())
                .collect(Collectors.joining(", "));

        player.sendMessage(CC.translate("&b&lEtheriaMC &f● &fAvailable warps&7:&b " + warpList));
    }

    @HelpCommand
    public void help(CommandSender sender) {
        sender.sendMessage(CC.translate("&cUsage: /warps"));
    }
}