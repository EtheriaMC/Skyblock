package xyz.etheriamc.skyblock.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.shop.menu.ShopMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Shop {

    private static Shop instance;
    private final Map<Material, Integer> itemPrices = new HashMap<>();

    private Shop() {
        itemPrices.put(Material.DIAMOND, 100);
        itemPrices.put(Material.GOLD_INGOT, 50);
        itemPrices.put(Material.IRON_INGOT, 30);
    }

    public static Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
        }
        return instance;
    }

    public int getItemPrice(Material material) {
        return itemPrices.getOrDefault(material, -1);
    }

    public Set<Material> getItemPrices() {
        return itemPrices.keySet();
    }

    public ShopMenu createShopMenu(Player player) {
        return new ShopMenu(this, player);
    }
}