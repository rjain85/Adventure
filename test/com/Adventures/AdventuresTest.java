package com.Adventures;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AdventuresTest {

    String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";

    private static Adventures testingAdventure;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        Adventures.makeApiRequest(url);
        testingAdventure = gson.fromJson(DataForTesting.getFileContentsAsString("Data/siebelForTesting.json"), Adventures.class);
    }

    @Test
    public void parseStartingRoomName() throws Exception {
        assertEquals("MatthewsStreet", Adventures.getGameLayout().getStartingRoom());
    }

    @Test
    public void parseEndingRoomName() throws Exception {
        assertEquals("Siebel1314", Adventures.getGameLayout().getEndingRoom());
    }

    @Test
    public void parseRoomName() throws Exception {
        assertEquals("MatthewsStreet", Adventures.getGameLayout().getRooms().get(0).getName());
    }

    @Test
    public void parseRoomDescription() throws Exception {
        assertEquals("You are on Matthews, outside the Siebel Center", Adventures.getGameLayout().getRooms().get(0).getDescription());
    }

    @Test
    public void parseDirectionName() throws Exception {
        assertEquals("East", Adventures.getGameLayout().getRooms().get(0).getDirections().get(0).getDirectionName());
    }

    @Test
    public void parseDirectionRoom() throws Exception {
        assertEquals("SiebelEntry", Adventures.getGameLayout().getRooms().get(0).getDirections().get(0).getRoom());
    }

    @Test
    public void createCorrectOptionsMessage() throws Exception {
        assertEquals("From here, you can go: East", Adventures.getGameLayout().getRooms().get(0).createOptions());
    }
}
