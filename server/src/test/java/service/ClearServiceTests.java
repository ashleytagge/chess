package service;

import dataaccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClearServiceTests {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();
    final ClearService service = new ClearService(userDAO, authDAO, gameDAO);

    @Test
    void clear() throws DataAccessException {
        //create some data here so it has something to clear
        service.clear();
    }
}
