package service.request;

public record JoinGameRequest(String authToken, String gameName) {
}
