package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO {

    final private HashMap<String, AuthData> auths = new HashMap<>();

    public void insertAuth(AuthData authData) throws DataAccessException {
        if(auths.containsKey(authData.authToken())){
            throw new DataAccessException("Not Authorized");
        }
        auths.put(authData.authToken(), authData);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        if(auths.containsKey(authToken)){
            return auths.get(authToken);
        }else{
            throw new DataAccessException("Token does not exist");
        }
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        if(auths.containsKey(authToken)){
            auths.remove(authToken);
        }else{
            throw new DataAccessException("Token does not exist");
        }
    }

    public void clearAuths(){
        auths.clear();
    }

}
