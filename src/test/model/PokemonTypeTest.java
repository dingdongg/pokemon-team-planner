package model;

import model.types.PokemonType;
import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PokemonTypeTest {

    private PokemonType typeA;
    private PokemonType typeB;
    private PokemonType typeC;


    @BeforeEach
    public void setUp() {
        typeA = new PokemonType("FLYING");
        typeB = new PokemonType("NONE");
        typeC = new PokemonType("I want water");
    }

    @Test
    public void testConstructor() {
        assertNull(typeC.getType());
        assertEquals(Types.FLYING, typeA.getType());
    }

    @Test
    public void testGetTypeName() {
        assertEquals("FLYING", typeA.getTypeName());
        assertEquals("NONE", typeB.getTypeName());
    }
}
