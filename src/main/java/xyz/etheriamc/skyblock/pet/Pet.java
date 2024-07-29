package xyz.etheriamc.skyblock.pet;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Pet {

    protected final ItemStack itemStack;
    protected final Pets.PetType petType;

    public Pet(Pets.PetType petType) {
        this.petType = petType;
        this.itemStack = new ItemStack(getMaterial());
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(getName());
            this.itemStack.setItemMeta(meta);
        }
    }

    public abstract Material getMaterial();

    public abstract String getName();

    public abstract void onRightClick(Player player);

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Pets.PetType getPetType() {
        return petType;
    }
}