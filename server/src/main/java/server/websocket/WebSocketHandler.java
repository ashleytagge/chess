package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
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
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static websocket.commands.UserGameCommand.CommandType.MAKE_MOVE;

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

    public void handleMessage(@NotNull WsMessageContext ctx) throws ResponseException, IOException {
        int gameID = -1;
        Session session = ctx.session;
        ChessMove move = null;

        try {
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            if(command.getCommandType() == MAKE_MOVE){
                move = command.getMove();
            }
            gameID = command.getGameID();
            String username = command.getUsername();
            saveSession(gameID, session);

            switch(command.getCommandType()) {
                case CONNECT -> connect(session, command);
                case MAKE_MOVE -> makeMove(session, command, move);
                    //deserialize as a make move command if its make move
                case LEAVE -> leaveGame(session, command);
                case RESIGN -> resign(session, command);
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

    public void sendMessage(Session session, int gameID, ServerMessage message) throws IOException {
        String json = new Gson().toJson(message);
        session.getRemote().sendString(json);
    }

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

    private void makeMove(Session session, UserGameCommand command, ChessMove move) throws IOException, DataAccessException, InvalidMoveException {

        AuthData auth = authDao.getAuth(command.getAuthToken());
        GameData game = gameDao.getGame(command.getGameID());
        boolean gameOver = game.game().getGameOver();
        ChessGame.TeamColor teamTurn = game.game().getTeamTurn();
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        Collection<ChessMove> validMoves = game.game().validMoves(start);
        String username = auth.username();
        int gameID = game.gameID();

        if(gameOver){
            sendMessage(session, command.getGameID(), new ErrorMessage("error: you can't make a move. this game is over."));
            return;
        }

        if(auth.username().equals(game.whiteUsername()) && teamTurn.equals(ChessGame.TeamColor.BLACK) ||
                auth.username().equals(game.blackUsername()) && teamTurn.equals(ChessGame.TeamColor.WHITE)){
            sendMessage(session, command.getGameID(), new ErrorMessage("error: it's not your turn."));
            return;
        }

        if(!auth.username().equals(game.whiteUsername()) && !auth.username().equals(game.blackUsername())){
            sendMessage(session, command.getGameID(), new ErrorMessage("error: you can't make a move as an observer"));
            return;
        }

        if(!validMoves.contains(move)){
            sendMessage(session, command.getGameID(), new ErrorMessage("error: invalid move"));
            return;
        }
        game.game().makeMove(move);
        gameDao.updateGame(game);

        var notification = String.format("%s moved from %s to %s", username, start, end);

        Set<Session> sessions = connections.get(gameID);
        String message = "";

        if(sessions != null){
            for (Session c : sessions) {
                if (c.isOpen()){
                    sendMessage(c, command.getGameID(), new LoadGameMessage(game.game()));
                    if(game.game().isInCheckmate(ChessGame.TeamColor.BLACK)){
                        message = "the black player is in checkmate";
                        sendMessage(c, command.getGameID(), new NotificationMessage(message));
                    } else if(game.game().isInCheck(ChessGame.TeamColor.BLACK)){
                        message = "the black player is in check";
                        sendMessage(c, command.getGameID(), new NotificationMessage(message));
                    } else if(game.game().isInStalemate(ChessGame.TeamColor.BLACK)){
                        message = "the black player is in stalemate";
                        sendMessage(c, command.getGameID(), new NotificationMessage(message));
                    } else if(game.game().isInCheckmate(ChessGame.TeamColor.WHITE)){
                        message = "the white player is in checkmate";
                        sendMessage(c, command.getGameID(), new NotificationMessage(message));
                    } else if(game.game().isInCheck(ChessGame.TeamColor.WHITE)){
                        message = "the white player is in check";
                        sendMessage(c, command.getGameID(), new NotificationMessage(message));
                    } else if(game.game().isInStalemate(ChessGame.TeamColor.WHITE)){
                        message = "the white player is in stalemate";
                        sendMessage(c, command.getGameID(), new NotificationMessage(message));
                    }
                }
            }
        }
        connections.broadcast(gameID, session, new NotificationMessage(notification));
    }

    private void leaveGame(Session session, UserGameCommand command) throws IOException, DataAccessException {

        AuthData auth = authDao.getAuth(command.getAuthToken());
        GameData game = gameDao.getGame(command.getGameID());
        String notification = "No one has left the game";

        String user = auth.username();
        int id = game.gameID();
        if(user.equals(game.whiteUsername())){
            //int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game
            GameData newData = new GameData(id, null, game.blackUsername(), game.gameName(), game.game());
            notification = String.format("%s left the game.", game.whiteUsername());
            gameDao.updateGame(newData);
        }else if (user.equals(game.blackUsername())){
            GameData newData = new GameData(id, game.whiteUsername(), null, game.gameName(), game.game());
            gameDao.updateGame(newData);
            notification = String.format("%s left the game.", game.blackUsername());
        }else{
            //don't forget observer
            notification = String.format("%s left the game.", user);
        }
        connections.remove(id, session);
        connections.broadcast(id, session, new NotificationMessage(notification));

    }

    private void resign(Session session, UserGameCommand command) throws IOException, DataAccessException {

        AuthData auth = authDao.getAuth(command.getAuthToken());
        GameData game = gameDao.getGame(command.getGameID());

        if(!auth.username().equals(game.blackUsername()) && !auth.username().equals(game.whiteUsername())){
            sendMessage(session, command.getGameID(), new ErrorMessage("error: you cannot resign as an observer"));
            return;
        }
        boolean gameOver;
        gameOver = game.game().getGameOver();
        if(gameOver){
            sendMessage(session, command.getGameID(), new ErrorMessage("error: this game is over"));
            return;
        }

        int gameID = command.getGameID();
        String username = command.getUsername();
        var message = String.format("%s resigned from the game", username);
        game.game().setGameOver();
        gameDao.updateGame(game);
        sendMessage(session, command.getGameID(), new NotificationMessage(message));
        connections.broadcast(gameID, session, new NotificationMessage(message));
        connections.remove(gameID, session);
    }

}