package xyz.etheriamc.skyblock.util.adapters;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import xyz.etheriamc.skyblock.Main;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BoardAdapter implements AssembleAdapter {

    private final Main plugin;

    private static final String VERTICAL_STRAIGHT_LINE = "\u25CE";

    @Override
    public String getTitle(Player player) {
        String title = plugin.getScoreboardFile().getString("title");
        return processSpecialCharacters(PlaceholderAPI.setPlaceholders(player, title));
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = plugin.getScoreboardFile().getStringList("lines");
        return lines.stream()
                .map(line -> processSpecialCharacters(PlaceholderAPI.setPlaceholders(player, line.replace("<balance>", String.valueOf(Main.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId()).getBalance())))))
                .collect(Collectors.toList());
    }

    private String processSpecialCharacters(String input) {
        return input.replace("▎", VERTICAL_STRAIGHT_LINE);
    }
}