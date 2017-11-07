package ufal.ic.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

/** Sets up the search bar pane*/
public class SearchPanel extends JPanel{

    private JRadioButton[] radioButtons;
    public  JTextField inputText;
    public  ButtonGroup buttonGroup;
    private JPanel radioPanel;
    private JButton confirm;
    public  Vector<String> listOfFields;

    public SearchPanel(SearchablePanel parentPanel, Vector<String> items, int n) {
        /**Variables instantiation*/
        listOfFields = items;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Find by: "));
        setPreferredSize(new Dimension(300, 70));
        setMinimumSize(new Dimension(200, 60));

        radioPanel = new JPanel(new GridLayout(1, n));
        radioButtons = new JRadioButton[n];
        buttonGroup = new ButtonGroup();

        /** Selectable options for search bar and set the one selected by default. Then group and addButton them to the panel.*/
        for (int i = 0; i < n; i++) {
            radioButtons[i] = new JRadioButton(items.get(i));
            buttonGroup.add(radioButtons[i]);
            radioPanel.add(radioButtons[i]);
        }
        radioButtons[0].setSelected(true);

        inputText = new JTextField();
        inputText.addActionListener(parentPanel);
        confirm = new JButton("Confirm");
        confirm.setAlignmentX(JButton.CENTER_ALIGNMENT);
        confirm.addActionListener(parentPanel);

        Dimension minSize = new Dimension(20, 20);
        Dimension prefSize = new Dimension(20, 50);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 100);
        add(radioPanel);
        add(inputText);
        add(new Box.Filler(minSize, prefSize, prefSize));
        add(confirm);
        add(new Box.Filler(maxSize, maxSize, maxSize));
    }
}
