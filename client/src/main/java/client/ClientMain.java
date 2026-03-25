package client;

import chess.*;
import client.websocket.WebSocketFacade;
import exception.ResponseException;

import java.util.Arrays;
import java.util.Scanner;

import static UI.EscapeSequences.WHITE_QUEEN;
import static java.awt.Color.*;


public class ClientMain {
    private String visitorName = null;
    private final ServerFacade server;
    private final WebSocketFacade ws;
    private State state = State.SIGNEDOUT;

    public ClientMain(String serverUrl) throws ResponseException {
        server = new ServerFacade(serverUrl);
        ws = new WebSocketFacade(serverUrl, this);
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
                System.out.print(BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

    public void notify(Notification notification) {
        System.out.println(RED + notification.message());
        printPrompt();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET + ">>> " + GREEN);
    }


    public String eval(String input) {
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login" -> signIn(params);
                case "register" -> rescuePet(params);
                case "list" -> listPets();
                case "logout" -> signOut();
                case "create" -> adoptPet(params);
                case "join" -> adoptAllPets();
                case "observe" -> observe();
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
