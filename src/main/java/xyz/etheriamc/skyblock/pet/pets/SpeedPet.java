package xyz.etheriamc.skyblock.pet.pets;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.pet.Pet;
import xyz.etheriamc.skyblock.pet.Pets;

public class SpeedPet extends Pet {

    public SpeedPet() {
        super(Pets.PetType.SPEED);
    }

    @Override
    public Material getMaterial() {
        return Material.FEATHER;
    }

    @Override
    public String getName() {
        return "Speed Pet";
    }

    @Override
    public void onRightClick(Player player) {
        if (Pets.canUsePet(player, Pets.PetType.SPEED)) {
            Pets.applyPetEffect(player, Pets.PetType.SPEED);
            Pets.setCooldown(player, Pets.PetType.SPEED);
            player.sendMessage("Speed Pet effect applied!");
        } else {
            player.sendMessage("You must wait before using this pet again.");
        }
    }
}