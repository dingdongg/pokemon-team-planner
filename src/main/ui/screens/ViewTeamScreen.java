package ui.screens;

import model.Pokemon;
import model.PokemonTeam;
import model.PokemonTeamCollection;
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

    private void addMasterPanel() {
        masterContainer.add(masterPanel, "main");
    }

    private void addLeftAndRightPanels() {
        masterPanel.add(leftPanel);
        masterPanel.add(rightPanel);
    }

    private void configureRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(HALF_PANEL_WIDTH, HEIGHT));
        rightPanel.setBackground(RIGHT_PANEL_COLOR);
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0, 0));
    }

    private void configureLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(HALF_PANEL_WIDTH, HEIGHT));
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.add(scrollPane);
        leftPanel.add(instructions);
        leftPanel.add(buttonPanel);
    }

    private void initializeKeyStructures(PokemonTeamCollection collection) {
        masterContainer = new JPanel();
        masterContainer.setLayout(cl);

        masterPanel = new JPanel();

        this.collection = collection;
    }

    public JPanel getMasterContainer() {
        return masterContainer;
    }

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

    private void addButtonFunctionality() {

        setViewButtonCommand();
        setAnalyzeButtonCommand();
        setDeleteButtonCommand();
        setBackButtonCommand();
    }

    private void setBackButtonCommand() {
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.removeAll();
                pokemonTeamPlannerWindow.forceReturnHomePanel();
            }
        });
    }

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
                // TODO
            }
        });
    }

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
                    sprites.setLayout(new FlowLayout(FlowLayout.CENTER, 27, 20));
                    rightPanel.add(sprites);
                    rightPanel.updateUI();
                } catch (IOException ioe) {
                    rightPanel.setBackground(Color.RED);
                }

            }
        });
    }

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

    private void generatePokemonSprite(Pokemon pokemon, JPanel returnPanel) throws IOException {

        JLabel pokemonName = new JLabel(pokemon.getName());
        String name = pokemon.getName();

        Image pokemonPic;
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

    private void loadNameAndPokemonPicture(JPanel returnPanel, JLabel pokemonName, JLabel pokemonPicLabel) {
        returnPanel.add(pokemonName);
        returnPanel.add(pokemonPicLabel);
    }

    private void handleNoneSecondTypeSprite(JPanel typePanel) throws IOException {
        Image typeImage = ImageIO.read(new File("./data/EMPTY_TYPE.png"));
        typeImage = typeImage.getScaledInstance(96, 96, Image.SCALE_SMOOTH);
        JLabel typePicLabel = new JLabel(new ImageIcon(typeImage));
        typePanel.add(typePicLabel);
    }

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

    private String updateContentsAndGetSelectedTeamName() {
        String teamName = (String) jlist.getSelectedValue();
        return teamName;
    }

    private PokemonTeam findTeam(String teamName) {
        for (int i = 0; i < collection.sizeCollection(); i++) {
            PokemonTeam team = collection.getTeam(i);
            if (team.getTeamName().equals(teamName)) {
                return team;
            }
        }
        return null;
    }


    public void updateCollection(PokemonTeamCollection collection) {
        this.collection = collection;

        updateContents();
    }

    private void updateContents() {
        contents.clear();
        for (int i = 0; i < this.collection.sizeCollection(); i++) {
            PokemonTeam team = collection.getTeam(i);
            contents.addElement(team.getTeamName());
        }
    }
}
