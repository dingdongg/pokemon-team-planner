package model;

import model.types.Type;

// models a Pokemon creature
public class Pokemon {

    private String name;
    private Type firstType;
    private Type secondType;

    // MODIFIES: this
    // EFFECTS: constructs a new Pokemon object with given name
    public Pokemon(String name) {
        this.name = name;
        this.firstType = new Type("NONE");
        this.secondType = new Type("NONE");
    }

    // MODIFIES: this
    // EFFECTS: assigns pokemon to the specified type
    //          as its primary type
    public void setFirstType(Type type) {
        this.firstType = type;
    }

    // MODIFIES: this
    // EFFECTS: assigns pokemon to the specified type
    //          as its secondary type
    public void setSecondType(Type type) {
        this.secondType = type;
    }

    // MODIFIES: this
    // EFFECTS: changes the name of a Pokemon object to newName
    public void changeName(String newName) {
        this.name = newName;
    }

    // getters
    public String getName() {
        return this.name;
    }

    public Type getFirstType() {
        return this.firstType;
    }

    public Type getSecondType() {
        return this.secondType;
    }


}
