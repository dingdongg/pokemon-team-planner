package model;

import model.types.PokemonType;
import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    private Pokemon testPokemon;
    private PokemonType testTypeA;
    private PokemonType testTypeB;
    private PokemonType testTypeC;

    @BeforeEach
    public void setUp() {
        Types.initializeTypeConstants();
        testPokemon = new Pokemon("Test");
        testTypeA = new PokemonType("FIRE");
        testTypeB = new PokemonType("STEEL");
        testTypeC = new PokemonType("ICE");
    }

    @Test
    public void testPokemon() {
        Pokemon anotherPokemon = new Pokemon("Another pokemon");

        assertEquals(testPokemon.getName(), "Test");
        assertEquals(anotherPokemon.getName(), "Another pokemon");

        assertEquals(testPokemon.getFirstType().getTypeName(), "NONE");
        assertEquals(anotherPokemon.getSecondType().getTypeName(), "NONE");
        assertEquals(testPokemon.getFirstType().getTypeName(), "NONE");
        assertEquals(anotherPokemon.getSecondType().getTypeName(), "NONE");
    }

    @Test
    public void testPokemonSecondConstructor() {
        Pokemon pokemon = new Pokemon("pikachu", "electric", "NONE");

        assertEquals("pikachu", pokemon.getName());
        assertEquals("ELECTRIC", pokemon.getFirstType().getTypeName());
        assertEquals("NONE", pokemon.getSecondType().getTypeName());
    }

    @Test
    public void testSetFirstType() {
        testPokemon.setFirstType(testTypeA);

        assertEquals(testPokemon.getFirstType().getTypeName(), "FIRE");
    }

    @Test
    public void testSetFirstTypeMultipleTimes() {
        testPokemon.setFirstType(testTypeA);
        testPokemon.setFirstType(testTypeB);
        testPokemon.setFirstType(testTypeC);

        assertEquals(testPokemon.getFirstType().getTypeName(), "ICE");
    }

    @Test
    public void testSetSecondType() {
        testPokemon.setSecondType(testTypeB);

        assertEquals(testPokemon.getSecondType().getTypeName(), "STEEL");
    }

    @Test
    public void testSetSecondTypeMultipleTimes() {
        testPokemon.setSecondType(testTypeB);
        testPokemon.setSecondType(testTypeC);
        testPokemon.setSecondType(testTypeA);

        assertEquals(testPokemon.getSecondType().getTypeName(), "FIRE");
    }

    @Test
    public void testChangeName() {
        testPokemon.changeName("Hello");
        assertEquals(testPokemon.getName(), "Hello");
    }

    @Test
    public void testChangeNameEmptyString() {
        testPokemon.changeName("");
        assertEquals(testPokemon.getName(), "");
    }
}