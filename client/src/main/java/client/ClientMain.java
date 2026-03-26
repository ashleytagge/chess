package client;

import com.google.gson.Gson;
import exception.ResponseException;

import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;

import model.request.*;
import model.result.*;
import websocketmessages.Notification;


public class ClientMain {
    private String authToken = null;
    private String username = null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;

    public ClientMain(String serverUrl) throws ResponseException {
        server = new ServerFacade(serverUrl);
    }

    public static void main(String[] args) {
        try {
            String serverUrl = "http://localhost:8080"; // change if needed
            ClientMain client = new ClientMain(serverUrl);
            client.run();
        } catch (ResponseException e) {
            System.out.println("Failed to start client: " + e.getMessage());
        }
    }

    //game start
    //press help to begin
    //print not logged in menu
    //if they are logged in
        //print logged in menu
    //if they log out
        //print not logged in menu
    //if they are in a game
        //print game play menu

    public String listGames() throws ResponseException {
        assertSignedIn();
        //get the authToken
        ListGamesRequest request = new ListGamesRequest(this.authToken);
        ListGamesResult games = server.listGames(request);
        var result = new StringBuilder();
        var gson = new Gson();
        for (ListGamesResult.GameData game : games.games()) {
            result.append("ID: ").append(game.gameID())
                    .append(" Name: ").append(game.gameName())
                    .append('\n');
        }
        return result.toString();
    }

    public String logout() throws ResponseException {
        assertSignedIn();
        //get authToken
        //get username
        LogoutRequest request = new LogoutRequest(this.authToken);
        server.logout(request);
        this.authToken = null;
        this.username = null;
        state = State.SIGNEDOUT;
        return "You have logged out";
    }

    public String createGame(String... params) throws ResponseException{
        assertSignedIn();
        //get the gameName
        String gameName = params[0];
        CreateGameRequest request = new CreateGameRequest(this.authToken, gameName);
        CreateGameResult result = server.createGame(request);
        return String.valueOf(result.gameID());
    }

    public String joinGame(String... params) throws ResponseException{
        assertSignedIn();
        //get gameID and player color from user
        String playerColor = params[0];
        String gameID = params[1];
        JoinGameRequest request = new JoinGameRequest(this.authToken, playerColor, Integer.parseInt(gameID));
        server.joinGame(request);
        return String.format("You have successfully joined game: %s as player %s", gameID, playerColor);
    }

    public String observeGame(String... params) throws ResponseException{
        assertSignedIn();
        assertSignedIn();
        //get gameID from user
        String gameID = params[0];
        //call observe game
        return String.format("You have successfully joined game: %s as an observer.", Integer.parseInt(gameID));
    }

    public String login(String... params) throws ResponseException {
        if (params.length == 2) {
            String username = params[0];
            String password = params[1];
            LoginRequest request = new LoginRequest(username, password);
            LoginResult result = server.login(request);
            this.authToken = result.authToken();
            this.username = username;
            state = State.SIGNEDIN;
            return String.format("You signed in as %s.", result.username());
        }
        throw new ResponseException(ResponseException.Code.ClientError, "Expected: <username>");
    }

    public String register(String... params) throws ResponseException{
        //get the username password and email from the user
        if (params.length != 3) {
            throw new ResponseException(ResponseException.Code.ClientError,
                    "Expected: register <USERNAME> <PASSWORD> <EMAIL>");
        }

        String username = params[0];
        String password = params[1];
        String email = params[2];

        RegisterRequest request = new RegisterRequest(username, password, email);
        RegisterResult result = server.register(request);
        this.authToken = result.authToken();
        this.username = username;
        state = State.SIGNEDIN;

        return String.format("You signed in as %s.", result.username());
    }

    public void run() {
        System.out.println(WHITE_QUEEN + " Welcome to 240 chess. Type help to get started." + WHITE_QUEEN);
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!"quit".equals(result)) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + SET_TEXT_COLOR_GREEN);
    }


    public String eval(String input) {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> login(params);
                case "register" -> register(params);
                case "list" -> listGames();
                case "logout" -> logout();
                case "create" -> createGame(params);
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                    login <USERNAME> <PASSWORD> - to play chess
                    quit - playing chess
                    help - with possible commands
                    """;
        }
        return """
                create <NAME> - a game
                list - games
                join <ID> [WHITE|BLACK] - a game
                observe <ID> - a game
                logout - when you are done
                quit - playing chess
                help - with possible commands
                """;
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(ResponseException.Code.ClientError, "You must sign in");
        }
    }
}
