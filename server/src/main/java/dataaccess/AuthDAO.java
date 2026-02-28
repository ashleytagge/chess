package dataaccess;

import model.AuthData;

public interface AuthDAO {
    /*
    Add Auth Data
    Delete Auth Token
    Get Auth Data

    Create objects in the data store
    Read objects from the data store
    Update objects already in the data store
    Delete objects from the data store
     */
    void insertAuth(AuthData authData) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clearAuths() throws DataAccessException;

}
