package ufal.ic.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by manoel on 02/05/17.
 */
public class BookManagementPanel extends JPanel {
    JPanel leftPane, searchPanel, registerBookPane;
    protected JButton addButton, updateButton, removeButton;
    Vector<String> booksColumns;
    DefaultTableModel model;
    JTable resultsTable;
    Vector<Vector<String>> data;

    public BookManagementPanel() {

        /** Instantiate variables*/
        booksColumns = new Vector<>();
        data = new Vector<>();
        resultsTable = new JTable();

        /** Setting sample headers for user table*/
        booksColumns.add("Title");
        booksColumns.add("Author");
        booksColumns.add("Publisher");
        booksColumns.add("Edition");
        booksColumns.add("Units");

        /** Instantiate dependent variables*/
        registerBookPane = new RegisterPanel("livros", booksColumns);
        registerBookPane.setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchPanel = new SearchPanel(booksColumns, 3);
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        model = new DefaultTableModel(booksColumns, 40);

        resultsTable.setModel(model);
        addButton = new JButton("Inserir");
        updateButton = new JButton("Atualizar");
        removeButton = new JButton("Remover");
        JPanel userHandlerButtons = new JPanel();
        userHandlerButtons.add(addButton);
        userHandlerButtons.add(updateButton);
        userHandlerButtons.add(removeButton);

        JPanel rightPane = new JPanel(new GridLayout(3, 1));
        rightPane.add(searchPanel);
        rightPane.add(registerBookPane);
        rightPane.add(userHandlerButtons);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        leftPane.setMinimumSize(new Dimension(600, 500));
        rightPane.setMaximumSize(new Dimension(300, 500));
        split.setPreferredSize(new Dimension(1000, 500));
        split.setOneTouchExpandable(false);
        split.setEnabled(false);
        add(split);
    }

    public void setUpButtons() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLIQUEI PARA REGISTRAR");
                //TODO: query insert into book table
//                User user = registerBookPane.getFields();
//                JOptionPane.showMessageDialog(registerBookPane, user.toString());
//                UserHandler.insert(book);

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLIQUEI PARA ATUALIZAR");
                //TODO: query update to book table
//                Book book = registerBookPane.getFields();
//                JOptionPane.showMessageDialog(registerBookPane, book.toString());
//                UserHandler.update(book);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("CLIQUEI PARA REMOVER");
                //TODO: query remove from book table
//                Book book = registerBookPane.getFields();
//                JOptionPane.showMessageDialog(registerBookPane, book.toString());
//                UserHandler.remove(book);
            }
        });
    }
}