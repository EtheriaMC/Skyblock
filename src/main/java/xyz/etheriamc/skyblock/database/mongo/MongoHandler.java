package xyz.etheriamc.skyblock.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bukkit.Bukkit;

import java.util.Objects;

public class MongoHandler {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public void setupMongoDB(String uri) {
        try {
            MongoClientURI mongoClientURI = new MongoClientURI(uri);
            mongoClient = new MongoClient(mongoClientURI);
            mongoDatabase = mongoClient.getDatabase(Objects.requireNonNull(mongoClientURI.getDatabase()));

            Bukkit.getLogger().info("[Mongo] Mongo has successfully been loaded and connected!");
        } catch (Exception e) {
            Bukkit.getLogger().severe("[Mongo] Failed to connect to MongoDB: " + e.getMessage());
        }
    }

    public void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
            Bukkit.getLogger().info("[Mongo] Mongo has been disconnected!");
        }
    }

    public MongoDatabase getDatabase() {
        if (mongoDatabase == null) {
            throw new IllegalStateException("MongoDB has not been initialized. Call setupMongoDB first.");
        }
        return mongoDatabase;
    }
}