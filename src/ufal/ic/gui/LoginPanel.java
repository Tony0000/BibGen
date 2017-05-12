package ufal.ic.gui;

import ufal.ic.entities.Librarian;
import ufal.ic.util.LibrarianUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by manoel on 30/04/2017.
 */
public class LoginPanel extends JPanel {

    private JLabel userLabel, passLabel;
    private JTextField userInput;
    private JPasswordField passInput;
    private JButton loginButton;
    private JPanel reference;

    public LoginPanel(){

        setBorder(new TitledBorder("Login"));
        setMinimumSize(new Dimension(200,100));
        setPreferredSize(new Dimension(500,200));
        setLayout(new GridBagLayout());
        userLabel = new JLabel("User: ", JLabel.CENTER);
        passLabel = new JLabel("Password: ", JLabel.CENTER);
        userInput = new JTextField();
        passInput = new JPasswordField();
        userInput.setColumns(20);
        passInput.setColumns(20);
        passInput.setEchoChar('*');
        passInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryLogin();
            }
        });
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            tryLogin();
        });
        addComponentsToPane();
    }

    /** Checks if the inputted login and password has a correspondent in the database */
    private void tryLogin() {
        if(!userInput.getText().isEmpty() && passInput.getPassword().length > 0){
            Librarian login = LibrarianUtil.findBy(userInput.getText());
            if(login != null) {
                System.out.println(passInput.getPassword());
                if (login.getSenha().equals(passInput.getText())) {
                    CardLayout cl = (CardLayout) reference.getLayout();
                    cl.show(reference, PanelManager.MAINSCREEN);
                }else{
                    JOptionPane.showMessageDialog(this, "Usuario ou Senha incorreta.");
                }
            }else{
                JOptionPane.showMessageDialog(this, "Usuario ou Senha incorreta.");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Preencha os campos usuario e senha");
        }

        userInput.setText("");
        passInput.setText("");
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
