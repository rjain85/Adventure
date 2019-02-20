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
public class Adventure {

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
     * Message that allows you to exit game.
     */
    public String exitMessage = "exit";

    /**
     * Alternative message that allows you to quit game.
     */
    public String quitMessage = "quit";

    /**
     * Message that initializes game with first set of options.
     */
    public String initializeGame = "Your journey begins now.";

    /**
     * instance of Player for th user to play the game.
     */
    public Player player;

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
     * takes .json filename and parses is to gameLayout;
     * @param fileName
     * @throws Exception
     */
    public void setUpJSON(String fileName) throws Exception {
        Gson gson = new Gson();
        gameLayout = gson.fromJson(DataForTesting.getFileContentsAsString(fileName), Layout.class);
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
    public void loopThroughGame(Room currentRoom, Scanner takesUserInput ) {

        // While the player has not reached the final room.
        while (!(currentRoom.getName().equals(gameLayout.getEndingRoom()))) {
            // A description of the current room is printed.
            System.out.println(currentRoom.getDescription());

            // If the room contains an item, the description is printed.
            if (currentRoom.getItems().size() != 0) {
                System.out.println(currentRoom.getItems().get(0).getItemDescription());
                String responseToItem = takesUserInput.nextLine();

                // The player can then indicate if they wish to take the item.
                if (isInputYes(responseToItem)) {
                    // The item is added to the player's item array.
                    player.getItems().add(currentRoom.getItems().get(0));
                }
            }

            // If the current room is not the final room, a list of potential directions is printed.
            if (!(currentRoom.getName().equals(gameLayout.getEndingRoom()))) {
                System.out.println(currentRoom.createOptions());
            }

            // The scanner takes the players input.
            String currentInput = takesUserInput.nextLine();

            // If the input is "Exit" or "Quit", the game ends.
            if (gameOver(currentInput)) {
                break;
            }

            // If the player correctly passes a valid room, they are moved to that room.
            if (checkUserInput(currentInput, currentRoom) != null) {
                //check if the room is enabled, and if so move the player to that room
                 if(checkUserInput(currentInput, currentRoom).getEnabled()) {
                     currentRoom = findRoomByName(checkUserInput(currentInput, currentRoom).getRoom());
                 } else {
                    if (canPlayerUnlock(checkUserInput(currentInput, currentRoom).getValidKeyNames().get(0))) {
                        currentRoom = findRoomByName(checkUserInput(currentInput, currentRoom).getRoom());
                    } else {
                        System.out.print(checkUserInput(currentInput, currentRoom).getLoserMessage());
                        break;
                    }
                 }
            }
        }

        if ((currentRoom.getName().equals(gameLayout.getEndingRoom()))) {
            System.out.println(currentRoom.getDescription());
        }
    }

    /**
     * Generates a message if the user passes a non-location.
     * @param whereToGo The String passed by the user.
     */
    public void printCantGoMessage(final String whereToGo) {
        System.out.println("I can't go to '" + whereToGo + "'");
    }

    /**
     * Generates a message if the user passes a String that does not begin with "go"
     * @param meaninglessPhrase The String passed by the user.
     */
    public void printDontUnderstandMessage(final String meaninglessPhrase) {
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
            printDontUnderstandMessage(userInput);
            return null;
        }

        //If the input does not begin with "go", return no direction.
        String checkGo = userInputEdited.substring(0, 2);
        if (!checkGo.equals("go")) {
            printDontUnderstandMessage(userInput);
            return null;
        }

        //If the input begins with go, check if the user indicates a direction and then return it.
        String checkDirection = userInputEdited.substring(2,(userInputEdited.length()));
        for (Direction possibleDirection : currentRoom.getDirections()) {
            if (checkDirection.equals(possibleDirection.getDirectionName().toLowerCase().replaceAll("\\s+",""))) {
                return possibleDirection;
            }
        }

        //If the user does not indicate a direction, print an  appropriate message and return null.
        printCantGoMessage(userInput);
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

        return (userInputEdited.equals(exitMessage) || userInputEdited.equals(quitMessage));
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

    /**
     * Checks if the player input is "yes".
     * @param input
     * @return
     */
    public boolean isInputYes(String input) {
        return(input.toLowerCase().equals("yes"));
    }

    /**
     * Checks whether the player has the necessary object to unlock a room.
     * @param keyName the item needed to enter that room
     * @return whether or not the Player can unlock the room
     */
    public boolean canPlayerUnlock(String keyName) {
        for (Item item : player.getItems()) {
            if (item.getItemName().equals(keyName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses player options from .json file so user can choose a player.
     * @param inputScanner to take the user's choice
     * @return
     */
    public Player choosePlayer(Scanner inputScanner) {
        boolean playerSelected = false;

        // The loop will run until the user correctly selects a player.
        while (!playerSelected) {
            System.out.println("Choose your character! You may play as: ");

            // Prints player options.
            for (Player p: gameLayout.getPlayers()) {
                System.out.println(p.getName());
            }

            // Checks if user's input matches a player.
            String userInput = inputScanner.nextLine();
            for (Player p: gameLayout.getPlayers()) {
                if (userInput.toLowerCase().equals(p.getName().toLowerCase())) {
                    player = p;
                    playerSelected = true;
                }
            }
            //Prints if user fails to choose a valid player.
            if (!playerSelected) {
                System.out.println("Invalid player.");
            }
        }
        return player;
    }
}
