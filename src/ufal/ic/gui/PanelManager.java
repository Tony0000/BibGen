package ufal.ic.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by manoel on 30/04/2017.
 */
public class PanelManager{

    private JPanel cards;
    final static String LOGINPANEL = "LOGIN SCREEN";
    final static String MAINSCREEN = "MAIN SCREEN";

    public void addComponentsToPane(Container pane){

        /**Create the card handler panel*/
        JPanel loginOuterPanel = new JPanel(new GridBagLayout());
        //loginOuterPanel.setBorder(new EmptyBorder(20,20,20,20));
        LoginPanel loginPanel = new LoginPanel();
        loginOuterPanel.setBackground(Color.cyan);
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
