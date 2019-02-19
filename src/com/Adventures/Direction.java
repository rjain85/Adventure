package com.Adventures;

import java.util.ArrayList;

/**
 * Objects and Getters for the Direction class.
 */
public class Direction {

    private String directionName;

    private String room;

    private boolean enabled;

    private ArrayList<String> validKeyNames;

    private String loserMessage;

    private boolean hidden;

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public ArrayList<String> getValidKeyNames() {
        return validKeyNames;
    }

    public String getLoserMessage() {
        return loserMessage;
    }
    public boolean isHidden() {
        return hidden;
    }

}
