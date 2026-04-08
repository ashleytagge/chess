package websocket.messages;

import static websocket.messages.ServerMessage.ServerMessageType.NOTIFICATION;

public class NotificationMessage extends ServerMessage {
    /*class NotificationMessage extends ServerMessage:
    field message
    constructor(message):
    set serverMessageType to NOTIFICATION
    store message*/
    private String message;

    public NotificationMessage(String currentMessage){
        serverMessageType = NOTIFICATION;
        message = currentMessage;
    }

    public String getMessage(){return this.message;}
    }
