package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalculator {

    public PieceMovesCalculator(){}

    public static Collection<ChessMove> KingMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();
        //Up
        if(row + 1 <= 8){
            ChessPosition tempPosition1 = new ChessPosition(row + 1, col);
            ChessPiece tempPiece1 = board.getPiece(tempPosition1);
            //if it's empty AND you're not in danger of being checked, you can move there
            if(tempPiece1 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition1, null));
            }
            //if position has an enemy piece you can move there
            else{ChessGame.TeamColor tempColor1 = tempPiece1.getTeamColor();
            if( teamColor != tempColor1){
                validMoves.add(new ChessMove(myPosition, tempPosition1, null));
            }}
        }
        //Down
        if(row - 1 >= 1) {
            ChessPosition tempPosition2 = new ChessPosition(row - 1, col);
            ChessPiece tempPiece2 = board.getPiece(tempPosition2);
            //if it's empty you can move there
            if (tempPiece2 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition2, null));
            }
            //if position has an enemy piece you can move there
            else{ChessGame.TeamColor tempColor2 = tempPiece2.getTeamColor();
            if (teamColor != tempColor2) {
                validMoves.add(new ChessMove(myPosition, tempPosition2, null));
            }}
        }
        //Left
        if(col - 1 >= 1) {
            ChessPosition tempPosition3 = new ChessPosition(row, col - 1);
            ChessPiece tempPiece3 = board.getPiece(tempPosition3);
            //if it's empty you can move there
            if (tempPiece3 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition3, null));
            }
            //if position has an enemy piece you can move there
            else{ChessGame.TeamColor tempColor3 = tempPiece3.getTeamColor();
            if (teamColor != tempColor3) {
                validMoves.add(new ChessMove(myPosition, tempPosition3, null));
            }}
        }
        //Right
        if(col + 1 <= 8) {
            ChessPosition tempPosition4 = new ChessPosition(row, col + 1);
            ChessPiece tempPiece4 = board.getPiece(tempPosition4);
            //if it's empty you can move there
            if (tempPiece4 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition4, null));
            }
            //if position has an enemy piece you can move there
            else{ChessGame.TeamColor tempColor4 = tempPiece4.getTeamColor();
            if (teamColor != tempColor4) {
                validMoves.add(new ChessMove(myPosition, tempPosition4, null));
            }}
        }
        //Up-Left
        if(row + 1 <= 8 && col - 1 >= 1){
            ChessPosition tempPosition5 = new ChessPosition(row + 1, col - 1);
            ChessPiece tempPiece5 = board.getPiece(tempPosition5);
            if(tempPiece5 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition5, null));
            }
            else{ChessGame.TeamColor tempColor5 = tempPiece5.getTeamColor();
            if( teamColor != tempColor5){
                validMoves.add(new ChessMove(myPosition, tempPosition5, null));
            }}
        }
        //Up-Right
        if(row + 1 <= 8 && col + 1 <= 8){
            ChessPosition tempPosition6 = new ChessPosition(row + 1, col + 1);
            ChessPiece tempPiece6 = board.getPiece(tempPosition6);
            if(tempPiece6 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition6, null));
            }
            else{ChessGame.TeamColor tempColor6 = tempPiece6.getTeamColor();
            if( teamColor != tempColor6){
                validMoves.add(new ChessMove(myPosition, tempPosition6, null));
            }}
        }
        //Down-Left
        if(row - 1 >= 1 && col - 1 >= 1){
            ChessPosition tempPosition7 = new ChessPosition(row - 1, col - 1);
            ChessPiece tempPiece7 = board.getPiece(tempPosition7);
            if(tempPiece7 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition7, null));
            }
            else{ChessGame.TeamColor tempColor7 = tempPiece7.getTeamColor();
            if( teamColor != tempColor7){
                validMoves.add(new ChessMove(myPosition, tempPosition7, null));
            }}
        }
        //Down-Right
        if(row - 1 >= 1 && col + 1 <= 8){
            ChessPosition tempPosition8 = new ChessPosition(row - 1, col + 1);
            ChessPiece tempPiece8 = board.getPiece(tempPosition8);
            if(tempPiece8 == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition8, null));
            }
            else{ChessGame.TeamColor tempColor8 = tempPiece8.getTeamColor();
            if( teamColor != tempColor8){
                validMoves.add(new ChessMove(myPosition, tempPosition8, null));
            }}
        }
        return validMoves;
    }
    public static Collection<ChessMove> QueenMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();

        //up and to the right
        int n1 = 1;
        while(row + n1 <= 8 && col + n1 <= 8){
            ChessPosition tempPosition = new ChessPosition(row + n1, col + n1);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n1 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
            //if position row+n col+n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
        }
        //up and to the left
        int n2 = 1;
        while(row + n2 <= 8 && col - n2 >= 1){
            //if position row+1 col-1 is empty, add to valid moves and keep going diagonal
            //if position row+n col-n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row + n2, col - n2);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n2 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //down and to the left
        int n3 = 1;
        while(row - n3 >= 1 && col - n3 >= 1){
            //if position row-1 col+1 is empty, add to valid moves and keep going diagonal
            //if position row-n col+n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row - n3, col - n3);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n3 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //down and to the right
        int n4 = 1;
        while(row - n4 >= 1 && col + n4 <= 8){
            //if position row-1 col-1 is empty, add to valid moves and keep going diagonal
            //if position row-n col-n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row - n4, col + n4);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n4 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //up
        int n5 = 1;
        while(row + n5 <= 8){
            ChessPosition tempPosition = new ChessPosition(row + n5, col);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n5 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //down
        int n6 = 1;
        while(row - n6 >= 1){
            ChessPosition tempPosition = new ChessPosition(row - n6, col);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n6 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //left
        int n7 = 1;
        while(col - n7 >= 1){
            ChessPosition tempPosition = new ChessPosition(row, col - n7);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n7 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //right
        int n8 = 1;
        while(col + n8 <= 8){
            ChessPosition tempPosition = new ChessPosition(row, col + n8);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n8 += 1;
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
    public static Collection<ChessMove> BishopMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();

        //up and to the right
        int n1 = 1;
        while(row + n1 <= 8 && col + n1 <= 8){
            ChessPosition tempPosition = new ChessPosition(row + n1, col + n1);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n1 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
            //if position row+n col+n is full
                //if the piece at this position is an enemy, add to valid moves and stop
                //if the piece at this position is mine, stop
        }
        //up and to the left
        int n2 = 1;
        while(row + n2 <= 8 && col - n2 >= 1){
            //if position row+1 col-1 is empty, add to valid moves and keep going diagonal
            //if position row+n col-n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row + n2, col - n2);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n2 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //down and to the left
        int n3 = 1;
        while(row - n3 >= 1 && col - n3 >= 1){
            //if position row-1 col+1 is empty, add to valid moves and keep going diagonal
            //if position row-n col+n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row - n3, col - n3);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n3 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //down and to the right
        int n4 = 1;
        while(row - n4 >= 1 && col + n4 <= 8){
            //if position row-1 col-1 is empty, add to valid moves and keep going diagonal
            //if position row-n col-n is full
            //if the piece at this position is an enemy, add to valid moves and stop
            //if the piece at this position is mine, stop
            ChessPosition tempPosition = new ChessPosition(row - n4, col + n4);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n4 += 1;
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
    public static Collection<ChessMove> KnightMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
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
    public static Collection<ChessMove> RookMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        Collection<ChessMove> validMoves = new ArrayList<>();

        //up
        int n1 = 1;
        while(row + n1 <= 8){
            ChessPosition tempPosition = new ChessPosition(row + n1, col);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n1 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //down
        int n2 = 1;
        while(row - n2 >= 1){
            ChessPosition tempPosition = new ChessPosition(row - n2, col);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n2 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //left
        int n3 = 1;
        while(col - n3 >= 1){
            ChessPosition tempPosition = new ChessPosition(row, col - n3);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                //if position row+1 col+1 is empty, add to valid moves and keep going diagonal
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n3 += 1;
                continue;
            }
            ChessGame.TeamColor tempColor = tempPiece.getTeamColor();
            if( teamColor != tempColor){
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
            }
            break;
        }
        //right
        int n4 = 1;
        while(col + n4 <= 8){
            ChessPosition tempPosition = new ChessPosition(row, col + n4);
            ChessPiece tempPiece = board.getPiece(tempPosition);
            if(tempPiece == null) {
                validMoves.add(new ChessMove(myPosition, tempPosition, null));
                n4 += 1;
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
    public static Collection<ChessMove> PawnMove(ChessPiece piece, ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> validMoves = new ArrayList<>();
        return validMoves;
    }
}
