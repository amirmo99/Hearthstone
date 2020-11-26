package UtilSwing.ModelPanels;

import UtilSwing.Drawers.CardDrawer;
import UtilSwing.Drawers.HeroDrawer;
import abstracts.Updatable;
import models.Heroes;

import javax.swing.*;
import java.awt.*;

public class HeroPanel extends ModelPanel implements Updatable {

//    private final Heroes mainHero;
    private Heroes hero;
    HeroDrawer heroDrawer;
    CardDrawer cardDrawer;
    private boolean includeWeapon = false;

    public HeroPanel(Heroes hero) {
        super();
//        this.mainHero = hero;
        this.hero = hero;
        this.heroDrawer = new HeroDrawer();
        this.cardDrawer = new CardDrawer();
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
        heroDrawer.drawHero(g, hero, 0, 0);

        if (hero.getWeapon() == null) {
            includeWeapon = false;
        } else if (includeWeapon) {
            cardDrawer.drawCard(g, hero.getWeapon(), true, false, (int) constants.getHeroWidth(), 0);
        }
        
    }

    @Override
    public void update() {
//        hero = mainHero.cloned();
        includeWeapon = true;
    }

    public Heroes getHero() {
        return hero;
    }

    public void setHero(Heroes hero) {
        this.hero = hero;
    }
}
