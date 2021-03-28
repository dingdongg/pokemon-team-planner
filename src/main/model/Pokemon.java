package model;

import model.types.PokemonType;
import org.json.JSONObject;
import persistence.Writable;

// models a Pokemon creature
public class Pokemon implements Writable {

    private String name;
    private PokemonType firstType;
    private PokemonType secondType;

    // MODIFIES: this
    // EFFECTS: constructs a new Pokemon object with given name
    public Pokemon(String name) {
        this.name = name;
        this.firstType = new PokemonType("NONE");
        this.secondType = new PokemonType("NONE");
    }

    // MODIFIES: this
    // EFFECTS : constructs a new Pokemon object with given name and types
    public Pokemon(String name, String firstType, String secondType) {
        this.name = name;
        this.firstType = new PokemonType(firstType);
        this.secondType = new PokemonType(secondType);
    }

    // MODIFIES: this
    // EFFECTS: assigns pokemon to the specified type
    //          as its primary type
    public void setFirstType(PokemonType type) {
        this.firstType = type;
    }

    // MODIFIES: this
    // EFFECTS: assigns pokemon to the specified type
    //          as its secondary type
    public void setSecondType(PokemonType type) {
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

    public PokemonType getFirstType() {
        return this.firstType;
    }

    public PokemonType getSecondType() {
        return this.secondType;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pokemonName", this.name);
        json.put("firstType", getFirstType().getTypeName());
        json.put("secondType", getSecondType().getTypeName());

        return json;
    }
}
