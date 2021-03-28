package model;

import model.types.exceptions.AttackNoneException;
import model.types.exceptions.TypeNameException;
import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TypesTest {

    private Types normal;
    private Types ghost;
    private Types ground;
    private Types none;

    @BeforeEach
    public void setUp() {
        normal = Types.NORMAL;
        ghost = Types.GHOST;
        ground = Types.GROUND;
        none = Types.NONE;
        Types.initializeTypeConstants();

    }

    @Test
    public void testInitializeTypeConstants() {
        assertEquals(9, none.getTypesBtoG().size());
        assertEquals(10,none.getTypesGtoW().size());

        assertEquals(9, ground.getTypesBtoG().size());
        assertEquals(10, ground.getTypesGtoW().size());
    }

    @Test
    public void testInitializeTypeConstantsMultipleTimes() {
        Types.initializeTypeConstants();
        Types.initializeTypeConstants();
        Types.initializeTypeConstants();

        assertEquals(9, none.getTypesBtoG().size());
        assertEquals(10,none.getTypesGtoW().size());

        assertEquals(9, ground.getTypesBtoG().size());
        assertEquals(10, ground.getTypesGtoW().size());
    }

    @Test
    public void testTypesStrongAgainst() {
        List<String> list = normal.typesStrongAgainst();
        assertTrue(list.isEmpty());

        list = ghost.typesStrongAgainst();
        assertEquals(2, list.size());
        assertTrue(list.contains("GHOST"));
        assertTrue(list.contains("PSYCHIC"));

        list = ground.typesStrongAgainst();
        assertEquals(5, list.size());
        assertTrue(list.contains("FIRE"));
        assertTrue(list.contains("POISON"));
        assertTrue(list.contains("STEEL"));
        assertTrue(list.contains("ELECTRIC"));
        assertTrue(list.contains("ROCK"));
    }

    @Test
    public void testMakeTypeContainWeirdChars() {
        try {
            Types.makeType("DARK!!");
            fail("Expected TypeNameException");
        } catch (TypeNameException e) {
            // passes
        }
    }

    @Test
    public void testMakeTypeContainSpaces() {
        try {
            Types.makeType("I want dragon");
            fail("Expected TypeNameException");
        } catch (TypeNameException e) {
            // passes
        }
    }

    @Test
    public void testMakeTypeWorksWithLowerCase() {
        try {
            assertEquals(Types.FIRE, Types.makeType("fire"));
            assertEquals(Types.WATER, Types.makeType("waTEr"));
            assertEquals(Types.ELECTRIC, Types.makeType("eLECTrIc"));
            assertEquals(Types.GRASS, Types.makeType("GRASS"));
            assertEquals(Types.ROCK, Types.makeType("roCK"));
            assertEquals(Types.GROUND, Types.makeType("gROund"));
            assertEquals(Types.FLYING, Types.makeType("Flying"));
            assertEquals(Types.BUG, Types.makeType("buG"));
            assertEquals(Types.ICE, Types.makeType("ICE"));
            assertEquals(Types.STEEL, Types.makeType("STEEL"));
            assertEquals(Types.POISON, Types.makeType("POISON"));
            assertEquals(Types.FAIRY, Types.makeType("FAIRY"));
            assertEquals(Types.DRAGON, Types.makeType("DRAGON"));
            assertEquals(Types.PSYCHIC, Types.makeType("PSYCHIC"));
            assertEquals(Types.DARK, Types.makeType("DARK"));
            assertEquals(Types.GHOST, Types.makeType("GHOST"));
            assertEquals(Types.FIGHTING, Types.makeType("FIGHTING"));
            assertEquals(Types.NORMAL, Types.makeType("NORMAL"));
            assertEquals(Types.NONE, Types.makeType("NONE"));
        } catch (TypeNameException e) {
            fail("Unexpected TypeNameException");
        }
    }


    @Test
    public void testCannotAttackNoExceptionsThrown() {

        try {
            assertTrue(normal.cannotAttack(Types.GHOST));
            assertFalse(normal.cannotAttack(ground));

            assertTrue(Types.FIGHTING.cannotAttack(Types.GHOST));
            assertFalse(Types.FIGHTING.cannotAttack((Types.WATER)));

            assertTrue(ghost.cannotAttack(normal));
            assertFalse(ghost.cannotAttack(Types.POISON));

            assertTrue(Types.ELECTRIC.cannotAttack(ground));
            assertFalse(Types.ELECTRIC.cannotAttack(Types.GRASS));

            assertTrue(Types.POISON.cannotAttack(Types.STEEL));
            assertFalse(Types.POISON.cannotAttack(Types.ICE));

            assertTrue(ground.cannotAttack(Types.FLYING));
            assertFalse(ground.cannotAttack(Types.ROCK));

            assertTrue(Types.PSYCHIC.cannotAttack(Types.DARK));
            assertFalse(Types.PSYCHIC.cannotAttack(Types.BUG));

            assertTrue(Types.DRAGON.cannotAttack(Types.FAIRY));
            assertFalse(Types.DRAGON.cannotAttack(Types.GHOST));

            assertFalse(Types.FIRE.cannotAttack(Types.WATER));
            assertFalse(Types.BUG.cannotAttack(Types.ICE));
            assertFalse(Types.ROCK.cannotAttack(Types.GROUND));

        } catch (AttackNoneException e) {
            unexpectedError();
        }



    }

    @Test
    public void testCannotAttackThisIsNone() {
        try {
            none.cannotAttack(ground);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testCannotAttackArgumentIsNone() {
        try {
            ground.cannotAttack(none);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testStrongAgainstThisIsNone() {
        try {
            none.strongAgainst(ground);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testStrongAgainstArgumentIsNone() {
        try {
            normal.strongAgainst(none);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testStrongAgainstSelfEffective() {
        try {
            assertTrue(ghost.strongAgainst(ghost));
            assertTrue(Types.DRAGON.strongAgainst(Types.DRAGON));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testStrongAgainstTrue() {
        try {
            assertTrue(ground.strongAgainst(Types.FIRE));
            assertTrue(ground.strongAgainst(Types.STEEL));
            assertTrue(ground.strongAgainst(Types.ROCK));
            assertTrue(ground.strongAgainst(Types.ELECTRIC));
            assertTrue(ground.strongAgainst(Types.POISON));
            assertTrue(ghost.strongAgainst(Types.PSYCHIC));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testStrongAgainstFalse() {
        try {
            assertFalse(normal.strongAgainst(Types.FIGHTING));
            assertFalse(Types.FIRE.strongAgainst(Types.WATER));
            assertFalse(Types.WATER.strongAgainst(Types.ICE));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testDamageMultiplierAllNone() {
        try {
            none.damageMultiplier(none, none);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testDamageMultiplierFirstArgumentNone() {
        try {
            ground.damageMultiplier(none, ghost);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testDamageMultiplierSecondNoneImmune() {
        try {
            assertEquals(Types.IMMUNE_MULTIPLIER, ground.damageMultiplier(Types.FLYING, none));
            assertEquals(Types.IMMUNE_MULTIPLIER, normal.damageMultiplier(ghost, none));
            assertEquals(Types.IMMUNE_MULTIPLIER, Types.POISON.damageMultiplier(Types.STEEL, none));

        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testDamageMultiplierOneImmune() {
        try {
            assertEquals(Types.IMMUNE_MULTIPLIER, ground.damageMultiplier(Types.FLYING, Types.ROCK));
            assertEquals(Types.IMMUNE_MULTIPLIER, Types.DRAGON.damageMultiplier(Types.DRAGON, Types.FAIRY));
            assertEquals(Types.IMMUNE_MULTIPLIER, Types.POISON.damageMultiplier(Types.PSYCHIC, Types.STEEL));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testDamageMultiplierSecondNoneSuper() {
        try {
            assertEquals(Types.SUPER_EFFECTIVE_MULTIPLIER, Types.FIRE.damageMultiplier(Types.GRASS, none));
            assertEquals(Types.SUPER_EFFECTIVE_MULTIPLIER, Types.WATER.damageMultiplier(Types.FIRE, none));
            assertEquals(Types.SUPER_EFFECTIVE_MULTIPLIER, Types.FIGHTING.damageMultiplier(normal, none));

        } catch (AttackNoneException e) {
            unexpectedError();
        }

    }

    @Test
    public void testDamageMultiplierSecondNoneRegular() {
        try {
            assertEquals(Types.REGULAR_MULTIPLIER, ghost.damageMultiplier(ground, none));
            assertEquals(Types.REGULAR_MULTIPLIER, normal.damageMultiplier(Types.FIRE, none));
            assertEquals(Types.REGULAR_MULTIPLIER, Types.ELECTRIC.damageMultiplier(Types.ICE, none));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testDamageMultiplierSuperDuper() {
        try {
            assertEquals(Types.SUPER_DUPER_EFFECTIVE_MULTIPLIER, Types.GRASS.damageMultiplier(Types.WATER, ground));
            assertEquals(Types.SUPER_DUPER_EFFECTIVE_MULTIPLIER, Types.ELECTRIC.damageMultiplier(Types.FLYING, Types.WATER));
            assertEquals(Types.SUPER_DUPER_EFFECTIVE_MULTIPLIER, ghost.damageMultiplier(Types.PSYCHIC, ghost));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testDamageMultiplierSuper() {
        try {
            assertEquals(Types.SUPER_EFFECTIVE_MULTIPLIER, Types.GRASS.damageMultiplier(Types.WATER, Types.FIRE));
            assertEquals(Types.SUPER_EFFECTIVE_MULTIPLIER, Types.ELECTRIC.damageMultiplier(Types.ICE, Types.WATER));
            assertEquals(Types.SUPER_EFFECTIVE_MULTIPLIER, ghost.damageMultiplier(Types.PSYCHIC, Types.FAIRY));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testDamageMultiplierRegular() {
        try {
            assertEquals(Types.REGULAR_MULTIPLIER, Types.GRASS.damageMultiplier(Types.BUG, Types.ELECTRIC));
            assertEquals(Types.REGULAR_MULTIPLIER, Types.ELECTRIC.damageMultiplier(ghost, normal));
            assertEquals(Types.REGULAR_MULTIPLIER, ghost.damageMultiplier(Types.DARK, ground));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }

    @Test
    public void testThrowExceptionIfNoneInvolvedBothNone() {
        try {
            none.throwExceptionIfNoneInvolved(none);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testThrowExceptionIfNoneInvolvedArgumentNone() {
        try {
            ground.throwExceptionIfNoneInvolved(none);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testThrowExceptionIfNoneInvolvedThisNone() {
        try {
            none.throwExceptionIfNoneInvolved(ground);
            expectedError();
        } catch (AttackNoneException e) {
            // passes
        }
    }

    @Test
    public void testThrowExceptionIfNoneInvolvedNeitherNone() {
        try {
            assertFalse(ground.throwExceptionIfNoneInvolved(ghost));
        } catch (AttackNoneException e) {
            unexpectedError();
        }
    }



    private void unexpectedError() {
        fail("Unexpected AttackNoneException");
    }

    private void expectedError() {
        fail("Expected AttackNoneException");
    }



}
