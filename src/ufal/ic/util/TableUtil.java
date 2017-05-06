package ufal.ic.util;

import ufal.ic.entities.Book;
import ufal.ic.entities.User;

import javax.persistence.Query;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by manoel on 06/05/2017.
 */
public class TableUtil {

    public static void buildTableModelU(JTable table, Vector<String> columnNames) {
        Query q = HibernateUtil.getSession().createQuery("from User");
        List<User> l = q.getResultList();

        // number and names of the columns
        int columnCount = columnNames.size();

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for(User u : l) {
            String[] tmp = u.getInfo();
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                vector.add(tmp[columnIndex]);
            }
            data.add(vector);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }

    public static void buildTableModelB(JTable table, Vector<String> columnNames) {
        Query q = HibernateUtil.getSession().createQuery("from Book");
        List<Book> l = q.getResultList();

        // number and names of the columns
        int columnCount = columnNames.size();

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for(Book b : l) {
            String[] tmp = b.getInfo();
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                vector.add(tmp[columnIndex]);
            }
            data.add(vector);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }

    public static void buildTableModelF(JTable table, Vector<String> columnNames) {

        Query q = HibernateUtil.getSession().createQuery("from Book");
        List<Book> l = q.getResultList();

        // number and names of the columns
        int columnCount = columnNames.size();

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for(Book b : l) {
            String[] tmp = b.getInfo();
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                vector.add(tmp[columnIndex]);
            }
            data.add(vector);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);
    }

    public static void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
