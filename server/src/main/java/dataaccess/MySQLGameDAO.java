package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Types.NULL;

public class MySQLGameDAO implements GameDAO{

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    public void createGame(GameData gameData) throws DataAccessException {
        //GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game)
        var statement = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        String json = new Gson().toJson(gameData.game());
        executeUpdate(statement, gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), json);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String whiteUsername = rs.getString("whiteUsername");
                        String blackUsername = rs.getString("blackUsername");
                        String gameName = rs.getString("gameName");
                        String json = rs.getString("game");
                        ChessGame game = new Gson().fromJson(json, ChessGame.class);
                        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
                    }else{
                        throw new DataAccessException("bad request");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to read database: " + e.getMessage());
        }
    }

    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> result = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("gameID");
                        String white = rs.getString("whiteUsername");
                        String black = rs.getString("blackUsername");
                        String name = rs.getString("gameName");
                        String json = rs.getString("game");

                        ChessGame game = new Gson().fromJson(json, ChessGame.class);

                        result.add(new GameData(id, white, black, name, game));
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException("unable to update database: " + e.getMessage());        }
        return result;
    }

    public void updateGame(GameData gameData) throws DataAccessException {
        String json = new Gson().toJson(gameData.game());
        var statement = "UPDATE game SET whiteUsername=?, blackUsername=?, gameName=?, game=? WHERE gameID=?";
        executeUpdate(statement, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), json, gameData.gameID());
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE game";
        executeUpdate(statement);
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to update database: " + e.getMessage());
        }
    }
    //var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game WHERE gameID=?";
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  game (
              `gameID` INT NOT NULL,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
              `gameName` varchar(256) NOT NULL,
              `game` varchar(256) NOT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("unable to update database: " + e.getMessage());
        }
    }
}