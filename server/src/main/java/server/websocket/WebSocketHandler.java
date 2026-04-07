package server.websocket;

import com.google.gson.Gson;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.ServerMessage;
import exception.ResponseException;

import java.io.IOException;
//petshop, modify for chess
public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    public void handleMessage(@NotNull WsMessageContext ctx) throws ResponseException {
        int gameID = -1;
        Session session = ctx.session;

        try {
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            gameID = command.getGameID();
            String username = command.getUsername();
            saveSession(gameID, session);

            switch(command.getCommandType()) {
                case CONNECT -> connect(session, username, gameID, command);
                case MAKE_MOVE -> makeMove(session, username, gameID, command);
                    //deserialize as a make move command if its make move
                case LEAVE -> leaveGame(session, username, gameID, command);
                case RESIGN -> resign(session, username, gameID, command);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            sendMessage(session, gameID, new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }

    public void sendMessage(Session session, int gameID, ErrorMessage message){}

    public void saveSession(int gameID, Session session){}

    private void connect(Session session, String username, int gameID, UserGameCommand command) throws IOException {
        connections.add(session);
        var message = String.format("%s has connected!", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(session, notification);
    }

    private void makeMove(Session session, String username, int gameID, UserGameCommand command) throws IOException {
        var message = String.format("%s made a move", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(session, notification);
        connections.remove(session);
    }

    private void leaveGame(Session session, String username, int gameID, UserGameCommand command) throws IOException {
        connections.add(session);
        var message = String.format("%s left the game", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(session, notification);
    }

    private void resign(Session session, String username, int gameID, UserGameCommand command) throws IOException {
        var message = String.format("%s resigned from the game", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(session, notification);
        connections.remove(session);
    }

}