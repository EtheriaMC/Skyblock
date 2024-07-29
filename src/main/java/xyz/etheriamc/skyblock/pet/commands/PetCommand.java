package xyz.etheriamc.skyblock.pet.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.etheriamc.skyblock.pet.Pet;
import xyz.etheriamc.skyblock.pet.pets.SpeedPet;

public class PetCommand extends BaseCommand {

    @CommandAlias("pet")
    @Subcommand("use")
    public void usePet(Player player) {
        if (player.getInventory().getItemInHand() == null) {
            player.sendMessage("You must hold a pet in your hand to use it.");
            return;
        }

        Pet pet = getPetFromItem(player.getInventory().getItemInHand());
        if (pet != null) {
            pet.onRightClick(player);
        } else {
            player.sendMessage("You are not holding a valid pet.");
        }
    }

    private Pet getPetFromItem(ItemStack item) {
        if (item.getType() == Material.FEATHER) {
            return new SpeedPet();
        }
        return null;
    }
}