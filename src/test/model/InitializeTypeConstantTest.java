package model;

import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitializeTypeConstantTest {

    private Types ground;
    private Types fire;
    private Types ghost;
    private Types none;

    @BeforeEach
    public void setUp() {
        ground = Types.GROUND;
        fire = Types.FIRE;
        ghost = Types.GHOST;
        none = Types.NONE;
    }

    @Test
    public void testInitializeTypeConstantsBeforeCall() {

        assertTrue(ground.getTypesBtoG().isEmpty());
        assertTrue(ghost.getTypesBtoG().isEmpty());
        assertTrue(fire.getTypesBtoG().isEmpty());
        assertTrue(none.getTypesBtoG().isEmpty());

        Types.initializeTypeConstants();

        assertEquals(9, fire.getTypesBtoG().size());
        assertEquals(10, fire.getTypesGtoW().size());
    }
}
