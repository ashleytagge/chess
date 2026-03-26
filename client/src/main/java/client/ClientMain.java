package client;

import chess.*;
import com.google.gson.Gson;
import exception.ResponseException;

import java.util.Arrays;
import java.util.Scanner;

import static UI.EscapeSequences.*;

import model.GameData;
import model.request.*;
import model.result.*;
import webSocketMessages.Notification;


public class ClientMain {
    private String username = null;
    private String authToken = null;
    private String email = null;
    private String password = null;
    private final ServerFacade server;
    private State state = State.SIGNEDOUT;

    public ClientMain(String serverUrl) throws ResponseException {
        server = new ServerFacade(serverUrl);
    }

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
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
        ListGamesRequest request = new ListGamesRequest(authToken);
        ListGamesResult games = server.listGames(request);
        var result = new StringBuilder();
        var gson = new Gson();
        for (GameData game : games) {
            result.append(gson.toJson(game)).append('\n');
        }
        return result.toString();
    }

    public String logout() throws ResponseException {
        assertSignedIn();
        //get authToken
        //get username
        server.logout(authToken);
        state = State.SIGNEDOUT;
        return String.format("%s left the shop", username);
    }

    public String createGame(String... params) throws ResponseException{
        assertSignedIn();
        //get the authToken
        CreateGameRequest request = new CreateGameRequest(authToken);
        CreateGameResult result = server.createGame(request);
        return String.valueOf(result.gameID());
    }

    public String joinGame(String... params) throws ResponseException{
        assertSignedIn();
        //get gameID and player color from user
        JoinGameRequest request = new JoinGameRequest(authToken, playerColor, gameID);
        JoinGameResult result = server.joinGame(request);
        return String.format("You have successfully joined game: %s as player %s", gameID.toString(), playerColor);
    }

    public String observeGame(String... params) throws ResponseException{
        assertSignedIn();
        assertSignedIn();
        //get gameID from user
        //call observe game
        return String.format("You have successfully joined game: %s as an observer.", gameID.toString());
    }

    public String login(String... params) throws ResponseException {
        if (params.length >= 1) {
            state = State.SIGNEDIN;
            username = String.join("-", params);
            //get username and password from the user
            LoginRequest request = new LoginRequest(username, password);
            LoginResult result = server.login(request);
            return String.format("You signed in as %s.", result.username());
        }
        throw new ResponseException(ResponseException.Code.ClientError, "Expected: <username>");
    }

    public String register(String... params) throws ResponseException{
        assertSignedIn();
        //get the username password and email from the user
        RegisterRequest request = new RegisterRequest(username, password, email);
        RegisterResult result = server.register(request);
        state = State.SIGNEDIN;
        return result.username() + result.authToken();
    }

    public void run() {
        System.out.println(WHITE_QUEEN + " Welcome to the pet store. Sign in to start." + WHITE_QUEEN);
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
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

    public void notify(Notification notification) {
        System.out.println(SET_TEXT_COLOR_RED + notification.message());
        printPrompt();
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
