package ui;

import model.PokemonTeamCollection;
import model.types.Types;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.screens.AddTeamScreen;
import ui.screens.ViewTeamScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// models the GUI version of the Pokemon Team Planner application
public class PokemonTeamPlannerWindow extends JFrame {

    public static final int FONT_SIZE = 20;
    public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, FONT_SIZE);
    private static final int WIDTH = 900;
    private static final int HEIGHT = 800;

    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private PokemonTeamCollection collection = new PokemonTeamCollection();

    private List<JButton> homeScreenButtons;
    private JPanel masterPanel = new JPanel();
    private JPanel homePanel;

    private AddTeamScreen newTeamScreen;
    private JPanel newTeamPanel;

    private ViewTeamScreen viewTeamScreen;
    private JPanel viewPanel;

    private JPanel subheadingPanel = new JPanel();
    private JLabel subheading = new JLabel("Welcome to Pokemon Planner!!!");

    private CardLayout cl;

    // EFFECTS: starts up the program after initializing the necessary information
    public PokemonTeamPlannerWindow() {

        super("Pokemon Team Planner");
        // creates panel to which buttons will be added
        createHomePanel();
        newTeamScreen = new AddTeamScreen(this, collection);
        newTeamPanel = newTeamScreen.getContainer();
        viewTeamScreen = new ViewTeamScreen(this, collection);
        viewPanel = viewTeamScreen.getMasterContainer();

        // initial set up
        initialSetUp();

        // creates the home screen window
        createMainWindow();

        // add each button to panel
        loadHomeScreenButtons();

        // configures the master panel
        setUpMasterPanel();

        // goes into view collection screen
        add(masterPanel);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS : forcibly switches the view from one panel to this.homePanel.
    //           Used to transition out of the AddTeamScreen and ViewTeamScreens.
    public void forceReturnHomePanel() {
        cl.show(masterPanel,"home");
    }

    // MODIFIES: this
    // EFFECTS : initializes fields, pre-conditions, and GUI buttons into the main frame
    private void initialSetUp() {
        Types.initializeTypeConstants();
        this.homeScreenButtons = new ArrayList<>();
        loadButtons();
        loadHeading();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS : sets up the master panel, which has the ability to switch between
    //           different panels of the application
    private void setUpMasterPanel() {
        cl = new CardLayout();
        masterPanel.setLayout(cl);
        initializeMasterPanel();
    }

    // MODIFIES: this
    // EFFECTS : enlarges home screen buttons slightly and add them to the home screen panel
    private void loadHomeScreenButtons() {
        for (JButton homeScreenButton : homeScreenButtons) {
            homeScreenButton.setFont(DEFAULT_FONT);
            homePanel.add(homeScreenButton);
        }
    }

    // MODIFIES: this
    // EFFECTS : configures settings for homePanel, the home screen panel
    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setPreferredSize(new Dimension(800, 600));
        homePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        homePanel.setBackground(Color.WHITE);
    }

    // MODIFIES: this
    // EFFECTS : loads masterPanel with the home, add team, and view team panels;
    //           also initializes the home screen to be the first panel users see
    //           when the application is launched
    private void initializeMasterPanel() {
        masterPanel.add(homePanel, "home");
        masterPanel.add(newTeamPanel, "new !");
        masterPanel.add(viewPanel, "view");

        cl.show(masterPanel, "home");
    }

    // MODIFIES: this
    // EFFECTS : configures dimensions and properties of the main frame of this application
    private void createMainWindow() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(this);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS : generate and set commands for buttons that are added to the homePanel,
    //           which includes: adding a new team, viewing your team (within which you
    //           have the ability to view/delete/analyze specific teams), saving and loading.
    private void loadButtons() {

        JButton addTeam = new JButton("Create new team");
        JButton viewCollection = new JButton("View collection");
        JButton saveCollection = new JButton("Save this collection");
        JButton loadCollection = new JButton("Load previous collection");

        registerActionListener(addTeam, "new !");
        registerActionListener(viewCollection, "view");
        registerActionListener(saveCollection, "Saved collection to " + JSON_STORE + "!", true);
        registerActionListener(loadCollection, "Loaded collection from " + JSON_STORE + "!", false);

        homeScreenButtons.add(addTeam);
        homeScreenButtons.add(viewCollection);
        homeScreenButtons.add(saveCollection);
        homeScreenButtons.add(loadCollection);
    }

    // EFFECTS : makes the buttons so that they switch to the panel
    //           with the corresponding command key when clicked
    private void registerActionListener(JButton button, String s) {

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(masterPanel, s);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS : makes the save/load buttons so that they allow the user
    //           to save their collection, or load from a previously saved
    //           collection at any time.
    private void registerActionListener(JButton button, String text, boolean saveOrLoad) {

        if (saveOrLoad) {
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    saveCollection();
                    subheading.setText(text);
                    cl.show(masterPanel, "home");
                }
            });
        } else {
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    loadCollection();
                    subheading.setText(text);
                    cl.show(masterPanel, "home");
                }
            });
        }
    }

    // MODIFIES: this
    // EFFECTS : configures the subheading font and places it below the home screen buttons
    private void loadHeading() {

        subheading.setFont(DEFAULT_FONT);
        subheadingPanel.add(subheading);
        subheadingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, WIDTH / 2, 100));
        subheadingPanel.setBackground(Color.WHITE);
        homePanel.add(subheadingPanel);
    }

    // MODIFIES: this
    // EFFECTS : saves the current collection of teams to the address JSON_STORE
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

    // MODIFIES: this
    // EFFECTS : loads the collection of teams stored in JSON_STORE
    private void loadCollection() {
        try {
            collection = jsonReader.read();
            newTeamScreen.updateCollection(collection);
            viewTeamScreen.updateCollection(collection);
        } catch (IOException e) {
            System.out.println("Oops! Something's wrong and we can't retrieve collection from " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS : keeps the collection field up to date with every addition of a
    //           new team in the AddTeamScreen class; also updates the
    //           viewTeamScreen collection to stay consistent across all portion of GUI
    public void updateCollectionAfterAdding(PokemonTeamCollection collection) {
        this.collection = collection;
        viewTeamScreen.updateCollection(this.collection);
    }

    // MODIFIES: this
    // EFFECTS : keeps the collection field up to date with every removal of a
    //           pre-existing team;
    public void updateCollectionAfterDeletion(PokemonTeamCollection collection) {
        this.collection = collection;
    }

    // EFFECTS : returns the JLabel subheading
    public JLabel getSubheading() {
        return this.subheading;
    }
}
