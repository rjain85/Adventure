package com.Adventures;

import java.util.ArrayList;

public class Room {

    private String name;

    private String description;

    private ArrayList<Direction> directions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }


}
