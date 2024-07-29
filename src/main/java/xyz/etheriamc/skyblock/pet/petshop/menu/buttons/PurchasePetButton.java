package xyz.etheriamc.skyblock.pet.petshop.menu.buttons;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.etheriamc.skyblock.pet.petshop.PetShop;
import xyz.etheriamc.skyblock.util.CC;

import java.util.Collections;

public class PurchasePetButton {

    private final Material material;

    public PurchasePetButton(Material material) {
        this.material = material;
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta meta = itemStack.getItemMeta();

        int price = PetShop.getInstance().getPetPrice(material);
        meta.setDisplayName(CC.translate("&aBuy " + material.name()));
        meta.setLore(Collections.singletonList(CC.translate("&7Price: &2$" + price)));

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}