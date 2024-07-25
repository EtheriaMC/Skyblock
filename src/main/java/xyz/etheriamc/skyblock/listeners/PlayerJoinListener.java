package xyz.etheriamc.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.etheriamc.skyblock.util.CC;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinListener implements Listener {

    private final JavaPlugin plugin;

    public PlayerJoinListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        List<String> messages = new ArrayList<>();
        messages.add("&7&m-------------------------------------------");
        messages.add("&fWelcome to &d&lSkyblock");
        messages.add("");
        messages.add("&7┃ &d&lStore&7: &fstore.etheriamc.xyz");
        messages.add("&7┃ &d&lDiscord&7: &fdiscord.gg/etheriamc");
        messages.add("&7┃ &d&lWebsite&7: &fhttps://etheriamc.xyz");
        messages.add("");
        messages.add("&7&m-------------------------------------------");

        messages = CC.translate(messages);

        for (String message : messages) {
            event.getPlayer().sendMessage(message);
        }
    }
}
