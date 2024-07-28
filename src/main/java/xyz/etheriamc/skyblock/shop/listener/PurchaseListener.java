package xyz.etheriamc.skyblock.shop.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.etheriamc.skyblock.profile.Profile;
import xyz.etheriamc.skyblock.shop.Shop;
import xyz.etheriamc.skyblock.util.CC;
import xyz.etheriamc.skyblock.EtheriaAPI;

public class PurchaseListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack currentItem = event.getCurrentItem();

        if (inventory.getTitle().equals("Shop Menu") && currentItem != null) {
            Material material = currentItem.getType();
            if (Shop.getInstance().getItemPrice(material) != -1) {
                event.setCancelled(true);
                handlePurchase(player, currentItem);
            }
        }
    }

    private void handlePurchase(Player player, ItemStack itemStack) {
        Profile profile = EtheriaAPI.getProfile(player.getUniqueId());
        int price = Shop.getInstance().getItemPrice(itemStack.getType());

        if (profile.getBalance() >= price) {
            profile.setBalance(profile.getBalance() - price);
            player.getInventory().addItem(new ItemStack(itemStack.getType(), 1));
            player.sendMessage(CC.translate("&aYou have purchased &e1 " + itemStack.getType().name() + " &afor &2$&a" + price));
        } else {
            player.sendMessage(CC.translate("&cYou do not have enough money to purchase this item."));
        }
    }
}