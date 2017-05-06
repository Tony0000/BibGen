package ufal.ic.util;

import javax.swing.*;
import java.util.Enumeration;

public class GroupButtonUtil {

    /** Loops through the button group to find which one is selected
     * @param buttonGroup the group of buttons
     * @return a string corresponding to the text of the selected button */
    public static String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}
