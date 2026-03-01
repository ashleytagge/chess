package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import service.request.LoginRequest;
import service.request.LogoutRequest;
import service.request.RegisterRequest;
import service.result.LoginResult;
import service.result.RegisterResult;
import dataaccess.DataAccessException;

import static service.GenerateToken.generateToken;

public class UserService {
    /*
    *To do their work, service classes
    *need to make heavy use of the Model
    *classes and Data Access classes
    *described above.
    */
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        //validate the registerRequest
        if(registerRequest == null || registerRequest.email() == null || registerRequest.username() == null || registerRequest.password() == null){
            throw new DataAccessException("bad request");
        }
        //create the user
        UserData user = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
        //add the info and check if username already exists and throw if it does
        userDAO.insertUser(user);
        //create auth
        String authToken = generateToken();
        //add auth
        AuthData auth = new AuthData(authToken, registerRequest.username());
        authDAO.insertAuth(auth);
        //return username and auth
        return new RegisterResult(registerRequest.username(), authToken);
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        if(loginRequest == null || loginRequest.username() == null || loginRequest.password() == null){
            throw new DataAccessException("unauthorized");
        }

        UserData user = userDAO.getUser(loginRequest.username());

        if (!user.password().equals(loginRequest.password())) {
            throw new DataAccessException("unauthorized");
        }
        String token = generateToken();
        AuthData auth = new AuthData(token, user.username());
        authDAO.insertAuth(auth);

        return new LoginResult(user.username(), token);
    }
    public void logout(LogoutRequest logoutRequest) throws DataAccessException {
        if (logoutRequest == null || logoutRequest.authToken() == null) {
            throw new DataAccessException("bad request");
        }
        authDAO.getAuth(logoutRequest.authToken());
        authDAO.deleteAuth(logoutRequest.authToken());
    }
}
