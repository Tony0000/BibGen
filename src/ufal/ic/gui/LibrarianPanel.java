package ufal.ic.gui;

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
 * Created by manoel on 30/04/2017.
 */
public class LibrarianPanel extends JPanel{

    protected JButton logoff, add, remove;
    JPanel reference, tabs;
    JPanel booksTab, usersTab, reportsTab, flowTab;
    JPanel menuPanel, tablePanel, searchPanel, insertUserPanel;
    Vector<String> userColumns;
    Vector<String> booksColumns;
    DefaultTableModel model;
    JTable resultsTable;
    Vector< Vector<String> > data;

    public LibrarianPanel(){
        setLayout(new GridBagLayout());
        booksTab = new JPanel();
        usersTab = new JPanel();
        reportsTab = new JPanel();
        flowTab = new JPanel(new GridLayout());
        tabs = new JPanel(new CardLayout());
        tabs.setBackground(Color.RED);
        setBackground(Color.LIGHT_GRAY);

        /** Instantiate variables*/
        userColumns = new Vector<String>();
        booksColumns = new Vector<String>();
        data = new Vector<>();
        resultsTable = new JTable();

        /** Setting sample headers for user table*/
        userColumns.add("Matricula");
        userColumns.add("Nome");
        userColumns.add("Email");
        userColumns.add("Curso");
        userColumns.add("Status");
        userColumns.add("Multa");
        userColumns.add("Livros Emprestados");

        /** Setting sample headers for books table*/
        booksColumns.add("Titulo");
        booksColumns.add("Autor");
        booksColumns.add("Editora");
        booksColumns.add("Ano");
        booksColumns.add("ISBN");

        /** Instantiate dependent variables*/
        insertUserPanel = new addUserPanel();
        tablePanel = new JPanel(new GridLayout());
        tablePanel.setBackground(Color.GREEN);
        searchPanel = new SearchPanel(userColumns);
        tablePanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        flowTab.add(tablePanel);
        flowTab.add(searchPanel);
//        addFlowComponents();
        tabs.add(flowTab);


        /** Instantiate and setting Data Model for the table*/
        model = new DefaultTableModel(userColumns,40);

        /** Sample data for user table*/
//        Vector<String> row = new Vector<>();
//        row.add("Ana Monteiro");
//        row.add("48 9923-7898");
//        row.add("ana.monteiro@gmail.com");
//        model.addRow(row);

        resultsTable.setModel(model);
        add = new JButton("Inserir");
        remove = new JButton("Remover");

        GridBagConstraints c = new GridBagConstraints();
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        menuPanel = new MenuPanel();

        /** Positioning inner Panels*/
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 8;
        c.anchor = GridBagConstraints.NORTH;
        add(menuPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 6;
        c.gridheight = 5;
        c.weightx = 1.0;
        c.anchor = GridBagConstraints.CENTER;
        add(tablePanel, c);

        c.gridx = 6;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(searchPanel, c);

        c.gridx = 6;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(insertUserPanel, c);

        c.gridx = 6;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(add, c);

        c.gridx = 7;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        add(remove, c);
    }

    private JButton scaleDownImage(String imgName) {
        BufferedImage master;

            try {
                master = ImageIO.read(new File("res/"+imgName));
                Image scaled = master.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);

                JButton button = new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(90, 90);
                    }
                };
                button.setIcon(new ImageIcon(scaled));
                return button;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    public void addButtonListener(JPanel cards) {
        this.reference = cards;
    }

    private class SearchPanel extends JPanel {

        private JRadioButton[] radioButtons;
        private JTextField inputText;
        private ButtonGroup buttonGroup;
        private JPanel radioPanel;
        private JButton confirm;

        public SearchPanel(Vector<String> items) {
            /**Variables instantiation*/
            setLayout(new GridBagLayout());
            setBorder(new TitledBorder("Search for: "));
            radioPanel = new JPanel(new GridLayout(1, 3));
            radioButtons = new JRadioButton[3];
            buttonGroup = new ButtonGroup();

            /** Selectable options for search bar and set the one selected by default. Then group and add them to the panel.*/
            for (int i = 0; i < 3; i++) {
                radioButtons[i] = new JRadioButton(items.get(i));
            }
            radioButtons[0].setSelected(true);

            for (int i = 0; i < 3; i++)
                buttonGroup.add(radioButtons[i]);

            for (int i = 0; i < 3; i++)
                radioPanel.add(radioButtons[i]);

            inputText = new JTextField();
            confirm = new JButton("Confirmar");
            confirm.addActionListener(e -> {

                /** Sample data for books table*/
                Vector<String> row = new Vector<>();
                data.clear();
                row.add("Catcher in the Rye");
                row.add("Sallinger");
                row.add("none");
                row.add("1950");
                row.add("95809743");
                data.add(row);
                row = new Vector<>();
                row.add("Cassandra guide book");
                row.add("Flower");
                row.add("O Reilly");
                row.add("2014");
                row.add("453066424");
                data.add(row);
                row = new Vector<>();
                row.add("The Fundation");
                row.add("Spencer");
                row.add("none");
                row.add("1970");
                row.add("3463278562");
                data.add(row);

                model.setDataVector(data, booksColumns);
            });

//            add(radioPanel);
//            add(inputText);
//            add(confirm);

            /** Set their position relative position in the main panel */
            GridBagConstraints c = new GridBagConstraints();
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;

            c.gridx = 0;
            c.gridy = 0;
            add(radioPanel, c);

            c.gridx = 0;
            c.gridy = 1;
            add(inputText, c);

            c.gridx = 0;
            c.gridy = 2;
            add(confirm, c);
        }
    }

    private class addUserPanel extends JPanel {
        String[] labels;

        public addUserPanel(){

            labels = new String[]{"Matricula", "Nome", "Curso", "Email", "Contato"};

            //Create and populate the panel.
            setLayout(new SpringLayout());
            for (int i = 0; i < labels.length; i++) {
                JLabel l = new JLabel(labels[i], JLabel.TRAILING);
                add(l);
                JTextField textField = new JTextField(10);
                l.setLabelFor(textField);
                add(textField);
            }

            SpringUtilities.makeCompactGrid(this,
                    labels.length, 2, //rows, cols
                    6, 6,        //initX, initY
                    6, 6);       //xPad, yPad
        }
    }

    private class MenuPanel extends JPanel{
        JButton addBooksButton, addUsersButton, flowButton, reportsButton, logoff;

        public MenuPanel(){
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            JPanel menuButtons = new JPanel();
            addUsersButton = scaleDownImage("user.png");
            addBooksButton = scaleDownImage("book.png");
            flowButton = scaleDownImage("report.gif");
            reportsButton = scaleDownImage("report.gif");
            logoff  = scaleDownImage("logoff.png");

            flowButton.setEnabled(false);
            addUsersButton.setEnabled(true);
            addBooksButton.setEnabled(true);
            reportsButton.setEnabled(true);

            /** Button listeners*/
            logoff.addActionListener(e->{
                CardLayout cl = (CardLayout) reference.getLayout();
                cl.show(reference, PanelManager.LOGINPANEL); //equivalent to cl.next(reference)
            });

            flowButton.addActionListener(e->{

            });

            menuButtons.add(addUsersButton);
            menuButtons.add(addBooksButton);
            menuButtons.add(flowButton);
            menuButtons.add(reportsButton);
            add(menuButtons);
            Dimension minSize = new Dimension(200, 90);
            Dimension prefSize = new Dimension(700, 90);
            Dimension maxSize = new Dimension(Short.MAX_VALUE, 90);
            add(new Box.Filler(minSize, prefSize, maxSize));
            add(logoff);
        }
    }
}
