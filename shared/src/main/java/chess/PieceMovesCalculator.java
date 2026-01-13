package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalculator {

    public PieceMovesCalculator(){}

    public void KingMove(){}
    public void QueenMove(){}
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
    public void KnightMove(){}
    public void RookMove(){}
    public void PawnMove(){}
}
