package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.GameData;
import model.*;
import model.request.CreateGameRequest;
import model.request.GetGameRequest;
import model.request.JoinGameRequest;
import model.request.ListGamesRequest;
import model.result.CreateGameResult;
import model.result.GetGameResult;
import model.result.JoinGameResult;
import model.result.ListGamesResult;

import java.util.ArrayList;
import java.util.Collection;

public class GameService {
    /*
     *To do their work, service classes
     *need to make heavy use of the Model
     *classes and Data Access classes
     *described above.
     */
    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws DataAccessException {
        authDAO.getAuth(listGamesRequest.authToken());
        Collection<GameData> games = gameDAO.listGames();
        ArrayList<ListGamesResult.GameData> listResults = new ArrayList<>();
        for(GameData game : games){
            listResults.add(new ListGamesResult.GameData(
                    game.gameID(), game.whiteUsername(),
                    game.blackUsername(), game.gameName()
            ));
        }
        return new ListGamesResult(listResults);
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        //authorize the user here
        authDAO.getAuth(createGameRequest.authToken());
        //GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game)
        ChessGame newGame = new ChessGame();
        GameData newGameData = new GameData(0, null, null, createGameRequest.gameName(), newGame);
        int generatedID = gameDAO.createGame(newGameData);
        return new CreateGameResult(generatedID);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws DataAccessException {
        AuthData auth = authDAO.getAuth(joinGameRequest.authToken());
        GameData game = gameDAO.getGame(joinGameRequest.gameID());
         /*Retrieve the game
        Check if requested color is already taken
        If taken → throw "already taken"
        Otherwise update the game with that username with new object
        Call gameDAO.updateGame(updatedGame)*/
        if(joinGameRequest.playerColor().equals("WHITE")){
            if(game.whiteUsername() != null){
                throw new DataAccessException("already taken");
            }
            game = new GameData(game.gameID(), auth.username(), game.blackUsername(), game.gameName(), game.game());
        }else if (joinGameRequest.playerColor().equals("BLACK")){
            if(game.blackUsername() != null){
                throw new DataAccessException("already taken");
            }
            game = new GameData(game.gameID(), game.whiteUsername(), auth.username(), game.gameName(), game.game());
        }else{
            throw new DataAccessException("bad request");
        }
        gameDAO.updateGame(game);
        return new JoinGameResult();
    }
}
