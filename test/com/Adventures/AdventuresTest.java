package com.Adventures;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


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

    @Test
    public void CreateCorrectOptionsMessageMultipleDirections() throws Exception {
        assertEquals("From here, you can go: West, Northeast, North, or East.", Adventures.getGameLayout().getRooms().get(1).createOptions());
    }

    @Test
    public void checkUserInputOneCharacterOneNull() throws Exception {
        assertEquals(null, testingAdventure.checkUserInput("g", Adventures.getGameLayout().getRooms().get(0)));
    }

    @Test
    public void checkUserInputGoNonDirectionReturnNull() throws Exception {
        assertEquals(null, testingAdventure.checkUserInput("go there", Adventures.getGameLayout().getRooms().get(0)));
    }

    @Test
    public void checkUserInputDirectionName() throws Exception {
        assertEquals("East", testingAdventure.checkUserInput("go east", Adventures.getGameLayout().getRooms().get(0)).getDirectionName());
    }

    @Test
    public void checkUserInputMixedCasesDirectionName() throws Exception {
        assertEquals("East", testingAdventure.checkUserInput("Go eaSt", Adventures.getGameLayout().getRooms().get(0)).getDirectionName());
    }

    @Test
    public void checkUserInputSpacesAndCasesDirectionName() throws Exception {
        assertEquals("East", testingAdventure.checkUserInput("go  eaSt", Adventures.getGameLayout().getRooms().get(0)).getDirectionName());
    }

    @Test
    public void findSiebelEntryRoomBeforeName() throws Exception {
        assertEquals("SiebelEntry", Adventures.getGameLayout().getRooms().get(0).getDirections().get(0).getRoom());
    }

    @Test
    public void gameOverQuitTrue() throws Exception {
        assertTrue(testingAdventure.gameOver("quit"));
    }

    @Test
    public void gameOverQuitMixedCasesTrue() throws Exception {
        assertTrue(testingAdventure.gameOver("qUiT"));
    }

    @Test
    public void gameOverQuitMixedCasesSpacesTrue() throws Exception {
        assertTrue(testingAdventure.gameOver("qUiT  "));
    }
    @Test
    public void gameOverExitTrue() throws Exception {
        assertTrue(testingAdventure.gameOver("exit"));
    }

    @Test
    public void gameOverExitMixedCasesTrue() throws Exception {
        assertTrue(testingAdventure.gameOver("eXiT"));
    }

    @Test
    public void gameOverExitMixedCasesSpacesTrue() throws Exception {
        assertTrue(testingAdventure.gameOver("exIt  "));
    }

    @Test
    public void gameOverFalse() throws Exception {
        assertFalse(testingAdventure.gameOver("q"));
    }

    @Test
    public void validUrlTrue() throws Exception {
        assertTrue(testingAdventure.validUrl("https://courses.engr.illinois.edu/cs126/adventure/circular.json"));
    }

    @Test
    public void validUrlFalse() throws Exception {
        assertFalse(testingAdventure.validUrl("htts://courses.engr.illinois.edu/cs126/adventure/circular.json"));
    }
}
