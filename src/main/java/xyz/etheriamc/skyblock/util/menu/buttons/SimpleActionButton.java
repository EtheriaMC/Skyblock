package xyz.etheriamc.skyblock.util.menu.buttons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import xyz.etheriamc.skyblock.util.CC;
import xyz.etheriamc.skyblock.util.menu.Button;

import java.util.List;

public class SimpleActionButton extends Button {
    private final Material material;
    private final List<String> description;
    private final String name;
    private final byte data;
    private ActionBody body;

    public SimpleActionButton(Material material, List<String> description, String name, byte data) {
        this.material = material;
        this.description = description;
        this.name = name;
        this.data = data;
    }

    public SimpleActionButton setBody(ActionBody body) {
        this.body = body;
        return this;
    }

    @Override
    public byte getDamageValue(Player player) {
        return data;
    }

    @Override
    public Material getMaterial(Player player) {
        return material;
    }

    @Override
    public String getName(Player player) {
        return CC.translate(name);
    }

    @Override
    public List<String> getDescription(Player player) {
        return description;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        if (body != null) {
            body.invoke(player, slot, clickType);
        }
    }

    // Define a functional interface for the body of the action
    public interface ActionBody {
        void invoke(Player player, int slot, ClickType type);
    }
}