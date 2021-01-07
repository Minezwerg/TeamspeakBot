package net.trainingsoase.teamspeakbot.events;

import com.github.theholywaffle.teamspeak3.api.event.*;
import com.mongodb.client.model.Filters;
import net.trainingsoase.teamspeakbot.main.Main;
import net.trainingsoase.teamspeakbot.utils.MongoManager;
import org.bson.Document;

public class Events {

    public static void loadEvents() {
        Main.ts3ApiAsync.registerAllEvents();
        Main.ts3ApiAsync.addTS3Listeners(new TS3Listener() {
            @Override
            public void onClientJoin(ClientJoinEvent e) {
                int dbid = e.getClientDatabaseId();
                int clientid = e.getClientId();
                Main.mongoManager.getAccounts().find(Filters.eq("ts_dbid", dbid)).first((Document doc, Throwable throwable) -> {
                    if(doc == null) {
                        e.getUniqueClientIdentifier();
                        MongoManager.createPlayer(e.getClientId(), e.getClientNickname(), dbid);
                        Main.ts3ApiAsync.sendPrivateMessage(clientid, "Du bist noch nicht verifiziert!1");
                        Main.ts3ApiAsync.sendPrivateMessage(clientid, "Du kannst dich InGame mit dem Befehl '/verify ts3' verifizieren.");
                        return;
                    }
                        String userRang = doc.getString("mc_Rang");
                        System.out.println(e.getClientNickname() + " besitzt laut Mongo Rang " + userRang);
                        if(userRang.equals("notset")) {
                            Main.ts3ApiAsync.sendPrivateMessage(clientid, "Du bist noch nicht verifiziert!2");
                            Main.ts3ApiAsync.sendPrivateMessage(clientid, "Du kannst dich InGame mit dem Befehl '/verify ts3' verifizieren.");
                        } else {
                            switch(userRang.toLowerCase()) {
                                case "spieler":
                                    Main.ts3ApiAsync.getServerGroupsByClientId(e.getClientDatabaseId()).onSuccess((serverGroups -> {
                                        serverGroups.forEach((groups -> {
                                            switch(groups.getId()) {
                                                case 67:
                                                    // Der Client hat die Gruppe ignoreGroupCheck
                                                    break;
                                                case 35:
                                                    // Client hat Spieler Rang laut Mongo aber Oase ID 35 auf Ts3
                                                    Main.ts3ApiAsync.sendPrivateMessage(clientid, "Dir wurde der Oase Rang entfernt!");
                                                    Main.ts3ApiAsync.sendPrivateMessage(clientid, "Grund: Du hast keinen Oase Rang InGame!");
                                                    Main.ts3ApiAsync.removeClientFromServerGroup(35, e.getClientDatabaseId());
                                                    break;
                                                case 36:
                                                    // Client hat Spieler Rang laut Mongo aber Oase+ ID 36 auf Ts3
                                                    Main.ts3ApiAsync.sendPrivateMessage(clientid, "Dir wurde der Oase+ Rang entfernt!");
                                                    Main.ts3ApiAsync.sendPrivateMessage(clientid, "Grund: Du bist keinen YouTuber Rang InGame!");
                                                    Main.ts3ApiAsync.removeClientFromServerGroup(36, e.getClientDatabaseId());
                                                    break;
                                                case 38:
                                                    // Client hat Spieler Rang laut Mongo aber YouTuber ID 38 auf Ts3
                                                    Main.ts3ApiAsync.sendPrivateMessage(clientid, "Dir wurde der YouTuber Rang entfernt!");
                                                    Main.ts3ApiAsync.sendPrivateMessage(clientid, "Grund: Du hast keinen YouTuber Rang InGame!");
                                                    Main.ts3ApiAsync.removeClientFromServerGroup(38, e.getClientDatabaseId());
                                                    break;
                                                default:
                                                    // Do nothing
                                                    break;
                                            }
                                        }));
                                    }));
                                    break;
                                case "oase":
                                    break;
                                case "oase+":
                                    break;
                                case "youtuber":
                                    break;
                                default:
                                    break;
                            }
                        }

                });

            }

            @Override
            public void onTextMessage(TextMessageEvent textMessageEvent) {

            }

            @Override
            public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {

            }

            @Override

            public void onServerEdit(ServerEditedEvent serverEditedEvent) {

            }

            @Override
            public void onChannelEdit(ChannelEditedEvent channelEditedEvent) {

            }

            @Override
            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent channelDescriptionEditedEvent) {

            }

            @Override
            public void onClientMoved(ClientMovedEvent clientMovedEvent) {

            }

            @Override
            public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {

            }

            @Override
            public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {

            }

            @Override
            public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {

            }

            @Override
            public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {

            }

            @Override
            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {

            }
        });
    }
}
