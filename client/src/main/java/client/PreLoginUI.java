package client;

import exception.ResponseException;

public class PreLoginUI {

    public String signIn(String... params) throws ResponseException {
        if (params.length >= 1) {
            state = State.SIGNEDIN;
            visitorName = String.join("-", params);
            ws.enterPetShop(visitorName);
            return String.format("You signed in as %s.", visitorName);
        }
        throw new ResponseException(ResponseException.Code.ClientError, "Expected: <yourname>");
    }

}
