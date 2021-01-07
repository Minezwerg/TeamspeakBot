package net.trainingsoase.teamspeakbot.utils;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.async.client.*;
import com.mongodb.connection.ClusterSettings;
import net.trainingsoase.teamspeakbot.main.Main;
import org.bson.Document;

import java.util.Collections;

public class MongoManager {

    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> accounts;

    public void connect(String username, String password, String database) {
        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());
        ClusterSettings clusterSettings = ClusterSettings.builder()
                .hosts(Collections.singletonList(new ServerAddress("144.91.77.45"))).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .clusterSettings(clusterSettings)
                .credentialList(Collections.singletonList(credential))
                .build();
        this.client = MongoClients.create(settings);
        this.database = client.getDatabase(database);
        this.accounts = this.database.getCollection("accounts");
    }

    public MongoCollection<Document> getCollection(String collection) {
        return this.database.getCollection(collection);
    }

    public MongoCollection<Document> getAccounts() {
        return accounts;
    }

    public static void createPlayer(int clientid, String firstJoinName, int dbid) {
        Main.ts3ApiAsync.getClientInfo(clientid).onSuccess((client -> {
            String clientIP = client.getIp();
            Document playerDoc = new Document("ts_dbid", dbid)
                    .append("ts_firstJoinName", firstJoinName)
                    .append("ts_dbid", dbid)
                    .append("ts_IP", clientIP)
                    .append("mc_UUID", "notset")
                    .append("mc_Rang", "notset");
            Main.mongoManager.getAccounts().insertOne(playerDoc, (unused, throwable2) -> {
                System.out.println("Player " + firstJoinName + " created.");
            });
        }));
    }

    public void close() {
        this.client.close();
    }

}
