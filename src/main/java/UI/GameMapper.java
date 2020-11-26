package UI;

import UI.GUI.MainFrame;
import UI.GUI.MainPanels.MainGame.GameBoardPanel;
import Util.*;
import configs.LogicConstants;
import enums.LogType;
import logic.CardInfo;
import logic.GameActionExecutor;
import logic.MyGameState;
import models.Card;
import logic.Player;
import models.Deck;
import models.InfoPassive;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameMapper {

    private static GameMapper gameMapper;

    private final LogicConstants constants;
    private DeckReader deckReader;

    private MyGameState gameState;
    private GameActionExecutor gameAction;

    private Player player;
    private Player opponent;

    private GameMapper() {
        constants = new LogicConstants();
        deckReader = new DeckReader();

        opponent = Administrator.getPlayer("player2");
        SyncData.syncWithDataBase(opponent);
    }

    public static GameMapper getInstance() {
        if (gameMapper == null)
            gameMapper = new GameMapper();
        return gameMapper;
    }

    public boolean canRunGame() {
        int report = player.canStartGame();
        switch (report) {
            case Player.NOT_ENOUGH_CARD:
                showError("Cards in your deck must be at least " + constants.getMinCardsForGame());
                return false;
            case Player.NO_ACTIVE_DECK:
                showError("Activate a deck in collections for playing");
                return false;
            case Player.SUCCESS:
                return true;
        }
        return false;
    }

    private void createGame(boolean isFromFile, boolean isPlayer2CPU) {
        gameState = new MyGameState(player, opponent);
        gameAction = new GameActionExecutor(gameState);

        GameBoardPanel.getInstance().setGameState(gameState);
        GameBoardPanel.getInstance().start(isPlayer2CPU);

        gameAction.beginGame(isFromFile, isPlayer2CPU);
    }

    public void createGame() {
        createGame(false, false);
    }

    public void destroyGame() {
        gameAction.endGame(true);
    }

    public void createGameFromFile(File file) {
        try {
            Deck p1Deck = player.getActiveDeck();
            Deck p2Deck = opponent.getActiveDeck();

            DeckFile deckFile = deckReader.readDeck(file);
            applyDeckFile(deckFile);
            createGame(true, false);

            player.setActiveDeck(p1Deck);
            opponent.setActiveDeck(p2Deck);
        } catch (IOException e) {
            System.out.println("Could not create Game from file...");
            e.printStackTrace();

        }
    }

    public void createGameWithCPU() {
        createGame(false, true);
    }

    private void applyDeckFile(DeckFile deckFile) {
        String deckName = "Read Deck";
        String heroName = "Mage";

        player.setActiveDeck(new Deck(heroName, deckName, deckFile.getPlayer1CardsAsList()));
        opponent.setActiveDeck(new Deck(heroName, deckName, deckFile.getPlayer2CardsAsList()));
    }


    public void updateGraphics() {
        GameBoardPanel.getInstance().update();
    }

    public Card useRecentlyPlayedCard() {
        return gameAction.useRecentlyPlayedCard();
    }

    public void sendEndTurnCommand() {
        gameAction.endTurn();
    }

    public boolean isGameOver() {
        return gameState.isGameOver();
    }

    public String readEvents() {
        return gameState.getEvents().toString();
    }

    public void playerEntry(CardInfo info) {
        gameAction.playerEntry(info);
    }

    public void showError(String error) {
        MainFrame.getInstance().showError(error);
//        PlayerLogger.writeLog("Error", "Message : " + error);
        getLogger().writeLog(LogType.ERROR, error);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public InfoPassive askForPassive(List<InfoPassive> passives) {
        return GameBoardPanel.getInstance().askForPassive(passives);
    }

    public ArrayList<Card> askForMultipleCards(List<Card> cards, String message) {
        return GameBoardPanel.getInstance().askForMultipleCards(cards, message);
    }

    public Card discover(List<Card> cards) {
        return GameBoardPanel.getInstance().playerDiscover(cards);
    }

    public MyGameState getGameState() {
        return gameState;
    }

    public GameLogger getLogger() {
        return player.getLogger();
    }
}
