package ui.screens;

import model.Pokemon;
import model.PokemonTeam;
import model.PokemonTeamCollection;
import model.exceptions.TeamNotFoundException;
import model.types.Types;
import ui.PokemonTeamPlannerWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ui.PokemonTeamPlannerWindow.DEFAULT_FONT;

// models the screen (a panel) where users can view, delete, or analyze a team in their collection
public class ViewTeamScreen extends JPanel {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 800;
    private static final int HALF_PANEL_WIDTH = (WIDTH / 2) - 10;
    private static final Color RIGHT_PANEL_COLOR = Color.LIGHT_GRAY;

    private JPanel masterContainer;
    private CardLayout cl = new CardLayout();

    private PokemonTeamPlannerWindow pokemonTeamPlannerWindow;

    private JPanel leftPanel;
    private JPanel rightPanel;

    private JPanel masterPanel;
    private DefaultListModel contents;
    private JList jlist;
    private JScrollPane scrollPane;
    private JLabel instructions;
    private JPanel buttonPanel;

    private PokemonTeamCollection collection;

    private JButton viewButton;
    private JButton deleteButton;
    private JButton analyzeButton;
    private JButton backButton;

    // MODIFIES: this
    // EFFECTS : initializes and brings together all subcomponents in the viewing panel
    //           to create an instance of the view team screen
    public ViewTeamScreen(PokemonTeamPlannerWindow pokemonTeamPlannerWindow, PokemonTeamCollection collection) {

        this.pokemonTeamPlannerWindow = pokemonTeamPlannerWindow;

        initializeKeyStructures(collection);


        contents = new DefaultListModel();

        updateContents();

        jlist = new JList(contents);
        jlist.setFont(DEFAULT_FONT);
        scrollPane = new JScrollPane(jlist);
        scrollPane.setPreferredSize(new Dimension(HALF_PANEL_WIDTH, HEIGHT * 3 / 5));

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(HALF_PANEL_WIDTH - 20, 150));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));

        configureAndLoadButtons();

        instructions = new JLabel("select ONE team to operate on, then a button");
        instructions.setFont(DEFAULT_FONT);
        instructions.setPreferredSize(new Dimension(HALF_PANEL_WIDTH - 10, 50));
        instructions.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        configureLeftPanel();
        configureRightPanel();
        addLeftAndRightPanels();
        addMasterPanel();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS : adds the master panel to the master container panel
    private void addMasterPanel() {
        masterContainer.add(masterPanel, "main");
    }

    // MODIFIES: this
    // EFFECTS : adds left and right panels into the master panel
    private void addLeftAndRightPanels() {
        masterPanel.add(leftPanel);
        masterPanel.add(rightPanel);
    }

    // MODIFIES: this
    // EFFECTS : initializes the right-hand panel in master panel
    private void configureRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(HALF_PANEL_WIDTH, HEIGHT));
        rightPanel.setBackground(RIGHT_PANEL_COLOR);
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0, 0));
    }

    // MODIFIES: this
    // EFFECTS : initializes the left-hand panel in master panel
    private void configureLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(HALF_PANEL_WIDTH, HEIGHT));
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.add(scrollPane);
        leftPanel.add(instructions);
        leftPanel.add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS : initializes the master container, master panel, and the pokemon team collection
    private void initializeKeyStructures(PokemonTeamCollection collection) {
        masterContainer = new JPanel();
        masterContainer.setLayout(cl);

        masterPanel = new JPanel();

        this.collection = collection;
    }

    // EFFECTS : returns the master container
    public JPanel getMasterContainer() {
        return masterContainer;
    }

    // MODIFIES: this
    // EFFECTS : Four buttons are created, linked with unique commands, and added to the buttonPanel:
    //           - view team button
    //           - delete team button
    //           - analyze team button
    //           - return to main menu button
    private void configureAndLoadButtons() {
        viewButton = new JButton("View team");
        viewButton.setFont(DEFAULT_FONT);
        deleteButton = new JButton("Delete team");
        deleteButton.setFont(DEFAULT_FONT);
        analyzeButton = new JButton("Analyze team");
        analyzeButton.setFont(DEFAULT_FONT);
        backButton = new JButton("Main menu");
        backButton.setFont(DEFAULT_FONT);

        addButtonFunctionality();

        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(analyzeButton);
        buttonPanel.add(backButton);
    }

    // MODIFIES: this
    // EFFECTS : sets the commands for each of the four buttons in the view team screen
    private void addButtonFunctionality() {

        setViewButtonCommand();
        setAnalyzeButtonCommand();
        setDeleteButtonCommand();
        setBackButtonCommand();
    }

    // MODIFIES: this
    // EFFECTS : configures back button so users can return to the home screen;
    //           any content in the right panel is also erased when this is clicked
    private void setBackButtonCommand() {
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                pokemonTeamPlannerWindow.forceReturnHomePanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS : configures delete button so users can remove teams from their collection;
    //           collection is updated in PokemonTeamPlannerWindow class to be in sync
    private void setDeleteButtonCommand() {
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!contents.isEmpty()) {
                    String teamName = updateContentsAndGetSelectedTeamName();
                    contents.remove(jlist.getSelectedIndex());
                    PokemonTeam team = findTeam(teamName);
                    collection.deleteTeam(team);
                    pokemonTeamPlannerWindow.updateCollectionAfterDeletion(collection);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS : configures analyze button so users can see what types a particular
    //           team in their collection is strong against
    private void setAnalyzeButtonCommand() {
        analyzeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                rightPanel.updateUI();
                String teamName = updateContentsAndGetSelectedTeamName();
                PokemonTeam team = findTeam(teamName);
                displayTeamStrengths(team);

                rightPanel.setBackground(RIGHT_PANEL_COLOR);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS : looks through the types that team is strong against, filter out
    //           duplicates, and adds the type images to the right panel
    private void displayTeamStrengths(PokemonTeam team) {

        List<Types> types = new ArrayList<>();

        for (int i = 0; i < team.teamSize(); i++) {
            Types firstType = team.getPokemon(i).getFirstType().getType();
            Types secondType = team.getPokemon(i).getSecondType().getType();
            if (!types.contains(firstType)) {
                types.add(firstType);
            }
            if (!types.contains(secondType) && !secondType.equals(Types.NONE)) {
                types.add(secondType);
            }
        }

        List<String> goodMatchUps = new ArrayList<>();

        for (Types t : types) {
            for (String s : t.typesStrongAgainst()) {
                if (!goodMatchUps.contains(s)) {
                    goodMatchUps.add(s);
                }
            }
        }

        rightPanel.add(generateTypeSprites(goodMatchUps));
        rightPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS : returns a panel that holds the images of all types
    //           listed in goodMatchUps
    private JPanel generateTypeSprites(List<String> goodMatchUps) {

        JPanel typePanel = new JPanel();

        for (String s : goodMatchUps) {
            typePanel.setPreferredSize(new Dimension(HALF_PANEL_WIDTH, HEIGHT));
            typePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            typePanel.setBackground(RIGHT_PANEL_COLOR);

            try {
                Image image = ImageIO.read(new File("./data/" + s + ".png"));
                image = image.getScaledInstance(96, 96, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                typePanel.add(imageLabel);
            } catch (IOException e) {
                //pass
            }
        }
        return typePanel;
    }

    // MODIFIES: this
    // EFFECTS : configures view button so users can select and see the contents of a team
    //           in their collection. Image of pokemon is unique only if the name of the pokemon
    //           corresponds to one of the image files in ./data/ folder. Otherwise, a default
    //           image is used instead. This was done since there are over 600 image files,
    //           and each file had to be renamed to the pokemon name.
    private void setViewButtonCommand() {
        viewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rightPanel.removeAll();
                    rightPanel.updateUI();
                    String teamName = updateContentsAndGetSelectedTeamName();
                    PokemonTeam team = findTeam(teamName);
                    JPanel sprites = generateTeamSprites(team);
                    sprites.setLayout(new FlowLayout(FlowLayout.CENTER, 26, 10));
                    rightPanel.add(sprites);
                    rightPanel.updateUI();
                } catch (IOException ioe) {
                    rightPanel.setBackground(Color.RED);
                }

            }
        });
    }

    // EFFECTS : returns a panel consisting of all pokemon sprites (name + image + type images)
    //           in the selected team. Throws IOException if default pokemon image cannot
    //           be accessed, or if the filler image for the NONE type cannot be accessed.
    private JPanel generateTeamSprites(PokemonTeam team) throws IOException {

        JPanel returnPanel = new JPanel();
        returnPanel.setPreferredSize(new Dimension(HALF_PANEL_WIDTH, HEIGHT));
        returnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 5));
        returnPanel.setBackground(RIGHT_PANEL_COLOR);

        for (int i = 0; i < team.teamSize(); i++) {
            generatePokemonSprite(team.getPokemon(i), returnPanel);

        }

        return returnPanel;
    }

    // MODIFIES: returnPanel
    // EFFECTS : adds the pokemon sprite (name + image + type images) of one pokemon
    //           specified. Throws IOException if default pokemon image cannot
    //           be accessed, or if the filler image for the NONE type cannot be accessed.
    private void generatePokemonSprite(Pokemon pokemon, JPanel returnPanel) throws IOException {

        JLabel pokemonName = new JLabel(pokemon.getName());
        String name = pokemon.getName();

        JLabel pokemonPicLabel;

        String firstType = pokemon.getFirstType().getTypeName();
        String secondType = pokemon.getSecondType().getTypeName();

        pokemonPicLabel = loadPokemonImages(name);

        loadNameAndPokemonPicture(returnPanel, pokemonName, pokemonPicLabel);

        JPanel typePanel = new JPanel();
        typePanel.setBackground(RIGHT_PANEL_COLOR);

        typePanel.add(getTypeSprite(firstType));

        if (!secondType.equals("NONE")) {
            typePanel.add(getTypeSprite(secondType));
        } else {
            handleNoneSecondTypeSprite(typePanel);
        }

        returnPanel.add(typePanel);

    }

    // EFFECTS : returns a JLabel with the image of the pokemon attached to it.
    //           Throws IOException if the default pokemon image cannot be accessed.
    private JLabel loadPokemonImages(String name) throws IOException {
        Image pokemonPic;
        JLabel pokemonPicLabel;
        try {
            File check = new File("./data/" + name.toLowerCase() + ".png");
            pokemonPic = ImageIO.read(check);
            pokemonPicLabel = new JLabel(new ImageIcon(pokemonPic));
        } catch (NullPointerException | IOException e) {
            pokemonPic = ImageIO.read(new File("./data/default.png"));
            pokemonPicLabel = new JLabel(new ImageIcon(pokemonPic));
        }
        return pokemonPicLabel;
    }

    // MODIFIES: returnPanel
    // EFFECTS : loads the pokemon name and picture as part of the sprite
    private void loadNameAndPokemonPicture(JPanel returnPanel, JLabel pokemonName, JLabel pokemonPicLabel) {
        returnPanel.add(pokemonName);
        returnPanel.add(pokemonPicLabel);
    }

    // MODIFIES: typePanel
    // EFFECTS : adds an empty type image in lieu of images for pokemon
    //           with no second type (ie. second type is NONE). Throws
    //           IOException if empty type image cannot be accessed.
    private void handleNoneSecondTypeSprite(JPanel typePanel) throws IOException {
        Image typeImage = ImageIO.read(new File("./data/EMPTY_TYPE.png"));
        typeImage = typeImage.getScaledInstance(96, 96, Image.SCALE_SMOOTH);
        JLabel typePicLabel = new JLabel(new ImageIcon(typeImage));
        typePanel.add(typePicLabel);
    }

    // EFFECTS : attaches the corresponding pokemon type image to a JLabel and return it;
    //           images are scaled to match the dimensions of the pokemon picture image file
    private JLabel getTypeSprite(String pokemonType) {

        JLabel typePicLabel = new JLabel();

        try {
            Image typeImage = ImageIO.read(new File("./data/" + pokemonType + ".png"));
            typeImage = typeImage.getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            typePicLabel = new JLabel(new ImageIcon(typeImage));
        } catch (IOException ioe) {
            Image typeImage = ImageIO.read(new File("./data/EMPTY_TYPE.png"));
            typeImage = typeImage.getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            typePicLabel = new JLabel(new ImageIcon(typeImage));
        } finally {
            return typePicLabel;
        }
    }

    // EFFECTS : returns the teamName that was selected in the view teams screen
    private String updateContentsAndGetSelectedTeamName() {
        String teamName = (String) jlist.getSelectedValue();
        return teamName;
    }

    // EFFECTS : finds the team in the collection with
    //           name teamName, and returns the team if found.
    //           Otherwise, return null, possibly accompanied
    //           by an error message if TeamNotFoundException was caught.
    private PokemonTeam findTeam(String teamName) {
        for (int i = 0; i < collection.sizeCollection(); i++) {
            try {
                PokemonTeam team = collection.getTeam(i);
                if (team.getTeamName().equals(teamName)) {
                    return team;
                }
            } catch (TeamNotFoundException e) {
                System.out.println("Unexpected TeamNotFoundException from findTeam().");
                break;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS : update this.collection to be in sync with incoming collection;
    //           update the contents to be loaded in the scroll-down list as well
    public void updateCollection(PokemonTeamCollection collection) {
        this.collection = collection;

        updateContents();
    }

    // MODIFIES: this
    // EFFECTS : after resetting the list of team names, re-load
    //           the names of teams in the collection. Prints
    //           an error message if TeamNotFoundException was caught.
    private void updateContents() {
        contents.clear();
        try {
            for (int i = 0; i < this.collection.sizeCollection(); i++) {
                PokemonTeam team = collection.getTeam(i);
                contents.addElement(team.getTeamName());
            }
        } catch (TeamNotFoundException e) {
            System.out.println("Unexpected TeamNotFoundException from updateContents().");
        }
    }
}
