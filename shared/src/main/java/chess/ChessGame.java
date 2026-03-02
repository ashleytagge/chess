package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class ChessGame {

    private ChessBoard board;
    private TeamColor teamColor;

    public ChessGame() {
        board = new ChessBoard();
        teamColor = ChessGame.TeamColor.WHITE;
        board.resetBoard();
    }

    public TeamColor getTeamTurn() {
        return teamColor;
    }

    public void setTeamTurn(TeamColor team) {
        teamColor = team;
    }

    public enum TeamColor {
        WHITE,
        BLACK
    }

     // Gets a valid moves for a piece at the given location

    private boolean inDangerOfCheck(ChessMove move){
        ChessBoard tempBoard = board.clone();
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
        Collection<ChessMove> validMovesList;
        ChessPiece piece = board.getPiece(startPosition); //does this need to be from the copied board?
        if(piece == null){
            return null;
        }else{
            validMovesList = piece.pieceMoves(board, startPosition);
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

    public void makeMove(ChessMove move) throws InvalidMoveException {
        /*Receives a given move and executes it, provided
        it is a legal move. If the move is illegal, it
        throws an InvalidMoveException. A move is illegal
        if it is not a "valid" move for the piece at the
        starting location, or if it’s not the corresponding
        team's turn.*/
        ChessPiece validatePiece = board.getPiece(move.getStartPosition());
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
            ChessPiece tempPiece = board.getPiece(startPosition);
            if(tempPiece.getPieceType() == ChessPiece.PieceType.PAWN){
                ChessPiece.PieceType promotion = move.getPromotionPiece();
                if(promotion == ChessPiece.PieceType.QUEEN){
                    ChessPiece promoteQueen = new ChessPiece(getTeamTurn(), ChessPiece.PieceType.QUEEN);
                    board.addPiece(endPosition, promoteQueen);
                }else if(promotion == ChessPiece.PieceType.BISHOP){
                    ChessPiece promoteBishop = new ChessPiece(getTeamTurn(), ChessPiece.PieceType.BISHOP);
                    board.addPiece(endPosition, promoteBishop);
                }else if(promotion == ChessPiece.PieceType.ROOK){
                    ChessPiece promoteRook = new ChessPiece(getTeamTurn(), ChessPiece.PieceType.ROOK);
                    board.addPiece(endPosition, promoteRook);
                }else if(promotion == ChessPiece.PieceType.KNIGHT){
                    ChessPiece promoteKnight = new ChessPiece(getTeamTurn(), ChessPiece.PieceType.KNIGHT);
                    board.addPiece(endPosition, promoteKnight);
                }else{
                    board.addPiece(endPosition, tempPiece);
                    board.addPiece(startPosition, null);
                }
                board.addPiece(startPosition, null);
            }else{
                board.addPiece(endPosition, tempPiece);
                board.addPiece(startPosition, null);
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
                ChessPiece findKingPiece = board.getPiece(pos);
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
                ChessPiece piece = board.getPiece(position);
                if(piece != null && piece.getTeamColor() == enemyColor){
                    Collection<ChessMove> pieceMoves = new ArrayList <>();
                    pieceMoves = piece.pieceMoves(board, position);
                    opponentMoves.addAll(pieceMoves);
                }
            }
        }
        //if the kings position is in list of capture moves return true
        for(ChessMove elem : opponentMoves){
            if(elem.getEndPosition().equals(king)){
                return true;
            }
        }
        return false;
    }

    private boolean hasNoMove(TeamColor teamColor) {
        //if the king is still in check, do nothing
        //if the king is not in check then return false
        for(int row = 1; row < 9; row++){
            for(int col = 1; col < 9; col++){
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if(piece != null && piece.getTeamColor() == teamColor){
                    Collection<ChessMove> pieceMoves = validMoves(position);
                    if(pieceMoves != null && !pieceMoves.isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isInCheckmate(TeamColor teamColor) {
        //Returns true if the given team has no way to protect their king from being captured.
        if(isInCheck(teamColor)){
            return hasNoMove(teamColor);
        }
        return false;
    }

    public boolean isInStalemate(TeamColor teamColor) {
        if(!isInCheck(teamColor)) {
            return hasNoMove(teamColor);
        }
        return false;

    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamColor == chessGame.teamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamColor);
    }
}
