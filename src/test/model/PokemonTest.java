package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    private Pokemon testPokemon;

    @BeforeEach
    public void setUp() {
        testPokemon = new Pokemon("Test");
    }

    @Test
    public void testPokemon() {
        Pokemon anotherPokemon = new Pokemon("Another pokemon");
        assertEquals(testPokemon.getName(), "Test");
        assertEquals(anotherPokemon.getName(), "Another pokemon");
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