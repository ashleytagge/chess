package chess;

import java.util.Collection;

public class RepeatedMoves {
    //move redundant code into this class so it only appears once

    //Rook && Queen
    public static Collection<ChessMove> moveMultipleLeft(Collection<ChessMove> validMoves, ChessPiece piece, ChessPosition myPosition, ChessBoard board){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int n = 1;
        while(col - n >= 1){
            ChessPosition tempPosition = new ChessPosition(row, col - n);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        return validMoves;
    }
    public static Collection<ChessMove> moveMultipleRight(Collection<ChessMove> validMoves, ChessPiece piece, ChessPosition myPosition, ChessBoard board){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int n = 1;
        while(col + n <= 8){
            ChessPosition tempPosition = new ChessPosition(row, col + n);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        return validMoves;
    }
    public static Collection<ChessMove> moveMultipleUp(Collection<ChessMove> validMoves, ChessPiece piece, ChessPosition myPosition, ChessBoard board){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int n = 1;
        while(row + n <= 8){
            ChessPosition tempPosition = new ChessPosition(row + n, col);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        return validMoves;
    }
    public static Collection<ChessMove> moveMultipleDown(Collection<ChessMove> validMoves, ChessPiece piece, ChessPosition myPosition, ChessBoard board){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int n = 1;
        while(row - n >= 1){
            ChessPosition tempPosition = new ChessPosition(row - n, col);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        return validMoves;
    }
    //Bishop && Queen
    public static Collection<ChessMove> moveMultipleUpLeft(Collection<ChessMove> validMoves, ChessPiece piece, ChessPosition myPosition, ChessBoard board){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int n = 1;
        while(row + n <= 8 && col - n >= 1){
            //if position row+1 col-1 is empty, add to valid moves and keep going diagonal
            //if position row+n col-n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row + n, col - n);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        return validMoves;
    }
    public static Collection<ChessMove> moveMultipleUpRight(Collection<ChessMove> validMoves, ChessPiece piece, ChessPosition myPosition, ChessBoard board){
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int n = 1;
        while(row + n <= 8 && col + n <= 8){
            ChessPosition tempPosition = new ChessPosition(row + n, col + n);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        return validMoves;
    }
    public static Collection<ChessMove> moveMultipleDownLeft(Collection<ChessMove> validMoves, ChessPiece piece, ChessPosition myPosition, ChessBoard board){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int n = 1;
        while(row - n >= 1 && col - n >= 1){
            //if position row-1 col+1 is empty, add to valid moves and keep going diagonal
            //if position row-n col+n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row - n, col - n);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        return validMoves;
    }
    public static Collection<ChessMove> moveMultipleDownRight(Collection<ChessMove> validMoves, ChessPiece piece, ChessPosition myPosition, ChessBoard board){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        int n = 1;
        while(row - n >= 1 && col + n <= 8){
            //if position row-1 col-1 is empty, add to valid moves and keep going diagonal
            //if position row-n col-n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row - n, col + n);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        return validMoves;
    }

    public static Collection<ChessMove> addPromotionPiece(ChessPosition myPosition, ChessPosition tempPosition, Collection<ChessMove> validMoves){
        validMoves.add(new ChessMove(myPosition, tempPosition, ChessPiece.PieceType.QUEEN));
        validMoves.add(new ChessMove(myPosition, tempPosition, ChessPiece.PieceType.BISHOP));
        validMoves.add(new ChessMove(myPosition, tempPosition, ChessPiece.PieceType.ROOK));
        validMoves.add(new ChessMove(myPosition, tempPosition, ChessPiece.PieceType.KNIGHT));
        return validMoves;
    }
}
