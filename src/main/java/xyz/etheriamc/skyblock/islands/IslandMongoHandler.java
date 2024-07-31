package xyz.etheriamc.skyblock.islands;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import xyz.etheriamc.skyblock.EtheriaSkyblock;
import xyz.etheriamc.skyblock.islands.models.Island;

public class IslandMongoHandler {
    public MongoCollection<Document> islandCollection = EtheriaSkyblock.getInstance().getMongoHandler().getDatabase().getCollection("islands");

    public void createIsland(Island island) {
        Document document = new Document("owner", island.getOwner().toString())
                .append("name", island.getName());
        islandCollection.insertOne(document);
    }
}
