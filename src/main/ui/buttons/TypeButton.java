package ui.buttons;

import model.types.Types;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TypeButton extends JButton {

    Types type;
    boolean clicked;


    public TypeButton(Types type) {
        super(type.toString());
        this.type = type;
        this.clicked = false;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void click() {
        this.clicked = !this.clicked;
    }

}
