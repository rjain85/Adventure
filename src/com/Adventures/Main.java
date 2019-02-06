package com.Adventures;

import java.util.Scanner;

import static com.Adventures.Adventures.catchApiRequestExceptions;
import static com.Adventures.Adventures.gameLayout;

public class Main {
    public static void main(String[] arguments) {
        String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";

        catchApiRequestExceptions(url);

        Adventures gamePlay = new Adventures();

        Scanner takesUserInput = new Scanner(System.in);

        System.out.println(gamePlay.initializeGame());

        Room currentRoom = gameLayout.getRooms().get(0);

        gamePlay.createGameLooper(currentRoom, takesUserInput);

    }
}