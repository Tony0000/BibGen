package ufal.ic.gui;

import ufal.ic.util.GroupButtonUtil;
import ufal.ic.util.SpringUtilities;
import ufal.ic.util.TableUtil;
import ufal.ic.entities.User;
import ufal.ic.util.UserUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    JTable resultsTable;
    Vector< Vector<String> > data;

    public UserManagementPanel() {

        /** Instantiate variables*/
        data = new Vector<>();
        resultsTable = new JTable();
        resultsTable.setEnabled(false);


        /** Instantiate dependent variables*/
        registerUserPane = new RegisterPanel("user", UserUtil.getColumns());
        registerUserPane.setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchPanel = new SearchPanel(registerUserPane, UserUtil.getColumns(), 3);
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        TableUtil.buildTableModelU(resultsTable, UserUtil.getColumns());
        TableUtil.resizeColumnWidth(resultsTable);

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

    /** Provides the action each button has to execute once it's clicked. */
    public void setUpButtons(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = registerUserPane.getFields();
                UserUtil.insert(user);
                TableUtil.buildTableModelU(resultsTable, UserUtil.getColumns());
                TableUtil.resizeColumnWidth(resultsTable);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: query update to user table
                User user = registerUserPane.getFields();
                UserUtil.update(user);
                TableUtil.buildTableModelU(resultsTable, UserUtil.getColumns());
                TableUtil.resizeColumnWidth(resultsTable);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = registerUserPane.getFields();
                UserUtil.remove(user);
                TableUtil.buildTableModelU(resultsTable, UserUtil.getColumns());
                TableUtil.resizeColumnWidth(resultsTable);
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
            setBorder(new TitledBorder("Find by: "));
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
            inputText.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doSearch();
                }
            });
            confirm = new JButton("Confirmar");
            confirm.setAlignmentX(this.CENTER_ALIGNMENT);
            confirm.addActionListener(e -> {
                doSearch();
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

        private void doSearch() {
            String field = GroupButtonUtil.getSelectedButtonText(buttonGroup);
            User u;
            if(field.equals("Enrollment")){
                u = UserUtil.findBy(inputText.getText());
            }else if (field.equals("Name")){
                u = UserUtil.queryUserTableByName(inputText.getText()).get(0);
            }else{
                u = UserUtil.queryUserTableByEmail(inputText.getText()).get(0);
            }
            registerUserPane.fillMe(u);
            inputText.setText("");
            TableUtil.buildTableModelU(resultsTable, UserUtil.getColumns());
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
