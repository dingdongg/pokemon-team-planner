package model;

import model.exceptions.TeamNotFoundException;
import model.types.PokemonType;
import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonTeamCollectionTest {

    private PokemonTeamCollection testCollection;
    private Pokemon testPokemonA;
    private Pokemon testPokemonC;
    private Pokemon testPokemonD;
    private Pokemon testPokemonE;
    private Pokemon testPokemonF;
    private PokemonTeam testTeam;

    @BeforeEach
    public void setUp() {
        Types.initializeTypeConstants();
        testCollection = new PokemonTeamCollection();

        testPokemonA = new Pokemon("A");
        testPokemonC = new Pokemon("C");
        testPokemonD = new Pokemon("D");
        testPokemonE = new Pokemon("E");
        testPokemonF = new Pokemon("F");

        testTeam = new PokemonTeam("a team");

    }

    @Test
    public void testPokemonTeamCollection() {

        PokemonTeamCollection newCollection = new PokemonTeamCollection();
    }

    @Test
    public void testAddNewTeamEmptyTeam() {

        testCollection.addNewTeam(testTeam);

        testCollection.addNewTeam(testTeam);

        assertTrue(testCollection.contains(testTeam));
    }

    @Test
    public void testAddNewTeamNotEmpty() {

        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonE);

        testCollection.addNewTeam(testTeam);

        assertTrue(testCollection.contains(testTeam));
    }

    @Test
    public void testAddNewTeamMultipleTeams() {

        PokemonTeam teamTwo = new PokemonTeam("second team");
        PokemonTeam teamThree = new PokemonTeam("third team");

        testTeam.addPokemon(testPokemonC);

        teamTwo.addPokemon(testPokemonD);
        teamTwo.addPokemon(testPokemonF);

        teamThree.addPokemon(testPokemonA);

        testCollection.addNewTeam(testTeam);
        testCollection.addNewTeam(teamTwo);
        testCollection.addNewTeam(teamThree);

        assertTrue(testCollection.contains(testTeam));
        assertTrue(testCollection.contains(teamTwo));
        assertTrue(testCollection.contains(teamThree));
        assertEquals(testCollection.sizeCollection(), 3);
    }

    @Test
    public void testEditTeamEmpty() {

        testCollection.addNewTeam(testTeam);
        testCollection.editTeam(testTeam);

        assertTrue(testTeam.getEditStatus());
    }

    @Test
    public void testEditTeamNotEmpty() {

        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonC);

        testCollection.addNewTeam(testTeam);
        testCollection.editTeam(testTeam);

        assertTrue(testTeam.getEditStatus());
    }

    @Test
    public void testDeleteTeam() {

        testCollection.addNewTeam(testTeam);
        testCollection.deleteTeam(testTeam);

        assertFalse(testCollection.contains(testTeam));
    }

    @Test
    public void testDeleteTeamMultipleTeams() {

        PokemonTeam teamTwo = new PokemonTeam("second team");
        PokemonTeam teamThree = new PokemonTeam("third team");

        testCollection.addNewTeam(testTeam);
        testCollection.addNewTeam(teamTwo);
        testCollection.addNewTeam(teamThree);

        testCollection.deleteTeam(teamThree);
        testCollection.deleteTeam(testTeam);

        assertFalse(testCollection.contains(testTeam));
        assertTrue(testCollection.contains(teamTwo));
        assertFalse(testCollection.contains(teamThree));
    }

    @Test
    public void testViewTeamEmpty() {

        testCollection.addNewTeam(testTeam);

        assertEquals(testCollection.viewTeam(testTeam), "empty team");
    }

    @Test
    public void testViewTeamOnePokemon() {

        PokemonType dragon = new PokemonType("DRAGON");
        testPokemonA.setFirstType(dragon);
        String name = testPokemonA.getName();
        String type = testPokemonA.getFirstType().getTypeName();

        testTeam.addPokemon(testPokemonA);
        testCollection.addNewTeam(testTeam);

        assertEquals(testCollection.viewTeam(testTeam), name + " (" + type + ")");
    }

    @Test
    public void testViewTeamMultiplePokemon() {

        PokemonType ground = new PokemonType("GROUND");
        PokemonType rock = new PokemonType("ROCK");

        testPokemonD.setFirstType(ground);
        testPokemonE.setFirstType(rock);

        String nameD = testPokemonD.getName();
        String typeD = testPokemonD.getFirstType().getTypeName();
        String nameE = testPokemonE.getName();
        String typeE = testPokemonE.getFirstType().getTypeName();

        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonE);

        testCollection.addNewTeam(testTeam);

        assertEquals(testCollection.viewTeam(testTeam), nameD + " (" + typeD + "), " + nameE + " (" + typeE + ")");
    }

    @Test
    public void testEmptyCollectionAlreadyEmpty() {

        testCollection.emptyCollection();

        assertEquals(testCollection.sizeCollection(), 0);
    }

    @Test
    public void testEmptyCollectionFewTeams() {

        PokemonTeam teamTwo = new PokemonTeam("second team");
        PokemonTeam teamThree = new PokemonTeam("third team");

        testCollection.addNewTeam(testTeam);
        testCollection.addNewTeam(teamTwo);
        testCollection.addNewTeam(teamThree);

        testCollection.emptyCollection();

        assertEquals(testCollection.sizeCollection(), 0);

    }

    @Test
    public void testGetTeamEmptyCollection() {

        try {
            testCollection.getTeam(0);
            fail("Expected TeamNotFoundException");
        } catch (TeamNotFoundException e) {

            try {
                testCollection.getTeam(2);
                fail("Expected TeamNotFoundException");
            } catch (TeamNotFoundException ee) {
                // passes
            }
        }
    }

    @Test
    public void testGetTeamNotEmptyCollection() {

        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonF);

        PokemonTeam teamTwo = new PokemonTeam("second");
        PokemonTeam teamThree = new PokemonTeam("three");

        testCollection.addNewTeam(testTeam);
        testCollection.addNewTeam(teamTwo);
        testCollection.addNewTeam(teamThree);

        try {
            PokemonTeam teamOne = testCollection.getTeam(0);
            PokemonTeam secondTeam = testCollection.getTeam(1);
            PokemonTeam thirdTeam = testCollection.getTeam(2);

            assertEquals(testTeam, teamOne);
            assertEquals(teamTwo, secondTeam);
            assertEquals(teamThree, thirdTeam);
        } catch (TeamNotFoundException e) {
            fail("Unexpected TeamNotFoundException");
        }
    }
}
