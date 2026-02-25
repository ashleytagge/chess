package service.result;

import java.util.List;

public record ListGamesResult(List<GameData> games) {
    public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName){}
}
