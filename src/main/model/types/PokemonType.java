package model.types;

import model.types.exceptions.AttackNoneException;
import model.types.exceptions.TypeNameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// models the types available in official pokemon games.
// NOTE: type dynamics (e.g. strong against, weak against)
//       have NOT been implemented as part of P1
public class PokemonType {

//    private String typeName;
    private Types type = null;

    // EFFECTS: creates a new Type object having specified name
    public PokemonType(String typeName) {
        try {
            this.type = Types.makeType(typeName);
        } catch (TypeNameException e) {
            System.out.println("Invalid name input. Please try again.");
        }
    }

    public Types getType() {
        return this.type;
    }

    // getter
    public String getTypeName() {
        return this.type.toString();
    }
}
