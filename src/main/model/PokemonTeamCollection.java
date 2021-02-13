package model;

import java.util.ArrayList;

public class PokemonTeamCollection {

    private ArrayList<PokemonTeam> teamCollection;

    // MODIFIES: this
    // EFFECTS: constructs a new PokemonTeamCollection object
    public PokemonTeamCollection() {

        teamCollection = new ArrayList<>();

    }

    // MODIFIES: this
    // EFFECTS: appends pokemonTeam to the collection of teams
    public void addNewTeam(PokemonTeam pokemonTeam) {
        this.teamCollection.add(pokemonTeam);
    }

    // REQUIRES: pokemonTeam must already exist in collection
    // MODIFIES: pokemonTeam
    // EFFECTS: activates a team to make it eligible for editing
    public void editTeam(PokemonTeam pokemonTeam) {
        pokemonTeam.changeEditingStatus();
    }

    // REQUIRES: pokemonTeam must already exist in collection
    // MODIFIES: this
    // EFFECTS: removes a pokemon team from the collection
    public void deleteTeam(PokemonTeam pokemonTeam) {
        this.teamCollection.remove(pokemonTeam);
    }

    // REQUIRES: pokemonTeam must already exist in collection
    // EFFECTS: shows the contents of the selected pokemon team
    public String viewTeam(PokemonTeam pokemonTeam) {
        return pokemonTeam.getTeamInfo();
    }

    // MODIFIES: this
    // EFFECTS: deletes all teams previously stored
    public void emptyCollection() {
        this.teamCollection = new ArrayList<>();
    }

    // EFFECTS: return true if specified PokemonTeam
    //          is part of the team collection
    public boolean contains(PokemonTeam team) {
        return this.teamCollection.contains(team);
    }

    // EFFECTS: returns the number of teams in collection
    public int sizeCollection() {
        return this.teamCollection.size();
    }

    // REQUIRES: teamCollection is not empty
    // EFFECTS: returns the pokemon team stored at specified index
    public PokemonTeam getTeam(int i) {
        return this.teamCollection.get(i);
    }
}
