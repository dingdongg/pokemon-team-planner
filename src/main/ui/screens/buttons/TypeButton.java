package ui.screens.buttons;

import model.types.Types;

import javax.swing.*;

// models buttons specifically designed for Pokemon types
public class TypeButton extends JButton {

    Types type;
    boolean clicked;

    // EFFECTS : create an instance of a pokemon type button
    public TypeButton(Types type) {
        super(type.toString());
        this.type = type;
        this.clicked = false;
    }

    // EFFECTS: return true if this button is clicked, false otherwise
    public boolean isClicked() {
        return this.clicked;
    }

    // EFFECTS : Set this button to clicked if un-clicked and vice versa
    public void click() {
        this.clicked = !this.clicked;
    }

}
