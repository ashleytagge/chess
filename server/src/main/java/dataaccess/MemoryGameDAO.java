package dataaccess;

import model.GameData;

import java.util.HashMap;
import java.util.List;

public class MemoryGameDAO {

    final private HashMap<Integer, GameData> games = new HashMap<>();

    void createGame(int gameID) {

    }

    GameData getGame(int gameID) throws DataAccessException {
        if(games.containsKey(gameID)){
            return games.get(gameID);
        }else{
            throw new DataAccessException("game ID does not exist");
        }
    }
    List<GameData> getAllGames() {}
    void addPlayer() {}
    void updateGame(int gameID) {}
    void clearGames() {
        games.clear();
    }
}
