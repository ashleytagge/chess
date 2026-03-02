package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalculator {

    private static void kingMoveHelper(
            ChessPiece piece,
            ChessBoard board,
            ChessPosition myPosition,
            Collection<ChessMove> validMoves,
            int newRow,
            int newCol) {

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();

        if(newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8){

            ChessPosition tempPosition = new ChessPosition(newRow, newCol);
            ChessPiece tempPiece = board.getPiece(tempPosition);

            //if it's empty you can move there
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            //if position has an enemy piece you can move there
            else{
                ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
                if(teamColor != tempColor){
                    validMoves.add(new ChessMove(myPosition, tempPosition, null));
                }
            }
        }
    }

    public static Collection<ChessMove> kingMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> validMoves = new ArrayList<>();

        //Up
        kingMoveHelper(piece, board, myPosition, validMoves, row + 1, col);
        //Down
        kingMoveHelper(piece, board, myPosition, validMoves, row - 1, col);
        //Left
        kingMoveHelper(piece, board, myPosition, validMoves, row, col - 1);
        //Right
        kingMoveHelper(piece, board, myPosition, validMoves, row, col + 1);
        //Up-Left
        kingMoveHelper(piece, board, myPosition, validMoves, row + 1, col - 1);
        //Up-Right
        kingMoveHelper(piece, board, myPosition, validMoves, row + 1, col + 1);
        //Down-Left
        kingMoveHelper(piece, board, myPosition, validMoves, row - 1, col - 1);
        //Down-Right
        kingMoveHelper(piece, board, myPosition, validMoves, row - 1, col + 1);
        //return moves
        return validMoves;
    }
    public static Collection<ChessMove> queenMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();

        //up and to the right
        validMoves = RepeatedMoves.moveMultipleUpRight(validMoves, piece, myPosition, board);
        //up and to the left
        validMoves = RepeatedMoves.moveMultipleUpLeft(validMoves, piece, myPosition, board);
        //down and to the left
        validMoves = RepeatedMoves.moveMultipleDownLeft(validMoves, piece, myPosition, board);
        //down and to the right
        validMoves = RepeatedMoves.moveMultipleDownRight(validMoves, piece, myPosition, board);
        //up
        validMoves = RepeatedMoves.moveMultipleUp(validMoves, piece, myPosition, board);
        //down
        validMoves = RepeatedMoves.moveMultipleDown(validMoves, piece, myPosition, board);
        //left
        validMoves = RepeatedMoves.moveMultipleLeft(validMoves, piece, myPosition, board);
        //right
        validMoves = RepeatedMoves.moveMultipleRight(validMoves, piece, myPosition, board);
        //return moves
        return validMoves;
    }
    public static Collection<ChessMove> bishopMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();

        //up and to the right
        validMoves = RepeatedMoves.moveMultipleUpRight(validMoves, piece, myPosition, board);
        //up and to the left
        validMoves = RepeatedMoves.moveMultipleUpLeft(validMoves, piece, myPosition, board);
        //down and to the left
        validMoves = RepeatedMoves.moveMultipleDownLeft(validMoves, piece, myPosition, board);
        //down and to the right
        validMoves = RepeatedMoves.moveMultipleDownRight(validMoves, piece, myPosition, board);
        //return moves
        return validMoves;
    }

    private static void knightMoveExecution(
            ChessPiece piece,
            ChessBoard board,
            ChessPosition myPosition,
            Collection<ChessMove> validMoves,
            int targetRow,
            int targetCol) {

        if(targetRow >= 1 && targetRow <= 8 && targetCol >= 1 && targetCol <= 8){

            ChessPosition tempPosition = new ChessPosition(targetRow, targetCol);
            ChessPiece tempPiece = board.getPiece(tempPosition);

            if(tempPiece == null){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }else {
                ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
                if(piece.getTeamColor() != tempColor){
                    validMoves.add(new ChessMove(myPosition, tempPosition, null));
                }
            }
        }
    }

    public static Collection<ChessMove> knightMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        Collection<ChessMove> validMoves = new ArrayList<>();

        //2 up 1 left
        knightMoveExecution(piece, board, myPosition, validMoves, row + 2, col - 1);

        //2 up 1 right
        knightMoveExecution(piece, board, myPosition, validMoves, row + 2, col + 1);

        //2 down 1 left
        knightMoveExecution(piece, board, myPosition, validMoves, row - 2, col - 1);

        //2 down 1 right
        knightMoveExecution(piece, board, myPosition, validMoves, row - 2, col + 1);

        //2 left 1 up
        knightMoveExecution(piece, board, myPosition, validMoves, row + 1, col - 2);

        //2 left 1 down
        knightMoveExecution(piece, board, myPosition, validMoves, row - 1, col - 2);

        //2 right 1 up
        knightMoveExecution(piece, board, myPosition, validMoves, row + 1, col + 2);

        //2 right 1 down
        knightMoveExecution(piece, board, myPosition, validMoves, row - 1, col + 2);

        return validMoves;
    }
    public static Collection<ChessMove> rookMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoves = new ArrayList<>();
        //up
        validMoves = RepeatedMoves.moveMultipleUp(validMoves, piece, myPosition, board);
        //down
        validMoves = RepeatedMoves.moveMultipleDown(validMoves, piece, myPosition, board);
        //left
        validMoves = RepeatedMoves.moveMultipleLeft(validMoves, piece, myPosition, board);
        //right
        validMoves = RepeatedMoves.moveMultipleRight(validMoves, piece, myPosition, board);
        //return moves
        return validMoves;
    }

    private static Collection<ChessMove> capturePawn(
            ChessPiece piece,
            ChessBoard board,
            ChessPosition myPosition,
            Collection<ChessMove> validMoves,
            int targetRow,
            int targetCol,
            int promotionRow) {

        if(targetCol >= 1 && targetCol <= 8 && targetRow >= 1 && targetRow <= 8){
            ChessPosition tempPosition = new ChessPosition(targetRow, targetCol);
            ChessPiece tempPiece = board.getPiece(tempPosition);

            if (tempPiece != null && tempPiece.getTeamColor() != piece.getTeamColor()) {
                if(targetRow == promotionRow){
                    validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition, validMoves);
                } else {
                    validMoves.add(new ChessMove(myPosition, tempPosition, null));
                }
            }
        }

        return validMoves;
    }

    public static Collection<ChessMove> pawnMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();

        if(teamColor == ChessGame.TeamColor.BLACK){
            if(row == 7) {
                int n = 1;
                while (n < 3) {
                    ChessPosition tempPosition = new ChessPosition(row - n, col);
                    ChessPiece tempPiece = board.getPiece(tempPosition);
                    if (tempPiece == null) {
                        validMoves.add(new ChessMove(myPosition, tempPosition, null));
                    }else{
                        break;
                    }
                    n += 1;
                }
                validMoves = capturePawn(piece, board, myPosition, validMoves, row - 1, col - 1, 1);
                validMoves = capturePawn(piece, board, myPosition, validMoves, row - 1, col + 1, 1);
            }else{
                if(row - 1 >= 1){
                    ChessPosition tempPosition = new ChessPosition(row - 1, col);
                    ChessPiece tempPiece = board.getPiece(tempPosition);
                    if (tempPiece == null && row - 1 != 1) {
                        validMoves.add(new ChessMove(myPosition, tempPosition, null));
                    }else if(tempPiece == null){
                        validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition, validMoves);
                    }
                }
                validMoves = capturePawn(piece, board, myPosition, validMoves, row - 1, col - 1, 1);
                validMoves = capturePawn(piece, board, myPosition, validMoves, row - 1, col + 1, 1);
            }
        }else{
            if (row == 2) {
                int n = 1;
                while (n < 3) {
                    ChessPosition tempPosition = new ChessPosition(row + n, col);
                    ChessPiece tempPiece = board.getPiece(tempPosition);
                    if (tempPiece == null) {
                        validMoves.add(new ChessMove(myPosition, tempPosition, null));
                    }else{
                        break;
                    }
                    n += 1;
                }
                validMoves = capturePawn(piece, board, myPosition, validMoves, row + 1, col - 1, 8);
                validMoves = capturePawn(piece, board, myPosition, validMoves, row + 1, col + 1, 8);

            } else {
                if(row + 1 <= 8){
                    ChessPosition tempPosition = new ChessPosition(row + 1, col);
                    ChessPiece tempPiece = board.getPiece(tempPosition);
                    if (tempPiece == null && row + 1 == 8) {
                        validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition, validMoves);
                    }else if(tempPiece  == null){
                        validMoves.add(new ChessMove(myPosition, tempPosition, null));
                    }
                }
                validMoves = capturePawn(piece, board, myPosition, validMoves, row + 1, col - 1, 8);
                validMoves = capturePawn(piece, board, myPosition, validMoves, row + 1, col + 1, 8);
            }
        }
        return validMoves;
    }
}
