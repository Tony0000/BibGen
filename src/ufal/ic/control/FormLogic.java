package ufal.ic.control;

import ufal.ic.view.FormPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FormLogic {
    FormPanel panel;

    public FormLogic(FormPanel panel){
        this.panel = panel;
    }

    public JPanel getPanel(){
        return panel;
    }

    public ArrayList<String> getFields(){
        return panel.getFields();
    }

    /** Fills the register pane with the current user
     * @param fields Data to have its information displayed */
    public void fill(String[] fields){

        int i = 0;
        Component[] components = panel.getComponents();
        for(Component c : components){
            if(c instanceof JTextField){
                JTextField tmp =((JTextField) c);
                tmp.setText(fields[i]);
                i++;
            }
        }
    }

    public void clear(){
        Component[] components = panel.getComponents();
        for(Component c : components){
            if(c instanceof JTextField){
                JTextField tmp =((JTextField) c);
                tmp.setText("");
            }
        }
    }
}
