package server.handler;
import dataaccess.DataAccessException;
import io.javalin.http.Context;

import chess.ChessGame;
import com.google.gson.Gson;
import service.GameService;
import service.request.CreateGameRequest;
import service.request.JoinGameRequest;
import service.request.ListGamesRequest;
import service.result.CreateGameResult;
import service.result.JoinGameResult;
import service.result.ListGamesResult;

import java.util.Map;

public class GameHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService){
        this.gameService = gameService;
    }
    public void listGames(Context ctx) throws DataAccessException {
        ListGamesRequest request = new ListGamesRequest(ctx.header("authorization"));
        //String authToken
        ListGamesResult result = gameService.listGames(request);
        ctx.contentType("application/json");
        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }
    public void createGame(Context ctx) throws DataAccessException {
        //String authToken, String gameName
        CreateGameRequest body = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
        if (body == null || body.gameName() == null) {
            throw new DataAccessException("bad request");
        }
        CreateGameRequest request = new CreateGameRequest(ctx.header("authorization"), body.gameName());
        CreateGameResult result = gameService.createGame(request);
        ctx.contentType("application/json");
        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }
    public void joinGame(Context ctx) throws DataAccessException {
        //String authToken, String playerColor, int gameID
        JoinGameRequest body = new Gson().fromJson(ctx.body(), JoinGameRequest.class);
        if (body == null || body.playerColor() == null || body.gameID() == 0) {
            throw new DataAccessException("bad request");
        }
        JoinGameRequest request = new JoinGameRequest(ctx.header("authorization"), body.playerColor(), body.gameID());
        JoinGameResult result = gameService.joinGame(request);
        ctx.contentType("application/json");
        ctx.status(200);
        ctx.result(new Gson().toJson(result));

    }

}
