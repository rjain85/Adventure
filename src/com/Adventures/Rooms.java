package com.Adventures;

import java.util.ArrayList;

public class Rooms {

    private String name;

    private String description;

    private ArrayList<Directions> directions;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Directions> getDirections() {
        return directions;
    }

    public StringBuilder createOptions() {
        StringBuilder presentOptions =  new StringBuilder("From here, you can go: ");
        for (int i = 0; i < getDirections().size(); i++) {
            presentOptions.append(getDirections().get(i));
        }
        return presentOptions;
    }


}
