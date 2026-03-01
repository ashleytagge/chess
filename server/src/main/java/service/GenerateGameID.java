package service;

import java.util.Random;

public class GenerateGameID {

    public static int gameID = 1000;

    public static int generateGameID(){
        return gameID++;
    }
}
