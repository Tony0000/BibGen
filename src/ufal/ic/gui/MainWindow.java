package ufal.ic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {

    private JFrame frame;
    /** Prepares the jframe and its main panel*/
    private void prepareGUI(){
        frame = new JFrame("BibGen");
        /** Size and positioning*/
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)dim.getWidth()>>3, (int)dim.getHeight()>>6);
        /** Handler for window closing */
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event){
                System.exit(0);
            }
        });
        PanelManager panel = new PanelManager();
        panel.addComponentsToPane(frame.getContentPane());
        frame.setSize(new Dimension(1000,600));
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /** Creates database and tables for the first time use, can be commented afterwards for lower overhead. */
//        EntityManagerFactory factory = Persistence.
//                createEntityManagerFactory("BibGen");
//        factory.close();
        javax.swing.SwingUtilities.invokeLater(() -> new MainWindow().prepareGUI());

    }
}
