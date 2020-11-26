package logic;

import enums.GameFieldType;
import models.GameModel;

public class CardInfo {
    private final GameFieldType fieldType;
    private final int index;

    public CardInfo(GameFieldType fieldType, int index) {
        this.fieldType = fieldType;
        this.index = index;
    }

    public GameFieldType getFieldType() {
        return fieldType;
    }

    public int getIndex() {
        return index;
    }
}
