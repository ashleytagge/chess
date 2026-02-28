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

    public void insertUser(UserData user){
        //insert user
        //throw exception if user alrady exists don't forget this here
    }
    public String getUser(){
        /*
         * Retrieve a user with the given username.
         * */
        //if username doesn't exist throw DataAccessException
        return "username";
    }
    public void clearUsers(){}
}
