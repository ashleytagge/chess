package server.handler;

public class UserHandler {

    public void register(){
        //Your handlers will convert an HTTP request into Java usable objects & data.
        //The handler then calls the appropriate service.
        /*When the service responds, the handler converts the response
        *object back to JSON and sends the HTTP response.
        */
        /*This could include converting thrown
        * exception types into the appropriate HTTP
        * status codes if necessary.
         */

        /*
        * var serializer = new Gson();
        var game = new ChessGame();
        // serialize to JSON
        var json = serializer.toJson(game);
        // deserialize back to ChessGame
        game = serializer.fromJson(json, ChessGame.class);
        * */
    }

    public void login(){}

    public void logout(){}

}
