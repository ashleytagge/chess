package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO {

    final private HashMap<String, UserData> users = new HashMap<>();

    void insertUser(UserData userData) throws DataAccessException {
        if(users.containsKey(userData.username())){
            throw new DataAccessException("already taken");
        }
        users.put(userData.username(), userData);
    }

    UserData getUser(String username) throws DataAccessException {

        if(users.containsKey(username)){
            return users.get(username);
        }else{
            throw new DataAccessException("bad request");
        }
    }

    void clearUsers() throws DataAccessException {
        users.clear();
    }

}
