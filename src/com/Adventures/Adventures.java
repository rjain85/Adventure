package com.Adventures;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * This class hold the methods and objects for the whole game.
 */

public class Adventures {

    private static final int STATUS_OK = 200;

    public static Layout gameLayout;

    public static Layout getGameLayout() {
        return gameLayout;
    }

    public String beginGame = (gameLayout.getRooms().get(0).getDescription() + "\n" + "Your journey begins here");

    public String exitMessage = ("exit");

    public String quitMessage = ("quit");


    public static void catchApiRequestExceptions(String url) {
        try {
            makeApiRequest(url);
        } catch (UnirestException e) {
//            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }
    }

    public static void makeApiRequest(String url) throws UnirestException, MalformedURLException {
        final HttpResponse<String> stringHttpResponse;

        // This will throw MalformedURLException if the url is malformed.
        new URL(url);

        stringHttpResponse = Unirest.get(url).asString();
        // Check to see if the request was successful; if so, convert the payload JSON into Java objects
        if (stringHttpResponse.getStatus() == STATUS_OK) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
            try {
                gameLayout = gson.fromJson(json, Layout.class);
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }


    public void createGameLooper (Room currentRoom, Scanner takesUserInput ) {
        while (!(currentRoom.getName().equals(gameLayout.getEndingRoom()))) {
            String currentInput = takesUserInput.nextLine();
            if (gameOver(currentInput)) {
                break;
            }
            if (checkForUrl(currentInput)) {
                Scanner newScanner = new Scanner(System.in);
                checkForUrl(currentInput);
                createGameLooper(gameLayout.getRooms().get(0), newScanner);
                break;
            }
            if (checkUserInput(currentInput, currentRoom) != null) {
                currentRoom = findRoomByName(checkUserInput(currentInput, currentRoom).getRoom());
            }
            System.out.println(currentRoom.getDescription());
            if (!(currentRoom.getName().equals(gameLayout.getEndingRoom()))) {
                System.out.println(currentRoom.createOptions());
            }
        }
    }

    public void createCantGoMessage(final String whereToGo) {
        System.out.println("I can't go to '" + whereToGo + "'");
    }

    public void createDontUnderstandMessage(final String meaninglessPhrase) {
        System.out.println("I don't understand '" + meaninglessPhrase + "'");
    }

    public StringBuilder initializeGame() {
        return new StringBuilder(beginGame + "\n" + gameLayout.getRooms().get(0).createOptions());
    }

    //https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java

    public Direction checkUserInput(final String userInput, final Room currentRoom) {
        String userInputEdited = userInput.replaceAll("\\s+","");
        userInputEdited = userInputEdited.toLowerCase();
        if (userInputEdited.length() < 2) {
            createDontUnderstandMessage(userInput);
            return null;
        }
        String checkGo = userInputEdited.substring(0, 2);
        if (!checkGo.equals("go")) {
            createDontUnderstandMessage(userInput);
            return null;
        }
        String checkDirection = userInputEdited.substring(2,(userInputEdited.length()));
        for (Direction possibleDirection : currentRoom.getDirections()) {
            if (checkDirection.equals(possibleDirection.getDirectionName().toLowerCase())) {
                return possibleDirection;
            }
        }
        createCantGoMessage(userInput);
        return null;
    }

    public Room findRoomByName(final String roomName) {
        for (Room room: gameLayout.getRooms()) {
            if (roomName.equals(room.getName())) {
                return room;
            }
        }
        return null;
    }

    public boolean gameOver (final String userInput) {
        String userInputEdited = userInput.replaceAll("\\s+","");
        userInputEdited = userInputEdited.toLowerCase();
        if (userInputEdited.equals(exitMessage) || userInputEdited.equals(quitMessage)) {
            return true;
        }
        return false;
    }

    public boolean checkForUrl (String userInput) {
        if (userInput.length() > 8 && userInput.substring(0, 8).equals("https://")) {
            catchApiRequestExceptions(userInput);
            return true;
        } else {
            return false;
        }
    }

}
