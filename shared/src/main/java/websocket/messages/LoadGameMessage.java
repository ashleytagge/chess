package websocket.messages;

public class LoadGameMessage extends ServerMessage {
    private Object loadGameMessage; // you can replace Object with your Game class

    public LoadGameMessage(Object loadGameMessage) {
        super(ServerMessageType.LOAD_GAME);
        this.loadGameMessage = loadGameMessage;
    }

    public Object getGame() {
        return loadGameMessage;
    }
}
