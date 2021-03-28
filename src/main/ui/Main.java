package ui;


import model.types.Types;

public class Main {
    public static void main(String[] args) {
        Types.initializeTypeConstants();
//        new PokemonTeamPlannerApp();    // user interface
        new PokemonTeamPlannerWindow();   // graphical user interface
    }
}
