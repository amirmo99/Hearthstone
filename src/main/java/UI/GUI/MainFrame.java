package UI.GUI;

import UI.GUI.MainPanels.*;
import UI.GUI.MainPanels.MainGame.GameBoardPanel;
import UI.PlayerMapper;
import configs.GameGraphicConstants;
import configs.PanelsConfigs;
import enums.LogType;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame mainFrame;
    private String activePanel;
    private PanelsConfigs panelsConfigs;

    private MainFrame() {
        super("HearthStone");

        init();
        createBackground();
        // SET LOGIN AS FIRST PANEL
        setActivePanel("login");
    }

    private void createBackground() {

    }

    private void init() {
        GameGraphicConstants constants = new GameGraphicConstants();

        UIManager.put("TabbedPane.contentOpaque", false);
        UIManager.put("ScrollPane.contentOpaque", false);

        panelsConfigs = new PanelsConfigs();
        activePanel = "login";

        setLayout(new BorderLayout());
        setSize((int)constants.getGameWidth(),(int) constants.getGameHeight());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static MainFrame getInstance() {
        if (mainFrame == null)
            mainFrame = new MainFrame();
        return mainFrame;
    }

    public void showError(String error) {
        JOptionPane.showConfirmDialog(this, error, "Error", JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE);
    }

    protected void setActivePanel(String panelName) {
        switch (panelName.toLowerCase()) {
            case "login":
                setContentPane(new LoginPanel());
                // ***********
//                PlayerMapper.getInstance().loginRequest("player1", "1234");
//                setActivePanel("home");
                // ***********
                break;
            case "home":
                setContentPane(new HomePanel());
                break;
            case "play":
                setContentPane(GameBoardPanel.getInstance(true));
                break;
            case "store":
                setContentPane(new StorePanel());
                break;
            case "collections":
                setContentPane(new CollectionsPanel());
                break;
            case "status":
                setContentPane(new StatusPanel());
                break;
        }
        if (!activePanel.equals(panelName)) {
//            PlayerLogger.writeLog("Room Changed", panelName);
            PlayerMapper.getInstance().getLogger().writeLog(LogType.ROOM_CHANGE, panelName);
        }
        activePanel = panelName;
        setVisible(true);
    }


    public void updatePanels() {
        setActivePanel(activePanel);
    }

}
