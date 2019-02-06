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

    public String createOptions() {
        String presentOptions = ("From here, you can go: ");

        if (getDirections().size() == 1) {
            presentOptions+= (getDirections().get(0).getDirectionName());
            return presentOptions;
        } else {
            for (int i = 0; i < getDirections().size() - 1; i++) {
                presentOptions+=(getDirections().get(i).getDirectionName());
                presentOptions+=(", ");
            }
            presentOptions+=("or ");
            presentOptions+=(getDirections().get(getDirections().size() - 1).getDirectionName());
            presentOptions+=(".");
            return presentOptions;
        }
    }
}
