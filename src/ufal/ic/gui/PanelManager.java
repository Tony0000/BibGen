package ufal.ic.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        loginOuterPanel.setBorder(new EmptyBorder(20,20,20,20));
        LoginPanel loginPanel = new LoginPanel();
        UserPanel userPanel = new UserPanel();
        loginOuterPanel.add(loginPanel);


        /**Create the panel that contain the cards*/
        cards = new JPanel(new CardLayout());
        cards.add(loginOuterPanel, LOGINPANEL);
        cards.add(userPanel, MAINSCREEN);

        loginPanel.addButtonListener(cards);
        userPanel.addButtonListener(cards);
        pane.add(cards, BorderLayout.CENTER);
    }
}
