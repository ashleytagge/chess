package service;

import dataaccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.LoginRequest;
import service.request.LogoutRequest;
import service.request.RegisterRequest;
import service.result.LoginResult;
import service.result.LogoutResult;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTests {

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
    void registerSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("registerTestUser", "testPassword", "testEmail");
        RegisterResult result = service.register(request);

        assertEquals("registerTestUser", result.username());
        assertNotNull(result.authToken());
    }
    @Test
    void registerNullUsername() throws DataAccessException {
        RegisterRequest request = new RegisterRequest(null, "testpassword", "testemail");
        assertThrows(DataAccessException.class, () -> service.register(request));
    }

    @Test
    void loginSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult result = service.login(loginRequest);

        assertEquals("loginTestUser", result.username());
        assertNotNull(result.authToken());
    }

    @Test
    void loginNull() throws DataAccessException {
        LoginRequest request = new LoginRequest(null, null);
        assertThrows(DataAccessException.class, () -> service.login(request));
    }

    @Test
    void loginNotRegistered() throws DataAccessException {
        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        assertThrows(DataAccessException.class, () -> service.login(loginRequest));
    }

    @Test
    void logoutSuccess() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult loginResult = service.login(loginRequest);

        LogoutRequest logoutRequest = new LogoutRequest(loginResult.authToken());
        service.logout(logoutRequest);
        assertThrows(DataAccessException.class, () -> authDAO.getAuth(loginResult.authToken()));

    }

    @Test
    void logoutBadToken() throws DataAccessException {
        RegisterRequest registerRequest = new RegisterRequest("loginTestUser", "loginTestPassword", "loginTestEmail");
        RegisterResult registerResult = service.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest("loginTestUser", "loginTestPassword");
        LoginResult loginResult = service.login(loginRequest);

        LogoutRequest logoutRequest = new LogoutRequest("badToken");
        assertThrows(DataAccessException.class, () -> service.logout(logoutRequest));

    }
}
