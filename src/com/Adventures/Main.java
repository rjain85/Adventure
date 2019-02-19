package com.Adventures;

import java.util.Scanner;

import static com.Adventures.Adventure.gameLayout;

/**
 * Class in which user inputs are taken and game methods are implemented.
 */
public class Main {
    public static void main(String[] arguments) throws Exception {

        //String url = arguments[0];

        //String url = "https://pastebin.com/raw/1RPk7im3";

        //catchApiRequestExceptions(url);

        String fileNameFromCommand = arguments[0];

        gamePlay.setUp(fileNameFromCommand);

        Scanner takesUserInput = new Scanner(System.in);

        gamePlay.choosePlayer(takesUserInput);

        // Print first Strings to initialize game.
        System.out.println(gamePlay.initializeGame);

        // Set the current room to the first one.
        Room currentRoom = gameLayout.getRooms().get(0);

        // Run the loop which implements the remaining methods for the game.
        gamePlay.loopThroughGame(currentRoom, takesUserInput);

    }

    public static Adventure gamePlay = new Adventure();
}