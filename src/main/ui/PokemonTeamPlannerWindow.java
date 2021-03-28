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

        setUpMasterPanel();


        // goes into view collection screen
        add(masterPanel);

        setVisible(true);

    }

    public void forceReturnHomePanel() {
        cl.show(masterPanel,"home");
    }

    private void initialSetUp() {
        Types.initializeTypeConstants();
        this.homeScreenButtons = new ArrayList<>();
        loadButtons();
        loadHeading();
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
            homeScreenButton.setFont(DEFAULT_FONT);
            homePanel.add(homeScreenButton);
        }
    }

    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setPreferredSize(new Dimension(800, 600));
        homePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
        homePanel.setBackground(Color.WHITE);
    }

    private void initializeMasterPanel() {
        masterPanel.add(homePanel, "home");
        masterPanel.add(newTeamPanel, "new !");
        masterPanel.add(viewPanel, "view");

        cl.show(masterPanel, "home");
    }

    private void createMainWindow() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(this);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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

    private void loadHeading() {

        subheading.setFont(DEFAULT_FONT);
        subheadingPanel.add(subheading);
        subheadingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, WIDTH / 2, 100));
        subheadingPanel.setBackground(Color.WHITE);
        homePanel.add(subheadingPanel);
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
            newTeamScreen.updateCollection(collection);
            viewTeamScreen.updateCollection(collection);
        } catch (IOException e) {
            System.out.println("Oops! Something's wrong and we can't retrieve collection from " + JSON_STORE);
        }
    }

    public void updateCollectionAfterAdding(PokemonTeamCollection collection) {
        this.collection = collection;
        viewTeamScreen.updateCollection(this.collection);
    }

    public void updateCollectionAfterDeletion(PokemonTeamCollection collection) {
        this.collection = collection;
    }

    public JLabel getSubheading() {
        return this.subheading;
    }
}
