package logic;

import configs.LogicConstants;
import models.Card;
import models.Deck;
import models.Heroes;
import models.InfoPassive;
import models.cards.Minion;
import models.cards.Mission;

import java.util.ArrayList;
import java.util.List;

public class PlayerFields {

    private final Player player;
    private Deck mainDeck;

    private int eachTurnMana, thisTurnMana;
    private int numberOfNewCardsEachTurn;
    private Heroes hero;
    private List<Card> deck, hand;
    private List<Minion> playedCards;
    private List<Mission> activeMissions;
    private InfoPassive passive;

    public PlayerFields(Player player) {
        this.player = player;

        LogicConstants constants = new LogicConstants();
        start(constants.getNewCardsEachTurn(), constants.getInitialMana());
    }

    public void start(int numberOfNewCardsEachTurn, int eachTurnMana) {
        mainDeck = player.getActiveDeck().cloned();
        deck = mainDeck.getCards();
        hero = Heroes.getHero(mainDeck.getHeroName());
        playedCards = new ArrayList<>();
        hand = new ArrayList<>();
        activeMissions = new ArrayList<>();
        this.eachTurnMana = eachTurnMana;
        this.numberOfNewCardsEachTurn = numberOfNewCardsEachTurn;

        fillField(playedCards);
    }

    private void fillField(List<Minion> list) {
        for (int i = 0; i < new LogicConstants().getMaxPlayedCards(); i++) {
            list.add(null);
        }
    }

    public ArrayList<Card> getAllCards() {
        ArrayList<Card> list = new ArrayList<>(deck);
        list.addAll(hand);
        list.addAll(playedCards);

        while (list.contains(null))
            list.remove(null);

        return list;
    }

    public Player getPlayer() {
        return player;
    }

    public int getEachTurnMana() {
        return eachTurnMana;
    }

    public void setEachTurnMana(int eachTurnMana) {
        this.eachTurnMana = eachTurnMana;
    }

    public int getThisTurnMana() {
        return thisTurnMana;
    }

    public void setThisTurnMana(int thisTurnMana) {
        this.thisTurnMana = thisTurnMana;
    }

    public int getNumberOfNewCardsEachTurn() {
        return numberOfNewCardsEachTurn;
    }

    public void setNumberOfNewCardsEachTurn(int numberOfNewCardsEachTurn) {
        this.numberOfNewCardsEachTurn = numberOfNewCardsEachTurn;
    }

    public Heroes getHero() {
        return hero;
    }

    public void setHero(Heroes hero) {
        this.hero = hero;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public List<Minion> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(List<Minion> playedCards) {
        this.playedCards = playedCards;
    }

    public List<Mission> getActiveMissions() {
        return activeMissions;
    }

    public void setActiveMissions(List<Mission> activeMissions) {
        this.activeMissions = activeMissions;
    }

    public InfoPassive getPassive() {
        return passive;
    }

    public void setPassive(InfoPassive passive) {
        this.passive = passive;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }
}
