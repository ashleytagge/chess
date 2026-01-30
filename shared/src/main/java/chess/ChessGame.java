package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard Board;
    private TeamColor teamColor;

    public ChessGame() {
        Board = new ChessBoard();
        teamColor = ChessGame.TeamColor.WHITE;
    }


    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        /*
        * Takes as input a position on the chessboard and
        * returns all moves the piece there can legally make.
        * If there is no piece at that location, this method returns null.
        * A move is valid if it is a "piece move" for the piece
        * at the input location and making that move would not
        * leave the team’s king in danger of check.*/
        //to remove from a collection use .hasnext, .next iterator, and the .remove
        Collection<ChessMove> validMovesList;
        Collection<ChessMove> dangerousMovesList = new ArrayList<>();
        ChessPiece piece = Board.getPiece(startPosition);
        if(piece == null){
            return null;
        }else{
            validMovesList = piece.pieceMoves(Board, startPosition);
            int counter = validMovesList.size();
            //loop through validMovesList and check if moving to that
            Iterator<ChessMove> updatedValidMoves = validMovesList.iterator();
               while(updatedValidMoves.hasNext()){
                   ChessMove move = updatedValidMoves.next();
                   //position would leave the team's king in danger of check
                   if(inDangerOfCheck(move)) {
                       /*DO THIS IN inDangerOfCheck When in your ChessGame.validMoves, you may want to create
                        a copy/clone of the ChessBoard so that you can make a piece move
                        and see if you are still in check to know if that is a valid
                        move or not.*/
                        //if yes, remove it from the list
                        updatedValidMoves.remove();
                    }
            }
        }
        return validMovesList;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        /*Receives a given move and executes it, provided
        it is a legal move. If the move is illegal, it
        throws an InvalidMoveException. A move is illegal
        if it is not a "valid" move for the piece at the
        starting location, or if it’s not the corresponding
        team's turn.*/
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //Returns true if the specified team’s King could be captured by an opposing piece.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //Returns true if the given team has no way to protect their king from being captured.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //Returns true if the given team has no legal moves but their king is not in immediate danger.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.Board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return Board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(Board, chessGame.Board) && teamColor == chessGame.teamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Board, teamColor);
    }
}
