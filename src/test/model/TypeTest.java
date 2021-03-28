package model;

import model.types.PokemonType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TypeTest {

    @Test
    public void testConstructor() {
        PokemonType testTypeC = new PokemonType("I want water");

        assertNull(testTypeC.getType());
    }
}
