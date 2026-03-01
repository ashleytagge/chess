package dataaccess;

import model.UserData;

public interface UserDAO {
    /*
    Add User Data
    Get User Data

    Create objects in the data store
    Read objects from the data store
    Update objects already in the data store
    Delete objects from the data store
     */

    //void insertUser(UserData u) throws DataAccessException

    void insertUser(UserData user) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    void clear() throws DataAccessException;
}
