package service;

import dataaccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {
    //register
    //login
    //logout
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();
    final UserService service = new UserService(userDAO, authDAO);
    final ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);

    @BeforeEach
    void clear() throws DataAccessException {
        clearService.clear();
    }

    @Test
    void listGameSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("testuser", "testpassword", "testemail");
        RegisterResult result = service.register(request);

        assertEquals("testuser", result.username());
        assertNotNull(result.authToken());
    }
    @Test
    void listGamesWrongListSize() throws DataAccessException {
        RegisterRequest request = new RegisterRequest(null, "testpassword", "testemail");
        assertThrows(DataAccessException.class, () -> service.register(request));
    }

    @Test
    void createGameSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("testuser", "testpassword", "testemail");
        RegisterResult result = service.register(request);

        assertEquals("testuser", result.username());
        assertNotNull(result.authToken());
    }
    @Test
    void createGameNotAuthorized() throws DataAccessException {
        RegisterRequest request = new RegisterRequest(null, "testpassword", "testemail");
        assertThrows(DataAccessException.class, () -> service.register(request));
    }

    @Test
    void joinGameSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("testuser", "testpassword", "testemail");
        RegisterResult result = service.register(request);

        assertEquals("testuser", result.username());
        assertNotNull(result.authToken());
    }
    @Test
    void joinGameBadGameID() throws DataAccessException {
        RegisterRequest request = new RegisterRequest(null, "testpassword", "testemail");
        assertThrows(DataAccessException.class, () -> service.register(request));
    }

    @Test
    void joinGamePlayerTaken() throws DataAccessException {
        RegisterRequest request = new RegisterRequest(null, "testpassword", "testemail");
        assertThrows(DataAccessException.class, () -> service.register(request));
    }
    //positive listGames
    //negative listGames

    //positive createGame
    //negative createGame

    //positive joinGame
    //negative joinGame

}
