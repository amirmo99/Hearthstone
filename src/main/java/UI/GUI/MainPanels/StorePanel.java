package UI.GUI.MainPanels;

import abstracts.MyButtonAction;
import UI.GUI.MainFrame;
import UI.GUI.MyPanel;
import UI.GUI.SubPanel.BackAndExitPanel;
import UI.GUI.SubPanel.ModelViewerScroll;
import UI.GUI.SubPanel.InfoPanel;
import UI.PlayerMapper;
import enums.LogType;
import models.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StorePanel extends MyPanel {

    private static int activeTab;
    private JTabbedPane buyOrSell;
    private ModelViewerScroll buyScroll;
    private ModelViewerScroll sellScroll;
    private BackAndExitPanel control;
    private InfoPanel infoPanel;


    public StorePanel() {
        super();
        drawBackground(pathConfigs.getHomeBGImage());
        start();
    }

    private void start() {
        createTitle("Store");
        init();
        configureElements();
        placeComponents();
    }


    private void init() {
        buyScroll = new ModelViewerScroll(new BuyAction(), false, false, true, false);
        sellScroll = new ModelViewerScroll(new SellAction(), false, false, true, false);

        buyOrSell = new JTabbedPane();
        control = new BackAndExitPanel();
        infoPanel = new InfoPanel(true);
    }

    private void updatePanelCards() {
        sellScroll.setModels(PlayerMapper.getInstance().getPlayerSellableCards());
        buyScroll.setModels(PlayerMapper.getInstance().getPlayerUnownedCards());
    }

    private void configureElements() {
        // ******* Move Later
        updatePanelCards();

        configureTabbedPane();
        // *******************
    }

    private void configureTabbedPane() {
        buyOrSell.addTab("Buy", buyScroll);
        buyOrSell.addTab("Sell", sellScroll);
        buyOrSell.setSelectedIndex(activeTab);
        buyOrSell.setOpaque(false);

        buyOrSell.addChangeListener(changeEvent -> activeTab = buyOrSell.getSelectedIndex());
    }

    private void placeComponents() {
        setLayout(new BorderLayout());

        add(buyOrSell, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.NORTH);
        add(control, BorderLayout.SOUTH);
    }

    private static class BuyAction extends MyButtonAction {

        @Override
        public MyButtonAction cloned() {
            return new BuyAction();
        }

        @Override
        public String getButtonLabel() {
            return "Buy For : " + ((Card) getModel()).getCardCost();
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
//            PlayerLogger.writeLog("Buy attempt", "card : " + getModel().getName());
            PlayerMapper.getInstance().getLogger().writeLog(LogType.BUTTON_CLICK, "Buy card -> " + getModel().getName());
            PlayerMapper.getInstance().buyAttempt((Card) getModel());
            MainFrame.getInstance().updatePanels();
        }

    }

    private static class SellAction extends MyButtonAction {

        @Override
        public MyButtonAction cloned() {
            return new SellAction();
        }

        @Override
        public String getButtonLabel() {
            return "Sell for : " + ((Card) getModel()).getCardCost();
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
//            PlayerLogger.writeLog("Sell Button Clicked", "card : " + getModel().getName());
            PlayerMapper.getInstance().getLogger().writeLog(LogType.BUTTON_CLICK, "Sell card -> " + getModel().getName());
            PlayerMapper.getInstance().sellAttempt((Card) getModel());
            MainFrame.getInstance().updatePanels();
        }
    }
}
