package ui;


import model.types.Types;
import ui.buttons.TypeButton;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Types.initializeTypeConstants();
//        new PokemonTeamPlannerApp();
        new PokemonTeamPlannerWindow();

//        TypeButton button = new TypeButton(Types.FIRE);
//
//        button.click();
//        System.out.println(button.isClicked());
//        button.click();
//        System.out.println(button.isClicked());

    }
}
