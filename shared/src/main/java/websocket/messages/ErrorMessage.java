package websocket.messages;

import static websocket.messages.ServerMessage.ServerMessageType.ERROR;

public class ErrorMessage extends ServerMessage {
    /*field errorMessage
    constructor(errorMessage):
    set serverMessageType to ERROR
    store errorMessage*/
    private String errorMessage;

    public ErrorMessage(String message){
        serverMessageType = ERROR;
        this.errorMessage = message;
    }

    public String getErrorMessage(){return this.errorMessage;}
}
