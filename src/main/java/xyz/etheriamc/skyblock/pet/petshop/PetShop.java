package xyz.etheriamc.skyblock.pet.petshop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.etheriamc.skyblock.util.CC;

import java.util.HashMap;
import java.util.Map;

public class PetShop {

    private static PetShop instance;
    private final Map<Material, Integer> petPrices;

    private PetShop() {
        petPrices = new HashMap<>();
        petPrices.put(Material.DIAMOND, 100);
        petPrices.put(Material.EMERALD, 150);
        petPrices.put(Material.GOLD_INGOT, 200);
    }

    public static PetShop getInstance() {
        if (instance == null) {
            instance = new PetShop();
        }
        return instance;
    }

    public int getPetPrice(Material material) {
        return petPrices.getOrDefault(material, -1);
    }

    public void openPetShop(Player player) {
        Inventory petShopInventory = Bukkit.createInventory(null, 27, CC.translate("Pet Shop"));

        for (Map.Entry<Material, Integer> entry : petPrices.entrySet()) {
            Material material = entry.getKey();
            int price = entry.getValue();

            ItemStack itemStack = new ItemStack(material);
            itemStack.getItemMeta().setDisplayName(CC.translate(material.name() + " - " + price + " dollars"));

            petShopInventory.addItem(itemStack);
        }

        player.openInventory(petShopInventory);
    }
}