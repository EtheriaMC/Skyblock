package xyz.etheriamc.skyblock.pet.pets;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.pet.Pet;
import xyz.etheriamc.skyblock.pet.Pets;

public class StrengthPet extends Pet {

    public StrengthPet() {
        super(Pets.PetType.STRENGTH);
    }

    @Override
    public Material getMaterial() {
        return Material.FEATHER;
    }

    @Override
    public String getName() {
        return "Strength Pet";
    }

    @Override
    public void onRightClick(Player player) {
        if (Pets.canUsePet(player, Pets.PetType.STRENGTH)) {
            Pets.applyPetEffect(player, Pets.PetType.STRENGTH);
            Pets.setCooldown(player, Pets.PetType.STRENGTH);
            player.sendMessage("You have used the Strenght pet!");
        } else {
            player.sendMessage("Your strength pet is currently on cooldown...");
        }
    }
}