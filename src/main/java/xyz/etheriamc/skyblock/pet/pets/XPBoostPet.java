package xyz.etheriamc.skyblock.pet.pets;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.etheriamc.skyblock.pet.Pet;
import xyz.etheriamc.skyblock.pet.Pets;

public class XPBoostPet extends Pet {

    private static final int XP_BOOST_AMOUNT = 50;

    public XPBoostPet() {
        super(Pets.PetType.XP_BOOST);
    }

    @Override
    public Material getMaterial() {
        return Material.ENCHANTED_BOOK;
    }

    @Override
    public String getName() {
        return "XP Boost Pet";
    }

    @Override
    public void onRightClick(Player player) {
        player.giveExp(XP_BOOST_AMOUNT);
    }

    public static class XpBoostPetListener implements Listener {

        private final JavaPlugin plugin;

        public XpBoostPetListener(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        @EventHandler(priority = EventPriority.HIGH)
        public void onPlayerInteract(PlayerInteractEvent event) {
            if (event.getItem() == null || event.getItem().getType() != Material.ENCHANTED_BOOK) {
                return;
            }

            ItemStack item = event.getItem();
            ItemMeta meta = item.getItemMeta();

            if (meta == null || !meta.hasDisplayName() || !meta.getDisplayName().equals("XP Boost Pet")) {
                return;
            }

            Player player = event.getPlayer();
            Pet pet = new XPBoostPet();

            if (Pets.canUsePet(player, pet.getPetType())) {
                pet.onRightClick(player);
                Pets.setCooldown(player, pet.getPetType());
                player.sendMessage("You have been given an XP boost!");

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
    }
}