package persistence;

import model.PokemonTeam;
import model.PokemonTeamCollection;
import model.exceptions.TeamNotFoundException;
import model.types.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @BeforeEach
    public void setUp() {
        Types.initializeTypeConstants();
    }

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PokemonTeamCollection collection = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // passes
        }
    }

    @Test
    public void testReaderEmptyCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCollection.json");
        try {
            PokemonTeamCollection collection = reader.read();
            assertEquals(0, collection.sizeCollection());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCollection.json");

        try {
            PokemonTeamCollection collection = reader.read();
            ArrayList<PokemonTeam> teams = new ArrayList<>();

            try {
                for (int i = 0; i < collection.sizeCollection(); i++) {
                    teams.add(collection.getTeam(i));
                }
            } catch (TeamNotFoundException e) {
                fail("Unexpected TeamNotFoundException");
            }
            // ensures it correctly loads 3 teams
            assertEquals(3, teams.size());

            // ensures the name, size, and pokemon details of the first team is correct
            assertEquals("team with 6 pokemons", teams.get(0).getTeamName());
            assertTrue(teams.get(0).isFull());
            checkPokemon("pikachu", "ELECTRIC", "NONE", teams.get(0).getPokemon(0));
            checkPokemon("mewtwo", "PSYCHIC", "NONE", teams.get(0).getPokemon(1));
            checkPokemon("charizard", "FIRE", "FLYING", teams.get(0).getPokemon(2));
            checkPokemon("gengar", "GHOST", "POISON", teams.get(0).getPokemon(3));
            checkPokemon("blastoise", "WATER", "NONE", teams.get(0).getPokemon(4));
            checkPokemon("scizor", "BUG", "STEEL", teams.get(0).getPokemon(5));


            // ensures the name, size, and pokemon details of the second team is correct
            assertEquals("team with 1 pokemon", teams.get(1).getTeamName());
            assertEquals(1, teams.get(1).teamSize());
            checkPokemon("snorlax", "NORMAL", "NONE", teams.get(1).getPokemon(0));


            // ensures the name, size, and pokemon details of the third team is correct
            assertEquals("team with no pokemon", teams.get(2).getTeamName());
            assertEquals(0, teams.get(2).teamSize());
            assertEquals(0, teams.get(2).teamSize());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
