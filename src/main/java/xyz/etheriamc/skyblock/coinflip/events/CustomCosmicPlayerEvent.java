package xyz.etheriamc.skyblock.coinflip.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Setter
@Getter
public class CustomCosmicPlayerEvent extends Event implements Cancellable {

    public boolean cancelled = false;

    private Player player;

    private UUID uuid;

    private String eventKey;

    private Object[] eventData;

    public CustomCosmicPlayerEvent(Player player, String eventKey, Object... data) {
        this.player = player;
        this.eventKey = eventKey;
        this.uuid = player.getUniqueId();
        this.eventData = data;
    }

    public CustomCosmicPlayerEvent(UUID uuid, String eventKey, Object... data) {
        this.uuid = uuid;
        this.eventKey = eventKey;
        this.eventData = data;
    }

    public HandlerList getHandlers() {
        return list;
    }

    public static HandlerList list = new HandlerList();

    public static HandlerList getHandlerList() {
        return list;
    }
}

