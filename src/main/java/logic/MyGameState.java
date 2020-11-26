package logic;

import configs.LogicConstants;
import enums.GameSituation;
import models.Card;
import models.GameModel;
import models.cards.Minion;

import java.util.*;

public class MyGameState {

    private final PlayerFields player1, player2;
    private GameSituation situation;
    private boolean gameOver;
    private int activePlayerIndex;
    private int winner;

    private List<String> events;
    private MyTimer timer;

    private ArrayList<Minion> deadMinions;
    private List<GameModel> selectableModels;

    private boolean recentlyPlayedCard;
    private boolean comboEnabled;

    private Stack<GameModel> affectingModels;

    private GameModel playingModel;
    private GameModel selectedModel;
    private GameModel summoningModel;
    private GameModel transformedModel;
    private Card receivedCard;

    public MyGameState(Player player1, Player player2) {
        this.player1 = new PlayerFields(player1);
        this.player2 = new PlayerFields(player2);
        init();
    }

    private void init() {
       events = new ArrayList<>();
       gameOver = false;
       activePlayerIndex = 1;
       situation = GameSituation.NORMAL;
       selectableModels = new ArrayList<>();
       deadMinions = new ArrayList<>();
       timer = new MyTimer(new LogicConstants().getEachTurnTime(), new LogicConstants().getWarningTime());

       this.affectingModels = new Stack<>();
    }

    public void refreshMana() {
        getActivePlayer().setThisTurnMana(getActivePlayer().getEachTurnMana());
    }

    public void addManaLimit(int maxMana) {
        getActivePlayer().setEachTurnMana(Math.min(maxMana, getActivePlayer().getEachTurnMana() + 1));
    }

    public void changeActivePlayer() {
        activePlayerIndex = (activePlayerIndex == 1) ? 2 : 1;
    }

    public PlayerFields getPlayer(int index) {
        switch (index) {
            case 1:
                return player1;
            case 2:
                return player2;
            default:
                return null;
        }
    }

    public PlayerFields getActivePlayer() {
        return (activePlayerIndex == 1) ? player1 : player2;
    }

    public PlayerFields getNotActivePlayer() {
        return (activePlayerIndex == 1) ? player2 : player1;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
    }

    public int getNotActivePlayerIndex() {
        return 3 - activePlayerIndex;
    }

    public void addEvent(String event) {
        synchronized (this) {
            events.add(0, event);
        }
    }

    public GameModel peekAffectingModel() {
        return affectingModels.peek();
    }

    public void pushAffectingModel(GameModel affectingModel) {
        affectingModels.push(affectingModel);
    }

    public void popAffectingModel() {
        affectingModels.pop();
    }

    /////// Getters & setters

    public List<String> getEvents() {
        synchronized (this) {
            return events;
        }
    }

    public PlayerFields getPlayer1() {
        return player1;
    }

    public PlayerFields getPlayer2() {
        return player2;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GameSituation getSituation() {
        return situation;
    }

    public void setSituation(GameSituation situation) {
        this.situation = situation;
    }

    public ArrayList<Minion> getDeadMinions() {
        return deadMinions;
    }

    public void setDeadMinions(ArrayList<Minion> deadMinions) {
        this.deadMinions = deadMinions;
    }

    public GameModel getPlayingModel() {
        return playingModel;
    }

    public void setPlayingModel(GameModel playingModel) {
        this.playingModel = playingModel;
    }

    public GameModel getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedModel(GameModel selectedModel) {
        this.selectedModel = selectedModel;
    }

    public GameModel getSummoningModel() {
        return summoningModel;
    }

    public void setSummoningModel(GameModel summoningModel) {
        this.summoningModel = summoningModel;
    }

    public List<GameModel> getSelectableModels() {
        return selectableModels;
    }

    public void setSelectableModels(List<GameModel> selectableModels) {
        this.selectableModels = selectableModels;
    }

    public boolean isRecentlyPlayedCard() {
        return recentlyPlayedCard;
    }

    public void setRecentlyPlayedCard(boolean recentlyPlayedCard) {
        this.recentlyPlayedCard = recentlyPlayedCard;
    }

    public Card getReceivedCard() {
        return receivedCard;
    }

    public void setReceivedCard(Card receivedCard) {
        this.receivedCard = receivedCard;
    }

    public MyTimer getTimer() {
        return timer;
    }

    public void setTimer(MyTimer timer) {
        this.timer = timer;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public boolean isComboEnabled() {
        return comboEnabled;
    }

    public void setComboEnabled(boolean comboEnabled) {
        this.comboEnabled = comboEnabled;
    }

    public GameModel getTransformedModel() {
        return transformedModel;
    }

    public void setTransformedModel(GameModel transformedModel) {
        this.transformedModel = transformedModel;
    }

}
