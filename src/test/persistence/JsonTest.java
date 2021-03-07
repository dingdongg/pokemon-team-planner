package persistence;

import model.Pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPokemon(String name, String firstType, String secondType, Pokemon pokemon) {
        assertEquals(name, pokemon.getName());
        assertEquals(firstType, pokemon.getFirstType().getTypeName());
        assertEquals(secondType, pokemon.getSecondType().getTypeName());
    }
}
