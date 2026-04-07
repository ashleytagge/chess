package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//implement sessions differently, use gameID
//for each different game, keep track of what sessions belong to that game
//store a map and use the gameID as the key and the value is a set of sessions that belong to that game

public class ConnectionManager {

    public final ConcurrentHashMap<Integer, Set<Session>> connections = new ConcurrentHashMap<>();

    public void add(int gameID, Session session) {
        Set<Session> currentSessions = connections.get(gameID);
        if(currentSessions == null){
            currentSessions = ConcurrentHashMap.newKeySet();
            Set<Session> currentConnections = connections.putIfAbsent(gameID, currentSessions);
        }
        currentSessions.add(session);
    }

    public void remove(Session session) {
        connections.remove(session);
    }

    //broadcast will be the same
    public void broadcast(Session excludeSession, ServerMessage notification) throws IOException {
        String msg = notification.toString();
        for (Session c : connections.values()) {
            if (c.isOpen()) {
                if (!c.equals(excludeSession)) {
                    c.getRemote().sendString(msg);
                }
            }
        }
    }
}
