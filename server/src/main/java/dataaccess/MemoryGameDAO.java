package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    final private HashMap<Integer, GameData> games = new HashMap<>();

    public void createGame(GameData gameData) throws DataAccessException {
        games.put(gameData.gameID(), gameData);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        if(games.containsKey(gameID)){
            return games.get(gameID);
        }else{
            throw new DataAccessException("bad request");
        }
    }
    public Collection<GameData> listGames() {
        return games.values();
    }
    public void updateGame(GameData game) throws DataAccessException {
        if(games.containsKey(game.gameID())){
             games.put(game.gameID(), game);
        }else{
            throw new DataAccessException("bad request");
        }
    }
    public void clear() {
        games.clear();
    }
}
