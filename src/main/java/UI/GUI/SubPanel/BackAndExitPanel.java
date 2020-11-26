package UI.GUI.SubPanel;

import UI.GUI.MyPanel;
import UI.PlayerMapper;
import enums.LogType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackAndExitPanel extends MyPanel {

    private JButton back;
    private JButton exit;

    public BackAndExitPanel() {
        super();
        init();
        configureElements();
        placeComponents();
    }

    private void init() {
        exit = new JButton("Exit");
        back = new JButton("Back");
    }

    public void configureElements() {
        Dimension dim = new Dimension(panelsConfigs.getLoginButtonsWidth(), panelsConfigs.getHomeButtonsHeight());
        configureButton(exit, dim, panelsConfigs.getHomeFontSize(), new MyButtonAction());
        configureButton(back, dim, panelsConfigs.getHomeFontSize(), new MyButtonAction());
    }

    private void placeComponents() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;

        //Column 1
        gc.weightx = 1;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        add(back, gc);
        //Column 2
        gc.weightx = 1;
        gc.gridx++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(exit, gc);
    }

    private class MyButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton button = (JButton) actionEvent.getSource();

            if (button == exit) {
//                PlayerLogger.writeLog("Exit Button Clicked");
                PlayerMapper.getInstance().getLogger().writeLog(LogType.BUTTON_CLICK, "Exit Button");
                PlayerMapper.getInstance().signPlayerOut();
                System.exit(0);
            } else if (button == back) {
//                PlayerLogger.writeLog("Back Button Clicked");
                PlayerMapper.getInstance().getLogger().writeLog(LogType.BUTTON_CLICK, "Back Button");
                changePanel("home");
            }
        }
    }

    public JButton getBack() {
        return back;
    }
}
