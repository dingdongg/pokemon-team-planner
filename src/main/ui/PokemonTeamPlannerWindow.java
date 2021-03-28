package ui;

import model.Pokemon;
import model.PokemonTeam;
import model.PokemonTeamCollection;
import model.types.PokemonType;
import model.types.Types;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.buttons.TypeButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokemonTeamPlannerWindow extends JFrame {

    public static final int FONT_SIZE = 20;
    public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, FONT_SIZE);
    private static final int WIDTH = 900;
    private static final int HEIGHT = 800;

    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private PokemonTeamCollection collection = new PokemonTeamCollection();
    private PokemonTeam teamToAdd = new PokemonTeam("default team");
    private Pokemon pokemonToAdd = new Pokemon("default");
    private List<JButton> homeScreenButtons;
    private JPanel masterPanel = new JPanel();
    private JPanel homePanel;

    private JPanel newTeamPanel = new JPanel();

    private JPanel viewPanel = new JPanel();
    private JPanel delTeamPanel = new JPanel();
    private JPanel analyzePanel = new JPanel();
    private CardLayout cl;
    private JLabel heading = new JLabel("home kek");

    private List<TypeButton> typeButtonsList = new ArrayList<>();
    private List<TypeButton> typesClicked = new ArrayList<>();

    public PokemonTeamPlannerWindow() {

        super("Pokemon Team Planner");
        // initial set up
        initialSetUp();

        // creates the home screen window
        createMainWindow();

        // creates panel to which buttons will be added
        createHomePanel();
        homePanel.add(heading);

        // add each button to panel
        loadHomeScreenButtons();
        setUpMasterPanel();


        makeTeamIfClicked();

        // goes into view collection screen
        JButton backButtonTwo = new JButton("go back from view");
        viewPanel.add(backButtonTwo);

        backButtonTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(masterPanel, "home");
            }
        });
        this.getContentPane();
        this.add(masterPanel, BorderLayout.CENTER);
//        add(masterPanel);

        setVisible(true);

    }

    private void makeTeamIfClicked() {
        // main panel of first page
        JPanel firstPageMainPanel = mainPanelAddNewTeam();

        // team name/pokemon name prompts and fields
        JLabel teamNameLabel = setUpNameLabel("Pokemon team name?");
        JLabel pokemonNameLabel = setUpNameLabel("Pokemon's name?");
        JTextField pokemonNamePrompt = setUpTextFields();
        JTextField teamNamePrompt = setUpTextFields();

        setTeamNameViaEnter(teamNameLabel, teamNamePrompt);
        setPokemonNameViaEnter(pokemonNameLabel, pokemonNamePrompt);

        // panel for all type buttons
        JPanel panelForTypeButtons = generateTypeButtonPanel();

        // panel for types chosen info (just below the types)
        JPanel justBelowTypeButtons = makePanelForInstructionsAndReset();

        // label displaying instructions
        JLabel instructions = generateInstructions();
        justBelowTypeButtons.add(instructions);

        // Reset type button
        JButton resetTypesButton = generateResetButton();
        justBelowTypeButtons.add(resetTypesButton);



        // add next and end buttons
        JPanel nextOrSavePanel = generateNextOrSavePanel();
        JButton nextPokemonButton = generateTransitionButton("Create next pokemon");
        JButton completeTeamButton = generateTransitionButton("End team creation");

        setNextButtonCommand(pokemonNamePrompt, nextPokemonButton);
        setCompleteButtonCommand(completeTeamButton);

        nextOrSavePanel.add(completeTeamButton);
        nextOrSavePanel.add(nextPokemonButton);

        firstPageMainPanel.add(teamNameLabel);
        firstPageMainPanel.add(teamNamePrompt);
        firstPageMainPanel.add(pokemonNameLabel);
        firstPageMainPanel.add(pokemonNamePrompt);
        firstPageMainPanel.add(panelForTypeButtons);
        firstPageMainPanel.add(justBelowTypeButtons);
        firstPageMainPanel.add(nextOrSavePanel);

        configureNewTeamPanel(firstPageMainPanel);
    }

    private void setCompleteButtonCommand(JButton completeTeamButton) {
        completeTeamButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                if (teamToAdd.isEmpty()) {
                    displayEmptyErrorMessage();
                } else {
                    collection.addNewTeam(teamToAdd);
                    cl.show(masterPanel, "home");
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
                cl.show(masterPanel, "add second pokemon");
                if (hasValidTypeSelections()) {
                    // create an instance of this pokemon and add to team

                    PokemonType firstType = new PokemonType(typesClicked.get(0).getText());
                    PokemonType secondType = new PokemonType(typesClicked.get(1).getText());

                    pokemonToAdd.changeName(pokemonNamePrompt.getText());
                    pokemonToAdd.setFirstType(firstType);

                    if (!(secondType == null)) {
                        pokemonToAdd.setSecondType(secondType);
                    }

                    teamToAdd.addPokemon(pokemonToAdd);
                    // reset page to add next pokemon
                    resetPage();
                    // TODO
                }
            }
        });
    }

    private void configureNewTeamPanel(JPanel firstPageMainPanel) {
        newTeamPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        newTeamPanel.setSize(new Dimension(400, HEIGHT));
        newTeamPanel.setBackground(Color.GRAY);
        newTeamPanel.add(firstPageMainPanel);
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

    private JPanel mainPanelAddNewTeam() {
        JPanel firstPageMainPanel = new JPanel();
        firstPageMainPanel.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT));
        firstPageMainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        return firstPageMainPanel;
    }

    private void resetPage() {



    }

    private void displayEmptyErrorMessage() {
        JOptionPane.showMessageDialog(this, "A team must have at least 1 pokemon.");
    }

    private void initialSetUp() {
        Types.initializeTypeConstants();
        this.homeScreenButtons = new ArrayList<>();
        loadButtons();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void setUpMasterPanel() {
        cl = new CardLayout();
        masterPanel.setLayout(cl);
        initializeMasterPanel();
    }

    private void loadHomeScreenButtons() {
        for (JButton homeScreenButton : homeScreenButtons) {
            homePanel.add(homeScreenButton);
        }
    }

    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setPreferredSize(new Dimension(800, 600));
        homePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 250));
        homePanel.setBackground(Color.WHITE);
    }

    private boolean hasValidTypeSelections() {
        int counter = 0;
        for (TypeButton t : typeButtonsList) {
            if (t.isClicked()) {
                counter++;
            }
        }
        if (counter > 2) {
            deselectTypeButtons();
            JOptionPane.showMessageDialog(this, "Please select 1 or 2 types only.");
            return false;
        } else if (counter == 0) {
            JOptionPane.showMessageDialog(this, "read the instructions silly");
            return false;
        }
        return true;
    }

    private void deselectTypeButtons() {
        for (TypeButton t : typeButtonsList) {
            if (t.isClicked()) {
                t.click();
                t.setFont(DEFAULT_FONT);
            }
        }
    }

    private void initializeTypeButtons(JPanel typeButtons) {

        Types[] types = Types.values();

        for (Types t : types) {

            if (!t.equals(Types.NONE)) {
                TypeButton button = configureButton(t);
                typeButtons.add(button);
                this.typeButtonsList.add(button);
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

    private void initializeMasterPanel() {
        masterPanel.add(homePanel, "home");
        masterPanel.add(newTeamPanel, "new !");
//        masterPanel.add(editTeamPanel, "edit");
        masterPanel.add(viewPanel, "view");
        masterPanel.add(delTeamPanel, "delete");
        masterPanel.add(analyzePanel, "analyze");

        cl.show(masterPanel, "home");
    }

    private void createMainWindow() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadButtons() {

        // TODO: 1. revise action listener (!!!)
        JButton addTeam = new JButton("Create new team");

        // TODO: 5. optional;
        JButton editTeam = new JButton("Edit existing team");

        // TODO: 2. action listener (!!!)
        JButton viewCollection = new JButton("View collection");

        // TODO: 3. action listener (!!!)
        JButton deleteTeam = new JButton("Delete existing team");

        // TODO: 4. action listener (!!!)
        JButton analyzeTeam = new JButton("Analyze a team");

        JButton saveCollection = new JButton("Save this collection");
        JButton loadCollection = new JButton("Load previous collection");


        registerActionListenerAdd(addTeam, "new !");
        registerActionListener(viewCollection, "view");
        registerActionListener(saveCollection, "SAVED!", true);
        registerActionListener(loadCollection, "LOADED!", false);

        // IMPLEMENT VERY LAST IF HAVE TIME (or remove if no time)
//        homeScreenButtons.add(editTeam);


        homeScreenButtons.add(addTeam);
        homeScreenButtons.add(viewCollection);
        homeScreenButtons.add(deleteTeam);
        homeScreenButtons.add(analyzeTeam);
        homeScreenButtons.add(saveCollection);
        homeScreenButtons.add(loadCollection);

    }

    private void registerActionListenerAdd(JButton button, String s) {
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(masterPanel, s);
                teamToAdd = new PokemonTeam("default");
                pokemonToAdd = new Pokemon("Default");
            }
        });
    }

    private void registerActionListener(JButton button, String s) {

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(masterPanel, s);
            }
        });
    }

    private void registerActionListener(JButton button, String text, boolean saveOrLoad) {

        if (saveOrLoad) {
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    saveCollection();
                    heading.setText(text);
                    cl.show(masterPanel, "home");
                }
            });
        } else {
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    loadCollection();
                    heading.setText(text);
                    cl.show(masterPanel, "home");
                }
            });
        }
    }

    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            System.out.println("Saved collection to " + JSON_STORE + "!");
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadCollection() {
        try {
            collection = jsonReader.read();
            System.out.println("Loaded collection from " + JSON_STORE + "!");
        } catch (IOException e) {
            System.out.println("Oops! Something's wrong and we can't retrieve collection from " + JSON_STORE);
        }
    }

}

//
//        TypeButton bugButton = new TypeButton(Types.BUG);
//        TypeButton darkButton = new TypeButton(Types.DARK);
//        TypeButton dragonButton = new TypeButton(Types.DRAGON);
//        TypeButton electricButton = new TypeButton(Types.ELECTRIC);
//        TypeButton fairyButton = new TypeButton(Types.FAIRY);
//        TypeButton fightingButton = new TypeButton(Types.FIGHTING);
//        TypeButton fireButton = new TypeButton(Types.FIRE);
//        TypeButton flyingButton = new TypeButton(Types.FLYING);
//        TypeButton ghostButton = new TypeButton(Types.GHOST);
//        TypeButton grassButton = new TypeButton(Types.GRASS);
//        TypeButton groundButton = new TypeButton(Types.GROUND);
//        TypeButton iceButton = new TypeButton(Types.ICE);
//        TypeButton normalButton = new TypeButton(Types.NORMAL);
//        TypeButton poisonButton = new TypeButton(Types.POISON);
//        TypeButton psychicButton = new TypeButton(Types.PSYCHIC);
//        TypeButton rockButton = new TypeButton(Types.ROCK);
//        TypeButton steelButton = new TypeButton(Types.STEEL);
//        TypeButton waterButton = new TypeButton(Types.WATER);
