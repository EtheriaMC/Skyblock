package xyz.etheriamc.skyblock.shop.menu.button;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.etheriamc.skyblock.shop.Shop;

import java.util.Arrays;

public class ShopPurchaseButton {

    private final Material material;

    public ShopPurchaseButton(Material material) {
        this.material = material;
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta meta = itemStack.getItemMeta();
        int price = Shop.getInstance().getItemPrice(material);

        meta.setDisplayName(ChatColor.YELLOW + material.name());
        meta.setLore(Arrays.asList(ChatColor.GREEN + "Price: $" + price));

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}