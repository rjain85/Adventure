package com.Adventures;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.util.Scanner;

import static com.Adventures.Adventures.gameLayout;
import static com.Adventures.Adventures.makeApiRequest;

public class Main {
    public static void main(String[] arguments) {
        String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
        apiRequestWrapper(url);

        Adventures gamePlay = new Adventures();

        Scanner takesUserInput = new Scanner(System.in);

        System.out.println(gamePlay.initializeGame());
        Room currentRoom = gameLayout.getRooms().get(0);

        while (!(currentRoom.getName().equals(gameLayout.getEndingRoom()))) {
            String currentInput = takesUserInput.nextLine();
            if (gamePlay.gameOver(currentInput)) {
                break;
            }
            if (gamePlay.checkUserInput(currentInput, currentRoom) != null) {
                currentRoom = gamePlay.findRoomByName(gamePlay.checkUserInput(currentInput, currentRoom).getRoom());
            }
            System.out.println(currentRoom.getDescription());
            if (!(currentRoom.getName().equals(gameLayout.getEndingRoom()))) {
                System.out.println(currentRoom.createOptions());
            }
        }
    }

    public static void apiRequestWrapper(String url) {
        try {
            makeApiRequest(url);
        } catch (UnirestException e) {
//            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }
    }
}