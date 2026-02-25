package service;

import service.request.LoginRequest;
import service.request.LogoutRequest;
import service.request.RegisterRequest;
import service.result.LoginResult;
import service.result.RegisterResult;

public class UserService {
    /*
    *To do their work, service classes
    *need to make heavy use of the Model
    *classes and Data Access classes
    *described above.
    */

    public RegisterResult register(RegisterRequest registerRequest) {
        return new RegisterResult("username", "authToken");
    }
    public LoginResult login(LoginRequest loginRequest) {
        //if password is incorrect throw 401:UnauthorizedException
        return new LoginResult("username", "authToken");
    }
    public void logout(LogoutRequest logoutRequest) {}
}
