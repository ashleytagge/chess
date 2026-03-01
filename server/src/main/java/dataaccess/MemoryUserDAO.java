package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO{

    final private HashMap<String, UserData> users = new HashMap<>();

    public void insertUser(UserData userData) throws DataAccessException {
        if(users.containsKey(userData.username())){
            throw new DataAccessException("already taken");
        }
        users.put(userData.username(), userData);
    }

    public UserData getUser(String username) throws DataAccessException {
        if(users.containsKey(username)){
            return users.get(username);
        }else{
            throw new DataAccessException("bad request");
        }
    }

    public void clear() throws DataAccessException {
        users.clear();
    }

}
