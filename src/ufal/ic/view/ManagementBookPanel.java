package ufal.ic.view;

import ufal.ic.control.FormLogic;
import ufal.ic.control.JPABook;
import ufal.ic.control.SearchBookLogic;
import ufal.ic.control.TableUtil;
import ufal.ic.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Book registering pane, which includes a search bar, a book updater tool, and a table of registered books
 * Created by manoel on 02/05/17.
 */
public class ManagementBookPanel extends JPanel implements SearchablePanel {
    SearchBookLogic searchLogic;
    JPanel leftPane, rightPane, userHandlerButtons;
    FormLogic formLogic;
    private JButton addButton, updateButton, removeButton;
    public static JTable resultsTable;

    public ManagementBookPanel() {

        /** Instantiate variables*/
        resultsTable = new JTable();
        resultsTable.setEnabled(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(2,20,20,20));


        /** Instantiate dependent variables*/
        formLogic = new FormLogic(new FormPanel("Register Book", JPABook.getBookColumns(), true));
        formLogic.getPanel().setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchLogic = new SearchBookLogic(new SearchPanel(this, JPABook.getBookColumns(), JPABook.SEARCHABLE_FIELDS));
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        TableUtil.buildTableModelB(resultsTable, JPABook.getBookColumns());
        TableUtil.resizeColumnWidth(resultsTable);

        addButton = new JButton("Insert");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
        setUpButtons();
        userHandlerButtons = new JPanel();
        userHandlerButtons.add(addButton);
        userHandlerButtons.add(updateButton);
        userHandlerButtons.add(removeButton);

        rightPane = new JPanel(new GridLayout(3, 1));
        rightPane.add(searchLogic.getSearchPanel());
        rightPane.add(formLogic.getPanel());
        rightPane.add(userHandlerButtons);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        split.setResizeWeight(0.7);
        split.setOneTouchExpandable(false);
        split.setEnabled(false);
        add(split);
    }

    /** Sets up the buttons operations */
    public void setUpButtons(){
        addButton.setBackground(Color.RED);
        updateButton.setBackground(Color.RED);
        removeButton.setBackground(Color.RED);

        addButton.addActionListener(e -> {
            Book book = buildObject(formLogic.getFields());
            JPABook.insert(book);
//            JOptionPane.showMessageDialog(this, book.toString());
            ((DefaultTableModel)resultsTable.getModel()).fireTableDataChanged();
            TableUtil.buildTableModelB(resultsTable, JPABook.getBookColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });

        updateButton.addActionListener(e -> {
            Book book = buildObject(formLogic.getFields());
            JPABook.update(book);
            TableUtil.buildTableModelB(resultsTable, JPABook.getBookColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });

        removeButton.addActionListener(e -> {
            Book book = buildObject(formLogic.getFields());
            JPABook.remove(book);
            TableUtil.buildTableModelB(resultsTable, JPABook.getBookColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Book b = searchLogic.doSearch();
        if(b!=null)
            formLogic.fill(b.getInfo());
    }

    private Book buildObject(ArrayList<String> data){
        return new Book(data.get(0),data.get(1),data.get(2),data.get(3), Integer.valueOf(data.get(4)));
    }

}