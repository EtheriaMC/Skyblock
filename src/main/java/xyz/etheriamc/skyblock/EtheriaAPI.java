package xyz.etheriamc.skyblock;

import xyz.etheriamc.skyblock.profile.Profile;

import java.util.UUID;

public class EtheriaAPI {
    public static Profile getProfile(UUID uuid) {
        return EtheriaSkyblock.getInstance().getProfileHandler().getProfileByUUID(uuid);
    }

    public static Profile getProfile(String username) {
        return EtheriaSkyblock.getInstance().getProfileHandler().getProfileByUsername(username);
    }
}
