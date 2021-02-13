package ui;

import model.Pokemon;
import model.PokemonTeam;
import model.PokemonTeamCollection;
import model.types.Type;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Scanner;

// The Pokemon Team Planner app
// CODE REFERENCED: TellerApp class in the Teller project provided
public class PokemonTeamPlannerApp {

    private PokemonTeamCollection teamList;
    private Scanner input;
    private boolean appRunning;

    // EFFECTS: starts up the program and creates an empty collection
    //          of pokemon team list
    public PokemonTeamPlannerApp() {
        teamList = new PokemonTeamCollection();
        appRunning = true;
        input = new Scanner(System.in);
        startApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void startApp() {
        String command;

        startUpMessages();

        while (appRunning) {
            showOptions();

            command = input.next().toLowerCase();

            processInput(command);
        }
    }

    // EFFECTS: takes in the user input and determines
    //          the next steps
    private void processInput(String command) {
        if (command.contentEquals("m")) {
            System.out.println("Making a new Pokemon team!\n");
            makeNewPokemonTeam();
        } else if (command.contentEquals("e")) {
            editPokemonTeam();
        } else if (command.contentEquals("v")) {
            viewPokemonTeam();
        } else if (command.contentEquals("d")) {
            deletePokemonTeam();
        } else if (command.contentEquals("r")) {
            resetCollection();
        } else if (command.contentEquals("q")) {
            closingMessage();
            appRunning = false;
        } else {
            askInputAgain();
        }
    }

    // EFFECTS: instructs user to re-input a valid input
    private void askInputAgain() {
        System.out.println("Invalid input. Please try again.");
    }

    // EFFECTS: displays program closing message
    private void closingMessage() {
        System.out.println("Gotta catch'em all! Farewell, trainer!");
    }

    // MODIFIES: this
    // EFFECTS: empties the entire collection of pokemon teams after confirmation
    private void resetCollection() {

        boolean consideringReset = true;

        while (consideringReset) {
            System.out.println("This process is IRREVERSIBLE. Proceed? (y/n) ");
            String decision = input.next().toLowerCase();

            if (decision.contentEquals("y")) {
                System.out.println("Reset successfully... All gone!");
                this.teamList.emptyCollection();
                consideringReset = false;
            } else if (decision.contentEquals("n")) {
                System.out.println("Collection reset cancelled!");
                consideringReset = false;
            } else {
                System.out.println("Invalid input. Please try again with either 'y' or 'n'.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a selected pokemon team from the collection
    private void deletePokemonTeam() {

        if (this.teamList.sizeCollection() == 0) {
            System.out.println("The collection is empty, how can you remove nothing?\n");
        } else {
            System.out.println("Name of team you want to delete? ");
            System.out.println("Teams currently stored: \n");
            displayAllTeamNames();
            PokemonTeam wantedTeam = findTeam(input.next() + input.nextLine());

            if (wantedTeam == null) {
                System.out.println("No such team found. Try again!");
            } else {
                System.out.println(wantedTeam.getTeamName() + ", you were a good team! Adios!");
                this.teamList.deleteTeam(wantedTeam);
            }
        }
    }

    // EFFECTS: displays the contents of a selected pokemon team
    private void viewPokemonTeam() {

        if (this.teamList.sizeCollection() == 0) {
            System.out.println("The collection is empty, nothing to see here...\n");
        } else {
            System.out.println("Name of team you want to check out? \n");
            System.out.println("Teams currently stored: \n");
            displayAllTeamNames();
            PokemonTeam wantedTeam = findTeam(input.next() + input.nextLine());

            if (wantedTeam == null) {
                System.out.println("No such team found. Try again!");
            } else {
                System.out.println(this.teamList.viewTeam(wantedTeam));
            }
        }
    }

    // EFFECTS: displays the names of teams currently stored
    private void displayAllTeamNames() {
        for (int i = 0; i < this.teamList.sizeCollection(); i++) {
            System.out.println("\t" + this.teamList.getTeam(i).getTeamName());
        }
    }

    // EFFECTS: finds a pokemon team with the given name (next),
    //          otherwise return null if not found
    private PokemonTeam findTeam(String next) {

        for (int i = 0; i < this.teamList.sizeCollection(); i++) {
            if (this.teamList.getTeam(i).getTeamName().contentEquals(next)) {
                return this.teamList.getTeam(i);
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: select a team and edit its contents
    private void editPokemonTeam() {

        if (this.teamList.sizeCollection() == 0) {
            System.out.println("The collection is empty, there's nothing to edit!");
        } else {
            System.out.println("Name of team you want to edit? ");
            displayAllTeamNames();
            PokemonTeam wantedTeam = findTeam(input.next() + input.nextLine());

            if (wantedTeam == null) {
                System.out.println("No such team found. Try again!");
            } else {
                editOptions();
                editContents(wantedTeam);
            }
        }
    }

    private void editContents(PokemonTeam wantedTeam) {
        String choice = input.next().toLowerCase();

        if (choice.contentEquals("n")) {
            System.out.println("What should be the new team name? ");
            wantedTeam.changeTeamName(input.next() + input.nextLine());
            System.out.println("Renamed to " + wantedTeam.getTeamName() + "!");
        } else if (choice.contentEquals("p")) {

            displayPokemonEditOptions();
            String option = input.next().toLowerCase();

            processEditPokemonInput(option, wantedTeam);
        }
    }

    private void processEditPokemonInput(String option, PokemonTeam wantedTeam) {
        if (option.equals("a")) {
            if (wantedTeam.isFull()) {
                System.out.println("This team is full!");
            } else {
                createAndAddPokemon(wantedTeam);
            }
        } else if (option.equals("r")) {
            System.out.println("Which pokemon to remove? \n");
            displayListOfPokemonInTeam(wantedTeam);
            Pokemon toBeRemoved = wantedTeam.findPokemon(input.next());
            System.out.println("Successfully removed " + toBeRemoved.getName() + "!");
            wantedTeam.deletePokemon(toBeRemoved);
        } else if (option.equals("e")) {
            System.out.println("Which pokemon to edit? \n");
            displayListOfPokemonInTeam(wantedTeam);
            Pokemon toBeEdited = wantedTeam.findPokemon(input.next());
            editPokemon(toBeEdited);
        }
    }

    private void editPokemon(Pokemon toBeEdited) {
        if (toBeEdited == null) {
            System.out.println("No such pokemon found!");
        } else {
            System.out.println("Edit name or types? (n/t) \n");
            String command = input.next().toLowerCase();

            if (command.equals("n")) {
                System.out.println("Enter pokemon's new name: ");
                toBeEdited.changeName(input.next());
            } else if (command.equals("t")) {
                System.out.println("New primary type: ");
                Type newType = new Type(input.next());
                toBeEdited.setFirstType(newType);
                System.out.println("New secondary type: ");
                newType = new Type(input.next());
                toBeEdited.setSecondType(newType);
            }
        }
    }

    private void displayPokemonEditOptions() {
        System.out.println("\tAdding a new pokemon to the team   -> a");
        System.out.println("\tEditing existing pokemons in team  -> e");
        System.out.println("\tRemoving a pokemon from the team   -> r");
    }

    private void displayListOfPokemonInTeam(PokemonTeam wantedTeam) {
        for (int i = 0; i < wantedTeam.teamSize(); i++) {
            System.out.println("\t" + wantedTeam.getPokemon(i).getName());
        }
    }

    private void editOptions() {
        System.out.println("What would you like to edit? \n");
        System.out.println("\tname of team      -> n");
        System.out.println("\tedit pokemon(s)   -> p");
        System.out.println("\tback to main menu -> b");
    }

    // MODIFIES: this
    // EFFECTS: make a new pokemon team and add it to this collection
    private void makeNewPokemonTeam() {

        System.out.println("Choose the name of your new team: ");
        PokemonTeam newPokemonTeam = new PokemonTeam(input.next() + input.nextLine());

        addPokemons(newPokemonTeam);
    }

    // MODIFIES: this
    // EFFECTS: create and add up to 6 pokemon to a pokemon team
    //          and add it to the collection
    private void addPokemons(PokemonTeam newPokemonTeam) {

        for (int i = 0; i < PokemonTeam.MAX_NUMBER_OF_POKEMON_PER_TEAM; i++) {
            createAndAddPokemon(newPokemonTeam);

            if (newPokemonTeam.teamSize() == PokemonTeam.MAX_NUMBER_OF_POKEMON_PER_TEAM) {
                this.teamList.addNewTeam(newPokemonTeam);
                break;
            }

            System.out.println("Add more pokemon? (y/n) ");
            String decision = input.next().toLowerCase();

            if (decision.contentEquals("n")) {
                this.teamList.addNewTeam(newPokemonTeam);
                break;
            }
        }
        System.out.println(newPokemonTeam.getTeamName() + " created! This team might just be TOO strong...");
    }

    // EFFECTS: create and add one pokemon to the given pokemon team
    private void createAndAddPokemon(PokemonTeam team) {

        System.out.println("Pokemon's name? ");
        Pokemon pokemon = new Pokemon(input.next());

        System.out.println("Its primary type? ");
        Type primaryType = new Type(input.next());
        pokemon.setFirstType(primaryType);

        System.out.println("Does it have a secondary type? (y/n) ");
        String option = input.next().toLowerCase();

        if (option.contentEquals("y")) {
            System.out.println("What is its secondary type? ");
            Type secondaryType = new Type(input.next());
            pokemon.setSecondType(secondaryType);
        }

        team.addPokemon(pokemon);
        System.out.println("Welcome to the team, " + pokemon.getName() + "!");
    }

    // EFFECTS: displays the possible options to user
    private void showOptions() {
        System.out.println("What would you like to do?\n");
        System.out.println("\tmake a new Pokemon team       -> m");
        System.out.println("\tedit an existing Pokemon team -> e");
        System.out.println("\tview an existing Pokemon team -> v");
        System.out.println("\tdelete a team from collection -> d");
        System.out.println("\treset the entire collection   -> r");
        System.out.println("\tclose the program             -> q");
    }

    // EFFECTS: shows a welcome message when booting up program
    private void startUpMessages() {
        System.out.println("Welcome to Pokemon Team Planner!\n");
    }
}
