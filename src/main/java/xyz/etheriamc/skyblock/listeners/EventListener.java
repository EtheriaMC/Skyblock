package xyz.etheriamc.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.etheriamc.skyblock.managers.IslandManager;

public class EventListener implements Listener {
    private IslandManager islandManager;

    public EventListener(IslandManager islandManager) {
        this.islandManager = islandManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
    }
}