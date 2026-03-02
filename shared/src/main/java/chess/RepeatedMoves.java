package chess;

import java.util.Collection;

public class RepeatedMoves {

    private static Collection<ChessMove> moveMultiple(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board,
            int rowDirection,
            int colDirection,
            boolean checkRowUpper,
            boolean checkRowLower,
            boolean checkColUpper,
            boolean checkColLower) {

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int n = 1;

        while (
                (!checkRowUpper || row + n <= 8) &&
                        (!checkRowLower || row - n >= 1) &&
                        (!checkColUpper || col + n <= 8) &&
                        (!checkColLower || col - n >= 1)
        ) {

            ChessPosition tempPosition = new ChessPosition(
                    row + (rowDirection * n),
                    col + (colDirection * n)
            );

            ChessPiece tempPiece = board.getPiece(tempPosition);

            if (tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }

            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if (teamColor != tempColor) {
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }

            break;
        }

        return validMoves;
    }

    //Rook && Queen
    public static Collection<ChessMove> moveMultipleLeft(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board){
        return moveMultiple(validMoves, piece, myPosition, board, 0, -1, false, false, false, true);
    }

    public static Collection<ChessMove> moveMultipleRight(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board){
        return moveMultiple(validMoves, piece, myPosition, board, 0, 1, false, false, true, false);
    }

    public static Collection<ChessMove> moveMultipleUp(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board){
        return moveMultiple(validMoves, piece, myPosition, board, 1, 0, true, false, false, false);
    }

    public static Collection<ChessMove> moveMultipleDown(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board){
        return moveMultiple(validMoves, piece, myPosition, board, -1, 0, false, true, false, false);
    }

    //Bishop && Queen
    public static Collection<ChessMove> moveMultipleUpLeft(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board){
        //if position row+1 col-1 is empty, add to valid moves and keep going diagonal
        //if position row+n col-n is full
        //if the piece at this position is an enemy, add to valid moves and stop
        //if the piece at this position is mine, stop
        return moveMultiple(validMoves, piece, myPosition, board, 1, -1, true, false, false, true);
    }

    public static Collection<ChessMove> moveMultipleUpRight(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board){
        return moveMultiple(validMoves, piece, myPosition, board, 1, 1, true, false, true, false);
    }

    public static Collection<ChessMove> moveMultipleDownLeft(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board){
        //if position row-1 col+1 is empty, add to valid moves and keep going diagonal
        //if position row-n col+n is full
        //if the piece at this position is an enemy, add to valid moves and stop
        //if the piece at this position is mine, stop
        return moveMultiple(validMoves, piece, myPosition, board, -1, -1, false, true, false, true);
    }

    public static Collection<ChessMove> moveMultipleDownRight(
            Collection<ChessMove> validMoves,
            ChessPiece piece,
            ChessPosition myPosition,
            ChessBoard board){
        //if position row-1 col-1 is empty, add to valid moves and keep going diagonal
        //if position row-n col-n is full
        //if the piece at this position is an enemy, add to valid moves and stop
        //if the piece at this position is mine, stop
        return moveMultiple(validMoves, piece, myPosition, board, -1, 1, false, true, true, false);
    }

    public static Collection<ChessMove> addPromotionPiece(
            ChessPosition myPosition,
            ChessPosition tempPosition,
            Collection<ChessMove> validMoves){
        validMoves.add(new ChessMove(myPosition, tempPosition, ChessPiece.PieceType.QUEEN));
        validMoves.add(new ChessMove(myPosition, tempPosition, ChessPiece.PieceType.BISHOP));
        validMoves.add(new ChessMove(myPosition, tempPosition, ChessPiece.PieceType.ROOK));
        validMoves.add(new ChessMove(myPosition, tempPosition, ChessPiece.PieceType.KNIGHT));
        return validMoves;
    }
}
