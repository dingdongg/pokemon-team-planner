package persistence;

import model.Pokemon;
import model.PokemonTeam;
import model.PokemonTeamCollection;
import model.types.Type;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// code in this class was referenced from JsonSerializationDemo in Phase 2, Task 4 of the personal project
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            PokemonTeamCollection collection = new PokemonTeamCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // passes
        }
    }

    @Test
    public void testWriterEmptyCollection() {
        try {
            PokemonTeamCollection collection = new PokemonTeamCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCollection.json");
            collection = reader.read();
            assertEquals(0, collection.sizeCollection());
        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }

    @Test
    public void testWriterGeneralCollection() {
        try {

            PokemonTeamCollection collection = new PokemonTeamCollection();

            PokemonTeam testTeamA = new PokemonTeam("an empty team");

            PokemonTeam testTeamB = new PokemonTeam("team with 1 pokemon :)");
            Pokemon magikarp = new Pokemon("magikarp");
            magikarp.setFirstType(new Type("water"));
            testTeamB.addPokemon(magikarp);

            PokemonTeam testTeamC = new PokemonTeam("team with 6 pokemons");
            Pokemon bulbasaur = new Pokemon("bulbasaur", "grass", "poison");
            Pokemon charmander = new Pokemon("charmander", "fire", "NONE");
            Pokemon squirtle = new Pokemon("squirtle", "water", "NONE");
            Pokemon diglett = new Pokemon("diglett", "ground", "NONE");
            Pokemon articuno = new Pokemon("articuno", "ice", "flying");
            testTeamC.addPokemon(magikarp);
            testTeamC.addPokemon(bulbasaur);
            testTeamC.addPokemon(charmander);
            testTeamC.addPokemon(squirtle);
            testTeamC.addPokemon(diglett);
            testTeamC.addPokemon(articuno);

            collection.addNewTeam(testTeamA);
            collection.addNewTeam(testTeamB);
            collection.addNewTeam(testTeamC);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCollection.json");
            writer.open();
            writer.write(collection);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCollection.json");
            collection = reader.read();

            assertEquals(3, collection.sizeCollection());
            ArrayList<PokemonTeam> teams = new ArrayList<>();

            for (int i = 0; i < collection.sizeCollection(); i++) {
                teams.add(collection.getTeam(i));
            }

            assertEquals("an empty team", teams.get(0).getTeamName());
            assertEquals(0, teams.get(0).teamSize());

            assertEquals("team with 1 pokemon :)", teams.get(1).getTeamName());
            assertEquals(1, teams.get(1).teamSize());
            checkPokemon("magikarp", "water", "NONE", teams.get(1).getPokemon(0));

            assertEquals("team with 6 pokemons", teams.get(2).getTeamName());
            assertTrue(teams.get(2).isFull());
            checkPokemon("magikarp", "water", "NONE", teams.get(2).getPokemon(0));
            checkPokemon("bulbasaur", "grass", "poison", teams.get(2).getPokemon(1));
            checkPokemon("charmander", "fire", "NONE", teams.get(2).getPokemon(2));
            checkPokemon("squirtle", "water", "NONE", teams.get(2).getPokemon(3));
            checkPokemon("diglett", "ground", "NONE", teams.get(2).getPokemon(4));
            checkPokemon("articuno", "ice", "flying", teams.get(2).getPokemon(5));

        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }
}
