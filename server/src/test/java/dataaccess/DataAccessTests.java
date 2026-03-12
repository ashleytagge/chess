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

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class})
    void insertUserPositive(Class<? extends UserDAO> userDAOClass) throws Exception {
        UserDAO userDAO = getUserDAO(userDAOClass);
        assertDoesNotThrow(() ->
                userDAO.insertUser(new UserData("spencer", "password", "spenytagg6@gmail.com")));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class})
    void insertUserNegative(Class<? extends UserDAO> userDAOClass) throws Exception {
        UserDAO userDAO = getUserDAO(userDAOClass);
        userDAO.insertUser(new UserData("spencer", "password", "spenytagg6@gmail.com"));

        assertThrows(DataAccessException.class, () ->
                userDAO.insertUser(new UserData("spencer", "password", "spenytagg6@gmail.com")));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class})
    void getUserPositive(Class<? extends UserDAO> userDAOClass) throws Exception {
        UserDAO userDAO = getUserDAO(userDAOClass);
        userDAO.insertUser(new UserData("spencer", "password", "spenytagg6@gmail.com"));

        UserData user = userDAO.getUser("spencer");

        assertEquals("spencer", user.username());
        assertEquals("password", user.password());
        assertEquals("spenytagg6@gmail.com", user.email());
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class})
    void getUserNegative(Class<? extends UserDAO> userDAOClass) throws Exception {
        UserDAO userDAO = getUserDAO(userDAOClass);
        assertThrows(DataAccessException.class, () ->
                userDAO.getUser("booty"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLUserDAO.class})
    void clearUserPositive(Class<? extends UserDAO> clearDAOClass) throws Exception {
        UserDAO userDAO = getUserDAO(clearDAOClass);
        userDAO.insertUser(new UserData("ashley", "ashpass", "ashemail"));
        userDAO.clear();

        assertThrows(DataAccessException.class, () ->
                userDAO.getUser("ashley"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class})
    void insertAuthPositive(Class<? extends AuthDAO> authDAOClass) throws Exception {
        AuthDAO authDAO = getAuthDAO(authDAOClass);
        assertDoesNotThrow(() ->
                authDAO.insertAuth(new AuthData("spenytoken", "speny")));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class})
    void insertAuthNegative(Class<? extends AuthDAO> authDAOClass) throws Exception {
        AuthDAO authDAO = getAuthDAO(authDAOClass);
        authDAO.insertAuth(new AuthData("spenytoken", "speny"));

        assertThrows(DataAccessException.class, () ->
                authDAO.insertAuth(new AuthData("spenytoken", "speny")));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class})
    void getAuthPositive(Class<? extends AuthDAO> authDAOClass) throws Exception {
        AuthDAO authDAO = getAuthDAO(authDAOClass);
        authDAO.insertAuth(new AuthData("spenytoken", "speny"));

        AuthData auth = authDAO.getAuth("spenytoken");

        assertEquals("spenytoken", auth.authToken());
        assertEquals("speny", auth.username());
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class})
    void getAuthNegative(Class<? extends AuthDAO> authDAOClass) throws Exception {
        AuthDAO authDAO = getAuthDAO(authDAOClass);
        assertThrows(DataAccessException.class, () ->
                authDAO.getAuth("badToken"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class})
    void deleteAuthPositive(Class<? extends AuthDAO> authDAOClass) throws Exception {
        AuthDAO authDAO = getAuthDAO(authDAOClass);
        authDAO.insertAuth(new AuthData("pingpong", "chalamet"));

        authDAO.deleteAuth("pingpong");

        assertThrows(DataAccessException.class, () ->
                authDAO.getAuth("pingpong"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLAuthDAO.class})
    void clearAuthPositive(Class<? extends AuthDAO> authDAOClass) throws Exception {
        AuthDAO authDAO = getAuthDAO(authDAOClass);
        authDAO.insertAuth(new AuthData("pingpong", "chalamet"));
        authDAO.clear();

        assertThrows(DataAccessException.class, () ->
                authDAO.getAuth("pingpong"));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class})
    void createGamePositive(Class<? extends GameDAO> gameDAOClass) throws Exception {
        GameDAO gameDAO = getGameDAO(gameDAOClass);

        int id = gameDAO.createGame(
                new GameData(0, "white", "black", "game", new ChessGame()));

        assertTrue(id > 0);
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class})
    void getGamePositive(Class<? extends GameDAO> gameDAOClass) throws Exception {
        GameDAO gameDAO = getGameDAO(gameDAOClass);

        int id = gameDAO.createGame(
                new GameData(0, "white", "black", "game", new ChessGame()));

        GameData game = gameDAO.getGame(id);

        assertEquals("white", game.whiteUsername());
        assertEquals("black", game.blackUsername());
        assertEquals("game", game.gameName());
        assertNotNull(game.game());
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class})
    void getGameNegative(Class<? extends GameDAO> gameDAOClass) throws Exception {
        GameDAO gameDAO = getGameDAO(gameDAOClass);
        assertThrows(DataAccessException.class, () ->
                gameDAO.getGame(4567));
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class})
    void listGamesPositive(Class<? extends GameDAO> gameDAOClass) throws Exception {
        GameDAO gameDAO = getGameDAO(gameDAOClass);

        gameDAO.createGame(new GameData(0, "whiteash", "blackash", "game1", new ChessGame()));
        gameDAO.createGame(new GameData(0, "whitespen", "blackspen", "game2", new ChessGame()));

        Collection<GameData> games = gameDAO.listGames();

        assertEquals(2, games.size());
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class})
    void updateGamePositive(Class<? extends GameDAO> gameDAOClass) throws Exception {
        GameDAO gameDAO = getGameDAO(gameDAOClass);

        int id = gameDAO.createGame(
                new GameData(0, "white", "black", "game", new ChessGame()));

        gameDAO.updateGame(
                new GameData(id, "white2", "black2", "game2", new ChessGame()));

        GameData updated = gameDAO.getGame(id);

        assertEquals("white2", updated.whiteUsername());
        assertEquals("black2", updated.blackUsername());
        assertEquals("game2", updated.gameName());
    }

    @ParameterizedTest
    @ValueSource(classes = {MySQLGameDAO.class})
    void clearGamePositive(Class<? extends GameDAO> gameDAOClass) throws Exception {
        GameDAO gameDAO = getGameDAO(gameDAOClass);

        gameDAO.createGame(new GameData(0, "white", "black", "game", new ChessGame()));
        gameDAO.clear();

        assertEquals(0, gameDAO.listGames().size());
    }
}
