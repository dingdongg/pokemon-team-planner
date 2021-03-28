package model;

import model.types.PokemonType;
import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonTeamTest {

    private PokemonTeam testTeam;
    private Pokemon testPokemonA;
    private Pokemon testPokemonB;
    private Pokemon testPokemonC;
    private Pokemon testPokemonD;
    private Pokemon testPokemonE;
    private Pokemon testPokemonF;


    @BeforeEach
    public void setUp() {
        Types.initializeTypeConstants();
        testTeam = new PokemonTeam("Testing team");
        testPokemonA = new Pokemon("a pokemon");
        testPokemonB = new Pokemon("another pokemon");
        testPokemonC = new Pokemon("yet another pokemon");
        testPokemonD = new Pokemon("pikachu");
        testPokemonE = new Pokemon("gyarados");
        testPokemonF = new Pokemon("mewtwo");

        PokemonType electric = new PokemonType("ELECTRIC");
        PokemonType water = new PokemonType("WATER");
        PokemonType flying = new PokemonType("FLYING");
        PokemonType psychic = new PokemonType("PSYCHIC");

        testPokemonD.setFirstType(electric);
        testPokemonE.setFirstType(water);
        testPokemonE.setSecondType(flying);
        testPokemonF.setFirstType(psychic);
    }

    @Test
    public void testPokemonTeam() {
        PokemonTeam newTeam = new PokemonTeam("Another team");
        assertEquals(newTeam.getTeamName(), "Another team");
        assertEquals(testTeam.getTeamName(), "Testing team");
    }

    @Test
    public void testAddPokemon() {
        testTeam.addPokemon(testPokemonA);
        assertTrue(testTeam.contains(testPokemonA));
    }

    @Test
    public void testAddPokemonMultipleAdds() {

        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);
        testTeam.addPokemon(testPokemonC);

        assertTrue(testTeam.contains(testPokemonA));
        assertTrue(testTeam.contains(testPokemonB));
        assertTrue(testTeam.contains(testPokemonC));

    }

    @Test
    public void testAddPokemonFullAdd() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);
        testTeam.addPokemon(testPokemonC);
        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonE);
        testTeam.addPokemon(testPokemonF);

        assertTrue(testTeam.contains(testPokemonA));
        assertTrue(testTeam.contains(testPokemonB));
        assertTrue(testTeam.contains(testPokemonC));
        assertTrue(testTeam.contains(testPokemonD));
        assertTrue(testTeam.contains(testPokemonE));
        assertTrue(testTeam.contains(testPokemonF));
    }

    @Test
    public void addPokemonMoreThanCapacity() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);
        testTeam.addPokemon(testPokemonC);
        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonE);
        testTeam.addPokemon(testPokemonF);
        Pokemon seventh = new Pokemon("I shouldn't be in the team");
        testTeam.addPokemon(seventh);

        assertTrue(testTeam.contains(testPokemonA));
        assertTrue(testTeam.contains(testPokemonB));
        assertTrue(testTeam.contains(testPokemonC));
        assertTrue(testTeam.contains(testPokemonD));
        assertTrue(testTeam.contains(testPokemonE));
        assertTrue(testTeam.contains(testPokemonF));
        assertFalse(testTeam.contains(seventh));
    }

    @Test
    public void testDeletePokemonTwoLeftInTeam() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);

        testTeam.deletePokemon(testPokemonA);

        assertFalse(testTeam.contains(testPokemonA));
    }

    @Test
    public void testDeletePokemonRemoveMoreThanOne() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);
        testTeam.addPokemon(testPokemonC);
        testTeam.addPokemon(testPokemonD);

        testTeam.deletePokemon(testPokemonC);
        testTeam.deletePokemon(testPokemonB);

        assertFalse(testTeam.contains(testPokemonC));
        assertFalse(testTeam.contains(testPokemonB));
    }

    @Test
    public void testChangeTeamName() {
        testTeam.changeTeamName("new name");

        assertEquals(testTeam.getTeamName(), "new name");
    }

    @Test
    public void testChangeTeamNameEmptyString() {
        testTeam.changeTeamName("");

        assertEquals(testTeam.getTeamName(), "");
    }

    @Test
    public void testChangeEditingStatus() {

        testTeam.changeEditingStatus();

        assertTrue(testTeam.getEditStatus());
    }

    @Test
    public void testChangeEditingStatusMultipleTimes() {

        testTeam.changeEditingStatus();
        testTeam.changeEditingStatus();
        testTeam.changeEditingStatus();
        testTeam.changeEditingStatus();

        assertFalse(testTeam.getEditStatus());
    }

    @Test
    public void testGetPokemonInfoOneType() {
        testTeam.addPokemon(testPokemonD);

        String name = testPokemonD.getName();
        String type = testPokemonD.getFirstType().getTypeName();

        assertEquals(testTeam.getPokemonInfo(testPokemonD), name + " (" + type + ")");
    }

    @Test
    public void testGetPokemonInfoTwoTypes() {
        testTeam.addPokemon(testPokemonE);

        String name = testPokemonE.getName();
        String typeOne = testPokemonE.getFirstType().getTypeName();
        String typeTwo = testPokemonE.getSecondType().getTypeName();

        assertEquals(testTeam.getPokemonInfo(testPokemonE), name + " (" + typeOne + " " + typeTwo + ")");
    }

    @Test
    public void testGetTeamInfoEmpty() {

        assertEquals(testTeam.getTeamInfo(), "empty team");
    }

    @Test
    public void testGetTeamInfoOnePokemon() {

        PokemonType grass = new PokemonType("GRASS");
        testPokemonA.setFirstType(grass);
        testTeam.addPokemon(testPokemonA);

        String name = testPokemonA.getName();
        String type = testPokemonA.getFirstType().getTypeName();

        assertEquals(testTeam.getTeamInfo(), name + " (" + type + ")");
    }

    @Test
    public void testGetTeamInfoThreePokemon() {

        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonE);
        testTeam.addPokemon(testPokemonF);

        String nameD = testPokemonD.getName();
        String nameE = testPokemonE.getName();
        String nameF = testPokemonF.getName();

        String typeD = testPokemonD.getFirstType().getTypeName();
        String typeOneE = testPokemonE.getFirstType().getTypeName();
        String typeTwoE = testPokemonE.getSecondType().getTypeName();
        String typeF = testPokemonF.getFirstType().getTypeName();

        assertEquals(testTeam.getTeamInfo(), nameD + " (" + typeD + "), " + nameE + " (" + typeOneE + " " + typeTwoE + "), " + nameF + " (" + typeF + ")");
    }

    @Test
    public void testTeamSizeEmpty() {

        assertEquals(testTeam.teamSize(), 0);
    }

    @Test
    public void testTeamSizeMultiple() {

        testTeam.addPokemon(testPokemonC);
        testTeam.addPokemon(testPokemonF);
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);

        assertEquals(testTeam.teamSize(), 4);
    }

    @Test
    public void testGetPokemonOnePokemon() {
        testTeam.addPokemon(testPokemonB);

        assertEquals(testTeam.getPokemon(0), testPokemonB);
    }

    @Test
    public void testGetPokemonMultiplePokemon() {
        testTeam.addPokemon(testPokemonF);
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonD);

        assertEquals(testTeam.getPokemon(0), testPokemonF);
        assertEquals(testTeam.getPokemon(1), testPokemonA);
        assertEquals(testTeam.getPokemon(2), testPokemonD);
    }

    @Test
    public void testFindPokemonOnePokemonFound() {
        testTeam.addPokemon(testPokemonB);

        assertEquals(testTeam.findPokemon("another pokemon"), testPokemonB);
    }

    @Test
    public void testFindPokemonOnePokemonNotFound() {
        testTeam.addPokemon(testPokemonB);

        assertNull(testTeam.findPokemon("pikachu"));
    }

    @Test
    public void testFindPokemonMultiplePokemonFound() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonF);

        assertEquals(testTeam.findPokemon("mewtwo"), testPokemonF);
        assertEquals(testTeam.findPokemon("a pokemon"), testPokemonA);
        assertEquals(testTeam.findPokemon("pikachu"), testPokemonD);
    }

    @Test
    public void testFindPokemonMultiplePokemonNotFound() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonF);

        assertNull(testTeam.findPokemon("yet another pokemon"));
    }

    @Test
    public void testIsFullEmpty() {
        assertFalse(testTeam.isFull());
    }

    @Test
    public void testIsFullOne() {
        testTeam.addPokemon(testPokemonA);

        assertFalse(testTeam.isFull());
    }

    @Test
    public void testIsFullThree() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonC);
        testTeam.addPokemon(testPokemonD);

        assertFalse(testTeam.isFull());
    }

    @Test
    public void testIsFullFull() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);
        testTeam.addPokemon(testPokemonC);
        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonE);
        testTeam.addPokemon(testPokemonF);

        assertTrue(testTeam.isFull());
    }

    @Test
    public void testIsEmptyEmpty() {
        assertTrue(testTeam.isEmpty());
    }

    @Test
    public void testIsEmptyOne() {
        testTeam.addPokemon(testPokemonA);
        assertFalse(testTeam.isEmpty());
    }

    @Test
    public void testIsEmptyFull() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);
        testTeam.addPokemon(testPokemonC);
        testTeam.addPokemon(testPokemonD);
        testTeam.addPokemon(testPokemonE);
        testTeam.addPokemon(testPokemonF);

        assertFalse(testTeam.isEmpty());

    }

}
