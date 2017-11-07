package ufal.ic.gui;

import ufal.ic.entities.User;
import ufal.ic.util.SearchUserLogic;
import ufal.ic.util.SpringUtilities;
import ufal.ic.util.TableUtil;
import ufal.ic.util.JPAUser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

/** User registering pane, which includes a search bar, an user updater tool, and a table of registered users
 * Created by manoel on 02/05/2017.
 */
public class ManagementUserPanel extends JPanel implements SearchablePanel {

    JPanel leftPane, rightPane, userHandlerButtons;
    SearchUserLogic searchLogic;
    RegisterPanel registerUserPane;
    protected JButton addButton, updateButton, removeButton;
    JTable resultsTable;
    Vector< Vector<String> > data;

    public ManagementUserPanel() {

        /** Instantiate variables*/
        data = new Vector<>();
        resultsTable = new JTable();
        resultsTable.setEnabled(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(2,20,20,20));


        /** Instantiate dependent variables*/
        registerUserPane = new RegisterPanel("user", JPAUser.getColumns());
        registerUserPane.setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchLogic = new SearchUserLogic(new SearchPanel(this, JPAUser.getColumns(), JPAUser.SEARCHABLE_FIELDS));
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        TableUtil.buildTableModelU(resultsTable, JPAUser.getColumns());
        TableUtil.resizeColumnWidth(resultsTable);

        addButton = new JButton("Insert");
        updateButton = new JButton("Update");
        removeButton = new JButton("Remove");
        setUpButtons();
        userHandlerButtons = new JPanel();
        userHandlerButtons.add(addButton);
        userHandlerButtons.add(updateButton);
        userHandlerButtons.add(removeButton);

        rightPane = new JPanel(new GridLayout(3,1));
        rightPane.add(searchLogic.getSearchPanel());
        rightPane.add(registerUserPane);
        rightPane.add(userHandlerButtons);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        split.setOneTouchExpandable(false);
        split.setEnabled(false);
        split.setResizeWeight(0.7);

        add(split);
    }

    /** Provides the action each button has to execute once they're clicked. */
    public void setUpButtons(){
        addButton.addActionListener(e -> {
            User user = registerUserPane.getFields();
            JPAUser.insert(user);
            TableUtil.buildTableModelU(resultsTable, JPAUser.getColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });

        updateButton.addActionListener(e -> {
            User user = registerUserPane.getFields();
            JPAUser.update(user);
            TableUtil.buildTableModelU(resultsTable, JPAUser.getColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });

        removeButton.addActionListener(e -> {
            User user = registerUserPane.getFields();
            JPAUser.remove(user);
            TableUtil.buildTableModelU(resultsTable, JPAUser.getColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User u = SearchUserLogic.doSearch();
        if(u!=null)
            registerUserPane.fillMe(u);
    }

    /** Sets up the register pane*/
    private class RegisterPanel extends JPanel{

        public RegisterPanel(String entity, Vector<String> fields) {

            setBorder(new TitledBorder("Register "+entity));

            //Create and populate the panel.
            setLayout(new SpringLayout());
            for (int i = 0; i < fields.size(); i++) {
                JLabel l = new JLabel(fields.get(i), JLabel.TRAILING);
                add(l);
                JTextField textField = new JTextField();
                l.setLabelFor(textField);
                add(textField);
            }

            SpringUtilities.makeCompactGrid(this,
                    fields.size(), 2, //rows, cols
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

        /** Fills the register pane with the current user
         * @param user user to have its information retrieved*/
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
