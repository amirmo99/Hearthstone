package UI;

import UI.GUI.MainFrame;
import UI.GUI.MainPanels.CollectionsPanel;
import Util.Administrator;
import Util.GameLogger;
import configs.LogicConstants;
import enums.LogType;
import logic.Player;
import models.Card;
import models.Deck;
import models.Heroes;

import java.util.List;

public class PlayerMapper {

    private static PlayerMapper mapper;
    private Player player;
    private Deck selectedDeck;

    private PlayerMapper() {
    }

    public boolean isCardLocked(Card card) {
        return !player.getCards().contains(card);
    }

    public static PlayerMapper getInstance() {
        if (mapper == null)
            mapper = new PlayerMapper();
        return mapper;
    }

    public List<Card> getPlayerUnownedCards() {
        return player.getUnownedCards();
    }

    public List<Card> getPlayerSellableCards() {
        return player.getSellableCards();
    }

    public int getPlayerGems() {
        return player.getGems();
    }

    public String getPlayerName() {
        return player.getUsername();
    }

    public void buyAttempt(Card card) {
        boolean success = player.buyCard(card);
        writePlayerData();
        if (!success)
            showError("You Don't Have Enough Gems");
    }

    public List<Deck> playerDecks() {
        return player.getMyDecks();
    }

    public List<Card> filteredCards(List<Card> cards, int mana, String searchText, String ownedCardsOnly) {
        return player.getFilteredCards(cards, mana, searchText, ownedCardsOnly);
    }

    public void sellAttempt(Card card) {
        int report = player.sellCard(card);
        switch (report) {
            case Player.CARD_IN_DECKS:
                showError("Card is in one of your decks");
                break;
            case Player.NOT_OWNED_CARD:
                showError("You do not have this card");
                break;
            case Player.SUCCESS:
                writePlayerData();
                break;
        }
    }

    public Deck getDeck(int index) {
        return player.getMyDecks().get(index);
    }

    public void removeDeck(int index) {
        player.removeDeck(index);
        writePlayerData();
    }

    public void removeCardFromDeck(int index) {
        selectedDeck.removeCard(index);
        writePlayerData();
    }

    public List<Heroes> playerHeroes() {
        return player.getMyAllHeroes();
    }

    public void createNewDeck(String deckName, String heroName) {
        if (player.existsDeckName(deckName)) {
            showError("A deck with this name already exists");
        } else if (deckName.length() < new LogicConstants().getDeckNameMinLength()) {
            showError("Deck name is too short");
        } else {
            player.addDeck(deckName, heroName);
            writePlayerData();
        }
    }

    public void addCardToDeck(Card card) {
        if (selectedDeck == null) {
            showError("You have to select a deck first");
        } else {
            int report = player.addCardToDeck(card, selectedDeck);
            switch (report) {
                case Player.NOT_OWNED_CARD:
                    CollectionsPanel.showGoToShopOption();
                    break;
                case Player.DIFFERENT_HERO_ERROR:
                    showError("Deck models.heroes and card class does not match");
                    break;
                case Player.DECK_SIZE_LIMIT:
                    showError("Deck Size Limit");
                    break;
                case Player.TWO_SAME_CARD_LIMIT:
                    showError("You Can't Have More Than 2 Cards of This Same Cards");
                    break;
                case Player.SUCCESS:
                    break;
            }
            writePlayerData();
        }
    }

    private void writePlayerData() {
        Administrator.updateDataModels(player);
    }


    public boolean loginRequest(String username, String password) {
        boolean success = Administrator.loginRequest(username, password);
        if (success) {
            setPlayer(Administrator.getPlayer(username));
        } else
            showError("Sorry!!! Wrong username or password.");
        return success;
    }

    public boolean signUpRequest(String username, String password) {
        int report = Administrator.signUpRequest(username, password);
        switch (report) {
            case Administrator.SHORT_PASSWORD:
                showError("Password Is Too Short");
                break;
            case Administrator.TAKEN_USER:
                showError("This Username Is Already Taken");
                break;
            case Administrator.SUCCESS:
                setPlayer(Administrator.getPlayer(username));
                return true;
        }
        return false;
    }

    public Deck getSelectedDeck() {
        return selectedDeck;
    }

    public void setSelectedDeck(int index) {
        this.selectedDeck = player.getMyDecks().get(index);
    }

    private void setPlayer(Player player) {
        this.player = player;
        GameMapper.getInstance().setPlayer(player);
    }

    private void showError(String error) {
        MainFrame.getInstance().showError(error);
        if (player != null) {
//            PlayerLogger.writeLog("Error", "Message : " + error);
            PlayerMapper.getInstance().getLogger().writeLog(LogType.ERROR, "Message : " + error);
        }
    }

    public void resetSelectedDeck() {
        selectedDeck = null;
    }

    public String isDeckActive(Deck deck) {
        return (deck.equals(player.getActiveDeck())) ? "Active" : "";
    }

    public String isDeckActive(int index) {
        return isDeckActive(getDeck(index));
    }

    public void activateDeck(int index) {
        player.setActiveDeck(getDeck(index));
//        PlayerLogger.writeLog("Deck Activated", "Deck name : " + player.getActiveDeck().getName());
        getLogger().writeLog(LogType.REPORT, "Deck Activated -> Deck name : " + player.getActiveDeck().getName());
        writePlayerData();
    }

    public void changeDeckDetails(String name, String heroName, int index) {
        if (player.existsDeckName(name) && !getDeck(index).getName().equals(name)) {
            showError("A deck with this name already exists.");
        } else if (name.length() < new LogicConstants().getDeckNameMinLength()) {
            showError("Deck name is too short.");
        } else if (!heroName.equals(getDeck(index).getHeroName()) && getDeck(index).hasHeroCard()) {
            showError("Cant change models.heroes. There are some NOT Natural models.cards in this deck.");
        } else {
//            PlayerLogger.writeLog("Deck Details changed", "name : " + name + ", Hero : " + heroName);
            getLogger().writeLog(LogType.REPORT, "Deck Details changed -> Name: " + name + ", Hero: " + heroName);
            getDeck(index).setName(name);
            getDeck(index).setHero(heroName);
        }
        writePlayerData();
    }

    public List<Deck> getSortedDecks() {
        List<Deck> sortedDecks = playerDecks();
        sortedDecks.sort(Deck::compareTo);
        return sortedDecks;
    }

    public void deleteRequest() {
        Administrator.deletePlayer(this.player);
        player = null;
    }

    public GameLogger getLogger() {
        return player.getLogger();
    }

    public int getDecksSize() {
        return player.getMyDecks().size();
    }

    public void signPlayerOut() {
        getLogger().writeLog(LogType.SIGN_OUT);
        player = null;
    }
}
