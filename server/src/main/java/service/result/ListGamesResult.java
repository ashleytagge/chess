package service.result;

import java.util.ArrayList;

public record ListGamesResult(ArrayList<GameData> games) {
    public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName){}
}
