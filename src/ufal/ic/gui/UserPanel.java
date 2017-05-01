package ufal.ic.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by manoel on 30/04/2017.
 */
public class UserPanel extends JPanel{

    JLabel menuLabels;
    JButton logoff;
    JPanel reference;
    JPanel books, users, report; // tabs
    JPanel menuPanel, tablePanel, searchPanel;
    Vector<String> userColumns;
    Vector<String> booksColumns;
    DefaultTableModel model;
    JTable resultsTable;
    Vector< Vector<String> > data;
    JScrollPane scroll = new JScrollPane();

    public UserPanel(){
        setLayout(new GridBagLayout());
        /** Instantiate variables*/
        userColumns = new Vector<String>();
        booksColumns = new Vector<String>();
        data = new Vector<>();
        resultsTable = new JTable();

        /** Setting sample headers for user table*/
        userColumns.add("Matricula");
        userColumns.add("Nome");
        userColumns.add("Email");

        /** Setting sample headers for books table*/
        booksColumns.add("Titulo");
        booksColumns.add("Autor");
        booksColumns.add("Editora");
        booksColumns.add("Ano");
        booksColumns.add("ISBN");

        /** Instantiate dependent variables*/
        tablePanel = new JPanel();
        menuPanel = new JPanel();
        searchPanel = new SearchPanel(userColumns);
        scroll = new JScrollPane(resultsTable);
        tablePanel.add(scroll);


        /** Instantiate and setting Data Model for the table*/
        model = new DefaultTableModel(userColumns,0);

        /** Sample data for user table*/
        Vector<String> row = new Vector<>();
        row.add("Ana Monteiro");
        row.add("48 9923-7898");
        row.add("ana.monteiro@gmail.com");
        model.addRow(row);
        row = new Vector<>();
        row.add("João da Silva");
        row.add("48 8890-3345");
        row.add("joaosilva@hotmail.com");
        model.addRow(row);
        row = new Vector<>();
        row.add("Pedro Cascaes");
        row.add("48 9870-5634");
        row.add("pedrinho@gmail.com");
        model.addRow(row);
        //row.clear();

        //model.setDataVector(data,userColumns);
        resultsTable.setModel(model);


        logoff = new JButton("LogOut");
        logoff.addActionListener(e->{
            CardLayout cl = (CardLayout) reference.getLayout();
            cl.show(reference, PanelManager.LOGINPANEL); //equivalent to cl.next(reference)
        });

        GridBagConstraints c = new GridBagConstraints();
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        /** User label*/
        c.gridx = 1;
        c.gridy = 0;
        add(tablePanel, c);

        /** User label*/
        c.gridx = 2;
        c.gridy = 0;
        add(searchPanel, c);

        /** User label*/
        c.gridx = 1;
        c.gridy = 2;
        add(logoff, c);
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
            radioPanel = new JPanel(new GridLayout(1, items.size()));
            radioButtons = new JRadioButton[items.size()];
            buttonGroup = new ButtonGroup();

            /** Selectable options for search bar and set the one selected by default. Then group and add them to the panel.*/
            for (int i = 0; i < items.size(); i++) {
                radioButtons[i] = new JRadioButton(items.get(i));
            }
            radioButtons[0].setSelected(true);

            for (int i = 0; i < items.size(); i++)
                buttonGroup.add(radioButtons[i]);

            for (int i = 0; i < items.size(); i++)
                radioPanel.add(radioButtons[i]);

            inputText = new JTextField();
            confirm = new JButton("Confirm");
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

            add(radioPanel);
            add(inputText);
            add(confirm);

            /** Set their position relative position in the main panel */
            GridBagConstraints c = new GridBagConstraints();
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;

            /** User label*/
            c.gridx = 0;
            c.gridy = 0;
            add(radioPanel, c);

            /** User label*/
            c.gridx = 0;
            c.gridy = 1;
            add(inputText, c);

            /** User label*/
            c.gridx = 0;
            c.gridy = 2;
            add(confirm, c);
        }
    }
}
