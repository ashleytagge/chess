package client;

import exception.ResponseException;
import model.request.*;
import model.result.*;
import org.junit.jupiter.api.*;
import server.Server;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static int port;

    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @BeforeAll
    public static void init() {
        server = new Server();
        port = server.run(0);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void clearDatabase() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/db"))
                .DELETE()
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void registerSuccess() throws ResponseException {
        RegisterResult result = facade.register(new RegisterRequest("ashley", "password", "ashley@email.com"));
        assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void registerFail() {
        assertThrows(ResponseException.class, () ->
                facade.register(new RegisterRequest(null, "password", "ashley@email.com")));
    }

    @Test
    public void loginSuccess() throws ResponseException {
        facade.register(new RegisterRequest("ashley", "password", "ashley@email.com"));

        LoginResult result = facade.login(new LoginRequest("ashley", "password"));
        assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void loginFail() {
        assertThrows(ResponseException.class, () ->
                facade.login(new LoginRequest("spencer", "wrongpassword")));
    }

    @Test
    public void logoutSuccess() throws ResponseException {
        RegisterResult reg = facade.register(new RegisterRequest("ashley", "password", "ashley@email.com"));
        facade.logout(new LogoutRequest(reg.authToken()));
        assertTrue(true);
    }

    @Test
    public void logoutFail() {
        assertThrows(ResponseException.class, () ->
                facade.logout(new LogoutRequest("badtoken")));
    }

    @Test
    public void listGamesSuccess() throws ResponseException {
        RegisterResult reg = facade.register(new RegisterRequest("ashley", "password", "ashley@email.com"));

        ListGamesResult result = facade.listGames(new ListGamesRequest(reg.authToken()));
        assertEquals(0, result.games().size());
    }

    @Test
    public void listGamesFail() {
        assertThrows(ResponseException.class, () ->
                facade.listGames(new ListGamesRequest("badtoken")));
    }

    @Test
    public void createGameSuccess() throws ResponseException {
        RegisterResult reg = facade.register(new RegisterRequest("ashley", "password", "ashley@email.com"));

        CreateGameResult result = facade.createGame(new CreateGameRequest(reg.authToken(), "ashley's game"));
        assertTrue(result.gameID() > 0);
    }

    @Test
    public void createGameFail() {
        assertThrows(ResponseException.class, () ->
                facade.createGame(new CreateGameRequest("badtoken", "ashley's game")));
    }

    @Test
    public void joinGameSuccess() throws ResponseException {
        RegisterResult reg = facade.register(new RegisterRequest("ashley", "password", "ashley@email.com"));
        CreateGameResult game = facade.createGame(new CreateGameRequest(reg.authToken(), "ashley's game"));

        facade.joinGame(new JoinGameRequest(reg.authToken(), "WHITE", game.gameID()));
        assertTrue(true);
    }

    @Test
    public void joinGameFail() throws ResponseException {
        RegisterResult reg = facade.register(new RegisterRequest("ashley", "password", "ashley@email.com"));
        CreateGameResult game = facade.createGame(new CreateGameRequest(reg.authToken(), "ashley's game"));

        assertThrows(ResponseException.class, () ->
                facade.joinGame(new JoinGameRequest("badtoken", "WHITE", game.gameID())));
    }
}