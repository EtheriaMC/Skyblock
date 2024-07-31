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
    private double balance;
    private int tokens, coinflipWins, coinflipLosses;
    private long coinflipWinnings, coinflipLossings;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.username = Bukkit.getOfflinePlayer(uuid).getName();
        this.balance = 1000;
        this.tokens = 0;
        this.loaded = false;
        this.coinflipLosses = 0;
        this.coinflipLossings = 0L;
        this.coinflipWins = 0;
        this.coinflipWinnings = 0L;
        this.loadProfile();
    }

    public Profile(String username) {
        this.uuid = Bukkit.getOfflinePlayer(username).getUniqueId();
        this.username = username;
        this.balance = 1000D;
        this.tokens = 0;
        this.coinflipLosses = 0;
        this.coinflipLossings = 0L;
        this.coinflipWins = 0;
        this.coinflipWinnings = 0L;
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

            if(document.getInteger("coinflipWins") == null) {
                setCoinflipWins(0);
            }else {
                setCoinflipWins(document.getInteger("coinflipWins"));
            }

            if(document.getInteger("coinflipLosses") == null) {
                setCoinflipLosses(0);
            }else {
                setCoinflipLosses(document.getInteger("coinflipLosses"));
            }

            if(document.getLong("coinflipWinnings") == null) {
                setCoinflipWinnings(0L);
            }else {
                setCoinflipWinnings(document.getLong("coinflipWinnings"));
            }

            if(document.getLong("coinflipLossings") == null) {
                setCoinflipLossings(0L);
            }else {
                setCoinflipLossings(document.getLong("coinflipLossings"));
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
        document.append("coinflipWins", coinflipWins);
        document.append("coinflipLosses", coinflipLosses);
        document.append("coinflipWinnings", coinflipWinnings);
        document.append("coinflipLossings", coinflipLossings);

        EtheriaSkyblock.getInstance().getProfileHandler().getProfilesCollection().replaceOne(Filters.eq("uuid", uuid.toString()), document, new ReplaceOptions().upsert(true));
    }
}
