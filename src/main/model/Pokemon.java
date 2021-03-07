package model;

import model.types.Type;
import org.json.JSONObject;
import persistence.Writable;

// models a Pokemon creature
public class Pokemon implements Writable {

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
    // EFFECTS : constructs a new Pokemon object with given name and types
    public Pokemon(String name, String firstType, String secondType) {
        this.name = name;
        this.firstType = new Type(firstType);
        this.secondType = new Type(secondType);
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


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pokemonName", this.name);
        json.put("firstType", getFirstType().getTypeName());
        json.put("secondType", getSecondType().getTypeName());

        return json;
    }
}
