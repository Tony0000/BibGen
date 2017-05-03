package ufal.ic.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

/**
 * Created by manoel on 03/05/2017.
 */
public class SearchPanel extends JPanel {

    private JRadioButton[] radioButtons;
    private JTextField inputText;
    private ButtonGroup buttonGroup;
    private JPanel radioPanel;
    private JButton confirm;

    public SearchPanel(Vector<String> items, int n) {
        /**Variables instantiation*/
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Buscar por: "));
        setPreferredSize(new Dimension(300,70));
        setMinimumSize(new Dimension(200,60));

        radioPanel = new JPanel(new GridLayout(1, 3));
        radioButtons = new JRadioButton[3];
        buttonGroup = new ButtonGroup();

        /** Selectable options for search bar and set the one selected by default. Then group and addButton them to the panel.*/
        for (int i = 0; i < n; i++) {
            radioButtons[i] = new JRadioButton(items.get(i));
        }
        radioButtons[0].setSelected(true);

        for (int i = 0; i < n; i++)
            buttonGroup.add(radioButtons[i]);

        for (int i = 0; i < n; i++)
            radioPanel.add(radioButtons[i]);

        inputText = new JTextField();
        confirm = new JButton("Confirmar");
        confirm.setAlignmentX(this.CENTER_ALIGNMENT);
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

    public SearchPanel(String[] item) {
        /**Variables instantiation*/
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Buscar por:"));

        setPreferredSize(new Dimension(300,100));
        setMinimumSize(new Dimension(200,60));

        radioPanel = new JPanel(new GridLayout(1, 3));
        radioButtons = new JRadioButton[3];
        buttonGroup = new ButtonGroup();

        /** Selectable options for search bar and set the one selected by default. Then group and addButton them to the panel.*/
        for (int i = 0; i < item.length; i++) {
            radioButtons[i] = new JRadioButton(item[i]);
        }
        radioButtons[0].setSelected(true);

        for (int i = 0; i < item.length; i++)
            buttonGroup.add(radioButtons[i]);

        for (int i = 0; i < item.length; i++)
            radioPanel.add(radioButtons[i]);

        inputText = new JTextField();
        inputText.setMaximumSize(new Dimension(400, 60));
        confirm = new JButton("Confirmar");
        confirm.setAlignmentX(this.CENTER_ALIGNMENT);
        confirm.addActionListener(e -> {
            //TODO: Consultar o banco e efetuar ação correspondente a botao radio selecionado

        });
        add(radioPanel);
        add(inputText);
        add(confirm);
    }
}