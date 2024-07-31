package xyz.etheriamc.skyblock.coinflip.struct;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

@Getter
public enum CoinColor {
    PURPLE(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Purple", ChatColor.LIGHT_PURPLE, DyeColor.PURPLE),
    BLUE(ChatColor.AQUA + ChatColor.BOLD.toString() + "Blue", ChatColor.AQUA, DyeColor.LIGHT_BLUE),
    RED(ChatColor.RED + ChatColor.BOLD.toString() + "Red", ChatColor.RED, DyeColor.RED),
    GREEN(ChatColor.GREEN + ChatColor.BOLD.toString() + "Green", ChatColor.GREEN, DyeColor.LIME),
    BLACK(ChatColor.DARK_GRAY + ChatColor.BOLD.toString() + "Black", ChatColor.DARK_GRAY, DyeColor.BLACK),
    YELLOW(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Yellow", ChatColor.YELLOW, DyeColor.YELLOW),
    ORANGE(ChatColor.GOLD + ChatColor.BOLD.toString() + "Orange", ChatColor.GOLD, DyeColor.ORANGE),
    WHITE(ChatColor.WHITE + ChatColor.BOLD.toString() + "White", ChatColor.WHITE, DyeColor.WHITE),
    GRAY(ChatColor.GRAY + ChatColor.BOLD.toString() + "Gray", ChatColor.GRAY, DyeColor.GRAY);

    CoinColor(String name, ChatColor chatColor, DyeColor dyeColor) {
        this.name = name;
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
    }

    final String name;

    private final ChatColor chatColor;

    final DyeColor dyeColor;

    public CoinColor getOppositeColor() {
        if (this == PURPLE)
            return BLUE;
        return PURPLE;
    }

    public static CoinColor getFromDyeColor(short dura) {
        for (CoinColor color : values()) {
            if (color.getDyeColor().getData() == dura)
                return color;
        }
        return null;
    }
}
