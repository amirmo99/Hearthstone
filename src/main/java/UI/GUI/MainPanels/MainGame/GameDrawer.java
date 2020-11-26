package UI.GUI.MainPanels.MainGame;

import UtilSwing.Drawers.MainDrawer;
import configs.GameGraphicConstants;
import configs.LogicConstants;
import configs.PanelsConfigs;
import logic.MyGameState;

import java.awt.*;

class GameDrawer extends MainDrawer {

    private Graphics g;
    private MyGameState gameState;

    private final PanelsConfigs panelsConfigs;
    private final GameGraphicConstants graphicConstants;

    private final int WARNING_TIME;

    public GameDrawer() {
        panelsConfigs = new PanelsConfigs();
        graphicConstants = new GameGraphicConstants();
        WARNING_TIME = new LogicConstants().getWarningTime();
    }

    private void drawManaBar(int x, int y, int mana) {
        g.setColor(Color.CYAN);
        int height = (int) graphicConstants.getManaHeight();
        int width = (int) graphicConstants.getManaWidth();

        for (int i = 0; i < mana; i++) {
            g.fill3DRect(x, y - (height - 1) * i, width, height, true);
        }
    }

    public void drawGameOver(boolean wonGame) {
        String string = (wonGame) ? "Victory :)))" : "You Lost :(";
        Color color = (wonGame) ? Color.YELLOW : Color.RED;

        g.setColor(color);

        Font font = new Font(panelsConfigs.getFont(), Font.BOLD, panelsConfigs.getBigTitleSize());
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();
        int offsetX = fontMetrics.stringWidth(string) / 2;
        g.drawString(string, (int) (graphicConstants.getGameWidth() / 2 - offsetX),
                (int) (graphicConstants.getGameHeight() / 2 - panelsConfigs.getBigTitleSize() / 2));
    }

    private void drawManaBars() {
        // Player 1
        drawManaBar((int) graphicConstants.getMana1X(), (int) graphicConstants.getMana1Y(), gameState.getPlayer1().getThisTurnMana());
        // Player 2
        drawManaBar((int) graphicConstants.getMana2X(), (int) graphicConstants.getMana2Y(), gameState.getPlayer2().getThisTurnMana());
    }

    public void drawGameState(MyGameState gameState) {
        if (gameState == null) return;
        setGameState(gameState);


        drawManaBars();
        drawTimer();
    }

    private void drawTimer() {
        Color color = (gameState.getTimer().getCount() >= WARNING_TIME) ? Color.WHITE : Color.RED;
        drawNumber(gameState.getTimer().getCount(), (int) graphicConstants.getTimerX(), (int) graphicConstants.getTimerY(), color, g);
    }

    public void setGraphics(Graphics g) {
        this.g = g;
    }

    public void setGameState(MyGameState gameState) {
        this.gameState = gameState;
    }
}
