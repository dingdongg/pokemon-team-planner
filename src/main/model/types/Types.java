package model.types;

import model.exceptions.AttackNoneException;
import model.exceptions.TypeNameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// represents the 18 pokemon types in existence
public enum Types {

    FIRE(new ArrayList<>(Arrays.asList("GRASS", "ICE", "BUG", "STEEL"))),
    WATER(new ArrayList<>(Arrays.asList("FIRE", "GROUND", "ROCK"))),
    GRASS(new ArrayList<>(Arrays.asList("WATER", "GROUND", "ROCK"))),
    ELECTRIC(new ArrayList<>(Arrays.asList("FLYING", "WATER"))),
    FLYING(new ArrayList<>(Arrays.asList("GRASS", "BUG", "FIGHTING"))),
    ROCK(new ArrayList<>(Arrays.asList("FIRE", "FLYING", "ICE", "BUG"))),
    GROUND(new ArrayList<>(Arrays.asList("FIRE", "ELECTRIC", "POISON", "STEEL", "ROCK"))),
    ICE(new ArrayList<>(Arrays.asList("GRASS", "GROUND", "FLYING", "DRAGON"))),
    GHOST(new ArrayList<>(Arrays.asList("PSYCHIC", "GHOST"))),
    DARK(new ArrayList<>(Arrays.asList("PSYCHIC", "GHOST"))),
    PSYCHIC(new ArrayList<>(Arrays.asList("FIGHTING", "POISON"))),
    BUG(new ArrayList<>(Arrays.asList("GRASS", "PSYCHIC", "DARK"))),
    STEEL(new ArrayList<>(Arrays.asList("ICE", "ROCK", "FAIRY"))),
    POISON(new ArrayList<>(Arrays.asList("GRASS", "FAIRY"))),
    DRAGON(new ArrayList<>(Arrays.asList("DRAGON"))),
    FAIRY(new ArrayList<>(Arrays.asList("FIGHTING", "DRAGON", "DARK"))),
    FIGHTING(new ArrayList<>(Arrays.asList("NORMAL", "ICE", "ROCK", "DARK", "STEEL"))),
    NORMAL(new ArrayList<>()),
    NONE(new ArrayList<>());

    private final List<String> strongAgainst;
    public static final int SUPER_DUPER_EFFECTIVE_MULTIPLIER = 4;
    public static final int SUPER_EFFECTIVE_MULTIPLIER = 2;
    public static final int REGULAR_MULTIPLIER = 1;
    public static final int IMMUNE_MULTIPLIER = 0;
    private static final List<String> typesBtoG = new ArrayList<>();
    private static final List<String> typesGtoW = new ArrayList<>();

    // EFFECT: enum constructor; allows each Types value to be created
    Types(List<String> strongAgainst) {
        this.strongAgainst = strongAgainst;
    }

    // MODIFIES: this.typesBtoG, this.typesGtoW
    // EFFECTS : sorts and splits (in half) the different types in string format
    //           into one of the two fields alphabetically
    public static void initializeTypeConstants() {
        if (typesBtoG.isEmpty() && typesGtoW.isEmpty()) {
            typesBtoG.add("BUG");
            typesBtoG.add("DARK");
            typesBtoG.add("DRAGON");
            typesBtoG.add("ELECTRIC");
            typesBtoG.add("FAIRY");
            typesBtoG.add("FIGHTING");
            typesBtoG.add("FIRE");
            typesBtoG.add("FLYING");
            typesBtoG.add("GHOST");

            typesGtoW.add("GRASS");
            typesGtoW.add("GROUND");
            typesGtoW.add("ICE");
            typesGtoW.add("NONE");
            typesGtoW.add("NORMAL");
            typesGtoW.add("POISON");
            typesGtoW.add("PSYCHIC");
            typesGtoW.add("ROCK");
            typesGtoW.add("STEEL");
            typesGtoW.add("WATER");
        }
    }

    // EFFECTS : returns a list with names of all pokemon types from Bug to Ghost
    public List<String> getTypesBtoG() {
        return typesBtoG;
    }

    // EFFECTS : returns a list with names of all pokemon names from Grass to Water
    public List<String> getTypesGtoW() {
        return typesGtoW;
    }

    // EFFECTS : returns the names of types this type is strong against
    public List<String> typesStrongAgainst() {
        return this.strongAgainst;
    }

    // EFFECTS : parses input name into appropriate pokemon type. Users
    //           must input only the name and no other characters.
    //           Throws TypeNameException if the above is violated.
    public static Types makeType(String name) throws TypeNameException {

        String formattedName = name.toUpperCase();

        if (isIncorrectTypeName(formattedName)) {
            throw new TypeNameException();
        } else {
            return makeTypePartOne(formattedName);
        }
    }

    // EFFECTS : returns true if input name is not found in
    //           either of the two fields
    private static boolean isIncorrectTypeName(String name) {
        return !typesBtoG.contains(name) && !typesGtoW.contains(name);
    }

    // EFFECTS : returns the pokemon type corresponding to one of the
    //           names in list typesBtoG; else, name is passed into
    //           check for list typesGtoW
    private static Types makeTypePartOne(String name) {
        return valueOf(name.toUpperCase());
    }


    // EFFECTS : returns true if this type cannot attack the defendingType,
    //           and false if this type can attack the defendingType.
    //           Throws AttackNoneException if either this or defending type
    //           is of type NONE.
    public boolean cannotAttack(Types defendingType) throws AttackNoneException {

        throwExceptionIfNoneInvolved(defendingType);

        switch (this) {
            case NORMAL:
            case FIGHTING:
                return defendingType.equals(GHOST);
            case ELECTRIC:
                return defendingType.equals(GROUND);
            case POISON:
                return defendingType.equals(STEEL);
            case GROUND:
                return defendingType.equals(FLYING);
            case PSYCHIC:
                return defendingType.equals(DARK);
            case GHOST:
                return defendingType.equals(NORMAL);
            case DRAGON:
                return defendingType.equals(FAIRY);
            default:
                return false;
        }

    }

    // EFFECTS : returns true if this type can land strong attacks
    //           against the defending type, and false otherwise.
    //           Throws AttackNoneException if either this or defending type
    //           is of type NONE.
    public boolean strongAgainst(Types defendingType) throws AttackNoneException {
        String formattedName = defendingType.toString().toUpperCase();
        throwExceptionIfNoneInvolved(defendingType);
        return this.strongAgainst.contains(formattedName);
    }

    // EFFECTS : Throws AttackNoneException if either this or input type is
    //           of type NONE. Otherwise, return false.
    public boolean throwExceptionIfNoneInvolved(Types type) throws AttackNoneException {
        if (this.equals(NONE) || type.equals(NONE)) {
            throw new AttackNoneException();
        } else {
            return false;
        }
    }

    // EFFECTS : returns an integer representing the damage multiplier based on
    //           this type and the defending pokemon's primary and secondary types
    //           IMMUNE_MULTIPLIER if defending pokemon is immune to this type's attacks,
    //           REGULAR_MULTIPLIER if defending pokemon is neutral to this type's attacks,
    //           SUPER_EFFECTIVE_MULTIPLIER if defending pokemon is weak to this type's attacks,
    //           SUPER_DUPER_EFFECTIVE_MULTIPLIER if defending pokemon is extremely weak to this type's attacks.
    //           Throws AttackNoneException if either this or primary type is
    //           of type NONE.
    public int damageMultiplier(Types primary, Types secondary) throws AttackNoneException {

        throwExceptionIfNoneInvolved(primary);

        if (secondary.equals(NONE)) {
            if (this.strongAgainst(primary)) {
                return SUPER_EFFECTIVE_MULTIPLIER;
            } else if (this.cannotAttack(primary)) {
                return IMMUNE_MULTIPLIER;
            } else {
                return REGULAR_MULTIPLIER;
            }
        } else if (this.cannotAttack(primary) || this.cannotAttack(secondary)) {
            return IMMUNE_MULTIPLIER;
        } else {
            if (this.strongAgainst(primary) && this.strongAgainst(secondary)) {
                return SUPER_DUPER_EFFECTIVE_MULTIPLIER;
            } else if (this.strongAgainst(primary) ^ this.strongAgainst(secondary)) {
                return SUPER_EFFECTIVE_MULTIPLIER;
            } else {
                return REGULAR_MULTIPLIER;
            }
        }
    }




}
