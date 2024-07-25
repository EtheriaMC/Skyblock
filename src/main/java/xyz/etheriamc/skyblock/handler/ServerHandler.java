package xyz.etheriamc.skyblock.handler;

import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.warp.Warp;

import java.util.ArrayList;
import java.util.List;

public class ServerHandler {

    private final List<Warp> warps = new ArrayList<>();

    public ServerHandler() {
        loadWarps();
    }

    private void loadWarps() {
        for (String warpName : Main.getInstance().getConfig().getConfigurationSection("warps").getKeys(false)) {
            Warp warp = Warp.load(warpName);
            if (warp != null) {
                warps.add(warp);
            }
        }
    }

    public void saveWarps() {
        for (Warp warp : warps) {
            warp.save();
        }
    }

    public void addWarp(Warp warp) {
        warps.add(warp);
        warp.save();
    }

    public void removeWarp(Warp warp) {
        warps.remove(warp);
        warp.delete();
    }

    public List<Warp> getWarps() {
        return warps;
    }

    public Warp getWarp(String name) {
        for (Warp warp : warps) {
            if (warp.getName().equals(name)) {
                return warp;
            }
        }
        return null;
    }
}