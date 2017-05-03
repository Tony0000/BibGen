package ufal.ic.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by manoel on 02/05/2017.
 */
public class UserManagementPanel extends JPanel {

    JPanel leftPane, searchPanel, registerUserPane;
    protected JButton addButton, updateButton, removeButton;
    Vector<String> userColumns;
    Vector<String> booksColumns;
    DefaultTableModel model;
    JTable resultsTable;
    Vector< Vector<String> > data;

    public UserManagementPanel() {

        /** Instantiate variables*/
        userColumns = new Vector<>();
        booksColumns = new Vector<>();
        data = new Vector<>();
        resultsTable = new JTable();

        /** Setting sample headers for user table*/
        userColumns.add("Matricula");
        userColumns.add("Nome");
        userColumns.add("Email");
        userColumns.add("Curso");
        userColumns.add("Multa");
        userColumns.add("Status");
        userColumns.add("Emprestimos");

        /** Instantiate dependent variables*/
        registerUserPane = new RegisterPanel("usuário", userColumns);
        registerUserPane.setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchPanel = new SearchPanel(userColumns, 3);
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        model = new DefaultTableModel(userColumns, 40);

        /** Sample data for user table*/
//        Vector<String> row = new Vector<>();
//        row.addButton("Ana Monteiro");
//        row.addButton("48 9923-7898");
//        row.addButton("ana.monteiro@gmail.com");
//        model.addRow(row);

        resultsTable.setModel(model);
        addButton = new JButton("Inserir");
        updateButton = new JButton("Atualizar");
        removeButton = new JButton("Remover");
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

}
