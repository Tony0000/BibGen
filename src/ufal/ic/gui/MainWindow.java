package ufal.ic.gui;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

public class MainWindow {

    private JFrame frame;
    /** Prepares the jframe and its main panel*/
    private void prepareGUI(){
        frame = new JFrame("BibGen");
        try {
            UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        frame.setSize(new Dimension(1000,700));
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
