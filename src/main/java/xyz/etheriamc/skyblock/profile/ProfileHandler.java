package xyz.etheriamc.skyblock.profile;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.etheriamc.skyblock.EtheriaSkyblock;
import xyz.etheriamc.skyblock.profile.listener.ProfileListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileHandler {
    @Getter public MongoCollection<Document> profilesCollection = EtheriaSkyblock.getInstance().getMongoHandler().getDatabase().getCollection("profiles");
    @Getter public List<Profile> profiles;

    public ProfileHandler() {
        profiles = new ArrayList<>();
        Bukkit.getServer().getPluginManager().registerEvents(new ProfileListener(), EtheriaSkyblock.getInstance());
    }

    public Profile getProfileByUsername(String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if(player != null) {
            for(Profile pl : profiles) {
                if(pl.getUsername().equalsIgnoreCase(playerName)) {
                    return pl;
                }
            }
        }

        OfflinePlayer ofPl = Bukkit.getOfflinePlayer(playerName);

        if(ofPl.hasPlayedBefore()) {
            for(Profile pf : profiles) {
                if(pf.getUuid().equals(ofPl.getUniqueId())) {
                    return pf;
                }
            }
            
            return new Profile(ofPl.getUniqueId());
        }
        
        return null;

    }

    public Profile getProfileByUUID(UUID uuid) {
        if(Bukkit.getPlayer(uuid) != null) {
            for(Profile profile : profiles) {
                if(profile.getUuid().equals(uuid)) {
                    return profile;
                }
            }
        }
        
        return null;
    }
    
    public void saveAllProfiles() {
        for(Profile profile : profiles) {
            profile.save();
        }
    }
}
