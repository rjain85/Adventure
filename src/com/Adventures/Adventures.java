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

    /**
     * The code for HTTP instance okay.
     */
    private static final int STATUS_OK = 200;

    /**
     * GameLayout for holding rooms. .
     */
    public static Layout gameLayout;

    /**
     * getter for GameLayout.
     * @return gameLayout
     */
    public static Layout getGameLayout() {
        return gameLayout;
    }

    /**
     * First two lines of game.
     */
    public String beginGame = (gameLayout.getRooms().get(0).getDescription() + "\n" + "Your journey begins here");

    /**
     * Message that allows you to exit game.
     */
    public String exitMessage = ("exit");

    /**
     * Alternative message that allows you to quit game.
     */
    public String quitMessage = ("quit");

    /**
     * Message that initializes game with first set of options.
     */
    public String initializeGame = (beginGame + "\n" + gameLayout.getRooms().get(0).createOptions());

    /**
     * Makes Api request and catches exceptions and prints error message otherwise.
     * Calls makeApiRequest method.
     * @param url for JSON.
     */
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

    /**
     * Makes API request
     * @param url for JSON.
     * @throws UnirestException
     * @throws MalformedURLException
     */
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

    /**
     * While loop that moves players through rooms, prints appropriate messages, and checks if the game is over,
     * @param currentRoom The Room the player starts in, typically the first.
     * @param takesUserInput A scanner to take Strings the user types into the console.
     */
    public void createGameLooper (Room currentRoom, Scanner takesUserInput ) {

        // While the player has not reached the final room.
        while (!(currentRoom.getName().equals(gameLayout.getEndingRoom()))) {

            // The scanner takes the players input.
            String currentInput = takesUserInput.nextLine();

            // If the input is "Exit" or "Quit", the game ends.
            if (gameOver(currentInput)) {
                break;
            }

            //If the player passes a valid url, the game is replayed with a new JSON file.
            if (validUrl(currentInput)) {
                Scanner newScanner = new Scanner(System.in);
                validUrl(currentInput);
                createGameLooper(gameLayout.getRooms().get(0), newScanner);
                break;
            }

            // If the player correctly passes a valid room, they are moved to that room.
            if (checkUserInput(currentInput, currentRoom) != null) {
                currentRoom = findRoomByName(checkUserInput(currentInput, currentRoom).getRoom());
            }

            // A description of the current room is printed.
            System.out.println(currentRoom.getDescription());

            // If the current room is not the final room, a list of potential directions is printed.
            if (!(currentRoom.getName().equals(gameLayout.getEndingRoom()))) {
                System.out.println(currentRoom.createOptions());
            }
        }
    }

    /**
     * Generates a message if the user passes a non-location.
     * @param whereToGo The String passed by the user.
     */
    public void createCantGoMessage(final String whereToGo) {
        System.out.println("I can't go to '" + whereToGo + "'");
    }

    /**
     * Generates a message if the user passes a String that does not begin with "go"
     * @param meaninglessPhrase The String passed by the user.
     */
    public void createDontUnderstandMessage(final String meaninglessPhrase) {
        System.out.println("I don't understand '" + meaninglessPhrase + "'");
    }

    /**
     * The regular expression in this method is from:
     * https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java
     * @param userInput The String passed by the user.
     * @param currentRoom The Room the player is currently in.
     * @return The direction in which the user wants to move.
     */
    public Direction checkUserInput(final String userInput, final Room currentRoom) {

        // Removes spaces and makes the user's input lower case.
        String userInputEdited = userInput.replaceAll("\\s+","");
        userInputEdited = userInputEdited.toLowerCase();

        // If the input is less than two characters long, return no direction.
        if (userInputEdited.length() < 2) {
            createDontUnderstandMessage(userInput);
            return null;
        }

        //If the input does not begin with "go", return no direction.
        String checkGo = userInputEdited.substring(0, 2);
        if (!checkGo.equals("go")) {
            createDontUnderstandMessage(userInput);
            return null;
        }

        //If the input begins with go, check if the user indicates a direction and then return it.
        String checkDirection = userInputEdited.substring(2,(userInputEdited.length()));
        for (Direction possibleDirection : currentRoom.getDirections()) {
            if (checkDirection.equals(possibleDirection.getDirectionName().toLowerCase())) {
                return possibleDirection;
            }
        }

        //If the user does not indicate a direction, print an  appropriate message and return null.
        createCantGoMessage(userInput);
        return null;
    }

    /**
     * input a Room name and return the corresponding Room object.
     * @param roomName the Room you're searching for.
     * @return a Room object
     */
    public Room findRoomByName(final String roomName) {
        for (Room room: gameLayout.getRooms()) {
            if (roomName.equals(room.getName())) {
                return room;
            }
        }
        return null;
    }

    /**
     * The regular expression is from:
     * https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java
     * Check if the user has passed a message indicating they want to end the game.
     * @param userInput The String passed by the user.
     * @return true or false, whether or not the game is over.
     */
    public boolean gameOver (final String userInput) {

        // Removes spaces and makes the user's input lower case.
        String userInputEdited = userInput.replaceAll("\\s+","");
        userInputEdited = userInputEdited.toLowerCase();

        if (userInputEdited.equals(exitMessage) || userInputEdited.equals(quitMessage)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if an input passed by the URL is a valid input.
     * @param userInput The String passed by the user.
     * @return whether or not the URL passed is valid.
     */
    public boolean validUrl(String userInput) {

        /**
         * If a String is longer than 8 characters and begins with "https://", then an API request is made.
         */
        if (userInput.length() > 8 && userInput.substring(0, 8).equals("https://")) {
            catchApiRequestExceptions(userInput);
            return true;
        } else {
            return false;
        }
    }
}
