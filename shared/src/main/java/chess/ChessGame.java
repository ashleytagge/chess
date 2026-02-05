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
        Board.resetBoard();
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


     // Gets a valid moves for a piece at the given location
     // @param startPosition the piece to get valid moves for
     // @return Set of valid moves for requested piece, or null if no piece at
     // startPosition

    private boolean inDangerOfCheck(ChessMove move){
        //create deep copy of board so I can check it without altering the og
        // One such method is to have ChessBoard implement Cloneable,
        // then in the override clone method, you loop through the 2d ChessPiece array,
        // and do Arrays.copyOf to copy the chess board row by row, then finally putting
        // the 2d array into the cloned ChessBoard.
        ChessBoard tempBoard = Board.clone();
        ChessPosition kingPosition = null;

        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece tempPiece = tempBoard.getPiece(startPosition);
        tempBoard.addPiece(endPosition, tempPiece);
        tempBoard.addPiece(startPosition, null);
        TeamColor currentTeam = tempPiece.getTeamColor();
        TeamColor opposingTeam;
        if(currentTeam == TeamColor.BLACK){
            opposingTeam = TeamColor.WHITE;
        }else{
            opposingTeam = TeamColor.BLACK;
        }

        for(int row = 1; row < 9; row++){
            for(int col = 1; col < 9; col++){
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece findKingPiece = tempBoard.getPiece(pos);
                if(findKingPiece != null && findKingPiece.getPieceType().equals(ChessPiece.PieceType.KING) && findKingPiece.getTeamColor().equals(currentTeam)){
                    kingPosition = pos;
                    break;
                }
            }
        }
        //get the valid moves lists for the opposing teams pieces that would
        //capture the king after this move piece
        Collection<ChessMove> opponentMoves = new ArrayList <>();
        for(int row = 1; row < 9; row++){
            for(int col = 1; col < 9; col++){
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = tempBoard.getPiece(position);
                if(piece != null && piece.getTeamColor().equals(opposingTeam)){
                    Collection<ChessMove> pieceMoves = new ArrayList <>();
                    pieceMoves = piece.pieceMoves(tempBoard, position);
                    opponentMoves.addAll(pieceMoves);
                }
            }
        }
        //if the kings pos is in list of capture moves return true
        for(ChessMove elem : opponentMoves){
            if(elem.getEndPosition().equals(kingPosition)){
                return true;
            }
        }
        return false;
    }

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
        ChessPiece piece = Board.getPiece(startPosition); //does this need to be from the copied board?
        if(piece == null){
            return null;
        }else{
            validMovesList = piece.pieceMoves(Board, startPosition);
            int counter = validMovesList.size();
            //loop through validMovesList and check if moving to that
            //position would leave the team's king in danger of check
            /*DO THIS IN inDangerOfCheck When in your ChessGame.validMoves, you may want to create
                        a copy/clone of the ChessBoard so that you can make a piece move
                        and see if you are still in check to know if that is a valid
                        move or not.*/
            //if yes, remove it from the list
            validMovesList.removeIf(this::inDangerOfCheck);
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
        ChessPiece validatePiece = Board.getPiece(move.getStartPosition());
        if(validatePiece == null){
            throw new InvalidMoveException("There is no piece at that start position");
        }else if(validatePiece.getTeamColor() != getTeamTurn()){
            throw new InvalidMoveException("It is not your turn");
        }

        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if(validMoves.contains(move)){
            //make move
            ChessPosition startPosition = move.getStartPosition();
            ChessPosition endPosition = move.getEndPosition();
            ChessPiece tempPiece = Board.getPiece(startPosition);
            if(tempPiece.getPieceType() == ChessPiece.PieceType.PAWN){
                ChessPiece.PieceType promotion = move.getPromotionPiece();
                if(promotion == ChessPiece.PieceType.QUEEN){
                    ChessPiece promoteQueen = new ChessPiece(getTeamTurn(), ChessPiece.PieceType.QUEEN);
                    Board.addPiece(endPosition, promoteQueen);
                }else if(promotion == ChessPiece.PieceType.BISHOP){
                    ChessPiece promoteBishop = new ChessPiece(getTeamTurn(), ChessPiece.PieceType.BISHOP);
                    Board.addPiece(endPosition, promoteBishop);
                }else if(promotion == ChessPiece.PieceType.ROOK){
                    ChessPiece promoteRook = new ChessPiece(getTeamTurn(), ChessPiece.PieceType.ROOK);
                    Board.addPiece(endPosition, promoteRook);
                }else if(promotion == ChessPiece.PieceType.KNIGHT){
                    ChessPiece promoteKnight = new ChessPiece(getTeamTurn(), ChessPiece.PieceType.KNIGHT);
                    Board.addPiece(endPosition, promoteKnight);
                }else{
                    Board.addPiece(endPosition, tempPiece);
                    Board.addPiece(startPosition, null);
                }
                Board.addPiece(startPosition, null);
            }else{
                Board.addPiece(endPosition, tempPiece);
                Board.addPiece(startPosition, null);
            }
                //change team turns
            TeamColor currentTeam = tempPiece.getTeamColor();
            if(currentTeam == TeamColor.WHITE){
                setTeamTurn(TeamColor.BLACK);
            }else{
                setTeamTurn(TeamColor.WHITE);
            }
        }else{
            throw new InvalidMoveException("Invalid Move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //Returns true if the specified team’s King could be captured by an opposing piece.
        ChessPosition king = null;
        TeamColor enemyColor;
        if(teamColor == TeamColor.WHITE){
            enemyColor = TeamColor.BLACK;
        }else{
            enemyColor = TeamColor.WHITE;
        }

        for(int row = 1; row < 9; row++){
            for(int col = 1; col < 9; col++){
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece findKingPiece = Board.getPiece(pos);
                if(findKingPiece != null && findKingPiece.getPieceType().equals(ChessPiece.PieceType.KING) && findKingPiece.getTeamColor().equals(teamColor)){
                    king = pos;
                    break;
                }
            }
        }

        Collection<ChessMove> opponentMoves = new ArrayList <>();
        for(int row = 1; row < 9; row++){
            for(int col = 1; col < 9; col++){
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = Board.getPiece(position);
                if(piece != null && piece.getTeamColor() == enemyColor){
                    Collection<ChessMove> pieceMoves = new ArrayList <>();
                    pieceMoves = piece.pieceMoves(Board, position);
                    opponentMoves.addAll(pieceMoves);
                }
            }
        }
        //if the kings pos is in list of capture moves return true
        for(ChessMove elem : opponentMoves){
            if(elem.getEndPosition().equals(king)){
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //Returns true if the given team has no way to protect their king from being captured.
        if(isInCheck(teamColor)){
            for(int row = 1; row < 9; row++){
                for(int col = 1; col < 9; col++){
                    ChessPosition position = new ChessPosition(row, col);
                    ChessPiece piece = Board.getPiece(position);
                    if(piece != null && piece.getTeamColor() == teamColor){
                        Collection<ChessMove> pieceMoves = new ArrayList <>();
                        pieceMoves = piece.pieceMoves(Board, position);
                        //simulate move on copy of board and see if the king is still in check
                        for(ChessMove move : pieceMoves){
                            if(!inDangerOfCheck(move)){
                                return false;
                            }
                        }
                        //if the king is still in check, do nothing
                        //if the king is not in check then return false
                    }
                }
            }
            return true;
            //if you get all the way through that loop return true;
        }else{
            return false;
        }
        //if in check
        //get all valid moves for the pieces on the board, try all the moves for each piece
        //if the king is still in check after then it is in checkmate and return true
        //otherwise return false
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
        if(!isInCheck(teamColor)) {
            for (int row = 1; row < 9; row++) {
                for (int col = 1; col < 9; col++) {
                    ChessPosition position = new ChessPosition(row, col);
                    ChessPiece piece = Board.getPiece(position);
                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> pieceMoves = new ArrayList<>();
                        pieceMoves = piece.pieceMoves(Board, position);
                        //simulate move on copy of board and see if the king is still in check
                        for (ChessMove move : pieceMoves) {
                            if (!inDangerOfCheck(move)) {
                                return false;
                            }
                        }
                        //if the king is still in check, do nothing
                        //if the king is not in check then return false
                    }
                }
            }
            return true;
            //if you get all the way through that loop return true;
        } else {

            return false;
        }
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
