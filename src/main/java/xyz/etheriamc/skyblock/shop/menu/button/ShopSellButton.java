package xyz.etheriamc.skyblock.shop.menu.button;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.etheriamc.skyblock.util.CC;
import xyz.etheriamc.skyblock.shop.Shop;

public class ShopSellButton {

    private final Player player;

    public ShopSellButton(Player player) {
        this.player = player;
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(Material.GOLD_NUGGET, 1);
        ItemMeta meta = itemStack.getItemMeta();

        if (hasItemsToSell(player)) {
            meta.setDisplayName(CC.translate("&cSell Items"));
        } else {
            meta.setDisplayName(CC.translate("&cNo items to sell"));
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private boolean hasItemsToSell(Player player) {
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && Shop.getInstance().getItemPrice(itemStack.getType()) != -1) {
                return true;
            }
        }
        return false;
    }
}