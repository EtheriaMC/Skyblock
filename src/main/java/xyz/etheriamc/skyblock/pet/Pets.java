package xyz.etheriamc.skyblock.pet;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Pets {

    private static final Map<UUID, Map<PetType, Long>> petCooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME_MS = 15 * 60 * 1000;

    public enum PetType {
        SPEED,
        STRENGTH,
        XP_BOOST
    }

    public static boolean canUsePet(Player player, PetType petType) {
        UUID playerId = player.getUniqueId();
        petCooldowns.putIfAbsent(playerId, new HashMap<>());
        Long lastUsed = petCooldowns.get(playerId).get(petType);

        if (lastUsed == null) {
            return true;
        }

        long currentTime = System.currentTimeMillis();
        return currentTime - lastUsed >= COOLDOWN_TIME_MS;
    }

    public static void setCooldown(Player player, PetType petType) {
        petCooldowns.putIfAbsent(player.getUniqueId(), new HashMap<>());
        petCooldowns.get(player.getUniqueId()).put(petType, System.currentTimeMillis());
    }

    public static void applyPetEffect(Player player, PetType petType) {
        switch (petType) {
            case SPEED:
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
                break;
            case STRENGTH:
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
                break;
            case XP_BOOST:
                break;
            default:
                throw new IllegalArgumentException("Unexpected pet type: " + petType);
        }
    }
}