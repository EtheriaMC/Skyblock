package xyz.etheriamc.skyblock.pet.petshop.menu.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.etheriamc.skyblock.EtheriaSkyblock;
import xyz.etheriamc.skyblock.pet.petshop.PetShop;
import xyz.etheriamc.skyblock.util.CC;

public class PurchasePetListener implements Listener {

    @EventHandler
    public void onPurchasePet(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(CC.translate("Pet Shop"))) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Material material = clickedItem.getType();
            int price = PetShop.getInstance().getPetPrice(material);
            if (price > 0) {
                Player player = (Player) event.getWhoClicked();
                if (EtheriaSkyblock.getInstance().getEconomy().has(player.getName(), price)) {
                    if (EtheriaSkyblock.getInstance().getEconomy().withdrawPlayer(player, price).transactionSuccess()) {
                        player.sendMessage(CC.translate("&aYou purchased a " + material.name() + " pet!"));
                        player.getInventory().addItem(new ItemStack(material));
                    } else {
                        player.sendMessage(CC.translate("&cThere was an error processing your transaction."));
                    }
                } else {
                    player.sendMessage(CC.translate("&cYou do not have enough money to purchase this pet."));
                }
            }
        }
    }
}