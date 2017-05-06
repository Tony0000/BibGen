package ufal.ic.gui;

import ufal.ic.entities.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by manoel on 02/05/17.
 */
public class BookManagementPanel extends JPanel {
    JPanel leftPane, searchPanel;
    RegisterPanel registerBookPane;
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
        registerBookPane = new RegisterPanel("books", booksColumns);
        registerBookPane.setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchPanel = new SearchPanel(booksColumns, 3);
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        model = new DefaultTableModel(booksColumns, 40);

        resultsTable.setModel(model);
        addButton = new JButton("Insert");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
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

    public void setUpButtons(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: query insert into user table
                Book book = registerBookPane.getFields();
                JOptionPane.showMessageDialog(registerBookPane, book.toString());
                BookHandler.insert(book);

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: query update to user table
                Book book = registerBookPane.getFields();
                JOptionPane.showMessageDialog(registerBookPane, book.toString());
                BookHandler.update(book);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: query remove from user table
                Book book = registerBookPane.getFields();
                JOptionPane.showMessageDialog(registerBookPane, book.toString());
                BookHandler.remove(book);
            }
        });
    }
    private class SearchPanel extends JPanel {

        private JRadioButton[] radioButtons;
        private JTextField inputText;
        private ButtonGroup buttonGroup;
        private JPanel radioPanel;
        private JButton confirm;

        public SearchPanel(Vector<String> items, int n) {
            /**Variables instantiation*/
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new TitledBorder("Buscar por: "));
            setPreferredSize(new Dimension(300, 70));
            setMinimumSize(new Dimension(200, 60));

            radioPanel = new JPanel(new GridLayout(1, 3));
            radioButtons = new JRadioButton[3];
            buttonGroup = new ButtonGroup();

            /** Selectable options for search bar and set the one selected by default. Then group and addButton them to the panel.*/
            for (int i = 0; i < n; i++) {
                radioButtons[i] = new JRadioButton(items.get(i));
            }
            radioButtons[0].setSelected(true);

            for (int i = 0; i < n; i++)
                buttonGroup.add(radioButtons[i]);

            for (int i = 0; i < n; i++)
                radioPanel.add(radioButtons[i]);

            inputText = new JTextField();
            confirm = new JButton("Confirmar");
            confirm.setAlignmentX(this.CENTER_ALIGNMENT);
            confirm.addActionListener(e -> {
                String field = GroupButtonUtil.getSelectedButtonText(buttonGroup);
                System.out.println(field + " - " + inputText.getText());
                Book b = BookHandler.findBy(inputText.getText());
                registerBookPane.fillMe(b);
                JOptionPane.showMessageDialog(this, b.toString());
            });

            Dimension minSize = new Dimension(20, 20);
            Dimension prefSize = new Dimension(20, 50);
            Dimension maxSize = new Dimension(Short.MAX_VALUE, 100);
            add(new Box.Filler(minSize, minSize, minSize));
            add(radioPanel);
            add(inputText);
            add(new Box.Filler(minSize, prefSize, prefSize));
            add(confirm);
            add(new Box.Filler(maxSize, maxSize, maxSize));
        }
    }

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
            return new Book();
        }

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