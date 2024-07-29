package xyz.etheriamc.skyblock.util.menu.menus;

import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.util.Pair;
import xyz.etheriamc.skyblock.util.menu.Button;
import xyz.etheriamc.skyblock.util.menu.pagination.PaginatedMenu;

import java.util.Map;

public class BoxMenu extends PaginatedMenu {

    @Override
    public Pair<Integer, Integer> getPageSlots() {
        return new Pair<>(18, 26);
    }

    @Override
    public boolean isFill(Player player, Map<Integer, Button> buttons) {
        return true;
    }

    @Override
    public int[] noneFillButtons() {
        return new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25,28, 29, 30, 31, 32, 33, 34};
    }

    @Override
    public String getPrePaginatedTitle(Player var1) {
        return null;
    }

    @Override
    public int size(Player player) {
        return 9*6;
    }

    @Override
    public boolean useNormalSize() {
        return false;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player var1) {
        return null;
    }
}