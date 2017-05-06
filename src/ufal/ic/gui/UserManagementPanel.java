package ufal.ic.gui;

import ufal.ic.entities.*;

import javax.persistence.Query;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

/**
 * Created by manoel on 02/05/2017.
 */
public class UserManagementPanel extends JPanel {

    JPanel leftPane, searchPanel;
    RegisterPanel registerUserPane;
    protected JButton addButton, updateButton, removeButton;
    Vector<String> userColumns;
    JTable resultsTable;
    Vector< Vector<String> > data;

    public UserManagementPanel() {

        /** Instantiate variables*/
        userColumns = new Vector<>();
        data = new Vector<>();
        resultsTable = new JTable();
        resultsTable.setEnabled(false);

        /** Setting sample headers for user table*/
        userColumns.add("Enrollment");
        userColumns.add("Name");
        userColumns.add("Email");
        userColumns.add("Course");

        /** Instantiate dependent variables*/
        registerUserPane = new RegisterPanel("user", userColumns);
        registerUserPane.setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchPanel = new SearchPanel(registerUserPane, userColumns, 3);
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        buildTableModel(resultsTable);

        addButton = new JButton("Insert");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
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

    public void buildTableModel(JTable table) {
        Query q = HibernateUtil.getSession().createQuery("from User");
        List<User> l = q.getResultList();

        // number and names of the columns
        int columnCount = userColumns.size();

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for(User u : l) {
            String[] tmp = u.getInfo();
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                vector.add(tmp[columnIndex]);
            }
            data.add(vector);
        }

        DefaultTableModel model = new DefaultTableModel(data, userColumns);
        table.setModel(model);

    }

    /** Provides the action each button has to execute once it's clicked. */
    public void setUpButtons(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = registerUserPane.getFields();
                JOptionPane.showMessageDialog(registerUserPane, user.toString());
                UserHandler.insert(user);
                buildTableModel(resultsTable);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: query update to user table
                User user = registerUserPane.getFields();
                JOptionPane.showMessageDialog(registerUserPane, user.toString());
                UserHandler.update(user);
                buildTableModel(resultsTable);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = registerUserPane.getFields();
                JOptionPane.showMessageDialog(registerUserPane, user.toString());
                UserHandler.remove(user);
                buildTableModel(resultsTable);
            }
        });
    }

    private class SearchPanel extends JPanel {

        private JRadioButton[] radioButtons;
        private JTextField inputText;
        private ButtonGroup buttonGroup;
        private JPanel radioPanel;
        private JButton confirm;

        public SearchPanel(RegisterPanel r, Vector<String> items, int n) {
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
                User u = UserHandler.findBy(inputText.getText());
                registerUserPane.fillMe(u);
                JOptionPane.showMessageDialog(this, u.toString());
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

    private class RegisterPanel extends JPanel{

        public RegisterPanel(String item, Vector<String> field) {

            setBorder(new TitledBorder("Register "+item));

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

        public User getFields(){
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

        public void fillMe(User user){
            String[] userFields = user.getInfo();

            int i = 0;
            Component[] components = getComponents();
            for(Component c : components){
                if(c instanceof JTextField){
                    JTextField tmp =((JTextField) c);
                    tmp.setText(userFields[i]);
                    i++;
                }
            }
        }
    }

}
