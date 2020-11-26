package UI.GUI.SubPanel;

import UI.GUI.MyPanel;
import UI.PlayerMapper;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends MyPanel {

    private JTextField playerName;
    private JTextField playerGems;
    private final boolean detailed;

    public InfoPanel(boolean detailed) {
        super();
        this.detailed = detailed;
        init();
        configureElements();
        placeComponents();
    }

    private void init() {
        if (detailed)
            playerName = new JTextField(PlayerMapper.getInstance().getPlayerName());
        else
            playerName = new JTextField("Welcome, " + PlayerMapper.getInstance().getPlayerName());

        playerGems = new JTextField("Gems : " + PlayerMapper.getInstance().getPlayerGems());
    }

    private void configureElements() {
        setOpaque(false);
        configureField(playerGems, 30, false, panelsConfigs.getLabelThemeColor());
        configureField(playerName, 30, false, panelsConfigs.getLabelThemeColor());

    }

    private void placeComponents() {
        if (detailed) {
            setLayout(new GridBagLayout());
            GridBagConstraints gc = new GridBagConstraints();
            gc.gridy = 0;
            gc.fill = GridBagConstraints.NONE;
            //Column 1
            gc.weightx = 1;
            gc.gridx = 0;
            gc.anchor = GridBagConstraints.LINE_START;
            add(playerName, gc);
            //Column 2
            gc.weightx = 1;
            gc.gridx++;
            gc.anchor = GridBagConstraints.LINE_END;
            add(playerGems, gc);
        } else {
            add(playerName);
        }
    }
}
