package ufal.ic.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by manoel on 30/04/2017.
 */
public class LoginPanel extends JPanel {

    private JLabel userLabel, passLabel;
    private JTextField userInput, passInput;
    private JButton loginButton;
    private JPanel reference;

    public LoginPanel(){

        setBorder(new TitledBorder("Login"));
        setMinimumSize(new Dimension(200,100));
        setLayout(new GridBagLayout());
        userLabel = new JLabel("User: ", JLabel.CENTER);
        passLabel = new JLabel("Password: ", JLabel.CENTER);
        userInput = new JTextField();
        passInput = new JTextField();
        userInput.setColumns(20);
        passInput.setColumns(20);
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            userInput.setText("");
            passInput.setText("");
            //Hibernate checks if there's an entry like the user in the database
            //If there is not then show a warning
            //JOptionPane.showMessageDialog(this, "Incorrect User or Password");
            CardLayout cl = (CardLayout) reference.getLayout();
            cl.show(reference, PanelManager.MAINSCREEN);
        });
        addComponentsToPane();
    }

    /** Add components and organize the positioning in the panel */
    public void addComponentsToPane(){

        GridBagConstraints c = new GridBagConstraints();
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        /** User label*/
        c.gridx = 0;
        c.gridy = 0;
        add(userLabel, c);

        /** User name input */
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0,10,0,0);  //external padding
        add(userInput, c);

        /** password label input */
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0,10,0,0);  //external padding
        add(passLabel, c);

        /** password input */
        c.gridx = 1;
        c.gridy = 1;
        add(passInput, c);

        /** login button input */
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(15,10,0,10);  //external padding
        add(loginButton, c);
    }

    public void addButtonListener(JPanel cards) {
        reference = cards;
    }
}
