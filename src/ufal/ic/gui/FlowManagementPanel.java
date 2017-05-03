package ufal.ic.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    JPanel menuPane, searchPane, tablePane;
    DefaultTableModel model;
    JTable booksRentedTable;
    Vector<String> booksColumns;

    public FlowManagementPanel(){
        booksColumns = new Vector<>();
        booksColumns.add("Título");
        booksColumns.add("Autor(es)");
        booksColumns.add("Editora");
        booksColumns.add("Edição");
        booksColumns.add("Status");
        booksColumns.add("Unidades");

        searchPane = new SearchPanel(new String[]{"matricula", "ISBN"});
        /** Buttons for the options menu*/
        rentBook = scaleDownImage("plus.png");
        renewBook = scaleDownImage("forward.png");
        returnBook = scaleDownImage("minus.png");
        scheduleBook = scaleDownImage("schedule.png");

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

        JPanel bookInfo = new RegisterPanel(booksColumns);
        JSplitPane bottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePane, bookInfo);
        bottomSplit.setOneTouchExpandable(false);
        bottomSplit.setEnabled(false);

        JSplitPane majorSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPane, bottomSplit);
        majorSplit.setOneTouchExpandable(false);
        majorSplit.setEnabled(false);
        add(majorSplit);

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
}
