package ufal.ic.gui;

import ufal.ic.entities.User;
import ufal.ic.entities.UserHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by manoel on 02/05/2017.
 */
public class UserManagementPanel extends JPanel {

    JPanel leftPane, searchPanel;
    RegisterPanel registerUserPane;
    protected JButton addButton, updateButton, removeButton;
    Vector<String> userColumns;
    DefaultTableModel model;
    JTable resultsTable;
    Vector< Vector<String> > data;

    public UserManagementPanel() {

        /** Instantiate variables*/
        userColumns = new Vector<>();
        data = new Vector<>();
        resultsTable = new JTable();

        /** Setting sample headers for user table*/
        userColumns.add("Matricula");
        userColumns.add("Nome");
        userColumns.add("Email");
        userColumns.add("Curso");

        /** Instantiate dependent variables*/
        registerUserPane = new RegisterPanel("usuário", userColumns);
        registerUserPane.setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchPanel = new SearchPanel(userColumns, 3);
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        model = new DefaultTableModel(userColumns, 40);

        resultsTable.setModel(model);
        addButton = new JButton("Inserir");
        updateButton = new JButton("Atualizar");
        removeButton = new JButton("Remover");
        setUpButtons();
        JPanel userHandlerButtons = new JPanel();
        userHandlerButtons.add(addButton);
        userHandlerButtons.add(updateButton);
        userHandlerButtons.add(removeButton);

        JPanel rightPane = new JPanel(new GridLayout(3,1));
        rightPane.add(searchPanel);
        rightPane.add(registerUserPane);
        rightPane.add(userHandlerButtons);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        split.setOneTouchExpandable(false);
        split.setEnabled(false);
        leftPane.setMinimumSize(new Dimension(600,500));
        rightPane.setMaximumSize(new Dimension(300,300));
        split.setPreferredSize(new Dimension(1000,500));
        add(split);
    }

    /** Provides the action each button has to execute once it's clicked. */
    public void setUpButtons(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLIQUEI PARA REGISTRAR");
                //TODO: query insert into user table
                User user = registerUserPane.getFields();
                JOptionPane.showMessageDialog(registerUserPane, user.toString());
                UserHandler.insert(user);

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLIQUEI PARA ATUALIZAR");
                //TODO: query update to user table
                User user = registerUserPane.getFields();
                JOptionPane.showMessageDialog(registerUserPane, user.toString());
                UserHandler.update(user);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLIQUEI PARA REMOVER");
                //TODO: query remove from user table
                User user = registerUserPane.getFields();
                JOptionPane.showMessageDialog(registerUserPane, user.toString());
                UserHandler.remove(user);
            }
        });


    }

}
