package dataaccess;

import model.GameData;

import java.util.List;

public interface GameDAO {
    void createGame() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    List<GameData> getAllGames() throws DataAccessException;
    void addPlayer() throws DataAccessException;
    void updateGame(int gameID) throws DataAccessException;
    void clearGames() throws DataAccessException;
}
