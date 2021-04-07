package model;

import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

//    @Test
//    public void testInitializeTypeConstantsBugToGhostFilled() {
//
////        Types.loadTypesBtoG();
//
//        assertFalse(ground.getTypesBtoG().isEmpty());
//        assertFalse(ghost.getTypesBtoG().isEmpty());
//        assertFalse(fire.getTypesBtoG().isEmpty());
//        assertFalse(none.getTypesBtoG().isEmpty());
//        assertTrue(ground.getTypesGtoW().isEmpty());
//        assertTrue(ghost.getTypesGtoW().isEmpty());
//        assertTrue(fire.getTypesGtoW().isEmpty());
//        assertTrue(none.getTypesGtoW().isEmpty());
//
//        Types.initializeTypeConstants();
//
//        assertEquals(9, fire.getTypesBtoG().size());
//        assertEquals(10, fire.getTypesGtoW().size());
//    }
}
