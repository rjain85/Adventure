package com.Adventures;

import java.util.ArrayList;

public class Room {

    private String name;

    private String description;

    private ArrayList<Direction> directions;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public StringBuilder createOptions() {
        StringBuilder presentOptions =  new StringBuilder("From here, you can go: ");
        for (int i = 0; i < getDirections().size(); i++) {
            presentOptions.append(getDirections().get(i).getDirectionName());
        }
        return presentOptions;
    }


}
