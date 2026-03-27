package ui;

import chess.ChessGame;

public class PrintBoard {

    private static final String[][] STARTING_BOARD = {
            {EscapeSequences.BLACK_ROOK, EscapeSequences.BLACK_KNIGHT, EscapeSequences.BLACK_BISHOP, EscapeSequences.BLACK_QUEEN,
                    EscapeSequences.BLACK_KING, EscapeSequences.BLACK_BISHOP, EscapeSequences.BLACK_KNIGHT, EscapeSequences.BLACK_ROOK},
            {EscapeSequences.BLACK_PAWN, EscapeSequences.BLACK_PAWN, EscapeSequences.BLACK_PAWN, EscapeSequences.BLACK_PAWN,
                    EscapeSequences.BLACK_PAWN, EscapeSequences.BLACK_PAWN, EscapeSequences.BLACK_PAWN, EscapeSequences.BLACK_PAWN},
            {EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY,
                    EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY},
            {EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY,
                    EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY},
            {EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY,
                    EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY},
            {EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY,
                    EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY, EscapeSequences.EMPTY},
            {EscapeSequences.WHITE_PAWN, EscapeSequences.WHITE_PAWN, EscapeSequences.WHITE_PAWN, EscapeSequences.WHITE_PAWN,
                    EscapeSequences.WHITE_PAWN, EscapeSequences.WHITE_PAWN, EscapeSequences.WHITE_PAWN, EscapeSequences.WHITE_PAWN},
            {EscapeSequences.WHITE_ROOK, EscapeSequences.WHITE_KNIGHT, EscapeSequences.WHITE_BISHOP, EscapeSequences.WHITE_QUEEN,
                    EscapeSequences.WHITE_KING, EscapeSequences.WHITE_BISHOP, EscapeSequences.WHITE_KNIGHT, EscapeSequences.WHITE_ROOK}
    };

    public static void drawBoard(ChessGame.TeamColor playerColor) {
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
                String piece = STARTING_BOARD[8 - rank][file - 1];

                System.out.print(lightSquare
                        ? EscapeSequences.SET_BG_COLOR_WHITE
                        : EscapeSequences.SET_BG_COLOR_BLACK);

                if (piece.equals(EscapeSequences.WHITE_KING) || piece.equals(EscapeSequences.WHITE_QUEEN)
                        || piece.equals(EscapeSequences.WHITE_BISHOP) || piece.equals(EscapeSequences.WHITE_KNIGHT)
                        || piece.equals(EscapeSequences.WHITE_ROOK) || piece.equals(EscapeSequences.WHITE_PAWN)) {
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
                } else if (piece.equals(EscapeSequences.BLACK_KING) || piece.equals(EscapeSequences.BLACK_QUEEN)
                        || piece.equals(EscapeSequences.BLACK_BISHOP) || piece.equals(EscapeSequences.BLACK_KNIGHT)
                        || piece.equals(EscapeSequences.BLACK_ROOK) || piece.equals(EscapeSequences.BLACK_PAWN)) {
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE);
                }

                System.out.print(piece);
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