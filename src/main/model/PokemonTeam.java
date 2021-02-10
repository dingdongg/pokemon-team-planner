package model;

import java.util.ArrayList;

// models a team of Pokemon
public class PokemonTeam {

    private String teamName;
    private ArrayList<Pokemon> pokemons;

    private static final int MAX_NUMBER_OF_POKEMON_PER_TEAM = 6;

    // MODIFIES: this
    // EFFECTS: constructs a new PokemonTeam object
    public PokemonTeam(String teamName) {
        this.teamName = teamName;
        pokemons = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add pokemon to the end of the team
    public void addPokemon(Pokemon pokemon) {
        if (this.pokemons.size() < MAX_NUMBER_OF_POKEMON_PER_TEAM) {
            this.pokemons.add(pokemon);
        }
    }

    // REQUIRES: pokemon must exist already in the team,
    //           and there must be at least two pokemon in team
    // MODIFIES: this
    // EFFECTS: removes selected pokemon from the team
    public void deletePokemon(Pokemon pokemon) {
        this.pokemons.remove(pokemon);
    }

    // MODIFIES: this
    // EFFECTS: changes the name of this pokemon team
    public void changeTeamName(String newTeamName) {
        this.teamName = newTeamName;
    }

    // EFFECTS: return true if pokemon is already in the team,
    //          and false otherwise
    public boolean contains(Pokemon pokemon) {
        return this.pokemons.contains(pokemon);
    }

    // getter
    public String getTeamName() {
        return this.teamName;
    }

    // EFFECTS: return a list of pokemon names in the team as a string
    //          separated by commas
    public String getListOfPokemon() {
        String result = "";

        for (int i = 0; i < this.pokemons.size(); i++) {
            if (i == this.pokemons.size() - 1) {
                result = result + this.pokemons.get(i).getName();
            } else {
                result = result + this.pokemons.get(i).getName() + ", ";
            }
        }

        return result;
    }
}
