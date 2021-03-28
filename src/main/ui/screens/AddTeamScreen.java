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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static ui.PokemonTeamPlannerWindow.DEFAULT_FONT;
import static ui.PokemonTeamPlannerWindow.FONT_SIZE;

public class AddTeamScreen extends JPanel {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 800;

    private JPanel containMasterQuit;

    private PokemonTeamPlannerWindow pokemonTeamPlannerWindow;

    private JPanel masterPanel;
    private CardLayout cl = new CardLayout();

    private PokemonTeamCollection collection;
    private PokemonTeam teamToAdd = new PokemonTeam("Default team");
    private Pokemon pokemonToAdd = new Pokemon("defaulo");

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

    public JPanel getContainer() {
        return containMasterQuit;
    }

    private void configureNameOptions() {
        this.teamNameLabel = setUpNameLabel("Pokemon team name?");
        this.pokemonNameLabel = setUpNameLabel("Pokemon's name?");
        this.pokemonNamePrompt = setUpTextFields();
        this.teamNamePrompt = setUpTextFields();
    }

    private void configureTypeRelatedOptions() {
        typeButtonInstructions = generateInstructions();
        resetTypesButton = generateResetButton();
        justBelowTypeButtons = makePanelForInstructionsAndReset();
        fillWithComponents(justBelowTypeButtons);
    }

    private void configureTransitionButtons() {
        nextPokemonButton = generateTransitionButton("Create next pokemon");
        completeTeamButton = generateTransitionButton("End team creation");
        transitionPanel = generateNextOrSavePanel();
        transitionPanel.add(nextPokemonButton);
        transitionPanel.add(completeTeamButton);

        setNextButtonCommand(pokemonNamePrompt, nextPokemonButton);
        setCompleteButtonCommand(completeTeamButton);
    }

    private void loadUpMainPanel() {
        mainPanel.add(teamNameLabel);
        mainPanel.add(teamNamePrompt);
        mainPanel.add(pokemonNameLabel);
        mainPanel.add(pokemonNamePrompt);
        mainPanel.add(panelForTypeButtons);
        mainPanel.add(justBelowTypeButtons);
        mainPanel.add(transitionPanel);
    }

    private void fillWithComponents(JPanel justBelowTypeButtons) {
        justBelowTypeButtons.add(typeButtonInstructions);
        justBelowTypeButtons.add(resetTypesButton);
    }

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

    private JButton generateTransitionButton(String s) {
        JButton nextPokemonButton = new JButton(s);
        nextPokemonButton.setFont(DEFAULT_FONT);
        return nextPokemonButton;
    }

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

    private void createAndAddPokemon(JTextField pokemonNamePrompt) {
        String name = pokemonNamePrompt.getText();
        PokemonType firstType = new PokemonType(typesClicked.get(0).getText());

        pokemonToAdd = new Pokemon(name);
        pokemonToAdd.setFirstType(firstType);

        handleSecondType();


        teamToAdd.addPokemon(pokemonToAdd);
    }

    private void handleSecondType() {
        try {
            PokemonType secondType = new PokemonType(typesClicked.get(1).getText());
            pokemonToAdd.setSecondType(secondType);
        } catch (IndexOutOfBoundsException ibe) {
            pokemonToAdd.setSecondType(new PokemonType("NONE"));
        }
    }

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

    private void configureNewTeamPanel(JPanel firstPageMainPanel) {
        masterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        masterPanel.setSize(new Dimension(400, HEIGHT));
        masterPanel.setBackground(Color.GRAY);
        masterPanel.add(firstPageMainPanel, "main");
    }

    private JPanel generateNextOrSavePanel() {
        JPanel nextOrSavePanel = new JPanel();
        nextOrSavePanel.setPreferredSize(new Dimension(350, 105));
        nextOrSavePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        return nextOrSavePanel;
    }

    private JPanel makePanelForInstructionsAndReset() {
        JPanel justBelowTypeButtons = new JPanel();
        justBelowTypeButtons.setPreferredSize(new Dimension(WIDTH / 3, 120));
        justBelowTypeButtons.setLayout(new FlowLayout(FlowLayout.CENTER,100, 15));
        return justBelowTypeButtons;
    }

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

    private void deselectTypeButtons() {
        for (TypeButton t : allTypeButtons) {
            if (t.isClicked()) {
                t.click();
                t.setFont(DEFAULT_FONT);
            }
        }
    }

    private JLabel generateInstructions() {
        JLabel instructions = new JLabel("Please select 1 or 2 types");
        instructions.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));
        instructions.setFont(DEFAULT_FONT);
        return instructions;
    }

    private JPanel generateTypeButtonPanel() {
        JPanel panelForTypeButtons = new JPanel();
        panelForTypeButtons.setPreferredSize(new Dimension(370, HEIGHT / 3));
        panelForTypeButtons.setLayout(new FlowLayout(FlowLayout.CENTER));

        panelForTypeButtons.setBackground(Color.GRAY);
        initializeTypeButtons(panelForTypeButtons);
        return panelForTypeButtons;
    }

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

    private JTextField setUpTextFields() {
        JTextField pokemonNamePrompt = new JTextField(10);
        pokemonNamePrompt.setFont(DEFAULT_FONT);
        return pokemonNamePrompt;
    }

    private JLabel setUpNameLabel(String s) {
        JLabel teamNameLabel = new JLabel(s);
        teamNameLabel.setFont(DEFAULT_FONT);
        return teamNameLabel;
    }

    private void setPokemonNameViaEnter(JLabel pokemonNameLabel, JTextField pokemonNamePrompt) {
        pokemonNamePrompt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pokemonToAdd.changeName(this.toString());
                    pokemonNameLabel.setText("Pokemon name: " + pokemonNamePrompt.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void setTeamNameViaEnter(JLabel teamNameLabel, JTextField teamNamePrompt) {
        teamNamePrompt.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                keyPressed(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    teamToAdd.changeTeamName(this.toString());
                    teamNameLabel.setText("Team name: " + teamNamePrompt.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private JPanel generateMainPanel() {
        JPanel firstPageMainPanel = new JPanel();
        firstPageMainPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        firstPageMainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        return firstPageMainPanel;
    }

    private void resetPage() {
        pokemonNamePrompt.setText("");
        deselectTypeButtons();
        typesClicked.clear();
    }

    private void displayEmptyErrorMessage() {
        JOptionPane.showMessageDialog(this, "A team must have at least 1 pokemon.");
    }

    public void updateCollection(PokemonTeamCollection collection) {
        this.collection = collection;
    }
}
