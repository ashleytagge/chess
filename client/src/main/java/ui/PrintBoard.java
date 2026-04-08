package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Objects;

public class PrintBoard {

    public static void drawBoard(ChessBoard board, ChessGame.TeamColor playerColor) {
        //is it white or black
        boolean isWhite = playerColor == ChessGame.TeamColor.WHITE;
        System.out.print(EscapeSequences.RESET);
        printHeaderFooter(isWhite);

        //print out alternating colored tiles nested for loop for rows and columns
        for (int row = 0; row < 8; row++) {

            //get orientation for rows/rank labels and print out
            int rank;
            if(isWhite){
                rank = 8 - row;
            }else{
                rank = row + 1;
            }
            System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            System.out.print(" " + rank + " ");
            System.out.print(EscapeSequences.RESET);

            for (int column = 0; column < 8; column++) {
                int file;
                if (isWhite) {
                    file = column + 1;
                } else {
                    file = 8 - column;
                }
                //figure out a way to determine light or dark square
                //print out the square with the right color
                if(file % 2 != 0 && rank % 2 == 0 || file % 2 == 0 && rank % 2 != 0){
                    System.out.print(EscapeSequences.SET_BG_COLOR_CREAM);
                }else{
                    System.out.print(EscapeSequences.SET_BG_COLOR_BROWN);
                }

                //get cute chess piece yayayay fun part
                ChessPiece piece = board.getPiece(new ChessPosition(rank, file));
                String symbol = getString(piece);

                if(piece != null){
                    if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
                    } else {
                        System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
                    }
                }

                System.out.print(symbol);
                System.out.print(EscapeSequences.RESET);
            }

            System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            System.out.print(" " + rank + " ");
            System.out.print(EscapeSequences.RESET);
            System.out.println();
        }

        printHeaderFooter(isWhite);
        System.out.print(EscapeSequences.RESET);
    }

    private static String getString(ChessPiece piece) {
        String symbol = "";

        if (piece == null){
            return EscapeSequences.EM_SPACE;
        }

        switch (Objects.requireNonNull(piece).getPieceType()) {
            case KING ->  symbol = EscapeSequences.BLACK_KING;
            case QUEEN ->  symbol = EscapeSequences.BLACK_QUEEN;
            case BISHOP ->  symbol = EscapeSequences.BLACK_BISHOP;
            case KNIGHT ->  symbol = EscapeSequences.BLACK_KNIGHT;
            case ROOK ->  symbol = EscapeSequences.BLACK_ROOK;
            case PAWN ->  symbol = EscapeSequences.BLACK_PAWN;
        }
        return symbol;
    }

    private static void printHeaderFooter(boolean whitePerspective) {

        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print("   ");
        System.out.print(EscapeSequences.RESET);

        for (int i = 0; i < 8; i++) {
            int file;
            char nextFile;

            if (!whitePerspective) {
                file = 'h' - i;
                nextFile = (char) (file);
            } else {
                file = 'a' + i;
                nextFile = (char) file;
            }
            System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            System.out.print(" " + nextFile + " ");
            System.out.print(EscapeSequences.RESET);
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print("   ");
        System.out.print(EscapeSequences.RESET);
        System.out.println();
    }
}