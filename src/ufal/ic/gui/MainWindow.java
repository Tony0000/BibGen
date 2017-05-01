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
        frame.setSize(1280,720);
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
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new MainWindow().prepareGUI());

    }
}
