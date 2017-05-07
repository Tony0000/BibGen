package ufal.ic.gui;

import ufal.ic.entities.*;
import ufal.ic.util.*;

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

/**
 * Created by manoel on 02/05/2017.
 */
public class FlowManagementPanel extends JPanel{
    JButton rentBook, renewBook, returnBook, scheduleBook;
    JPanel menuPane, searchPane, tablePane, bookInfo;
    JTable booksRentedTable;
    User user;
    Book book;

    public FlowManagementPanel(){

        bookInfo = new RegisterPanel(BookUtil.getBookColumns());
        searchPane = new SearchPanel(new String[]{"enrollment", "ISBN"});
        /** Buttons for the options menu*/
        rentBook = scaleDownImage("plus.png");
        renewBook = scaleDownImage("forward.png");
        returnBook = scaleDownImage("minus.png");
        scheduleBook = scaleDownImage("schedule.png");
        setUpButtons();

        /** Options menu*/
        menuPane = new JPanel();
        menuPane.setLayout(new BoxLayout(menuPane, BoxLayout.X_AXIS));
        menuPane.add(rentBook);
        menuPane.add(returnBook);
        menuPane.add(renewBook);
        menuPane.add(scheduleBook);

        /** Table of books currently rented */
        booksRentedTable = new JTable();
        booksRentedTable.setEnabled(false);
        //TableUtil.buildTableModelF(booksRentedTable, booksColumns);
        TableUtil.buildTableModelF(booksRentedTable, BookUtil.getRentBookColumns(), new User());
        TableUtil.resizeColumnWidth(booksRentedTable);

        tablePane = new JPanel();
        tablePane.add(new JScrollPane(booksRentedTable), BorderLayout.CENTER);

        /**Split panes for menu above and list of books plus book rent bellow*/
        JPanel upperPane = new JPanel();
        upperPane.setLayout(new BoxLayout(upperPane, BoxLayout.X_AXIS));
        Dimension prefSize = new Dimension(300, 50);
        upperPane.add(searchPane);
        upperPane.add(new Box.Filler(prefSize, prefSize, prefSize));
        upperPane.add(menuPane);

        JSplitPane bottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePane, bookInfo);
        bottomSplit.setOneTouchExpandable(false);
        bottomSplit.setEnabled(false);

        JSplitPane majorSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPane, bottomSplit);
        majorSplit.setOneTouchExpandable(false);
        majorSplit.setEnabled(false);
        add(majorSplit);

    }

    private void setUpButtons() {
        rentBook.addActionListener(e->{
            if(user != null && book != null){
                EntityManager em = HibernateUtil.getManager();
                // Checa se há livro disponivel
                if(book.getSamples() > 0) {
                    // Decrementa a quantidade de livros disponveis
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
                    JOptionPane.showMessageDialog(this, "Não há livros disponíveis");
                }

            } else{
                JOptionPane.showMessageDialog(this, "Selecione o usuário e o livro");
            }
            TableUtil.buildTableModelF(booksRentedTable, BookUtil.getRentBookColumns(), user);
            TableUtil.resizeColumnWidth(booksRentedTable);
            ((RegisterPanel)bookInfo).clear();
            book = null;
        });

        renewBook.addActionListener(e->{
        });

        returnBook.addActionListener(e->{
            if(book!= null && user != null){
                EntityManager EM = HibernateUtil.getManager();
                Query q = EM.createQuery(
                        "FROM UsersBook WHERE book_id = :book_id");
                q.setParameter("book_id", book.getIsbn());
                List<UsersBook> relations = q.getResultList();
                EM.getTransaction().begin();
                EM.remove(EM.find(UsersBook.class,relations.get(0).getId()));
                EM.getTransaction().commit();
                book.setSamples(book.getSamples()+1);
                BookUtil.update(book);
            }else{
                JOptionPane.showMessageDialog(this, "Nenhum livro ou usuario selecionado");
            }

            TableUtil.buildTableModelF(booksRentedTable, BookUtil.getRentBookColumns(), user);
            TableUtil.resizeColumnWidth(booksRentedTable);
            ((RegisterPanel)bookInfo).clear();
            book = null;
        });

        scheduleBook.addActionListener(e->{
            JOptionPane.showMessageDialog(this, "Quero agendar");
        });
    }

    /** Given an input image it will scale it down and set it as an icon for a jbutton
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
            button.setBackground(Color.WHITE);
            button.setIcon(new ImageIcon(scaledImg));
            return button;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class SearchPanel extends JPanel {

        private JRadioButton[] radioButtons;
        private JTextField inputText;
        private ButtonGroup buttonGroup;
        private JPanel radioPanel;
        private JButton confirm;

        SearchPanel(String[] item) {
            /**Variables instantiation*/
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new TitledBorder("Find by:"));

            setPreferredSize(new Dimension(300, 100));
            setMinimumSize(new Dimension(200, 60));

            radioPanel = new JPanel(new GridLayout(1, 3));
            radioButtons = new JRadioButton[3];
            buttonGroup = new ButtonGroup();

            /** Selectable options for search bar and set the one selected by default.
             * Then group and addButton them to the panel.*/
            for (int i = 0; i < item.length; i++) {
                radioButtons[i] = new JRadioButton(item[i]);
            }
            radioButtons[0].setSelected(true);

            for (int i = 0; i < item.length; i++)
                buttonGroup.add(radioButtons[i]);

            for (int i = 0; i < item.length; i++)
                radioPanel.add(radioButtons[i]);

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
            add(radioPanel);
            add(inputText);
            add(confirm);
        }

        private void doSearch() {
            String field = GroupButtonUtil.getSelectedButtonText(buttonGroup);

            if (field.equals("enrollment")) {
                user = UserUtil.findBy(inputText.getText());
                TableUtil.buildTableModelF(booksRentedTable, BookUtil.getRentBookColumns(), user);

            } else if (field.equals("ISBN")) {
                book = BookUtil.findBy(inputText.getText());
                ((RegisterPanel)bookInfo).fillMe(book);
            }
            inputText.setText("");
        }
    }

    private class RegisterPanel extends JPanel {

        public RegisterPanel(Vector<String> field) {

            setBorder(new TitledBorder("Rent book: "));
            //Create and populate the panel.
            setLayout(new SpringLayout());
            for (int i = 0; i < field.size(); i++) {
                JLabel l = new JLabel(field.get(i), JLabel.TRAILING);
                add(l);
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(200,30));
                textField.setMaximumSize(new Dimension(200,30));
                textField.setEnabled(false);
                l.setLabelFor(textField);
                add(textField);
            }

            SpringUtilities.makeCompactGrid(this,
                    field.size(), 2, //rows, cols
                    4, 4,        //initX, initY
                    4, 4);       //xPad, yPad
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

        public void clear(){
            Component[] components = getComponents();
            for(Component c : components){
                if(c instanceof JTextField){
                    JTextField tmp =((JTextField) c);
                    tmp.setText("");
                }
            }
        }
    }
}
