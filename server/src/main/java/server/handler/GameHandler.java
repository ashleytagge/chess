package server.handler;
import dataaccess.DataAccessException;
import io.javalin.http.Context;

import chess.ChessGame;
import com.google.gson.Gson;
import service.GameService;

public class GameHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService){
        this.gameService = gameService;
    }
    public void listGames(Context ctx) throws DataAccessException {}
    public void createGame(Context ctx) throws DataAccessException {}
    public void joinGame(Context ctx) throws DataAccessException {}

}
