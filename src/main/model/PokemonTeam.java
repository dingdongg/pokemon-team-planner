package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// models a team of Pokemon
public class PokemonTeam implements Writable {

    private String teamName;
    private ArrayList<Pokemon> pokemonsInTeam;
    private boolean isBeingEdited;

    public static final int MAX_NUMBER_OF_POKEMON_PER_TEAM = 6;

    // MODIFIES: this
    // EFFECTS: constructs a new PokemonTeam object
    public PokemonTeam(String teamName) {
        this.teamName = teamName;
        pokemonsInTeam = new ArrayList<>();
        isBeingEdited = false;
    }

    // MODIFIES: this
    // EFFECTS: add pokemon to the end of the team
    public void addPokemon(Pokemon pokemon) {
        if (this.pokemonsInTeam.size() < MAX_NUMBER_OF_POKEMON_PER_TEAM) {
            this.pokemonsInTeam.add(pokemon);
        }
    }

    // REQUIRES: pokemon must exist already in the team,
    //           and there must be at least two pokemon in team
    // MODIFIES: this
    // EFFECTS: removes selected pokemon from the team
    public void deletePokemon(Pokemon pokemon) {
        this.pokemonsInTeam.remove(pokemon);
    }

    // MODIFIES: this
    // EFFECTS: changes the name of this pokemon team
    public void changeTeamName(String newTeamName) {
        this.teamName = newTeamName;
    }

    // MODIFIES: this
    // EFFECTS: switches the editing status of this team
    public void changeEditingStatus() {
        this.isBeingEdited = !this.isBeingEdited;
    }

    // EFFECTS: return true if pokemon is already in the team,
    //          and false otherwise
    public boolean contains(Pokemon pokemon) {
        return this.pokemonsInTeam.contains(pokemon);
    }

    // REQUIRES: retrieving pokemon must already be in the team
    // EFFECTS: returns a string with selected pokemon's name and type(s)
    public String getPokemonInfo(Pokemon pokemon) {

        String firstType = pokemon.getFirstType().getTypeName();
        String secondType = pokemon.getSecondType().getTypeName();
        String name = pokemon.getName();

        if (secondType.equals("NONE")) {
            return name + " (" + firstType + ")";
        } else {
            return name + " (" + firstType + " " + secondType + ")";
        }
    }

    // EFFECTS: returns information of each pokemon stored in team.
    //          If empty, return string that says "empty team"
    public String getTeamInfo() {

        if (this.pokemonsInTeam.isEmpty()) {
            return "empty team";
        }

        String result = "";

        for (int i = 0; i < this.pokemonsInTeam.size(); i++) {

            Pokemon p = this.pokemonsInTeam.get(i);

            if (i == this.pokemonsInTeam.size() - 1) {
                result = result + getPokemonInfo(p);
            } else {
                result = result + getPokemonInfo(p) + ", ";
            }
        }
        return result;
    }

    // EFFECTS: finds and returns pokemon with given name in this pokemon team,
    //          otherwise return null
    public Pokemon findPokemon(String name) {
        for (int i = 0; i < teamSize(); i++) {
            if (name.equals(getPokemon(i).getName())) {
                return getPokemon(i);
            }
        }
        return null;
    }

    // EFFECTS: returns the number of pokemons in the team
    public int teamSize() {
        return this.pokemonsInTeam.size();
    }

    // EFFECTS: return true if team has reached
    //          maximum capacity of pokemons, and false otherwise
    public boolean isFull() {
        return teamSize() == MAX_NUMBER_OF_POKEMON_PER_TEAM;
    }

    // EFFECTS: return true if team is empty, false otherwise
    public boolean isEmpty() {
        return teamSize() == 0;
    }

    // getters

    public String getTeamName() {
        return this.teamName;
    }

    public boolean getEditStatus() {
        return this.isBeingEdited;
    }

    // REQUIRES: pokemon team must not be empty
    public Pokemon getPokemon(int index) {
        return this.pokemonsInTeam.get(index);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teamName", this.teamName);
        json.put("pokemonsInTeam", pokemonsToJson());

        return json;
    }

    // EFFECTS: returns pokemons in this team as JSON array
    private JSONArray pokemonsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pokemon p : this.pokemonsInTeam) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
