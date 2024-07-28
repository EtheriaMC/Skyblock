package xyz.etheriamc.skyblock.profile;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import xyz.etheriamc.skyblock.EtheriaSkyblock;

import java.util.UUID;

@Getter
@Setter
public class Profile {
    private UUID uuid;
    private String username;
    private boolean loaded;
    private int balance, tokens;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.username = Bukkit.getOfflinePlayer(uuid).getName();
        this.balance = 1000;
        this.tokens = 0;
        this.loaded = false;

        this.loadProfile();
    }

    public Profile(String username) {
        this.uuid = Bukkit.getOfflinePlayer(username).getUniqueId();
        this.username = username;
        this.balance = 1000;
        this.tokens = 0;
        this.loaded = false;

        this.loadProfile();
    }

    public void loadProfile() {
        Document document = EtheriaSkyblock.getInstance().getProfileHandler().getProfilesCollection().find(Filters.eq("uuid", getUuid().toString())).first();
        if(document != null) {
            if(document.getInteger("balance") == null) {
                setBalance(0);
            }else {
                setBalance(document.getInteger("balance"));
            }

            if(document.getInteger("tokens") == null) {
                setTokens(0);
            }else {
                setTokens(document.getInteger("tokens"));
            }

            setLoaded(true);
        }
    }

    public void save() {
        Document document = new Document();
        document.append("uuid", uuid.toString());
        document.append("username", username);
        document.append("balance", balance);
        document.append("tokens", tokens);

        EtheriaSkyblock.getInstance().getProfileHandler().getProfilesCollection().replaceOne(Filters.eq("uuid", uuid.toString()), document, new ReplaceOptions().upsert(true));
    }
}
