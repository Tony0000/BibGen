package ufal.ic.view;


import ufal.ic.control.SpringUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/** Sets up the register pane*/
public class FormPanel extends JPanel {

    public FormPanel(String entity, Vector<String> fields, boolean flag) {

        setBorder(new TitledBorder(entity));

        //Create and populate the panel.
        setLayout(new SpringLayout());
        for (int i = 0; i < fields.size(); i++) {
            JLabel l = new JLabel(fields.get(i), JLabel.TRAILING);
            add(l);
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(250,30));
            textField.setMaximumSize(new Dimension(300,30));
            textField.setEnabled(flag);
            l.setLabelFor(textField);
            add(textField);
        }

        SpringUtilities.makeCompactGrid(this,
                fields.size(), 2, //rows, cols
                4, 4,        //initX, initY
                4, 4);       //xPad, yPad
    }

    public ArrayList<String> getFields(){
        ArrayList<String> fields = new ArrayList<>();

        Component[] components = getComponents();
        for(Component c : components){
            if(c instanceof JTextField){
                JTextField tmp =((JTextField) c);
                if(tmp.getText() == null)
                    return null;
                fields.add(tmp.getText());
                tmp.setText("");
            }
        }
        return fields;
    }
}
