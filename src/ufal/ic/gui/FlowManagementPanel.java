package ufal.ic.gui;

import ufal.ic.entities.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by manoel on 02/05/2017.
 */
public class FlowManagementPanel extends JPanel{
    JButton rentBook, renewBook, returnBook, scheduleBook;
    JPanel menuPane, searchPane, tablePane, bookInfo;
    DefaultTableModel model;
    JTable booksRentedTable;
    Vector<String> booksColumns;

    public FlowManagementPanel(){
        booksColumns = new Vector<>();
        booksColumns.add("ISBN");
        booksColumns.add("Title");
        booksColumns.add("Author");
        booksColumns.add("Publisher");
        booksColumns.add("Units");
//        booksColumns.add("Rented at");
//        booksColumns.add("Due in");

        bookInfo = new RegisterPanel(booksColumns);
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
        model = new DefaultTableModel(booksColumns,10);
        booksRentedTable = new JTable();
        booksRentedTable.setModel(model);
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
            JOptionPane.showMessageDialog(this, "Quero alugar");
        });

        renewBook.addActionListener(e->{
            JOptionPane.showMessageDialog(this, "Quero renovar");
        });

        returnBook.addActionListener(e->{
            JOptionPane.showMessageDialog(this, "Quero devolver");
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

            /** Selectable options for search bar and set the one selected by default. Then group and addButton them to the panel.*/
            for (int i = 0; i < item.length; i++) {
                radioButtons[i] = new JRadioButton(item[i]);
            }
            radioButtons[0].setSelected(true);

            for (int i = 0; i < item.length; i++)
                buttonGroup.add(radioButtons[i]);

            for (int i = 0; i < item.length; i++)
                radioPanel.add(radioButtons[i]);

            inputText = new JTextField();
            inputText.setMaximumSize(new Dimension(400, 60));
            confirm = new JButton("Confirmar");
            confirm.setAlignmentX(this.CENTER_ALIGNMENT);
            confirm.addActionListener(e -> {


                String field = GroupButtonUtil.getSelectedButtonText(buttonGroup);

                if (field.equals("enrollment")) {
                    User user = UserHandler.findBy(inputText.getText());
                    JOptionPane.showMessageDialog(this, user.toString());
                    //TODO: CONSULTE O BANCO E TRAGA OS LIVROS ALUGADOS

                } else if (field.equals("ISBN")) {
                    Book book = BookHandler.findBy(inputText.getText());
                    ((RegisterPanel)bookInfo).fillMe(book);
                    JOptionPane.showMessageDialog(this, book.toString());
                }
                inputText.setText("");
            });
            add(radioPanel);
            add(inputText);
            add(confirm);
        }
    }

    private class RegisterPanel extends JPanel {

        public RegisterPanel(Vector<String> field) {

            setBorder(new TitledBorder("Rent book: "));
            //Create and populate the panel.
            setLayout(new SpringLayout());
            for (int i = 0; i < field.size()-2; i++) {
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


        public User getFields() {
            String[] userFields = new String[5];
            int i = 0;
            Component[] components = getComponents();
            for (Component c : components) {
                if (c instanceof JTextField) {
                    JTextField tmp = ((JTextField) c);
                    if (tmp.getText() == null)
                        return null;
                    userFields[i] = tmp.getText();
                    tmp.setText("");
                    i++;
                }
            }
            return new User(userFields[0], userFields[1], userFields[2], userFields[3]);
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
