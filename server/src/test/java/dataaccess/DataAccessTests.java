package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DataAccessTests {

    private UserDAO getUserDAO(Class<? extends UserDAO> daoClass) throws DataAccessException {
        UserDAO userDAO = new MySQLUserDAO();
        userDAO.clear();
        return userDAO;
    }

    private AuthDAO getAuthDAO(Class<? extends AuthDAO> daoClass) throws DataAccessException {
        AuthDAO authDAO = new MySQLAuthDAO();
        authDAO.clear();
        return authDAO;
    }

    private GameDAO getGameDAO(Class<? extends GameDAO> daoClass) throws DataAccessException {
        GameDAO gameDAO = new MySQLGameDAO();
        gameDAO.clear();
        return gameDAO;
    }
}
