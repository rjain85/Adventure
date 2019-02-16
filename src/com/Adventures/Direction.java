package com.Adventures;

import java.util.ArrayList;

/**
 * Objects and Getters for the Direction class.
 */
public class Direction {

    private String directionName;

    private String room;

    private String enabled;

    private ArrayList<String> validKeyNames;

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }

    public String getEnabled() {
        return enabled;
    }

    public ArrayList<String> getValidKeyNames() {
        return validKeyNames;
    }

}
