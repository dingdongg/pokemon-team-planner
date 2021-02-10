package model;

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
        testTeam = new PokemonTeam("Testing team");
        testPokemonA = new Pokemon("a pokemon");
        testPokemonB = new Pokemon("another pokemon");
        testPokemonC = new Pokemon("yet another pokemon");
        testPokemonD = new Pokemon("pikachu");
        testPokemonE = new Pokemon("magikarp");
        testPokemonF = new Pokemon("mewtwo");
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
    public void testGetListOfPokemon() {
        testTeam.addPokemon(testPokemonA);
        testTeam.addPokemon(testPokemonB);
        testTeam.addPokemon(testPokemonC);

        assertEquals(testTeam.getListOfPokemon(), "a pokemon, another pokemon, yet another pokemon");
    }

}
