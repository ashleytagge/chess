package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.MySQLAuthDAO;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;
import exception.ResponseException;

import java.io.IOException;
//petshop, modify for chess
public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final GameDAO gameDao;
    private final AuthDAO authDao;

    public WebSocketHandler(GameDAO gameDao, AuthDAO authDao){
        this.gameDao = gameDao;
        this.authDao = authDao;
    }

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

    public void sendMessage(Session session, int gameID, ServerMessage message){}

    public void saveSession(int gameID, Session session){}

    private void connect(Session session, UserGameCommand command) throws IOException, DataAccessException {

        String message;
        AuthData auth = authDao.getAuth(command.getAuthToken());
        if(auth == null){
            sendMessage(session, command.getGameID(), new ErrorMessage("error: unauthorized"));
            return;
        }
        GameData game = gameDao.getGame(command.getGameID());
        if(game == null){
            sendMessage(session, command.getGameID(), new ErrorMessage("error: bad request"));
            return;
        }

        connections.add(command.getGameID(), session);
        sendMessage(session, command.getGameID(), new LoadGameMessage(game.game()));

        if(auth.username().equals(game.whiteUsername())){
            message = String.format("%s has connected as the white player!", auth.username());
        }else if(auth.username().equals(game.blackUsername())){
            message = String.format("%s has connected as the black player!", auth.username());
        }else{
            message = String.format("%s has connected as an observer!", auth.username());
        }

        connections.broadcast(command.getGameID(), session, new NotificationMessage(message));

    }

    private void makeMove(Session session, String username, int gameID, UserGameCommand command) throws IOException {
        var message = String.format("%s made a move", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(gameID, session, notification);
        try{
            connections.remove(gameID, session);
        }catch(ResponseException ex){
            sendMessage(session, gameID, new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

    private void leaveGame(Session session, String username, int gameID, UserGameCommand command) throws IOException {
        connections.add(gameID, session);
        var message = String.format("%s left the game", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(gameID, session, notification);
    }

    private void resign(Session session, String username, int gameID, UserGameCommand command) throws IOException {
        var message = String.format("%s resigned from the game", username);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(gameID, session, notification);
        try{
            connections.remove(gameID, session);
        }catch(ResponseException ex){
            sendMessage(session, gameID, new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

}