package xyz.etheriamc.skyblock.pet.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import xyz.etheriamc.skyblock.pet.*;
import xyz.etheriamc.skyblock.pet.pets.SpeedPet;
import xyz.etheriamc.skyblock.pet.pets.StrengthPet;
import xyz.etheriamc.skyblock.pet.pets.XPBoostPet;

public class UsePetListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) {
            return;
        }

        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        Pet pet = determinePet(item.getType());
        if (pet == null) {
            return;
        }

        if (Pets.canUsePet(player, pet.getPetType())) {
            Pets.applyPetEffect(player, pet.getPetType());
            Pets.setCooldown(player, pet.getPetType());
            player.sendMessage("Pet effect applied!");

            int newAmount = item.getAmount() - 1;
            if (newAmount <= 0) {
                player.getInventory().remove(item);
            } else {
                item.setAmount(newAmount);
            }
        } else {
            player.sendMessage("You need to wait before using this pet again.");
        }
    }

    private Pet determinePet(Material material) {
        switch (material) {
            case FEATHER:
                return new SpeedPet();
            case NETHER_STAR:
                return new StrengthPet();
            case ENCHANTED_BOOK:
                return new XPBoostPet();
            default:
                return null;
        }
    }
}