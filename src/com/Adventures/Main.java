package com.Adventures;

import java.util.Scanner;

import static com.Adventures.Adventure.gameLayout;

/**
 * Class in which user inputs are taken and game methods are implemented.
 */
public class Main {
    public static void main(String[] arguments) throws Exception{

        recieveURLfromCOMMAND(arguments[0]);

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

    /**
     * allow user to pass a URL or filename throught the command line;
     * @param firstArgument the first argument passed through the commandLine
     * @throws Exception
     */
    public static void recieveURLfromCOMMAND (String firstArgument) throws Exception {

        if (firstArgument.length() > 8 && firstArgument.substring(0, 8).equals("https://")) {
            gamePlay.catchApiRequestExceptions(firstArgument);
        } else {
            gamePlay.setUpJSON(firstArgument);
        }
    }
}