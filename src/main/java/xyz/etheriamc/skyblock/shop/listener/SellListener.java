package xyz.etheriamc.skyblock.shop.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.etheriamc.skyblock.shop.Shop;
import xyz.etheriamc.skyblock.util.CC;
import xyz.etheriamc.skyblock.profile.Profile;
import xyz.etheriamc.skyblock.EtheriaAPI;

public class SellListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack currentItem = event.getCurrentItem();

        if (inventory.getTitle().equals("Shop Menu") && currentItem != null) {
            if (currentItem.getType() == Material.GOLD_NUGGET && currentItem.getItemMeta().getDisplayName().equals(CC.translate("&cSell Items"))) {
                event.setCancelled(true);
                sellItems(player);
            }
        }
    }

    private void sellItems(Player player) {
        Inventory inventory = player.getInventory();
        int totalAmount = 0;
        int totalItemsSold = 0;
        Material soldItemType = null;

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null && Shop.getInstance().getItemPrice(itemStack.getType()) != -1) {
                int price = Shop.getInstance().getItemPrice(itemStack.getType());
                totalAmount += price * itemStack.getAmount();
                totalItemsSold += itemStack.getAmount();
                soldItemType = itemStack.getType();
                inventory.remove(itemStack);
            }
        }

        if (totalAmount > 0) {
            Profile profile = EtheriaAPI.getProfile(player.getUniqueId());
            profile.setBalance(profile.getBalance() + totalAmount);
            player.sendMessage(CC.translate("&aYou sold " + totalItemsSold + " " + formatItemName(soldItemType.name()) + " for &2$&a" + totalAmount));
        } else {
            player.sendMessage(CC.translate("&cYou have no items to sell"));
        }
    }

    private String formatItemName(String itemName) {
        String[] words = itemName.split("_");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            formattedName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
        }
        return formattedName.toString().trim();
    }
}