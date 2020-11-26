package UI.GUI.MainPanels.MainGame;

import UI.GUI.MainFrame;
import UI.GUI.SubPanel.ModelViewerScroll;
import models.cards.Mission;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MissionsFrame extends JFrame {

    private ModelViewerScroll modelViewerScroll;
    private JPanel noMissionsPanel;

    public MissionsFrame(String title) {
        super(title);

        init();
    }

    private void init() {
        this.modelViewerScroll = new ModelViewerScroll(null, true, true, false, true);
        this.noMissionsPanel = new JPanel();
        makeEmptyPanel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void makeEmptyPanel() {
        noMissionsPanel.setLayout(new GridBagLayout());

        JLabel label = new JLabel("There is no active missions.");
        noMissionsPanel.add(label);
    }

    public void showMissions(List<Mission> missions) {
        if (missions.size() > 0) {
            modelViewerScroll.setModels(missions);
            setContentPane(modelViewerScroll);
        } else {
            setContentPane(noMissionsPanel);
        }
        setVisible(true);
    }
}
