package xyz.etheriamc.skyblock.pet.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.pet.petshop.PetShop;

public class PetShopCommand extends BaseCommand {

    @CommandAlias("petshop")
    public void openPetShop(Player player) {
        PetShop.getInstance().openPetShop(player);
    }
}