package xyz.etheriamc.skyblock.shop.menu.button;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.etheriamc.skyblock.util.CC;

public class ShopSellButton {

    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(Material.GOLD_NUGGET, 1);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(CC.translate("&cSell Items"));

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}