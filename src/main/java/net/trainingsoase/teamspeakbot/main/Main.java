package net.trainingsoase.teamspeakbot.main;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import net.trainingsoase.teamspeakbot.events.Events;
import net.trainingsoase.teamspeakbot.utils.MongoManager;
import org.bson.Document;

public class Main {

    public static final TS3Config ts3Config = new TS3Config();
    public static final TS3Query ts3Query = new TS3Query(ts3Config);
    public static final TS3ApiAsync ts3ApiAsync = new TS3ApiAsync(ts3Query);

    public static final MongoManager mongoManager = new MongoManager();

    private static final String username = "TeamspeakBot";
    private static final String password = "HQlk4lJb";

    public static void main(String[] args){
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("  _______                                        _    ____        _   ");
        System.out.println(" |__   __|                                      | |  |  _ \\      | |  ");
        System.out.println("    | | ___  __ _ _ __ ___  ___ _ __   ___  __ _| | _| |_) | ___ | |_ ");
        System.out.println("    | |/ _ \\/ _` | '_ ` _ \\/ __| '_ \\ / _ \\/ _` | |/ /  _ < / _ \\| __|");
        System.out.println("    | |  __/ (_| | | | | | \\__ \\ |_) |  __/ (_| |   <| |_) | (_) | |_ ");
        System.out.println("    |_|\\___|\\__,_|_| |_| |_|___/ .__/ \\___|\\__,_|_|\\_\\____/ \\___/ \\__|");
        System.out.println("                               | | ");
        System.out.println("         TrainingsOase.NET     |_|     by Minezwerg");
        System.out.println(" ");
        System.out.println(" ");
        mongoManager.connect("ts3bot", "2t1NGP4D0JZO", "System");
        ts3Config.setHost("144.91.77.45");
        ts3Config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        ts3Query.connect();
        ts3ApiAsync.login(username, password);
        ts3ApiAsync.selectVirtualServerByPort(9987);
        ts3ApiAsync.setNickname("TrainingsOase | Verify");
        System.out.println("Der Bot wurde gestartet!");
        Events.loadEvents();
        System.out.println("Der Event-Listener wurde registriert!");

        /*Document playerDoc = new Document("ts_clientid", "123")
                .append("ts_firstJoinName", "name")
                .append("ts_dbid", "31")
                .append("ts_IP", "10.10.10.1")
                .append("mc_UUID", "notset")
                .append("mc_Rang", "notset");
        Main.mongoManager.getAccounts().insertOne(playerDoc, (unused, throwable2) -> {
            System.out.println("Player created");
        });*/
    }
}
