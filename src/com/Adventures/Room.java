package com.Adventures;

import java.util.ArrayList;

/**
 * Contains Objects, getters, and methods for the Room class.
 */
public class Room {

    private String name;

    private String description;

    private ArrayList<Direction> directions;

    private ArrayList<Item> items;

    private ArrayList<Spell> spells;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    /**
     * Calls the ArrayList of directions and builds a grammatically correct String based on ArrayList length.
     * @return A String of options, directions in which the user may move.
     */
    public String createOptions() {

        StringBuilder presentOptions = new StringBuilder("From here, you can go: ");

        if (getDirections().size() == 1) {
            presentOptions.append(getDirections().get(0).getDirectionName());
            return presentOptions.toString();
        } else {
            for (int i = 0; i < getDirections().size() - 1; i++) {
                presentOptions.append(getDirections().get(i).getDirectionName());
                presentOptions.append(", ");
            }
            presentOptions.append("or ");
            presentOptions.append(getDirections().get(getDirections().size() - 1).getDirectionName());
            presentOptions.append(".");
        }
        return presentOptions.toString();
    }
}
