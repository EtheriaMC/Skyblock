package xyz.etheriamc.skyblock.api;

import xyz.etheriamc.skyblock.Main;
import xyz.etheriamc.skyblock.profile.Profile;

import java.util.UUID;

public class EtheriaAPI {
    public static Profile getProfile(UUID uuid) {
        return Main.getInstance().getProfileHandler().getProfileByUUID(uuid);
    }

    public static Profile getProfile(String username) {
        return Main.getInstance().getProfileHandler().getProfileByUsername(username);
    }
}
