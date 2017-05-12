package ufal.ic.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Manages the transition of two tabs, the Login and Librarian interface panels
 * Created by manoel on 30/04/2017.
 */
public class PanelManager{

    private JPanel cards;
    final static String LOGINPANEL = "LOGIN SCREEN";
    final static String MAINSCREEN = "MAIN SCREEN";

    public void addComponentsToPane(Container pane){

        /**Create the card handler panel*/
        JPanel loginOuterPanel = new JPanel(new GridBagLayout()){
            private int w, h;

            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                try {
                    BufferedImage image = ImageIO.read(new File("res/login.png"));
                    w = image.getWidth();
                    h = image.getHeight();
                    g.drawImage(image, 0,0,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public Dimension getPreferredSize(){
                return new Dimension(w,h);
            }
        };
        LoginPanel loginPanel = new LoginPanel();

        LibrarianPanel librarianPanel = new LibrarianPanel();
        loginOuterPanel.add(loginPanel);


        /**Create the panel that contain the cards*/
        cards = new JPanel(new CardLayout());
        cards.add(loginOuterPanel, LOGINPANEL);
        cards.add(librarianPanel, MAINSCREEN);

        loginPanel.addButtonListener(cards);
        librarianPanel.addButtonListener(cards);
        pane.add(cards, BorderLayout.CENTER);
    }
}
