package server.websocket;

import com.google.gson.Gson;
import exception.ResponseException;
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
            if (currentConnections != null) {
                currentSessions = currentConnections;
            }
        }
        currentSessions.add(session);
    }

    public Set<Session> get(int gameID){
        return connections.get(gameID);
    }

    public void remove(int gameID, Session session) {
        Set<Session> currentSessions = connections.get(gameID);
        if(currentSessions == null){
           return;
        }

        currentSessions.remove(session);
        if(currentSessions.isEmpty()){
            connections.remove(gameID);
        }
    }

    //broadcast will be the same
    public void broadcast(int gameID, Session excludeSession, ServerMessage notification) throws IOException{
        Set<Session> sessions = connections.get(gameID);
        if(sessions != null){
            String msg = new Gson().toJson(notification);
            for (Session c : sessions) {
                if (c.isOpen()) {
                    if (!c.equals(excludeSession)) {
                        c.getRemote().sendString(msg);
                    }
                }
            }
        }
    }
}
