package ufal.ic.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by manoel on 02/05/17.
 */
public class BookManagementPanel extends JPanel {
    JPanel leftPane, searchPanel, insertBookPanel;
    protected JButton addButton, updateButton, removeButton;
    Vector<String> booksColumns;
    DefaultTableModel model;
    JTable resultsTable;
    Vector<Vector<String>> data;

    public BookManagementPanel() {

        /** Instantiate variables*/
        booksColumns = new Vector<String>();
        data = new Vector<>();
        resultsTable = new JTable();

        /** Setting sample headers for user table*/
        booksColumns.add("Título");
        booksColumns.add("Autor(es)");
        booksColumns.add("Editora");
        booksColumns.add("Edição");
        booksColumns.add("Status");
        booksColumns.add("Unidades");

        /** Instantiate dependent variables*/
        insertBookPanel = new BookManagementPanel.addBookPanel();
        insertBookPanel.setPreferredSize(new Dimension(300,300));
        leftPane = new JPanel(new GridLayout());
        leftPane.setBackground(Color.GREEN);
        searchPanel = new BookManagementPanel.SearchPanel(booksColumns);
        leftPane.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        add(leftPane);

        /** Instantiate and setting Data Model for the table*/
        model = new DefaultTableModel(booksColumns, 40);

        /** Sample data for user table*/
//        Vector<String> row = new Vector<>();
//        row.addButton("Ana Monteiro");
//        row.addButton("48 9923-7898");
//        row.addButton("ana.monteiro@gmail.com");
//        model.addRow(row);

        resultsTable.setModel(model);
        addButton = new JButton("Inserir");
        updateButton = new JButton("Atualizar");
        removeButton = new JButton("Remover");
        JPanel userHandlerButtons = new JPanel();
        userHandlerButtons.add(addButton);
        userHandlerButtons.add(updateButton);
        userHandlerButtons.add(removeButton);

        JPanel rightPane = new JPanel(new GridLayout(3, 1));
        rightPane.add(searchPanel);
        rightPane.add(insertBookPanel);
        rightPane.add(userHandlerButtons);
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        split.setOneTouchExpandable(false);
        leftPane.setMinimumSize(new Dimension(600, 500));
        rightPane.setMaximumSize(new Dimension(300, 500));
        split.setPreferredSize(new Dimension(1000, 500));
        split.setOneTouchExpandable(false);
        add(split);
    }

    private class SearchPanel extends JPanel {

        private JRadioButton[] radioButtons;
        private JTextField inputText;
        private ButtonGroup buttonGroup;
        private JPanel radioPanel;
        private JButton confirm;

        public SearchPanel(Vector<String> items) {
            /**Variables instantiation*/
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new TitledBorder("Buscar por: "));
            radioPanel = new JPanel(new GridLayout(1, 3));
            radioButtons = new JRadioButton[3];
            buttonGroup = new ButtonGroup();

            /** Selectable options for search bar and set the one selected by default. Then group and addButton them to the panel.*/
            for (int i = 0; i < 3; i++) {
                radioButtons[i] = new JRadioButton(items.get(i));
            }
            radioButtons[0].setSelected(true);

            for (int i = 0; i < 3; i++)
                buttonGroup.add(radioButtons[i]);

            for (int i = 0; i < 3; i++)
                radioPanel.add(radioButtons[i]);

            inputText = new JTextField();
            inputText.setMaximumSize(new Dimension(400, 60));
            confirm = new JButton("Confirmar");
            confirm.setAlignmentX(this.CENTER_ALIGNMENT);
            confirm.setPreferredSize(new Dimension(100, 30));
            confirm.setMaximumSize(new Dimension(150, 30));
            confirm.addActionListener(e -> {
                //TODO: Consultar o banco e efetuar ação correspondente a botao radio selecionado

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

    private class addBookPanel extends JPanel {
        String[] labels;

        public addBookPanel() {

            setBorder(new TitledBorder("Cadastrar Livro"));
            labels = new String[]{"Titulo", "Autor(es)", "Editora", "Edição", "Unidades"};

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
}