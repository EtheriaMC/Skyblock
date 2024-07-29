package xyz.etheriamc.skyblock.pet.petshop.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import xyz.etheriamc.skyblock.pet.petshop.PetShop;
import xyz.etheriamc.skyblock.pet.petshop.menu.buttons.PurchasePetButton;

public class PetsShopMenu {

    public static void openPetShop(Inventory inventory) {
        PetShop petShop = PetShop.getInstance();

        inventory.setItem(10, new PurchasePetButton(Material.DIAMOND).toItemStack());
        inventory.setItem(12, new PurchasePetButton(Material.EMERALD).toItemStack());
        inventory.setItem(14, new PurchasePetButton(Material.GOLD_INGOT).toItemStack());
    }
}