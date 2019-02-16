package com.Adventures;

import java.util.ArrayList;

/**
 * contains Objects and getters for the Layout class.
 */
public class Layout {

    private String startingRoom;

    private String endingRoom;

    private ArrayList<Room> rooms;

    private Player player;

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Player getPlayer() {
        return player;
    }
}
