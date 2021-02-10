package model;

import java.util.List;

// models a Pokemon creature
public class Pokemon {

    private String name;

    // MODIFIES: this
    // EFFECTS: constructs a new Pokemon object with given name
    public Pokemon(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: changes the name of a Pokemon object to newName
    public void changeName(String newName) {
        this.name = newName;
    }

    // getter
    public String getName() {
        return this.name;
    }


}
