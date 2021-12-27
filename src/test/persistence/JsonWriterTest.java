package persistence;

import model.Pokemon;
import model.PokemonTeam;
import model.PokemonTeamCollection;
import model.exceptions.TeamNotFoundException;
import model.types.PokemonType;
import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @BeforeEach
    public void setUp() {
        Types.initializeTypeConstants();
    }

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
            magikarp.setFirstType(new PokemonType("water"));
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

            try {
                for (int i = 0; i < collection.sizeCollection(); i++) {
                    teams.add(collection.getTeam(i));
                }
            } catch (TeamNotFoundException e) {
                fail("Unexpected TeamNotFoundException");
            }
            assertEquals("an empty team", teams.get(0).getTeamName());
            assertEquals(0, teams.get(0).teamSize());

            assertEquals("team with 1 pokemon :)", teams.get(1).getTeamName());
            assertEquals(1, teams.get(1).teamSize());
            checkPokemon("magikarp", "WATER", "NONE", teams.get(1).getPokemon(0));

            assertEquals("team with 6 pokemons", teams.get(2).getTeamName());
            assertTrue(teams.get(2).isFull());
            checkPokemon("magikarp", "WATER", "NONE", teams.get(2).getPokemon(0));
            checkPokemon("bulbasaur", "GRASS", "POISON", teams.get(2).getPokemon(1));
            checkPokemon("charmander", "FIRE", "NONE", teams.get(2).getPokemon(2));
            checkPokemon("squirtle", "WATER", "NONE", teams.get(2).getPokemon(3));
            checkPokemon("diglett", "GROUND", "NONE", teams.get(2).getPokemon(4));
            checkPokemon("articuno", "ICE", "FLYING", teams.get(2).getPokemon(5));

        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }
}
