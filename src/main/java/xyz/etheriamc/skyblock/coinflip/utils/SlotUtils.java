package xyz.etheriamc.skyblock.coinflip.utils;

public class SlotUtils {
    public static int fitSlots(int size) {
        return (size <= 9) ? 9 : ((size <= 18) ? 18 : ((size <= 27) ? 27 : ((size <= 36) ? 36 : ((size <= 45) ? 45 : (54)))));
    }
}
