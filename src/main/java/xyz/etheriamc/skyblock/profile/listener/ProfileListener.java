package xyz.etheriamc.skyblock.profile.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.etheriamc.skyblock.EtheriaSkyblock;
import xyz.etheriamc.skyblock.profile.Profile;

public class ProfileListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile = new Profile(player.getUniqueId());
        EtheriaSkyblock.getInstance().getProfileHandler().getProfiles().add(profile);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Profile profile = EtheriaSkyblock.getInstance().getProfileHandler().getProfileByUUID(player.getUniqueId());

        profile.save();
        EtheriaSkyblock.getInstance().getProfileHandler().getProfiles().remove(profile);
    }
}
