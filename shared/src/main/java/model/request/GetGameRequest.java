package model.request;

import model.GameData;

public record GetGameRequest (String authToken, int gameID) {}