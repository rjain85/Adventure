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
        // do i need com.Adventures?
    }

    @Test
    public void parseStartingRoomName() throws Exception {
        assertEquals("MatthewsStreet", Adventures.getGameLayout().getStartingRoom());
    }

    @Test public void parseEndingRoomName() throws Exception {
        assertEquals("Siebel1314", Adventures.getGameLayout().getEndingRoom());
    }

    /**@Test public void parseRoom() throws Exception {
        assertEquals();
    }**/

}
