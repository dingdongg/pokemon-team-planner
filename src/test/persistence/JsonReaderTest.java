package persistence;

import model.PokemonTeam;
import model.PokemonTeamCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// code in this class was referenced from JsonSerializationDemo in Phase 2, Task 4 of the personal project
public class JsonReaderTest {

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

            for (int i = 0; i < collection.sizeCollection(); i++) {
                teams.add(collection.getTeam(i));
            }
            // ensures it correctly loads 3 teams
            assertEquals(3, teams.size());

            // ensures the name, size, and pokemon details of the first team is correct
            assertEquals("team with 6 pokemons", teams.get(0).getTeamName());
            assertTrue(teams.get(0).isFull());
            assertEquals("pikachu", teams.get(0).getPokemon(0).getName());
            assertEquals("electric", teams.get(0).getPokemon(0).getFirstType().getTypeName());

            assertEquals("mewtwo", teams.get(0).getPokemon(1).getName());
            assertEquals("psychic", teams.get(0).getPokemon(1).getFirstType().getTypeName());

            assertEquals("charizard", teams.get(0).getPokemon(2).getName());
            assertEquals("fire", teams.get(0).getPokemon(2).getFirstType().getTypeName());
            assertEquals("flying", teams.get(0).getPokemon(2).getSecondType().getTypeName());

            assertEquals("gengar", teams.get(0).getPokemon(3).getName());
            assertEquals("ghost", teams.get(0).getPokemon(3).getFirstType().getTypeName());
            assertEquals("poison", teams.get(0).getPokemon(3).getSecondType().getTypeName());

            assertEquals("blastoise", teams.get(0).getPokemon(4).getName());
            assertEquals("water", teams.get(0).getPokemon(4).getFirstType().getTypeName());

            assertEquals("scizor", teams.get(0).getPokemon(5).getName());
            assertEquals("bug", teams.get(0).getPokemon(5).getFirstType().getTypeName());
            assertEquals("steel", teams.get(0).getPokemon(5).getSecondType().getTypeName());


            // ensures the name, size, and pokemon details of the second team is correct
            assertEquals("team with 1 pokemon", teams.get(1).getTeamName());
            assertEquals(1, teams.get(1).teamSize());
            assertEquals("snorlax", teams.get(1).getPokemon(0).getName());
            assertEquals("normal", teams.get(1).getPokemon(0).getFirstType().getTypeName());


            // ensures the name, size, and pokemon details of the third team is correct
            assertEquals("team with no pokemon", teams.get(2).getTeamName());
            assertEquals(0, teams.get(2).teamSize());
            assertEquals(0, teams.get(2).teamSize());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
