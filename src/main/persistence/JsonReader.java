package persistence;


import model.Pokemon;
import model.PokemonTeam;
import model.PokemonTeamCollection;
import model.types.Type;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads PokemonTeamCollection from JSON data stored in file
// code in this class was referenced from JsonSerializationDemo in Phase 2, Task 4 of the personal project
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads PokemonTeamCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PokemonTeamCollection read() throws IOException {
        String jsonData = readFile(this.source);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parsePokemonTeamCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses PokemonTeamCollection from JSON object and returns it
    private PokemonTeamCollection parsePokemonTeamCollection(JSONObject jsonObject) {
        PokemonTeamCollection collection = new PokemonTeamCollection();
        addTeams(collection, jsonObject);

        return collection;
    }

    // MODIFIES: collection
    // EFFECTS: parses pokemon teams from JSON object and adds them to collection
    private void addTeams(PokemonTeamCollection collection, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");

        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(collection, nextTeam);
        }
    }

    // MODIFIES: collection
    // EFFECTS: parses a pokemon team from JSON object and adds them to collection
    private void addTeam(PokemonTeamCollection collection, JSONObject jsonObject) {
        String teamName = jsonObject.getString("teamName");
        PokemonTeam team = new PokemonTeam(teamName);

        addPokemons(team, jsonObject);
        collection.addNewTeam(team);
    }

    // MODIFIES: team
    // EFFECTS: parses pokemons from JSON object and adds them to team
    private void addPokemons(PokemonTeam team, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pokemonsInTeam");

        for (Object json : jsonArray) {
            JSONObject nextPokemon = (JSONObject) json;
            addPokemon(team, nextPokemon);
        }
    }

    // MODIFIES: team
    // EFFECTS: parses a pokemon, set its types, from JSON object and adds it to team
    private void addPokemon(PokemonTeam team, JSONObject jsonObject) {
        String pokemonName = jsonObject.getString("pokemonName");
        Type firstType = new Type(jsonObject.getString("firstType"));
        Type secondType = new Type(jsonObject.getString("secondType"));

        Pokemon pokemon = new Pokemon(pokemonName);
        pokemon.setFirstType(firstType);
        pokemon.setSecondType(secondType);

        team.addPokemon(pokemon);
    }
}
