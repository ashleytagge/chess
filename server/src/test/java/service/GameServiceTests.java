package service;

import dataaccess.*;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.*;
import service.result.*;

import static org.junit.jupiter.api.Assertions.*;
import static service.GenerateGameID.generateGameID;

public class GameServiceTests {
    //register
    //login
    //logout
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();

    final UserService service = new UserService(userDAO, authDAO);
    final ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);
    final GameService gameService = new GameService(userDAO, authDAO, gameDAO);

    @BeforeEach
    void clear() throws DataAccessException {
        clearService.clear();
    }

    @Test
    void listGameSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult loginResult = service.login(loginRequest);

        ListGamesRequest listGamesRequest = new ListGamesRequest(loginResult.authToken());
        ListGamesResult listGamesResult = gameService.listGames(listGamesRequest);
    }
    @Test
    void listGamesBadToken() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult loginResult = service.login(loginRequest);

        ListGamesRequest listGamesRequest = new ListGamesRequest("badToken");
        assertThrows(DataAccessException.class, () -> gameService.listGames(listGamesRequest));
    }

    @Test
    void createGameSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult loginResult = service.login(loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest(loginResult.authToken(), "testGameName");
        CreateGameResult createGameResult = gameService.createGame(createGameRequest);

        ListGamesRequest listGamesRequest = new ListGamesRequest(loginResult.authToken());
        ListGamesResult listGamesResult = gameService.listGames(listGamesRequest);

        assertEquals(1, listGamesResult.games().size());
    }

    @Test
    void createGameNotAuthorized() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult loginResult = service.login(loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("bad token", "testGameName");
        assertThrows(DataAccessException.class, () -> gameService.createGame(createGameRequest));

    }

    @Test
    void joinGameSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult loginResult = service.login(loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest(loginResult.authToken(), "testGameName");
        CreateGameResult createGameResult = gameService.createGame(createGameRequest);

        JoinGameRequest joinGameRequest = new JoinGameRequest(loginResult.authToken(), "WHITE", createGameResult.gameID());
        gameService.joinGame(joinGameRequest);

        GameData game = gameDAO.getGame(joinGameRequest.gameID());
        if(joinGameRequest.playerColor().equals("WHITE")){
            assertEquals("loginTestUser", game.whiteUsername());
        }else{
            assertEquals("loginTestUser", game.blackUsername());
        }
    }
    @Test
    void joinGameBadGameID() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult loginResult = service.login(loginRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest(loginResult.authToken(), "testGameName");
        CreateGameResult createGameResult = gameService.createGame(createGameRequest);

        JoinGameRequest joinGameRequest = new JoinGameRequest("bad token", "WHITE", 1234);
        assertThrows(DataAccessException.class, () -> gameService.joinGame(joinGameRequest));
    }
}
