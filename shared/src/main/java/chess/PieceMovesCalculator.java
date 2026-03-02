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

    public static Collection<ChessMove> knightMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();
        //2 up 1 left
        if(row + 2 <= 8 && col - 1 >= 1) {
            ChessPosition tempPosition1 = new ChessPosition(row + 2, col - 1);
            ChessPiece tempPiece1 = board.getPiece(tempPosition1);
            if(tempPiece1 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition1, null));
            }
            else{ChessGame.TeamColor tempColor1 = tempPiece1.getTeamColor();
                if( teamColor != tempColor1){
                    validMoves.add(new ChessMove(myPosition, tempPosition1, null));
                }}
        }
        //2 up 1 right
        if(row + 2 <= 8 && col + 1 <= 8) {
            ChessPosition tempPosition2 = new ChessPosition(row + 2, col + 1);
            ChessPiece tempPiece2 = board.getPiece(tempPosition2);
            if(tempPiece2 == null){
                validMoves.add(new ChessMove(myPosition, tempPosition2, null));
            }else {
                ChessGame.TeamColor tempColor2 = tempPiece2.getTeamColor();
                if(teamColor != tempColor2){
                    validMoves.add(new ChessMove(myPosition, tempPosition2, null));
                }
            }
        }
        //2 down 1 left
        if(row - 2 >= 1 && col - 1 >= 1) {
            ChessPosition tempPosition3 = new ChessPosition(row - 2, col - 1);
            ChessPiece tempPiece3 = board.getPiece(tempPosition3);
            if(tempPiece3 == null){
                validMoves.add(new ChessMove(myPosition, tempPosition3, null));
            }else {
                ChessGame.TeamColor tempColor3 = tempPiece3.getTeamColor();
                if(teamColor != tempColor3){
                    validMoves.add(new ChessMove(myPosition, tempPosition3, null));
                }
            }
        }
        //2 down 1 right
        if(row - 2 >= 1 && col + 1 <= 8) {
            ChessPosition tempPosition4 = new ChessPosition(row - 2, col + 1);
            ChessPiece tempPiece4 = board.getPiece(tempPosition4);
            if(tempPiece4 == null){
                validMoves.add(new ChessMove(myPosition, tempPosition4, null));
            }else {
                ChessGame.TeamColor tempColor4 = tempPiece4.getTeamColor();
                if(teamColor != tempColor4){
                    validMoves.add(new ChessMove(myPosition, tempPosition4, null));
                }
            }
        }
        //2 left 1 up
        if(row + 1 <= 8 && col - 2 >= 1) {
            ChessPosition tempPosition5 = new ChessPosition(row + 1, col - 2);
            ChessPiece tempPiece5 = board.getPiece(tempPosition5);
            if(tempPiece5 == null){
                validMoves.add(new ChessMove(myPosition, tempPosition5, null));
            }else {
                ChessGame.TeamColor tempColor5 = tempPiece5.getTeamColor();
                if(teamColor != tempColor5){
                    validMoves.add(new ChessMove(myPosition, tempPosition5, null));
                }
            }
        }
        //2 left 1 down
        if(row - 1 >= 1 && col - 2 >= 1) {
            ChessPosition tempPosition6 = new ChessPosition(row - 1, col - 2);
            ChessPiece tempPiece6 = board.getPiece(tempPosition6);
            if(tempPiece6 == null){
                validMoves.add(new ChessMove(myPosition, tempPosition6, null));
            }else {
                ChessGame.TeamColor tempColor6 = tempPiece6.getTeamColor();
                if(teamColor != tempColor6){
                    validMoves.add(new ChessMove(myPosition, tempPosition6, null));
                }
            }
        }
        //2 right 1 up
        if(row + 1 <= 8 && col + 2 <= 8) {
            ChessPosition tempPosition7 = new ChessPosition(row + 1, col + 2);
            ChessPiece tempPiece7 = board.getPiece(tempPosition7);
            if(tempPiece7 == null){
                validMoves.add(new ChessMove(myPosition, tempPosition7, null));
            }else {
                ChessGame.TeamColor tempColor7 = tempPiece7.getTeamColor();
                if(teamColor != tempColor7){
                    validMoves.add(new ChessMove(myPosition, tempPosition7, null));
                }
            }
        }
        //2 right 1 down
        if(row - 1 >= 1 && col + 2 <= 8) {
            ChessPosition tempPosition8 = new ChessPosition(row - 1, col + 2);
            ChessPiece tempPiece8 = board.getPiece(tempPosition8);
            if(tempPiece8 == null){
                validMoves.add(new ChessMove(myPosition, tempPosition8, null));
            }else {
                ChessGame.TeamColor tempColor8 = tempPiece8.getTeamColor();
                if(teamColor != tempColor8){
                    validMoves.add(new ChessMove(myPosition, tempPosition8, null));
                }
            }
        }

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
    public static Collection<ChessMove> pawnMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();
        //if team color is BLACK and row == 7 (first turn)
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
                if(col - 1 >= 1){
                    ChessPosition tempPosition2 = new ChessPosition(row - 1, col - 1);
                    ChessPiece tempPiece2 = board.getPiece(tempPosition2);
                    if (tempPiece2 != null && tempPiece2.getTeamColor() != teamColor) {
                        validMoves.add(new ChessMove(myPosition, tempPosition2, null));
                    }}
                if(col + 1 <= 8){
                    ChessPosition tempPosition3 = new ChessPosition(row - 1, col + 1);
                    ChessPiece tempPiece3 = board.getPiece(tempPosition3);
                    if (tempPiece3 != null && tempPiece3.getTeamColor() != teamColor) {
                        validMoves.add(new ChessMove(myPosition, tempPosition3, null));
                    }}
            }else{
                if(row - 1 >= 1){
                    ChessPosition tempPosition = new ChessPosition(row - 1, col);
                    ChessPiece tempPiece = board.getPiece(tempPosition);
                    if (tempPiece == null && row - 1 != 1) {
                        validMoves.add(new ChessMove(myPosition, tempPosition, null));
                    }else if(tempPiece == null){
                        validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition, validMoves);
                    }}
                if(row -1 >= 1 && col - 1 >= 1){
                    ChessPosition tempPosition2 = new ChessPosition(row - 1, col - 1);
                    ChessPiece tempPiece2 = board.getPiece(tempPosition2);
                    if (tempPiece2 != null && tempPiece2.getTeamColor() != teamColor && row - 1 != 1) {
                        validMoves.add(new ChessMove(myPosition, tempPosition2, null));
                    }else if(tempPiece2 != null && tempPiece2.getTeamColor() != teamColor){
                        validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition2, validMoves);
                    }}
                if(row-1 >= 1 && col + 1 <= 8){
                    ChessPosition tempPosition3 = new ChessPosition(row - 1, col + 1);
                    ChessPiece tempPiece3 = board.getPiece(tempPosition3);
                    if (tempPiece3 != null && tempPiece3.getTeamColor() != teamColor && row - 1 != 1) {
                        validMoves.add(new ChessMove(myPosition, tempPosition3, null));
                    }else if(tempPiece3 != null && tempPiece3.getTeamColor() != teamColor){
                        validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition3, validMoves);
                    }
                }
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
                if(col + 1 <=8){
                    ChessPosition tempPosition2 = new ChessPosition(row + 1, col + 1);
                    ChessPiece tempPiece2 = board.getPiece(tempPosition2);
                    if (tempPiece2 != null && tempPiece2.getTeamColor() != teamColor) {
                        validMoves.add(new ChessMove(myPosition, tempPosition2, null));
                    }}
                if(col - 1 >= 1){
                    ChessPosition tempPosition3 = new ChessPosition(row + 1, col - 1);
                    ChessPiece tempPiece3 = board.getPiece(tempPosition3);
                    if (tempPiece3 != null && tempPiece3.getTeamColor() != teamColor) {
                        validMoves.add(new ChessMove(myPosition, tempPosition3, null));
                    }}
            } else {
                if(row + 1 <= 8){
                    ChessPosition tempPosition = new ChessPosition(row + 1, col);
                    ChessPiece tempPiece = board.getPiece(tempPosition);
                    if (tempPiece == null && row + 1 == 8) {
                        validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition, validMoves);
                    }else if(tempPiece  == null){
                        validMoves.add(new ChessMove(myPosition, tempPosition, null));
                    }}
                if(col - 1 >= 1 && row + 1 <= 8){
                    ChessPosition tempPosition2 = new ChessPosition(row + 1, col - 1);
                    ChessPiece tempPiece2 = board.getPiece(tempPosition2);
                    if (tempPiece2 != null && tempPiece2.getTeamColor() != teamColor && row + 1 == 8) {
                        validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition2, validMoves);
                    }else if (tempPiece2 != null && tempPiece2.getTeamColor() != teamColor){
                        validMoves.add(new ChessMove(myPosition, tempPosition2, null));
                    }}
                if(col + 1 <= 8 && row + 1 <= 8){
                    ChessPosition tempPosition3 = new ChessPosition(row + 1, col + 1);
                    ChessPiece tempPiece3 = board.getPiece(tempPosition3);
                    if(tempPiece3 != null && tempPiece3.getTeamColor() != teamColor && row + 1 == 8) {
                        validMoves = RepeatedMoves.addPromotionPiece(myPosition, tempPosition3, validMoves);
                    }else if(tempPiece3 != null && tempPiece3.getTeamColor() != teamColor){
                        validMoves.add(new ChessMove(myPosition, tempPosition3, null));
                    }}
            }
        }
        return validMoves;
    }
}
