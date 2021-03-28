package model.types;

import model.types.exceptions.TypeNameException;

// models the types available in official pokemon games.
// NOTE: type dynamics (e.g. strong against, weak against)
//       have NOT been implemented as part of P1
// EDIT: type dynamics have been implemented as part of P3.
//       this feature is now accessible via the GUI
public class PokemonType {

    private Types type = null;

    // EFFECTS: creates a new Type object having specified name
    public PokemonType(String typeName) {
        try {
            this.type = Types.makeType(typeName);
        } catch (TypeNameException e) {
            System.out.println("Invalid name input. Please try again.");
        }
    }

    // getter
    public Types getType() {
        return this.type;
    }

    // getter
    public String getTypeName() {
        return this.type.toString();
    }
}
