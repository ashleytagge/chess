package dataaccess;

import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO {

    final private HashMap<Integer, GameData> games = new HashMap<>();

    void createGame(GameData gameData) throws DataAccessException {
        games.put(gameData.gameID(), gameData);
    }

    GameData getGame(int gameID) throws DataAccessException {
        if(games.containsKey(gameID)){
            return games.get(gameID);
        }else{
            throw new DataAccessException("game does not exist");
        }
    }
    HashMap<Integer, GameData> listGames() {
        return games;
    }
    void updateGame(GameData game) throws DataAccessException {
        if(games.containsKey(game.gameID())){
             games.put(game.gameID(), game);
        }else{
            throw new DataAccessException("bad request");
        }
    }
    void clearGames() {
        games.clear();
    }
}
