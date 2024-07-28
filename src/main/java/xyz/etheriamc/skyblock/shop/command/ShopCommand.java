package xyz.etheriamc.skyblock.shop.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.shop.Shop;

@CommandAlias("shop")
public class ShopCommand extends BaseCommand {

    @CommandAlias("shop")
    public void shop(Player player) {
        Shop.getInstance().createShopMenu(player).open(player);
    }
}