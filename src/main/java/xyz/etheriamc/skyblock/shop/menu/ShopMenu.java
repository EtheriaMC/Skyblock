package xyz.etheriamc.skyblock.shop.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import xyz.etheriamc.skyblock.shop.Shop;
import xyz.etheriamc.skyblock.shop.menu.button.ShopPurchaseButton;
import xyz.etheriamc.skyblock.shop.menu.button.ShopSellButton;

public class ShopMenu {

    private final Inventory inventory;
    private final Shop shop;

    public ShopMenu(Shop shop, Player player) {
        this.shop = shop;
        this.inventory = Bukkit.createInventory(null, 27, "Shop Menu");

        for (Material material : shop.getItemPrices()) {
            inventory.addItem(new ShopPurchaseButton(material).toItemStack());
        }

        inventory.setItem(26, new ShopSellButton(player).toItemStack());
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}