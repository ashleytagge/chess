package server;

import dataaccess.*;
import io.javalin.*;
import server.handler.ClearHandler;
import server.handler.GameHandler;
import server.handler.UserHandler;
import io.javalin.http.Context;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import server.websocket.WebSocketHandler;
import service.ClearService;
import service.GameService;
import service.UserService;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.ws;

public class Server {
//update this class with websocket
    private final WebSocketHandler webSocketHandler;
    private final Javalin javalin;

    public Server(){
        this(sqlUserDAO(), sqlGameDAO(), sqlAuthDAO());
    }

    private static UserDAO sqlUserDAO() {
        try {
            return new MySQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static GameDAO sqlGameDAO() {
        try {
            return new MySQLGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static AuthDAO sqlAuthDAO() {
        try {
            return new MySQLAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Server(UserDAO userDAO, GameDAO gameDAO, AuthDAO authDAO) {

        UserService userService = new UserService(userDAO, authDAO);
        GameService gameService = new GameService(userDAO, authDAO, gameDAO);
        ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);

        ClearHandler clearHandler = new ClearHandler(clearService);
        GameHandler gameHandler = new GameHandler(gameService);
        UserHandler userHandler = new UserHandler(userService);

        webSocketHandler = new WebSocketHandler(gameDAO, authDAO);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .delete("/db", clearHandler::clearApp)
                .post("/user", userHandler::register)
                .post("/session", userHandler::login)
                .delete("/session", userHandler::logout)
                .get("/game", gameHandler::listGames)
                .post("/game", gameHandler::createGame)
                .put("/game", gameHandler::joinGame)
                .exception(DataAccessException.class, this::exceptionHandler)
                .ws("/ws", ws -> {
                    ws.onConnect(webSocketHandler);
                    ws.onMessage(webSocketHandler);
                    ws.onClose(webSocketHandler);
                });
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
                case "already exists", "already taken" -> ctx.status(403);
                default -> ctx.status(500);
            }
            ctx.contentType("application/json");
            ctx.result(new Gson().toJson(Map.of("message", "Error: " + errorMessage)));
        }
    }



}