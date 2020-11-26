package admin;

import UI.GUI.MainFrame;
import UI.PlayerMapper;
import UtilSwing.MyOptionPane;
import logic.Player;
import models.Card;
import models.Heroes;

import javax.swing.*;

public class HearthStoneGUI {
    public static void main(String[] args) {


        System.out.println(Heroes.getHero("Hunter"));


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = MainFrame.getInstance();
            }
        });


    }
}
