package service.request;

public record CreateGameRequest(String authToken, String playerColor, int gameID) {
}
