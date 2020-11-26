package UtilSwing.ModelPanels;

import UtilSwing.Drawers.HeroPowerDrawer;
import models.HeroPower;

import java.awt.*;

public class HeroPowerPanel extends ModelPanel {

    private HeroPower heroPower;
    HeroPowerDrawer drawer;

    public HeroPowerPanel(HeroPower specialPower) {
        super();
        this.heroPower = specialPower;
        drawer = new HeroPowerDrawer();
        configure();
        repaint();
    }

    private void configure() {
        setPreferredSize(new Dimension(200, 250));
        setOpaque(false);
        setBorder(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawer.drawHeroPower(g, heroPower, 0, 0);
    }

    public HeroPower getHeroPower() {
        return heroPower;
    }

    public void setHeroPower(HeroPower heroPower) {
        this.heroPower = heroPower;
    }
}

