package ui.screens;

import model.Pokemon;
import model.PokemonTeam;
import model.PokemonTeamCollection;
import model.types.PokemonType;
import model.types.Types;
import ui.PokemonTeamPlannerWindow;
import ui.screens.buttons.TypeButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static ui.PokemonTeamPlannerWindow.DEFAULT_FONT;
import static ui.PokemonTeamPlannerWindow.FONT_SIZE;

// models the screen (a panel) where you design and add your pokemon teams to your collection
public class AddTeamScreen extends JPanel {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 800;

    private JPanel containMasterQuit;

    private PokemonTeamPlannerWindow pokemonTeamPlannerWindow;

    private JPanel masterPanel;
    private CardLayout cl = new CardLayout();

    private PokemonTeamCollection collection;
    private PokemonTeam teamToAdd = new PokemonTeam("Default team");
    private Pokemon pokemonToAdd = new Pokemon("default");

    private JPanel mainPanel;

    private JLabel teamNameLabel;
    private JLabel pokemonNameLabel;
    private JTextField teamNamePrompt;
    private JTextField pokemonNamePrompt;

    private JPanel panelForTypeButtons;

    private JPanel justBelowTypeButtons;
    private JLabel typeButtonInstructions;
    private JButton resetTypesButton;

    private JPanel transitionPanel;
    private JButton nextPokemonButton;
    private JButton completeTeamButton;

    private List<TypeButton> allTypeButtons = new ArrayList<>();
    private List<TypeButton> typesClicked = new ArrayList<>();


    // MODIFIES: this
    // EFFECTS : constructs and initializes an instance of this "add new pokemon team" screen
    public AddTeamScreen(PokemonTeamPlannerWindow mainWindow, PokemonTeamCollection collection) {
        containMasterQuit = new JPanel();
        containMasterQuit.setLayout(cl);

        masterPanel = new JPanel();

        this.pokemonTeamPlannerWindow = mainWindow;
        this.collection = collection;

        this.mainPanel = generateMainPanel();

        configureNameOptions();
        panelForTypeButtons = generateTypeButtonPanel();
        configureTypeRelatedOptions();
        configureTransitionButtons();

        loadUpMainPanel();
        configureNewTeamPanel(mainPanel);

        containMasterQuit.add(masterPanel, "main");

        setVisible(true);
    }

    // EFFECTS : returns the topmost JPanel
    public JPanel getContainer() {
        return containMasterQuit;
    }

    // MODIFIES: this
    // EFFECTS : generate text input boxes and labels in this screen
    private void configureNameOptions() {
        this.teamNameLabel = setUpNameLabel("Pokemon team name?");
        this.pokemonNameLabel = setUpNameLabel("Pokemon's name?");
        this.pokemonNamePrompt = setUpTextFields();
        this.teamNamePrompt = setUpTextFields();
    }

    // MODIFIES: this
    // EFFECTS : displays instructions and a reset option in this panel
    private void configureTypeRelatedOptions() {
        typeButtonInstructions = generateInstructions();
        resetTypesButton = generateResetButton();
        justBelowTypeButtons = makePanelForInstructionsAndReset();
        fillWithComponents(justBelowTypeButtons);
    }

    // MODIFIES: this
    // EFFECTS : generate buttons to allow users to stop adding pokemon prematurely,
    //           or to move on to create their next pokemon(s)
    private void configureTransitionButtons() {
        nextPokemonButton = generateTransitionButton("Create next pokemon");
        completeTeamButton = generateTransitionButton("End team creation");
        transitionPanel = generateNextOrSavePanel();
        transitionPanel.add(nextPokemonButton);
        transitionPanel.add(completeTeamButton);

        setNextButtonCommand(pokemonNamePrompt, nextPokemonButton);
        setCompleteButtonCommand(completeTeamButton);
    }

    // MODIFIES: this
    // EFFECTS : adds labels, buttons, and other sub panels to the main panel for this screen
    private void loadUpMainPanel() {
        mainPanel.add(teamNameLabel);
        mainPanel.add(teamNamePrompt);
        mainPanel.add(pokemonNameLabel);
        mainPanel.add(pokemonNamePrompt);
        mainPanel.add(panelForTypeButtons);
        mainPanel.add(justBelowTypeButtons);
        mainPanel.add(transitionPanel);
    }

    // MODIFIES: this
    // EFFECTS : loads the instructions and reset types button just beneath the types buttons
    private void fillWithComponents(JPanel justBelowTypeButtons) {
        justBelowTypeButtons.add(typeButtonInstructions);
        justBelowTypeButtons.add(resetTypesButton);
    }

    // MODIFIES: this
    // EFFECTS : sets command for the "End team creation" button;
    //           ensures at least 1 pokemon is added to each team created,
    //           updates the collection and returns to the main home screen
    //           where the user is informed of the addition of this new team
    public void setCompleteButtonCommand(JButton completeTeamButton) {
        completeTeamButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (teamToAdd.isEmpty()) {
                    displayEmptyErrorMessage();
                } else {
                    teamToAdd.changeTeamName(teamNamePrompt.getText());
                    collection.addNewTeam(teamToAdd);
                    teamToAdd = new PokemonTeam("");
                    pokemonTeamPlannerWindow.updateCollectionAfterAdding(collection);
                    pokemonTeamPlannerWindow.getSubheading().setText(teamNamePrompt.getText() + " created!");
                    pokemonTeamPlannerWindow.forceReturnHomePanel();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS : creates a JButton and enlarges it slightly.
    //           transitionButton will allow users to interact
    //           with transitioning between panels
    private JButton generateTransitionButton(String s) {
        JButton transitionButton = new JButton(s);
        transitionButton.setFont(DEFAULT_FONT);
        return transitionButton;
    }

    // MODIFIES: this
    // EFFECTS : sets command for the "Create next pokemon" button;
    //           adds the pokemon with selected types and given name into the team to be added.
    //           Once a team nears full team capacity, adds the very last pokemon to the team,
    //           adds the team to the collection to update it, and returns to the home screen
    //           where the user is informed that their new team has been added to the collection
    private void setNextButtonCommand(JTextField pokemonNamePrompt, JButton nextPokemonButton) {
        nextPokemonButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasValidTypeSelections()) {
                    // create an instance of this pokemon and add to team
                    if (teamToAdd.teamSize() < PokemonTeam.MAX_NUMBER_OF_POKEMON_PER_TEAM - 1) {
                        createAndAddPokemon(pokemonNamePrompt);
                        resetPage();
                    } else {
                        createAndAddPokemon(pokemonNamePrompt);
                        teamToAdd.changeTeamName(teamNamePrompt.getText());
                        collection.addNewTeam(teamToAdd);
                        pokemonTeamPlannerWindow.updateCollectionAfterAdding(collection);
                        pokemonTeamPlannerWindow.getSubheading().setText(teamNamePrompt.getText() + " created!");
                        pokemonTeamPlannerWindow.forceReturnHomePanel();
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS : Creates and adds the pokemon with specified names and types by the
    //           user into the new team to be added to the collection
    private void createAndAddPokemon(JTextField pokemonNamePrompt) {
        String name = pokemonNamePrompt.getText();
        PokemonType firstType = new PokemonType(typesClicked.get(0).getText());

        pokemonToAdd = new Pokemon(name);
        pokemonToAdd.setFirstType(firstType);

        handleSecondType();


        teamToAdd.addPokemon(pokemonToAdd);
    }

    // MODIFIES: this
    // EFFECTS : handles the case where the user wants the pokemon to
    //           only have one type (second type is NONE)
    private void handleSecondType() {
        try {
            PokemonType secondType = new PokemonType(typesClicked.get(1).getText());
            pokemonToAdd.setSecondType(secondType);
        } catch (IndexOutOfBoundsException ibe) {
            pokemonToAdd.setSecondType(new PokemonType("NONE"));
        }
    }

    // MODIFIES: this
    // EFFECTS : ensures that the user clicks 1 or 2 type buttons ONLY
    //           before clicking on a panel transition button. Message
    //           dialogs are thrown for invalid type button choices and returns
    //           false (otherwise return true)
    public boolean hasValidTypeSelections() {
        int counter = 0;
        for (TypeButton t : allTypeButtons) {
            if (t.isClicked()) {
                counter++;
            }
        }
        if (counter > 2) {
            deselectTypeButtons();
            JOptionPane.showMessageDialog(this, "Please select 1 or 2 types only.");
            return false;
        } else if (counter == 0) {
            JOptionPane.showMessageDialog(this, "Please select 1 or 2 types");
            return false;
        }
        return true;
    }

    // MODIFIES: this
    // EFFECTS : configures the master panel which will be displayed when
    //           user clicks the "add team" button from the home screen
    private void configureNewTeamPanel(JPanel firstPageMainPanel) {
        masterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        masterPanel.setSize(new Dimension(400, HEIGHT));
        masterPanel.setBackground(Color.GRAY);
        masterPanel.add(firstPageMainPanel, "main");
    }

    // MODIFIES: this
    // EFFECTS : generates a panel that will group the transition buttons into one
    private JPanel generateNextOrSavePanel() {
        JPanel nextOrSavePanel = new JPanel();
        nextOrSavePanel.setPreferredSize(new Dimension(350, 105));
        nextOrSavePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        return nextOrSavePanel;
    }

    // MODIFIES: this
    // EFFECTS : generates a panel that will group the instruction and reset buttons into one
    //           and returns it
    private JPanel makePanelForInstructionsAndReset() {
        JPanel justBelowTypeButtons = new JPanel();
        justBelowTypeButtons.setPreferredSize(new Dimension(WIDTH / 3, 120));
        justBelowTypeButtons.setLayout(new FlowLayout(FlowLayout.CENTER,100, 15));
        return justBelowTypeButtons;
    }

    // MODIFIES: this
    // EFFECTS : generates the reset button and enlarges it slightly, and returns it.
    //           Also give it a command to deselect all clicked type buttons.
    private JButton generateResetButton() {
        JButton resetTypesButton = new JButton("Reset types");
        resetTypesButton.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 2));
        resetTypesButton.setFont(DEFAULT_FONT);

        resetTypesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deselectTypeButtons();
            }
        });
        return resetTypesButton;
    }

    // MODIFIES: this
    // EFFECTS : un-clicks all type buttons that have been clicked so far
    //           and reset to default font
    private void deselectTypeButtons() {
        for (TypeButton t : allTypeButtons) {
            if (t.isClicked()) {
                t.click();
                t.setFont(DEFAULT_FONT);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS : generates the instructions label and enlarges it slightly and returns it
    private JLabel generateInstructions() {
        JLabel instructions = new JLabel("Please select 1 or 2 types");
        instructions.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));
        instructions.setFont(DEFAULT_FONT);
        return instructions;
    }

    // MODIFIES: this
    // EFFECTS : generates the panel in which all type buttons will be grouped together and returns it;
    //           also calls initializeTypeButtons() to configure the type buttons
    private JPanel generateTypeButtonPanel() {
        JPanel panelForTypeButtons = new JPanel();
        panelForTypeButtons.setPreferredSize(new Dimension(370, HEIGHT / 3));
        panelForTypeButtons.setLayout(new FlowLayout(FlowLayout.CENTER));

        panelForTypeButtons.setBackground(Color.GRAY);
        initializeTypeButtons(panelForTypeButtons);
        return panelForTypeButtons;
    }

    // MODIFIES: this
    // EFFECTS : creates and configures a new button for each pokemon type, excluding NONE.
    //           buttons are added to the allTypeButtons list afterwards
    private void initializeTypeButtons(JPanel typeButtons) {
        Types[] types = Types.values();

        for (Types t : types) {

            if (!t.equals(Types.NONE)) {
                TypeButton button = configureButton(t);
                typeButtons.add(button);
                this.allTypeButtons.add(button);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS : button is configured in terms of its color, size,
    //           and command (where the button text is bolded when clicked
    //           and un-bolded when clicked again), as well as being
    //           added to/removed from the selected type buttons accordingly.
    private TypeButton configureButton(Types t) {
        Color buttonColor = getCorrespondingColor(t);
        TypeButton button = new TypeButton(t);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (button.isClicked()) {
                    button.setFont(DEFAULT_FONT);
                    typesClicked.remove(button);
                } else {
                    button.setFont(new Font("SansSerif", Font.BOLD, FONT_SIZE + 1));
                    typesClicked.add(button);
                }
                button.click();
            }
        });
        if (hardToRead(t)) {
            button.setForeground(new Color(175, 175, 175));
        }
        button.setBackground(buttonColor);
        button.setFont(DEFAULT_FONT);
        return button;
    }

    // EFFECTS : returns true if black text with the button color
    //           is difficult to read with the naked eye
    private boolean hardToRead(Types t) {
        switch (t) {
            case FIRE:
            case WATER:
            case DARK:
            case DRAGON:
            case GHOST:
            case BUG:
                return true;
            default:
                return false;
        }
    }

    // EFFECTS : returns the representative color for
    //           each pokemon type available (excluding NONE)
    private Color getCorrespondingColor(Types t) {

        switch (t) {
            case BUG:
                return new Color(36, 103, 40);
            case DARK:
                return new Color(58, 77, 107);
            case DRAGON:
                return new Color(17, 22, 123);
            case ELECTRIC:
                return new Color(255, 235, 2);
            case FAIRY:
                return new Color(255, 142, 223);
            case FIGHTING:
                return new Color(185, 68, 0);
            case FIRE:
                return new Color(191, 0, 0);
            case FLYING:
                return new Color(0, 186, 230);
            case GHOST:
                return new Color(79, 60, 154);
            default:
                return getColorPartTwo(t);
        }
    }

    // EFFECTS : returns the representative color for
    //           each pokemon type available (excluding NONE);
    //           getColorPartTwo was implemented to adhere
    //           to the 25-line maximum checkstyle rule
    private Color getColorPartTwo(Types t) {
        switch (t) {
            case GRASS:
                return new Color(12, 194, 44);
            case GROUND:
                return new Color(196, 127, 48);
            case ICE:
                return new Color(0, 255, 229);
            case NORMAL:
                return new Color(156, 156, 156);
            case POISON:
                return new Color(124, 0, 255);
            case PSYCHIC:
                return new Color(196, 66, 181);
            case ROCK:
                return new Color(176, 144, 103);
            case STEEL:
                return new Color(96, 149, 163);
            default:
                return new Color(0, 59, 176);
        }
    }

    // MODIFIES: this
    // EFFECTS : returns a text field that the user can type into;
    //           enlarges the text written into the field to see it better
    private JTextField setUpTextFields() {
        JTextField pokemonNamePrompt = new JTextField(10);
        pokemonNamePrompt.setFont(DEFAULT_FONT);
        return pokemonNamePrompt;
    }

    // MODIFIES: this
    // EFFECTS : returns a label that will detail what the user should
    //           type into the respective text field
    private JLabel setUpNameLabel(String s) {
        JLabel teamNameLabel = new JLabel(s);
        teamNameLabel.setFont(DEFAULT_FONT);
        return teamNameLabel;
    }

    // MODIFIES: this
    // EFFECTS : generates and returns the main panel to be added to the master panel
    //           that will house the text fields, labels, type buttons, and transition buttons
    private JPanel generateMainPanel() {
        JPanel firstPageMainPanel = new JPanel();
        firstPageMainPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        firstPageMainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        return firstPageMainPanel;
    }

    // MODIFIES: this
    // EFFECTS : clears the pokemon name text field, un-clicks all clicked type buttons
    //           and clears the history of clicked buttons
    private void resetPage() {
        pokemonNamePrompt.setText("");
        deselectTypeButtons();
        typesClicked.clear();
    }

    // EFFECTS : prompts a message dialog that ensures users add at least 1 pokemon to each team they make
    private void displayEmptyErrorMessage() {
        JOptionPane.showMessageDialog(this, "A team must have at least 1 pokemon.");
    }

    // EFFECTS : updates this.collection with incoming collection to be in sync
    //           with the other panel/frame classes
    public void updateCollection(PokemonTeamCollection collection) {
        this.collection = collection;
    }
}
