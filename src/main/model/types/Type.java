package model.types;

// models the types available in official pokemon games.
// NOTE: type dynamics (e.g. strong against, weak against)
//       have NOT been implemented as part of P1
public class Type {

    private String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }

    // getter
    public String getTypeName() {
        return this.typeName;
    }
}
