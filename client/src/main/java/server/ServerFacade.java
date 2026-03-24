package server;

import com.google.gson.Gson;
import exception.ResponseException;
import model.*;
import model.request.*;
import model.result.*;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws ResponseException {
        var request = buildRequest("POST", "/user", null, registerRequest);
        var response = sendRequest(request);
        return handleResponse(response, RegisterResult.class);
    }
    public LoginResult login(LoginRequest loginRequest) throws ResponseException {
        var request = buildRequest("POST", "/session", null, loginRequest);
        var response = sendRequest(request);
        return handleResponse(response, LoginResult.class);
    }

    public LogoutResult logout(LogoutRequest logoutRequest) throws ResponseException {
        var request = buildRequest("DELETE", "/session", logoutRequest.authToken(), null);
        var response = sendRequest(request);
        return handleResponse(response, LogoutResult.class);
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws ResponseException {
        var request = buildRequest("GET", "/game", listGamesRequest.authToken(), null);
        var response = sendRequest(request);
        return handleResponse(response, ListGamesResult.class);
    }
    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws ResponseException {
        var request = buildRequest("POST", "/game", createGameRequest.authToken(), createGameRequest);
        var response = sendRequest(request);
        return handleResponse(response, CreateGameResult.class);
    }
    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws ResponseException {
        var request = buildRequest("PUT", "/game", joinGameRequest.authToken(), joinGameRequest);
        var response = sendRequest(request);
        return handleResponse(response, JoinGameResult.class);
    }

    private HttpRequest buildRequest(String method, String path, String authToken, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path));

        if (authToken != null) {
            request.header("authorization", authToken);
        }
        if (body != null) {
            request.header("Content-Type", "application/json");
            request.method(method, makeRequestBody(body));
        } else {
            request.method(method, BodyPublishers.noBody());
        }
        return request.build();
    }

    private BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return BodyPublishers.noBody();
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws ResponseException {
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws ResponseException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw ResponseException.fromJson(body);
            }

            throw new ResponseException(ResponseException.fromHttpStatusCode(status), "other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
