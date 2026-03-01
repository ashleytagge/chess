package server;

import dataaccess.*;
import io.javalin.*;
import server.handler.ClearHandler;
import server.handler.GameHandler;
import server.handler.UserHandler;
import io.javalin.http.Context;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.ClearService;
import service.GameService;
import service.UserService;
import java.util.Map;

public class Server {

    private final Javalin javalin;

    public Server() {

        UserDAO userDAO = new MemoryUserDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        UserService userService = new UserService(userDAO, authDAO);
        GameService gameService = new GameService(userDAO, authDAO, gameDAO);
        ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);

        ClearHandler clearHandler = new ClearHandler(clearService);
        GameHandler gameHandler = new GameHandler(gameService);
        UserHandler userHandler = new UserHandler(userService);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .delete("/db", clearHandler::clearApp)
                .post("/user", userHandler::register)
                .post("/session", userHandler::login)
                .delete("/session", userHandler::logout)
                .get("/game", gameHandler::listGames)
                .post("/game", gameHandler::createGame)
                .put("/game", gameHandler::joinGame)
                .exception(DataAccessException.class, this::exceptionHandler);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    private void exceptionHandler(DataAccessException ex, Context ctx) {
        if(ex != null){
            String errorMessage = ex.getMessage();
            switch (errorMessage) {
                case "bad request" -> ctx.status(400);
                case "unauthorized" -> ctx.status(401);
                case "already taken" -> ctx.status(403);
                default -> ctx.status(500);
            }
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(Map.of("message", "Error: " + errorMessage)));
        }
    }



}
