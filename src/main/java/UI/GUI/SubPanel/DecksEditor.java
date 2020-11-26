package UI.GUI.SubPanel;

import UI.GUI.MyPanel;
import UI.PlayerMapper;
import enums.LogType;
import models.Heroes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecksEditor extends MyPanel {

    private JScrollPane scrollPane;
    private JTable table;
    private int selectedIndex;

    private JButton select;
    private JButton addDeck;
    private JButton removeDeck;
    private JButton reformatDeck;

    private JButton removeCard;
    private JButton back;
    private JButton activate;
    private String status;

    public DecksEditor() {
        super();
        init();
        createTitle();
        updateTable();
    }

    private void init() {
        table = new JTable();
        scrollPane = new JScrollPane();

        select = new JButton("Select");
        addDeck = new JButton("New");
        removeDeck = new JButton("Remove");
        reformatDeck = new JButton("Reformat");

        removeCard = new JButton("Remove");
        back = new JButton("Back");
        activate = new JButton("Set Active");

        status = "Show Decks";
        configureElements();
    }

    private void configureElements() {
        setOpaque(false);
        table.setFont(getFont().deriveFont(Font.ITALIC, panelsConfigs.getTableFontSize()));
        table.setRowHeight(panelsConfigs.getTableRowHeight());

        JViewport viewport = new JViewport();
        viewport.setOpaque(false);
        viewport.setView(table);
        scrollPane.setPreferredSize(new Dimension(350, 500));
        scrollPane.setViewport(viewport);
        scrollPane.setOpaque(false);

        configureButtons();
    }

    private void createTitle() {
        Border border = BorderFactory.createEmptyBorder(10, 0, 0, 0);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Deck Viewer");
        titledBorder.setTitleColor(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(border, titledBorder));
    }

    public void configureButtons() {
        int fontSize = 15;
        MyButtonAction action = new MyButtonAction();
        Dimension dimension = new Dimension(-1, 40);

        configureButton(select, dimension, fontSize, action);
        configureButton(addDeck, dimension, fontSize, action);
        configureButton(removeDeck, dimension, fontSize, action);
        configureButton(removeCard, dimension, fontSize, action);
        configureButton(back, dimension, fontSize, action);
        configureButton(activate, dimension, fontSize, action);
        configureButton(reformatDeck, dimension, fontSize, action);
    }

    private void showDecksTableButtons() {

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // Row 1
        gc.weightx = 1;
        gc.weighty = 2;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 3;
        add(scrollPane, gc);

        // Row 2
        gc.gridwidth = 2;
        gc.gridy++;
        gc.weighty = 0.2;
        add(activate, gc);

        gc.gridwidth = 1;
        gc.gridx = 2;
        add(reformatDeck, gc);

        // Row 3
        gc.gridy++;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.weightx = 1;
        gc.weighty = 0.2;
        gc.gridx = 0;
        add(removeDeck, gc);

        gc.gridx = 1;
        add(select, gc);

        gc.gridx = 2;
        add(addDeck, gc);
    }

    private void showCardsTableButtons() {

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        // Row 1
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.weightx = 1;
        gc.weighty = 2;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        add(scrollPane, gc);

        // Row 2
        gc.gridy++;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.weightx = 1;
        gc.weighty = 0.2;
        gc.gridx = 0;
        gc.gridwidth = 1;
        add(back, gc);

        gc.gridx = 1;
        add(removeCard, gc);

    }

    public void updateTable() {
        removeAll();
        revalidate();
        repaint();
        if (status.equals("Show Decks")) {
            table.setModel(new DeckTable());
            showDecksTableButtons();
        } else if (status.equals("Show Cards In Deck")) {
            table.setModel(new CardTable());
            showCardsTableButtons();
        }

        table.updateUI();
    }

    class MyButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton button = (JButton) actionEvent.getSource();
            selectedIndex = table.getSelectedRow();
//            PlayerLogger.writeLog("Button Clicked", button.getText());
            PlayerMapper.getInstance().getLogger().writeLog(LogType.BUTTON_CLICK, button.getText());

            if (button == back) {
                backAction();
            } else if (button == select) {
                selectDeck();
            } else if (button == addDeck) {
                createNewDeck();
            } else if (button == removeDeck) {
                removeSelectedDeck();
            } else if (button == removeCard) {
                removeSelectedCard();
            } else if (button == activate) {
                activateDeck();
            } else if (button == reformatDeck) {
                reformatSelectedDeck();
            }
            updateTable();
        }
    }

    private void reformatSelectedDeck() {
        if (selectedIndex != -1) {
            DeckEntry entries = new DeckEntry();
            int result = JOptionPane.showConfirmDialog(null, entries,
                    "Change details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                PlayerMapper.getInstance().changeDeckDetails(entries.getDeckName(), entries.getHeroName(), selectedIndex);

            } else {
                PlayerMapper.getInstance().getLogger().writeLog(LogType.BUTTON_CLICK, "Cancel");
//                PlayerLogger.writeLog("Cancel Button clicked");
            }
        }
    }

    private void backAction() {
        status = "Show Decks";
        PlayerMapper.getInstance().resetSelectedDeck();
    }

    private void selectDeck() {
        if (selectedIndex != -1) {
//            PlayerLogger.writeLog(" deck selected", "Deck Index : " + selectedIndex);
            PlayerMapper.getInstance().getLogger().writeLog(LogType.BUTTON_CLICK, "Select Deck -> " + "Deck Index : " + selectedIndex);
            status = "Show Cards In Deck";
            PlayerMapper.getInstance().setSelectedDeck(selectedIndex);
        }
    }

    private void removeSelectedDeck() {
        if (selectedIndex != -1) {
//            PlayerLogger.writeLog("Deck Removed", "Deck Index : " + selectedIndex);
            PlayerMapper.getInstance().getLogger().writeLog(LogType.BUTTON_CLICK,  "Deck Remove -> Deck Index : " + selectedIndex);
            PlayerMapper.getInstance().removeDeck(selectedIndex);
        }
    }

    private void removeSelectedCard() {
        if (selectedIndex != -1) {
            PlayerMapper.getInstance().removeCardFromDeck(selectedIndex);
        }
    }

    private void activateDeck() {
        if (selectedIndex != -1) {
            PlayerMapper.getInstance().activateDeck(selectedIndex);
        }
    }

    private void createNewDeck() {
        DeckEntry inputPanel = new DeckEntry();

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Create a New Deck", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            PlayerMapper.getInstance().createNewDeck(inputPanel.getDeckName(), inputPanel.getHeroName());
        }

    }


    static class DeckTable implements TableModel {

        @Override
        public int getRowCount() {
            return PlayerMapper.getInstance().playerDecks().size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int i) {
            switch (i) {
                case 0:
                    return "Deck Name";
                case 1:
                    return "Hero";
                case 2:
                    return "Status";
                default:
                    return null;
            }

        }

        @Override
        public Class<?> getColumnClass(int i) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            if (i1 == 0)
                return PlayerMapper.getInstance().getDeck(i).getName();
            else if (i1 == 1)
                return PlayerMapper.getInstance().getDeck(i).getHeroName();
            else
                return PlayerMapper.getInstance().isDeckActive(i);
        }

        @Override
        public void setValueAt(Object o, int i, int i1) {

        }

        @Override
        public void addTableModelListener(TableModelListener tableModelListener) {

        }

        @Override
        public void removeTableModelListener(TableModelListener tableModelListener) {

        }
    }

    class CardTable implements TableModel {

        @Override
        public int getRowCount() {
            return PlayerMapper.getInstance().getSelectedDeck().getCards().size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int i) {
            if (i == 0)
                return "Card Name";
            else if (i == 1)
                return "Class";
            else
                return null;
        }

        @Override
        public Class<?> getColumnClass(int i) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            if (i1 == 0) {
                return PlayerMapper.getInstance().getSelectedDeck().getCards().get(i).getName();
            } else if (i1 == 1) {
                return PlayerMapper.getInstance().getSelectedDeck().getCards().get(i).getCardClass().toString();
            } else
                return null;
        }

        @Override
        public void setValueAt(Object o, int i, int i1) {

        }

        @Override
        public void addTableModelListener(TableModelListener tableModelListener) {

        }

        @Override
        public void removeTableModelListener(TableModelListener tableModelListener) {

        }
    }

}

class DeckEntry extends JPanel {
    private JLabel nameLabel;
    private JLabel heroLabel;
    private JComboBox<String> heroesNames;
    private JTextField deckName;

    public DeckEntry() {
        super();
        init();
        place();
    }

    private void init() {
        nameLabel = new JLabel("Name : ");
        nameLabel.setAlignmentX(JLabel.EAST);
        heroLabel = new JLabel("Hero : ");
        heroLabel.setAlignmentX(JLabel.EAST);
        deckName = new JTextField("New Deck" + PlayerMapper.getInstance().getDecksSize());
        heroesNames = new JComboBox<>();
        for (Heroes hero : PlayerMapper.getInstance().playerHeroes()) {
            heroesNames.addItem(hero.getName());
        }
    }

    private void place() {
        setLayout(new GridLayout(2, 2));
        add(nameLabel);
        add(deckName);
        add(heroLabel);
        add(heroesNames);
    }

    public String getHeroName() {
        return (String) heroesNames.getSelectedItem();
    }

    public String getDeckName() {
        return deckName.getText();
    }

}