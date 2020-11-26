package configs;

public class LogicConstants extends MyConfigs {

    private int minCardsForGame,
            maxHandCards,
            maxPlayedCards,
            startingCardsNumber,
            maxMana,
            initialMana,
            initialGems,
            deckMaxSize,
            deckNameMinLength,
            eachTurnTime,
            newCardsEachTurn,
            warningTime;

    public LogicConstants() {
        super(Configs.CONSTANTS_CONFIG);
        init();
    }

    public void init() {
        minCardsForGame = properties.readInteger("MIN_CARDS_FOR_GAME");
        maxHandCards = properties.readInteger("MAX_HAND_CARDS");
        maxPlayedCards = properties.readInteger("MAX_PLAYED_CARDS");
        startingCardsNumber = properties.readInteger("STARTING_CARDS_NUMBER");
        maxMana = properties.readInteger("MAX_MANA");
        initialMana = properties.readInteger("INITIAL_MANA");
        initialGems = properties.readInteger("INITIAL_GEMS");
        deckMaxSize = properties.readInteger("DECK_MAX_SIZE_LIMIT");
        deckNameMinLength = properties.readInteger("DECK_NAME_LENGTH_LIMIT");
        eachTurnTime = properties.readInteger("SECONDS_EACH_TURN");
        newCardsEachTurn = properties.readInteger("NEW_CARDS_EACH_TURN");
        warningTime = properties.readInteger("WARNING_TIME");
    }

    public int getMinCardsForGame() {
        return minCardsForGame;
    }

    public int getMaxHandCards() {
        return maxHandCards;
    }

    public int getMaxPlayedCards() {
        return maxPlayedCards;
    }

    public int getStartingCardsNumber() {
        return startingCardsNumber;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getInitialMana() {
        return initialMana;
    }

    public int getInitialGems() {
        return initialGems;
    }

    public int getDeckMaxSize() {
        return deckMaxSize;
    }

    public int getDeckNameMinLength() {
        return deckNameMinLength;
    }

    public int getEachTurnTime() {
        return eachTurnTime;
    }

    public int getNewCardsEachTurn() {
        return newCardsEachTurn;
    }

    public int getWarningTime() {
        return warningTime;
    }
}
