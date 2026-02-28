package dataaccess;

import model.GameData;

import java.util.List;

public interface GameDAO {
    /*
    Add Game
    Add Player to Game
    GetAllGames
    GetGameName

    Create objects in the data store
    Read objects from the data store
    Update objects already in the data store
    Delete objects from the data store
     */
    void createGame() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    List<GameData> getAllGames() throws DataAccessException;
    void addPlayer() throws DataAccessException;
    void updateGame(int gameID) throws DataAccessException;
        /*
        * It should replace the chess game
        * string corresponding to a given
        * gameID. This is used when players
        * join a game or when a move is made.
        */
    void clearGames() throws DataAccessException;
}
