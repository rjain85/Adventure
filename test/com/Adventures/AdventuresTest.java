package com.Adventures;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;


public class AdventuresTest {

    private String url = "https://pastebin.com/raw/jWrB4BAt";

    private Adventure testingAdventure = new Adventure();
    private Layout layout;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        //Adventures.makeApiRequest(url);
        layout = gson.fromJson(DataForTesting.getFileContentsAsString("Data/Hogwarts.json"), Layout.class);
    }

    @Test
    public void parseStartingRoomName() throws Exception {
        assertEquals("GryffindorCommonRoom", layout.getStartingRoom());
    }

    @Test
    public void parseEndingRoomName() throws Exception {
        assertEquals("MirrorRoom", layout.getEndingRoom());
    }

    @Test
    public void parseRoomName() throws Exception {
        assertEquals("GryffindorCommonRoom", layout.getRooms().get(0).getName());
    }

    @Test
    public void parseRoomDescription() throws Exception {
        assertEquals("You are in the Gryffindor Common Room.", layout.getRooms().get(0).getDescription());
    }

    @Test
    public void parseDirectionName() throws Exception {
        assertEquals("Outside the common room", layout.getRooms().get(0).getDirections().get(0).getDirectionName());
    }

    @Test
    public void parseEnabledState() throws Exception {
        assertTrue(layout.getRooms().get(3).getDirections().get(0).getEnabled());
    }

    @Test
    public void parseItemName() throws Exception {
        assertEquals("CloakOfInvisibility", layout.getRooms().get(0).getItems().get(0).getItemName());
    }

    @Test
    public void parseLoserMessage() throws Exception {
        assertEquals("Filch and Mrs. Norris have caught you! GAME OVER.", layout.getRooms().get(0).getDirections().get(0).getLoserMessage());
    }

    @Test
    public void parsePlayerName() throws Exception {
        assertEquals("Harry", layout.getPlayers().get(0).getName());
    }

    @Test
    public void parseEnabled() throws Exception {
        assertTrue(layout.getRooms().get(2).getDirections().get(0).getEnabled());
    }

    // Tests for createOptions

    @Test
    public void createCorrectOptionsMessage() throws Exception {
        assertEquals("From here, you can go: Outside the common room", layout.getRooms().get(0).createOptions());
    }


    // Tests for checkUserInput

    @Test
    public void checkUserInputDirectionName() throws Exception {
        assertEquals("Outside the common room", testingAdventure.checkUserInput("go outside the common room", layout.getRooms().get(0)).getDirectionName());
    }

    @Test
    public void checkUserInputMixedCasesDirectionName() throws Exception {
        assertEquals("Outside the common room", testingAdventure.checkUserInput("Go OutsIdE the common room", layout.getRooms().get(0)).getDirectionName());
    }

    @Test
    public void checkUserInputSpacesAndCasesDirectionName() throws Exception {
        assertEquals("Outside the common room", testingAdventure.checkUserInput("go  outside  the COMMOn room", layout.getRooms().get(0)).getDirectionName());
    }

    // Tests for findRoomByName

    @Test
    public void findRoomByName() throws Exception {
        assertEquals("SecondFloorCorridor", layout.getRooms().get(0).getDirections().get(0).getRoom());
    }

    // Tests for gameOver

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

    // Tests for validUrl

    @Test
    public void validUrlTrue() throws Exception {
        assertTrue(testingAdventure.validUrl("https://courses.engr.illinois.edu/cs126/adventure/circular.json"));
    }

    @Test
    public void validUrlFalse() throws Exception {
        assertFalse(testingAdventure.validUrl("htts://courses.engr.illinois.edu/cs126/adventure/circular.json"));
    }

    // Test for isInputYes

    @Test
    public void inputYesTrue() throws Exception {
        assertTrue(testingAdventure.isInputYes("Yes"));
    }

    @Test
    public void inputYesFalse() throws Exception {
        assertFalse(testingAdventure.isInputYes("nah"));
    }
}
