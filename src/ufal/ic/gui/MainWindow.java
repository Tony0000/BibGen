package ufal.ic.gui;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
        //frame.setSize(1200,565);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((int)dim.getWidth()>>5, (int)dim.getHeight()>>6);
        /** Handler for window closing */
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event){
                System.exit(0);
            }
        });
        PanelManager panel = new PanelManager();
        panel.addComponentsToPane(frame.getContentPane());
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new MainWindow().prepareGUI());

        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("BibGen");

        factory.close();

    }
}
