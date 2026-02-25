package service;

import service.request.CreateGameRequest;
import service.request.JoinGameRequest;
import service.request.ListGamesRequest;
import service.result.CreateGameResult;
import service.result.JoinGameResult;
import service.result.ListGamesResult;

import java.util.List;

public class GameService {
    /*
     *To do their work, service classes
     *need to make heavy use of the Model
     *classes and Data Access classes
     *described above.
     */

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) {
        return new ListGamesResult(List.of(new ListGamesResult.GameData(1234, "whitePiece", "blackPiece", "game name")));
    }
    public CreateGameResult createGame(CreateGameRequest createGameRequest) {
        return new CreateGameResult(1234);
    }
    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) {
        //if player is already taken 403: AlreadyTakenException
        return new JoinGameResult();
    }
}
