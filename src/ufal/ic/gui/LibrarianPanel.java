package ufal.ic.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Outmost panel for all components accessible to a Librarian, such as registering
 * users, books, books operations and generating reports
 * Created by manoel on 30/04/2017.
 */
public class LibrarianPanel extends JPanel{

    final static String[] TAGS = {"USERS", "BOOKS", "FLOW", "REPORT"};
    private JPanel reference, tabs;

    public LibrarianPanel(){
        JPanel booksTab, usersTab, reportsTab, flowTab;
        JPanel menuPanel;

        setLayout(new GridBagLayout());
        booksTab = new BookManagementPanel();
        usersTab = new UserManagementPanel();
        reportsTab = new ReportManagementPanel();
        flowTab = new FlowManagementPanel();
        tabs = new JPanel(new CardLayout());

        tabs.add(usersTab, TAGS[0]);
        tabs.add(booksTab, TAGS[1]);
        tabs.add(flowTab, TAGS[2]);
        tabs.add(reportsTab, TAGS[3]);

        GridBagConstraints c = new GridBagConstraints();
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;

        /** Positioning inner Panels*/
        menuPanel = new MenuPanel();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        add(menuPanel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        add(tabs, c);

    }

    /** Creates a jbutton, resizes and sets its icon
     * @param imgName icon name with format
     * @return a formatted JButton*/
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


    public void addButtonListener(JPanel cards) {
        this.reference = cards;
    }

    /** The menu which handles the tabs selection and quit session buttons*/
    private class MenuPanel extends JPanel{
        JButton[] session;

        public MenuPanel(){
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            JPanel menuButtons = new JPanel();
            session = new JButton[5];
            session[0] = scaleDownImage("user.png");
            session[1] = scaleDownImage("book.png");
            session[2] = scaleDownImage("flow.png");
            session[3] = scaleDownImage("report.gif");
            session[4] = scaleDownImage("logoff.png");

            session[0].setEnabled(false);

            /** Button listeners*/
            session[4].addActionListener(e->{
                CardLayout cl = (CardLayout) reference.getLayout();
                cl.show(reference, PanelManager.LOGINPANEL);
            });

            for(int i = 0; i < session.length-1; i++){
                addSessionListener(session, i);
                menuButtons.add(session[i]);
            }

            menuButtons.setBorder(new TitledBorder(""));
            add(menuButtons);
            Dimension minSize = new Dimension(200, 90);
            Dimension prefSize = new Dimension(400, 90);
            Dimension maxSize = new Dimension(Short.MAX_VALUE, 90);
            add(new Box.Filler(minSize, prefSize, maxSize));
            add(session[4]);
        }

        private void addSessionListener(JButton[] button, int index) {
            button[index].addActionListener(e->{
                button[index].setEnabled(false);
                for(int i = 0; i < button.length-1; i++){
                    if(i != index){
                        button[i].setEnabled(true);
                        CardLayout cl = (CardLayout) tabs.getLayout();
                        cl.show(tabs, TAGS[index]);
                    }
                }
            });
        }
    }
}
