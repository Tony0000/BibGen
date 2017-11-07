package ufal.ic.gui;

import ufal.ic.entities.Book;
import ufal.ic.util.JPABook;
import ufal.ic.util.SearchBookLogic;
import ufal.ic.util.SpringUtilities;
import ufal.ic.util.TableUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

/**
 * Book registering pane, which includes a search bar, a book updater tool, and a table of registered books
 * Created by manoel on 02/05/17.
 */
public class ManagementBookPanel extends JPanel implements SearchablePanel {
    SearchBookLogic searchLogic;
    JPanel leftPane, rightPane, userHandlerButtons;
    RegisterPanel registerBookPane;
    protected JButton addButton, updateButton, removeButton;
    public static JTable resultsTable;
    Vector<Vector<String>> data;

    public ManagementBookPanel() {

        /** Instantiate variables*/
        data = new Vector<>();
        resultsTable = new JTable();
        resultsTable.setEnabled(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(2,20,20,20));


        /** Instantiate dependent variables*/
        registerBookPane = new RegisterPanel("books", JPABook.getBookColumns());
        registerBookPane.setPreferredSize(new Dimension(300,300));
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
        rightPane.add(registerBookPane);
        rightPane.add(userHandlerButtons);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        split.setResizeWeight(0.7);
        split.setOneTouchExpandable(false);
        split.setEnabled(false);
        add(split);
    }

    /** Sets up the buttons operations */
    public void setUpButtons(){
        addButton.addActionListener(e -> {
            Book book = registerBookPane.getFields();
            JPABook.insert(book);
//            JOptionPane.showMessageDialog(this, book.toString());
            ((DefaultTableModel)resultsTable.getModel()).fireTableDataChanged();
            TableUtil.buildTableModelB(resultsTable, JPABook.getBookColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });

        updateButton.addActionListener(e -> {
            Book book = registerBookPane.getFields();
            JPABook.update(book);
            TableUtil.buildTableModelB(resultsTable, JPABook.getBookColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });

        removeButton.addActionListener(e -> {
            Book book = registerBookPane.getFields();
            JPABook.remove(book);
            TableUtil.buildTableModelB(resultsTable, JPABook.getBookColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Book b = SearchBookLogic.doSearch();
        if(b!=null)
            registerBookPane.fillMe(b);
    }

    /** Sets up the register pane*/
    private class RegisterPanel extends JPanel {

        public RegisterPanel(String item, Vector<String> field) {

            setBorder(new TitledBorder("Register " + item));

            //Create and populate the panel.
            setLayout(new SpringLayout());
            for (int i = 0; i < field.size(); i++) {
                JLabel l = new JLabel(field.get(i), JLabel.TRAILING);
                add(l);
                JTextField textField = new JTextField();
                l.setLabelFor(textField);
                add(textField);
            }

            SpringUtilities.makeCompactGrid(this,
                    field.size(), 2, //rows, cols
                    4, 4,        //initX, initY
                    4, 4);       //xPad, yPad
        }

        public Book getFields() {
            String[] bookFields = new String[5];
            int i = 0;
            Component[] components = getComponents();
            for (Component c : components) {
                if (c instanceof JTextField) {
                    JTextField tmp = ((JTextField) c);
                    if (tmp.getText() == null)
                        return null;
                    bookFields[i] = tmp.getText();
                    tmp.setText("");
                    i++;
                }
            }
            return new Book(bookFields[0],bookFields[1],bookFields[2],bookFields[3], Integer.valueOf(bookFields[4]));
        }

        /** Fills the register pane with the current book
         * @param book book to have its information retrieved*/
        public void fillMe(Book book){
            String[] bookFields = book.getInfo();

            int i = 0;
            Component[] components = getComponents();
            for(Component c : components){
                if(c instanceof JTextField){
                    JTextField tmp =((JTextField) c);
                    tmp.setText(bookFields[i]);
                    i++;
                }
            }
        }
    }
}