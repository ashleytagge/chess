package websocket.messages;

import chess.ChessGame;

import static websocket.messages.ServerMessage.ServerMessageType.LOAD_GAME;

public class LoadGameMessage extends ServerMessage {
    /*class LoadGameMessage extends ServerMessage:
    field game
    constructor(game):
        set serverMessageType to LOAD_GAME
        store game*/
    private ChessGame game;

    public LoadGameMessage(ChessGame currentGame) {
        serverMessageType = LOAD_GAME;
        this.game = currentGame;
    }

    public ChessGame getGame(){return game;}


}
