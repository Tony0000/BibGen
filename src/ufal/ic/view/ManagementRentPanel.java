package ufal.ic.view;

import ufal.ic.model.*;
import ufal.ic.control.*;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import ufal.ic.model.Book;
import ufal.ic.model.User;
import ufal.ic.control.JPABook;
import ufal.ic.control.SearchRentLogic;

/**
 * Flow Pane of rented, returned, overdue and scheduled books information of a given user
 * which includes a search bar, a selection of buttons for book related operations, and
 * a table of registered books to a given user.
 * Created by manoel on 02/05/2017.
 */
public class ManagementRentPanel extends JPanel implements SearchablePanel {
    private JButton rentBook, renewBook, returnBook, scheduleBook, payPenalty;
    private JPanel menuPane, tablePane, resultPane, upperPane;
    private SearchRentLogic searchLogic;
    private FormLogic formLogic;
    public static JTable booksRentedTable;
    private User user;
    private Book book;

    public ManagementRentPanel(){

        setLayout(new BorderLayout());

        formLogic = new FormLogic(new FormPanel("Rent book: ", JPABook.getBookColumns(), false));
        Vector<String> fields = new Vector<>();
        fields.add("enrollment");
        fields.add("ISBN");
        searchLogic = new SearchRentLogic();
        resultPane = new ResultPanel();

        /** Buttons for the options menu*/
        rentBook = scaleDownImage("plus.png");
        renewBook = scaleDownImage("forward.png");
        returnBook = scaleDownImage("minus.png");
        scheduleBook = scaleDownImage("schedule.png");
        payPenalty = scaleDownImage("dollar.png");
        setUpButtons();

        /** Options menu*/
        menuPane = new JPanel();
        menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.X_AXIS));
        menuPane.setBorder(new TitledBorder(""));
        menuPane.add(rentBook);
        menuPane.add(returnBook);
        menuPane.add(renewBook);
        menuPane.add(scheduleBook);
        menuPane.add(payPenalty);

        /** Table of books currently rented */
        booksRentedTable = new JTable();
        booksRentedTable.setEnabled(false);
        TableUtil.buildTableModelF(booksRentedTable, JPABook.getRentBookColumns(), new User());
        TableUtil.resizeColumnWidth(booksRentedTable);

        tablePane = new JPanel();
        tablePane.setLayout(new BorderLayout());
        tablePane.add(new JScrollPane(booksRentedTable), BorderLayout.CENTER);

        /**Split panes for menu above and list of books plus book rent bellow*/
        upperPane = new JPanel();
        upperPane.setLayout(new BoxLayout(upperPane, BoxLayout.X_AXIS));
        upperPane.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
        SearchPanel p = new SearchPanel(this, fields, 2);
        searchLogic.setPanel(p);
        upperPane.add(searchLogic.getSearchPanel());
        upperPane.add(resultPane);
        upperPane.add(menuPane);

        JSplitPane bottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePane, formLogic.getPanel());
        bottomSplit.setBorder(BorderFactory.createEmptyBorder(2,20,20,20));
        bottomSplit.setResizeWeight(0.7);
        bottomSplit.setOneTouchExpandable(false);
        bottomSplit.setEnabled(false);

        JSplitPane majorSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPane, bottomSplit);
        majorSplit.setOneTouchExpandable(false);
        majorSplit.setEnabled(false);
        add(majorSplit);
    }

    /** Sets up the buttons operations */
    private void setUpButtons() {
        rentBook.addActionListener(e->{
            if(user != null && book != null){
                EntityManager em = JPAClient.getSessionManager();
                // Checa se há livro disponivel
                if(book.getSamples() > 0) {
                    if(user.getPenalty() < 15){
                        // Decrementa a quantidade de livros disponiveis
                        book.setSamples(book.getSamples() - 1);

                        // Atribui a relacao de usuario e livro
                        UsersBook ub = new UsersBook();
                        ub.setUser(user);
                        ub.setBook(book);

                        // Atualiza data de locacao e entrega
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                        String date = sdf.format(new Date());

                        Date dt = null;
                        try {
                            dt = sdf.parse(date);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        ub.setDataLocacao(dt);
                        Calendar c = Calendar.getInstance();
                        c.setTime(dt);
                        c.add(Calendar.DATE, 15);
                        dt = c.getTime();
                        ub.setDataEntrega(dt);

                        // Finaliza a transacao salvando no banco
                        em.getTransaction().begin();
                        em.persist(ub);
                        em.persist(book);
                        em.getTransaction().commit();
                    }else{
                        JOptionPane.showMessageDialog(this, "User must first take out his ticket");
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "There is not available books");
                }

            } else{
                JOptionPane.showMessageDialog(this, "No books or user selected");
            }
            TableUtil.buildTableModelF(booksRentedTable, JPABook.getRentBookColumns(), user);
            TableUtil.resizeColumnWidth(booksRentedTable);
            formLogic.clear();
            /** Refreshes all jtables with newer data from database */
            TableUtil.buildTableModelB(ManagementBookPanel.resultsTable, JPABook.getRentBookColumns());
            TableUtil.resizeColumnWidth(ManagementBookPanel.resultsTable);
            book = null;
        });

        renewBook.addActionListener(e->{
            if(user != null && book != null){
                EntityManager EM = JPAClient.getSessionManager();
                Query q = EM.createQuery(
                        "FROM UsersBook WHERE user_id = :user_id and book_id = :book_id")
                        .setParameter("user_id", user.getEnrollment())
                        .setParameter("book_id", book.getIsbn());
                List<UsersBook> ub = q.getResultList();

                Date dt = new Date();
                Date dt1 = ub.get(0).getDataEntrega();
                if(dt.compareTo(dt1) < 0){
                    if(ub.size() > 0) {
                        for (UsersBook usersBook : ub) {
                            usersBook.setDataLocacao(dt);

                            Calendar c = Calendar.getInstance();
                            c.setTime(dt);
                            c.add(Calendar.DATE, 15);
                            dt = c.getTime();
                            usersBook.setDataEntrega(dt);

                            EM.getTransaction().begin();
                            EM.merge(usersBook);
                            EM.getTransaction().commit();
                            JOptionPane.showMessageDialog(this, "Successfully renewed");
                            TableUtil.buildTableModelF(booksRentedTable, JPABook.getRentBookColumns(), user);
                            TableUtil.resizeColumnWidth(booksRentedTable);
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "Book not allocated by this user");
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "Return time exceeded. \n" +
                            "Please return it and try to rent again.");
                }


            }else{
                JOptionPane.showMessageDialog(this, "No books or user selected");
            }

        });

        returnBook.addActionListener(e->{
            if(book!= null && user != null){
                EntityManager EM = JPAClient.getSessionManager();
                Query q = EM.createQuery(
                        "FROM UsersBook WHERE book_id = :book_id");
                q.setParameter("book_id", book.getIsbn());
                List<UsersBook> relations = q.getResultList();

                Date dt = new Date();
                Date dt1 = relations.get(0).getDataEntrega();
                Boolean cond = false;
                if(dt.compareTo(relations.get(0).getDataEntrega()) > 0){
                    long days_between = ((dt.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));
                    Integer days = (int) (long) days_between;
                    user.setPenalty(user.getPenalty() + days);
                    cond = true;
                }


                EM.getTransaction().begin();
                EM.remove(EM.find(UsersBook.class,relations.get(0).getId()));
                EM.merge(user);
                EM.getTransaction().commit();
                book.setSamples(book.getSamples()+1);
                JPABook.update(book);
                resultPane.setName(user.getName());
                ((ResultPanel)resultPane).setTaxValue(user.getPenalty().toString());
                if(cond){
                    JOptionPane.showMessageDialog(this, "User with fine");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Book returned successfully!");
                }
            }else{
                JOptionPane.showMessageDialog(this, "No books or user selected");
            }

            TableUtil.buildTableModelF(booksRentedTable, JPABook.getRentBookColumns(), user);
            TableUtil.resizeColumnWidth(booksRentedTable);
            formLogic.clear();
            /** Refreshes all jtables with newer data from database */
            TableUtil.buildTableModelB(ManagementBookPanel.resultsTable, JPABook.getBookColumns());
            TableUtil.resizeColumnWidth(ManagementBookPanel.resultsTable);
            book = null;
        });

        scheduleBook.addActionListener(e->{
            if(user != null && book != null){
                EntityManager EM = JPAClient.getSessionManager();
                ScheduleBook sb = new ScheduleBook();
                sb.setUser(user);
                sb.setBook(book);
                Query q = EM.createQuery(
                        "FROM UsersBook WHERE book_id = :book_id")
                        .setParameter("book_id", book.getIsbn());
                List<UsersBook> ub = q.getResultList();
                Date dt = new Date(Long.MAX_VALUE);
                if(ub.size() > 0){
                    for(UsersBook usersBook : ub ){
                        if(usersBook.getBook().getSamples() == 0) {
                            if (dt.compareTo(usersBook.getDataEntrega()) > 0) {
                                dt = usersBook.getDataEntrega();
                            }
                        }else{
                                JOptionPane.showMessageDialog(this, "There is available books");
                            break;
                        }
                    }
                    sb.setDataReserva(dt);
                    EM.getTransaction().begin();
                    EM.persist(sb);
                    EM.getTransaction().commit();
                    JOptionPane.showMessageDialog(this, "Reservado com sucesso");

                }else{
                    JOptionPane.showMessageDialog(this, "Reserved successfully");
                }
            }else{
                JOptionPane.showMessageDialog(this, "No books or user selected");
            }


        });

        payPenalty.addActionListener(e -> {
            if(user != null){
                user.setPenalty(0);
                JPAUser.update(user);
                ((ResultPanel)resultPane).setTaxValue("0");
            }else{
                JOptionPane.showMessageDialog(menuPane, "No books or user selected");
            }
        });
    }

    /** Given an input image it will scale it down and set it as an icon for a JButton
     * @param imgName image path
     * @return an instance of a button
     * */
    private JButton scaleDownImage(String imgName) {
        BufferedImage img;

        try {
            int defaultSize = 70;
            img = ImageIO.read(new File("res/"+imgName));
            Image scaledImg = img.getScaledInstance(defaultSize, defaultSize, java.awt.Image.SCALE_SMOOTH);

            JButton button = new JButton() {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(defaultSize, defaultSize);
                }
            };
            button.setIcon(new ImageIcon(scaledImg));
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setBorder(null);
            button.setMargin(new Insets(0, 0, 0, 0));
            return button;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String field = GroupButtonUtil.getSelectedButtonText(searchLogic.getSearchPanel().getRadioGroup());
        if (field.equals("enrollment")) {
            user = searchLogic.searchUser();
            resultPane.setName(user.getName());
            ((ResultPanel)resultPane).setTaxValue(user.getPenalty().toString());
        } else if (field.equals("ISBN")) {
            book = searchLogic.searchBook();
            formLogic.fill(book.getInfo());
        }
    }

    /** Sets up the result pane*/
    public class ResultPanel extends JPanel{

        JLabel name, taxValue;

        public ResultPanel(){
            setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(200, 100));
            setMaximumSize(new Dimension(300, 100));
            name = new JLabel("Name: ", SwingConstants.CENTER);
            taxValue = new JLabel("Fine: R$ ", SwingConstants.CENTER);

            Dimension maxSize = new Dimension(0, 10);
            add(new Box.Filler(maxSize, maxSize, maxSize));
            add(name);
            add(new Box.Filler(maxSize, maxSize, maxSize));
            add(taxValue);
        }

        public void setName(String username){
            name.setText("Name: " + username);
        }

        public void setTaxValue(String tax){
            taxValue.setText("Fine: R$ "+tax);
        }

    }
}
