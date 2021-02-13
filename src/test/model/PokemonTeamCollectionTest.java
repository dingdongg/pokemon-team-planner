package model;

import model.types.Type;
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

        Type dragon = new Type("DRAGON");
        testPokemonA.setFirstType(dragon);
        String name = testPokemonA.getName();
        String type = testPokemonA.getFirstType().getTypeName();

        testTeam.addPokemon(testPokemonA);
        testCollection.addNewTeam(testTeam);

        assertEquals(testCollection.viewTeam(testTeam), name + " (" + type + ")");
    }

    @Test
    public void testViewTeamMultiplePokemon() {

        Type ground = new Type("GROUND");
        Type rock = new Type("ROCK");

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
    public void testGetTeam() {

        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonF);


        PokemonTeam teamTwo = new PokemonTeam("second");
        PokemonTeam teamThree = new PokemonTeam("three");

        testCollection.addNewTeam(testTeam);
        testCollection.addNewTeam(teamTwo);
        testCollection.addNewTeam(teamThree);

        assertEquals(testTeam, testCollection.getTeam(0));
        assertEquals(teamTwo, testCollection.getTeam(1));
        assertEquals(teamThree, testCollection.getTeam(2));
    }


}
