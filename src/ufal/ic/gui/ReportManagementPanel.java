package ufal.ic.gui;

import ufal.ic.entities.Book;
import ufal.ic.entities.ScheduleBook;
import ufal.ic.entities.User;
import ufal.ic.entities.UsersBook;
import ufal.ic.util.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by manoel on 05/05/2017.
 */
public class ReportManagementPanel extends JPanel{
    User user;
    JPanel searchPane;
    JLabel qtUsers, userWithPenaltys, qtRentedBooks, qtBooks;
    public static JTable booksScheduleTable;
    public ReportManagementPanel() {

        //setLayout(new BorderLayout());
        //setBorder(BorderFactory.createEmptyBorder(2,20,20,20));

        setBackground(Color.ORANGE);

        qtUsers = new JLabel("Quantidade de Usuários:");
        userWithPenaltys = new JLabel("Quantidade de Usuários com Multa:");
        qtRentedBooks = new JLabel("Quantidade de Livros Locados:");
        qtBooks = new JLabel("Quantidade de Livros:");
        JPanel upperPane = new JPanel();
        searchPane = new SearchPanel("enrollment");

        booksScheduleTable = new JTable();
        booksScheduleTable.setEnabled(false);
        TableUtil.buildTableModelB(booksScheduleTable, BookUtil.getRentBookColumns());
        TableUtil.resizeColumnWidth(booksScheduleTable);

        Dimension maxSize = new Dimension(0, 10);
        add(new Box.Filler(maxSize, maxSize, maxSize));

        upperPane.setLayout(new BoxLayout(upperPane, BoxLayout.Y_AXIS));
        upperPane.add(searchPane);
        upperPane.add(qtUsers);
        upperPane.add(userWithPenaltys);
        upperPane.add(qtRentedBooks);
        upperPane.add(qtBooks);
        upperPane.add(booksScheduleTable);
        add(upperPane);

        showData();

    }

    public void showData(){

        EntityManager EM = HibernateUtil.getManager();
        Query queryQtUsers = EM.createQuery(
                "FROM User");
        List<User> allUsers = queryQtUsers.getResultList();
        qtUsers.setText(qtUsers.getText() + " " + allUsers.size());

        Query queryUserWithPenaltys =  EM.createQuery(
                "FROM User WHERE penalty > 0");

        List<User> allUsersPenalty = queryUserWithPenaltys.getResultList();
        userWithPenaltys.setText(userWithPenaltys.getText() + " " + allUsersPenalty.size());

        Query queryQtBooks= EM.createQuery(
                "FROM Book");
        List<Book> allBooks = queryQtBooks.getResultList();
        qtBooks.setText(qtBooks.getText() + " " + allBooks.size());

        Query queryQtRentedBooks= EM.createQuery(
                "FROM UsersBook");
        List<Book> allRentedBooks = queryQtRentedBooks.getResultList();
        qtRentedBooks.setText(qtRentedBooks.getText() + " " + allRentedBooks.size());

    }

    private class SearchPanel extends JPanel {

        private JRadioButton[] radioButtons;
        private JTextField inputText;
        private ButtonGroup buttonGroup;
        private JPanel radioPanel;
        private JButton confirm;


        SearchPanel(String item) {
            /**Variables instantiation*/
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new TitledBorder("Scheduled Books by Enrollment:"));

            setPreferredSize(new Dimension(300, 100));
            setMinimumSize(new Dimension(200, 60));

            inputText = new JTextField();
            inputText.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doSearch();
                }
            });
            inputText.setMaximumSize(new Dimension(400, 60));
            confirm = new JButton("Confirmar");
            confirm.setAlignmentX(this.CENTER_ALIGNMENT);
            confirm.addActionListener(e -> {
                doSearch();
            });
            add(inputText);
            add(confirm);
        }

        private void doSearch() {

            user = UserUtil.findBy(inputText.getText());
            Vector<String> columns;
            columns = new Vector<>();
            columns.add("ISBN");
            columns.add("Title");
            columns.add("Author");
            columns.add("Schedule at");
            TableUtil.buildTableModelS(booksScheduleTable, columns, user);
            inputText.setText("");
        }
    }

}
