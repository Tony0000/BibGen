package ufal.ic.view;

import ufal.ic.control.FormLogic;
import ufal.ic.control.JPAUser;
import ufal.ic.control.SearchUserLogic;
import ufal.ic.control.TableUtil;
import ufal.ic.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/** User registering pane, which includes a search bar, an user updater tool, and a table of registered users
 * Created by manoel on 02/05/2017.
 */
public class ManagementUserPanel extends JPanel implements SearchablePanel {

    private JPanel leftPane, rightPane, userHandlerButtons;
    private SearchUserLogic searchLogic;
    private FormLogic formLogic;
    private JButton addButton, updateButton, removeButton;
    JTable resultsTable;

    public ManagementUserPanel() {

        /** Instantiate variables*/
        resultsTable = new JTable();
        resultsTable.setEnabled(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(2,20,20,20));


        /** Instantiate dependent variables*/
        formLogic = new FormLogic(new FormPanel("Register User", JPAUser.getUserColumns(), true));
        formLogic.getPanel().setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        searchLogic = new SearchUserLogic(new SearchPanel(this, JPAUser.getUserColumns(), JPAUser.SEARCHABLE_FIELDS));
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        /** Instantiate and setting Data Model for the table*/
        TableUtil.buildTableModelU(resultsTable, JPAUser.getUserColumns());
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
        rightPane.add(formLogic.getPanel());
        rightPane.add(userHandlerButtons);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        split.setOneTouchExpandable(false);
        split.setEnabled(false);
        split.setResizeWeight(0.7);

        add(split);
    }

    /** Provides the action each button has to execute once they're clicked. */
    public void setUpButtons(){
        addButton.setBackground(Color.RED);
        updateButton.setBackground(Color.RED);
        removeButton.setBackground(Color.RED);
        addButton.addActionListener(e -> {
            User user = buildObject(formLogic.getFields());
            JPAUser.insert(user);
            TableUtil.buildTableModelU(resultsTable, JPAUser.getUserColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });

        updateButton.addActionListener(e -> {
            User user = buildObject(formLogic.getFields());
            JPAUser.update(user);
            TableUtil.buildTableModelU(resultsTable, JPAUser.getUserColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });

        removeButton.addActionListener(e -> {
            User user = buildObject(formLogic.getFields());
            JPAUser.remove(user);
            TableUtil.buildTableModelU(resultsTable, JPAUser.getUserColumns());
            TableUtil.resizeColumnWidth(resultsTable);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User u = searchLogic.doSearch();
        if(u!=null)
            formLogic.fill(u.getInfo());
    }

    private User buildObject(ArrayList<String> data){
        return new User(data.get(0),data.get(1),data.get(2),data.get(3));
    }

}
