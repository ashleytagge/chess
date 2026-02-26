package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import dataaccess.DataAccessException;

public class ClearService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public void clearUsers() throws DataAccessException {
        userDAO.clearUsers();
    }
    public void clearGames() throws DataAccessException {
        gameDAO.clearGames();

    }
    public void clearAuths() throws DataAccessException{
        authDAO.clearAuths();
    }
}
