package server.handler;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import service.UserService;
import service.request.ListGamesRequest;
import service.request.LoginRequest;
import service.request.LogoutRequest;
import service.request.RegisterRequest;
import service.result.ListGamesResult;
import service.result.LoginResult;
import service.result.LogoutResult;
import service.result.RegisterResult;

import java.util.Map;

public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService){
        this.userService = userService;
    }


    public void register(Context ctx) throws DataAccessException {
        //username, password, email
        RegisterRequest body = new Gson().fromJson(ctx.body(), RegisterRequest.class);
        if(body == null || body.username() == null || body.password() == null || body.email() == null){
            throw new DataAccessException("bad request");
        }
        RegisterRequest request = new RegisterRequest(body.username(), body.password(), body.email());
        RegisterResult result = userService.register(request);
        ctx.contentType("application/json");
        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }

    public void login(Context ctx) throws DataAccessException {
        LoginRequest body = new Gson().fromJson(ctx.body(), LoginRequest.class);
        if(body == null || body.username() == null || body.password() == null){
            throw new DataAccessException("bad request");
        }
        LoginRequest request = new LoginRequest(body.username(), body.password());
        LoginResult result = userService.login(request);
        ctx.contentType("application/json");
        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }

    public void logout(Context ctx) throws DataAccessException {
        LogoutRequest request = new LogoutRequest(ctx.header("authorization"));
        //String authToken
        userService.logout(request);
        ctx.contentType("application/json");
        ctx.status(200);
        ctx.result(new Gson().toJson(Map.of()));
    }

}
