package ufal.ic.gui;

import ufal.ic.entities.User;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

/**
 * Created by manoel on 03/05/2017.
 */
public class RegisterPanel extends JPanel {

    public RegisterPanel(String item, Vector<String> field) {

        setBorder(new TitledBorder("Cadastrar "+item));

        //Create and populate the panel.
        setLayout(new SpringLayout());
        for (int i = 0; i < field.size(); i++) {
            JLabel l = new JLabel(field.get(i), JLabel.TRAILING);
            add(l);
            JTextField textField = new JTextField(20);
            l.setLabelFor(textField);
            add(textField);
        }

        SpringUtilities.makeCompactGrid(this,
                field.size(), 2, //rows, cols
                4, 4,        //initX, initY
                4, 4);       //xPad, yPad
    }

    public RegisterPanel(Vector<String> field) {

        setBorder(new TitledBorder("Alugar livro: "));

        //Create and populate the panel.
        setLayout(new SpringLayout());
        for (int i = 0; i < field.size(); i++) {
            JLabel l = new JLabel(field.get(i), JLabel.TRAILING);
            add(l);
            JLabel textField = new JLabel();
            l.setLabelFor(textField);
            add(textField);
        }

        SpringUtilities.makeCompactGrid(this,
                field.size(), 2, //rows, cols
                4, 4,        //initX, initY
                4, 4);       //xPad, yPad
    }

    public User getFields(){
        System.out.println("CLIQUEI NA BUSCA");
        String[] userFields = new String[5];
        int i = 0;
        Component[] components = getComponents();
        for(Component c : components){
            if(c instanceof JTextField){
                 JTextField tmp =((JTextField) c);
                if(tmp.getText() == null)
                    return null;
                userFields[i] = tmp.getText();
                tmp.setText("");
                i++;
            }
        }
        return new User(userFields[0], userFields[1], userFields[2], userFields[3]);
    }
}