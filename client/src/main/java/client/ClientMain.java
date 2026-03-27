package client;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;

import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;

import model.request.*;
import model.result.*;
import ui.PrintBoard;


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

    public String listGames() throws ResponseException {
        assertSignedIn();
        //get the authToken
        ListGamesRequest request = new ListGamesRequest(this.authToken);
        ListGamesResult games = server.listGames(request);
        var result = new StringBuilder();
        var gson = new Gson();
        for (ListGamesResult.GameData game : games.games()) {
            result.append(game.gameID()).append(".")
                    .append(" Game Name: ").append(game.gameName())
                    .append(", White Player: ").append(game.whiteUsername())
                    .append(", Black Player: ").append(game.blackUsername()).append('\n');
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
        return "You have withdrawn from the court. May you return in due time.";
    }

    public String createGame(String... params) throws ResponseException{
        assertSignedIn();
        //get the gameName
        if (params.length != 1) {
            throw new ResponseException(ResponseException.Code.ClientError,
                    "Expected: create <GAMENAME>");
        }
        String gameName = params[0];
        CreateGameRequest request = new CreateGameRequest(this.authToken, gameName);
        CreateGameResult result = server.createGame(request);
        return String.format("Match %s. %s has been forged. Step forth and join the battle!", result.gameID(), gameName);
    }

    public String joinGame(String... params) throws ResponseException{
        assertSignedIn();
        //get gameID and player color from user
        if (params.length != 2) {
            throw new ResponseException(ResponseException.Code.ClientError,
                    "Expected: join <ID> [WHITE|BLACK]");
        }
        String gameID = params[0];
        String playerColor = params[1];

        try {
            int id = Integer.parseInt(gameID);
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseException.Code.ClientError,
                    "Hear ye, hear ye! The game ID must be a number!");
        }
        if (!playerColor.equals("WHITE") && !playerColor.equals("BLACK")){
            throw new ResponseException(ResponseException.Code.ClientError,
                    "You must enter your desired player color as 'WHITE' or 'BLACK'.");
        }
        JoinGameRequest request = new JoinGameRequest(this.authToken, playerColor, Integer.parseInt(gameID));
        try{
            server.joinGame(request);
        }catch(Exception e){
            if (e.getMessage().contains("already taken")){
                return "Another champion has already claimed that side of the board. Please try again.";
            }else if (e.getMessage().contains("bad request")){
                return "Alas- You chose a game that does not exist";
            }else{
                System.out.print(e.getMessage());
            }
        }
        ChessBoard board = new ChessGame().getBoard();
        if(playerColor.equals("WHITE")){
            PrintBoard.drawBoard(board, ChessGame.TeamColor.WHITE);
        }else{
            PrintBoard.drawBoard(board, ChessGame.TeamColor.BLACK);
        }
        state = State.GAMEPLAY;
        return String.format("You have joined match %s as %s, Your Majesty. May your moves be wise and your victory swift.", gameID, playerColor);
    }

    public String endGame (){
        state = State.SIGNEDIN;
        System.out.println(SET_TEXT_COLOR_BLUE + "You have left the game." + RESET);
        return "Type 'help' to review your command options, Your Majesty.";
    }

    public String observeGame(String... params) throws ResponseException{
        assertSignedIn();
        //get gameID from user
        if (params.length != 1) {
            throw new ResponseException(ResponseException.Code.ClientError,
                    "Expected: observe <ID>");
        }
        String gameID = params[0];
        try {
            int id = Integer.parseInt(gameID);
        } catch (NumberFormatException e) {
            throw new ResponseException(ResponseException.Code.ClientError,
                    "Hear ye, hear ye! The game ID must be a number!");
        }
        //check if game exists
        ListGamesRequest request = new ListGamesRequest(this.authToken);
        ListGamesResult games = server.listGames(request);

        boolean exists = games.games().stream()
                .anyMatch(game -> game.gameID() == Integer.parseInt(gameID));
        if (!exists) {
            throw new ResponseException(ResponseException.Code.ClientError,
                    "That game doesn't exist.");
        }
        //call observe game
        ChessBoard board = new ChessGame().getBoard();
        PrintBoard.drawBoard(board, ChessGame.TeamColor.WHITE);
        return String.format("You now stand as a watcher of match %s. Let the battle commence!.", Integer.parseInt(gameID));
    }

    public String login(String... params) throws ResponseException {
        if (params.length == 2) {
            String username = params[0];
            String password = params[1];
            LoginRequest request = new LoginRequest(username, password);
            LoginResult result;
            try {
                result = server.login(request);
            }catch(Exception e){
                return "Incorrect username or password.";
            }
            this.authToken = result.authToken();
            this.username = username;
            state = State.SIGNEDIN;
            return String.format("You are signed in as %s. Welcome back Your Majesty, your court stands ready.", result.username());
        }
        throw new ResponseException(ResponseException.Code.ClientError, "Expected: login <username> <password>");
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
        RegisterResult result;
        try {
            result = server.register(request);
        }catch(Exception e){
            return "That username already exists! Please choose a different one.";
        }
        this.authToken = result.authToken();
        this.username = username;
        state = State.SIGNEDIN;

        return String.format("You are signed in as %s. Welcome Your Majesty, your court stands ready.", result.username());
    }

    public String clear() throws ResponseException{
        server.clear();
        return "You have successfully cleared the realm";
    }

    public void run() {
        System.out.println(WHITE_QUEEN + SET_TEXT_COLOR_BLUE + " Welcome to the Royal Chess Court" + RESET_TEXT_COLOR + WHITE_QUEEN);
        System.out.println(SET_TEXT_COLOR_BLUE + "Type 'help' to begin your game, Your Majesty.");
        System.out.println(SET_TEXT_COLOR_BLUE + "You may type 'help' at any time to review your available commands.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!"quit".equals(result)) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result + RESET);
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
            String[] tokens = input.trim().split("\\s+");
            String cmd = tokens[0].toLowerCase();
            if (cmd.isEmpty()){
                cmd = "help";
            }
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
                case "clear" -> clear();
                case "leave" -> endGame();
                default -> help();
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    ♔ The Royal Entrance ♔
                    register <USERNAME> <PASSWORD> <EMAIL> - to enter the realm
                    login <USERNAME> <PASSWORD> - to return to your throne
                    quit - to depart the court
                    help - to view your available commands
                    clear - to reset the realm (for testing)
                    """;
        }
        if (state == State.GAMEPLAY) {
            return """
                    ♔ GAMEPLAY MENU ♔
                    leave - to leave the match and return to the Royal Command Menu
                    quit - to depart the court
                    help - to view your available commands
                    clear - to reset the realm (for testing)
                    """;
        }
        return """
                ♔ The Royal Command Menu ♔
                create <NAME> - to forge a new match
                list - to view all active matches
                join <ID> [WHITE|BLACK] - to enter the match as a player
                observe <ID> - a game
                logout - to leave the court
                quit - to depart the court
                help - to view your available commands
                clear - to reset the realm (for testing)
                """;
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException(ResponseException.Code.ClientError, "You must sign in before returning to your throne");
        }
    }
}
