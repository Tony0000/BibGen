package ufal.ic.gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import ufal.ic.entities.Book;
import ufal.ic.entities.User;
import ufal.ic.entities.UsersBook;
import ufal.ic.util.HibernateUtil;
import ufal.ic.util.TableUtil;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by manoel on 05/05/2017.
 */
public class ReportManagementPanel extends JPanel{

    JLabel qtUsers, userWithPenaltys, qtRentedBooks, qtBooks, amtFine;
    public static JTable usersDebtors;
    private static Vector<String> columns;
    JSplitPane majorSplit;

    public ReportManagementPanel() {

        setLayout(new BorderLayout());
        Date date = Calendar.getInstance().getTime();
        TitledBorder border = new TitledBorder("Date of issue: "+date.toGMTString().substring(0,11));
        border.setTitleColor(Color.RED);
        border.setTitleFont(Font.getFont(Font.SERIF));
        setBorder(border);

        qtUsers = new JLabel("Number of users:");
        userWithPenaltys = new JLabel("Number of users with a fine:");
        qtRentedBooks = new JLabel("Number of rented books:");
        qtBooks = new JLabel("Number of books:");
        amtFine = new JLabel("Amount of Fine: R$ ");
        JPanel leftPane = new JPanel();

        columns = new Vector<>();
        columns.add("Enrollment");
        columns.add("Name");
        columns.add("Email");
        columns.add("Course");
        columns.add("Fine");

        usersDebtors = new JTable();
        usersDebtors.setEnabled(false);
        TableUtil.buildTableModelS(usersDebtors, columns);
        TableUtil.resizeColumnWidth(usersDebtors);

        Dimension prefSize = new Dimension(0, 10);


        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));
        leftPane.add(new Box.Filler(prefSize, prefSize, prefSize));
        leftPane.add(qtUsers);
        leftPane.add(new Box.Filler(prefSize, prefSize, prefSize));
        leftPane.add(userWithPenaltys);
        leftPane.add(new Box.Filler(prefSize, prefSize, prefSize));
        leftPane.add(qtRentedBooks);
        leftPane.add(new Box.Filler(prefSize, prefSize, prefSize));
        leftPane.add(qtBooks);
        leftPane.add(new Box.Filler(prefSize, prefSize, prefSize));
        leftPane.add(amtFine);

        final JPanel p = this;
        JButton b = scaleDownImage("pdf.png");
        b.addActionListener(new ActionListener() {
            final JPanel pane = p;

            @Override
            public void actionPerformed(ActionEvent e) {
                final java.awt.Image image = getImageFromPanel(pane);
                System.out.println(image.getHeight(null) + " " + image.getWidth(null));
                String folder = System.getProperty("user.dir");
                printToPDF(image, folder+"/relatorio.pdf");
            }
        });
        JSplitPane upperPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, b);
        upperPane.setBorder(BorderFactory.createEmptyBorder(2,20,20,20));
        upperPane.setResizeWeight(0.9);
        upperPane.setOneTouchExpandable(false);
        upperPane.setEnabled(false);
        upperPane.setDividerLocation(0.9);

        majorSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, upperPane, new JScrollPane(usersDebtors));
        majorSplit.setBorder(BorderFactory.createEmptyBorder(2,20,20,20));
        majorSplit.setResizeWeight(0.0);
        majorSplit.setOneTouchExpandable(false);
        majorSplit.setEnabled(false);
        majorSplit.setDividerLocation(0.4);

        add(majorSplit);

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

        int sum = 0;
        for(User u : allUsersPenalty){
            sum += u.getPenalty();
        }
        amtFine.setText(amtFine.getText() + sum);

        Query queryQtBooks= EM.createQuery(
                "FROM Book");
        List<Book> allBooks = queryQtBooks.getResultList();
        qtBooks.setText(qtBooks.getText() + " " + allBooks.size());


        LocalDate dateTime = LocalDate.now();
        Query queryQtRentedBooks= EM.createQuery(
                "FROM UsersBook WHERE datalocacao = :dataLocacao")
                .setParameter("dataLocacao", dateTime);

        List<UsersBook> allRentedBooks = queryQtRentedBooks.getResultList();

        qtRentedBooks.setText(qtRentedBooks.getText() + " " + allRentedBooks.size());




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
            java.awt.Image scaledImg = img.getScaledInstance(defaultSize, defaultSize, java.awt.Image.SCALE_SMOOTH);

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

    public void printToPDF(java.awt.Image awtImage, String fileName) {
        try {
            Document d = new Document(PageSize.A4.rotate());
            PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(
                    fileName));
            d.open();


            Image iTextImage = Image.getInstance(writer, awtImage, 1);
            iTextImage.setAbsolutePosition(10, 200);
            iTextImage.scalePercent(80);
            d.add(iTextImage);

            d.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static java.awt.Image getImageFromPanel(Component component) {

        BufferedImage image = new BufferedImage(component.getWidth(),
                component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image;
    }

}
