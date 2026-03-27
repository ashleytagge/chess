package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class PrintBoard {

    private static String pieceToSymbol(ChessPiece piece) {
        if (piece == null){
            return EscapeSequences.EMPTY;
        }

        return switch (piece.getTeamColor()) {
            case WHITE -> switch (piece.getPieceType()) {
                case KING -> EscapeSequences.WHITE_KING;
                case QUEEN -> EscapeSequences.WHITE_QUEEN;
                case BISHOP -> EscapeSequences.WHITE_BISHOP;
                case KNIGHT -> EscapeSequences.WHITE_KNIGHT;
                case ROOK -> EscapeSequences.WHITE_ROOK;
                case PAWN -> EscapeSequences.WHITE_PAWN;
            };
            case BLACK -> switch (piece.getPieceType()) {
                case KING -> EscapeSequences.BLACK_KING;
                case QUEEN -> EscapeSequences.BLACK_QUEEN;
                case BISHOP -> EscapeSequences.BLACK_BISHOP;
                case KNIGHT -> EscapeSequences.BLACK_KNIGHT;
                case ROOK -> EscapeSequences.BLACK_ROOK;
                case PAWN -> EscapeSequences.BLACK_PAWN;
            };
        };
    }

    public static void drawBoard(ChessBoard board, ChessGame.TeamColor playerColor) {
        boolean whitePerspective = playerColor != ChessGame.TeamColor.BLACK;

        System.out.print(EscapeSequences.RESET);
        printFiles(whitePerspective);

        for (int displayRank = 0; displayRank < 8; displayRank++) {
            int rank = whitePerspective ? 8 - displayRank : displayRank + 1;

            System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            System.out.print(" " + rank + " ");
            System.out.print(EscapeSequences.RESET);

            for (int displayFile = 0; displayFile < 8; displayFile++) {
                int file;

                if (whitePerspective) {
                    file = displayFile + 1;
                } else {
                    file = 8 - displayFile;
                }

                boolean lightSquare = ((file + rank) % 2 == 1);

                System.out.print(lightSquare
                        ? EscapeSequences.SET_BG_COLOR_LIGHT_GREY
                        : EscapeSequences.SET_BG_COLOR_BLACK);

                ChessPiece piece = board.getPiece(new ChessPosition(rank, file));
                String symbol = pieceToSymbol(piece);

                System.out.print(lightSquare
                        ? EscapeSequences.SET_BG_COLOR_LIGHT_GREY
                        : EscapeSequences.SET_BG_COLOR_BLACK);

                if (piece != null) {
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

        printFiles(whitePerspective);
        System.out.print(EscapeSequences.RESET);
    }

    private static void printFiles(boolean whitePerspective) {

        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print("   ");
        System.out.print(EscapeSequences.RESET);

        for (int i = 0; i < 8; i++) {
            char file;

            if (whitePerspective) {
                file = (char) ('a' + i);
            } else {
                file = (char) ('h' - i);
            }

            System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
            System.out.print(EscapeSequences.SET_TEXT_COLOR_WHITE);
            System.out.print(" " + file + " ");
            System.out.print(EscapeSequences.RESET);
        }

        System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
        System.out.print("   ");
        System.out.print(EscapeSequences.RESET);

        System.out.println();
    }
}