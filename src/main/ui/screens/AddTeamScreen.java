package ui.screens;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AddTeamScreen extends JPanel {

    private JPanel masterPanel;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JPanel fourthPanel;
    private JPanel fifthPanel;
    private JPanel sixthPanel;
    private List<JPanel> subPanels;

    public AddTeamScreen(JPanel firstPanel) {

        subPanels = new ArrayList<>();
        masterPanel = new JPanel();
        this.firstPanel = firstPanel;
        secondPanel = new JPanel();
        thirdPanel = new JPanel();
        fourthPanel = new JPanel();
        fifthPanel = new JPanel();
        sixthPanel = new JPanel();

        initializeSubPanels();
        initializeMasterPanel();

    }

    private void initializeSubPanels() {
        subPanels.add(firstPanel);
        subPanels.add(secondPanel);
        subPanels.add(thirdPanel);
        subPanels.add(fourthPanel);
        subPanels.add(fifthPanel);
        subPanels.add(sixthPanel);
    }

    public JPanel getMasterPanel() {

        return masterPanel;
    }

    private void initializeMasterPanel() {
        masterPanel.add(firstPanel);
        masterPanel.add(secondPanel);
        masterPanel.add(thirdPanel);
        masterPanel.add(fourthPanel);
        masterPanel.add(fifthPanel);
        masterPanel.add(sixthPanel);
    }




}
// ALL COMPONENTS REQUIRED:
// - user-text panels (2)
// - text labels (2)
// - pokemon type buttons (18)
// - next/end buttons (2)