package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    /*
    Field	Type
    gameID	int
    whiteUsername	String
    blackUsername	String
    gameName	String
    game	ChessGame
    */
}
