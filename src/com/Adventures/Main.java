package com.Adventures;

import java.util.Scanner;

import static com.Adventures.Adventures.catchApiRequestExceptions;
import static com.Adventures.Adventures.gameLayout;

/**
 * Class in which user inputs are taken and game methods are implemented.
 */
public class Main {
    public static void main(String[] arguments) {
        String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";

        catchApiRequestExceptions(url);

        Adventures gamePlay = new Adventures();

        Scanner takesUserInput = new Scanner(System.in);

        // Print first Strings to initialize game.
        System.out.println(gamePlay.initializeGame);

        // Set the current room to the first one.
        Room currentRoom = gameLayout.getRooms().get(0);

        // Run the loop which implements the remaining methods for the game.
        gamePlay.createGameLooper(currentRoom, takesUserInput);

    }
}