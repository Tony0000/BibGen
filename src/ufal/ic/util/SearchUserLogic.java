package ufal.ic.util;

import ufal.ic.entities.User;
import ufal.ic.gui.SearchPanel;

public class SearchUserLogic {

    static SearchPanel panel;
    public SearchUserLogic(SearchPanel panel) {
        this.panel = panel;
    }

    public SearchPanel getSearchPanel(){
        return panel;
    }

    /** Search operation logic */
    public static User doSearch() {
        String field = GroupButtonUtil.getSelectedButtonText(panel.buttonGroup);
        User u = null;
        if(field != null){
            switch (field) {
                case "Enrollment":
                    u = JPAUser.findBy(panel.inputText.getText());
                    break;
                case "Name":
                    u = JPAUser.queryByField(panel.listOfFields.get(1).toLowerCase(), panel.inputText.getText());
                    break;
                case "Email":
                    u = JPAUser.queryByField(panel.listOfFields.get(2).toLowerCase(), panel.inputText.getText());
                    break;
            }
            panel.inputText.setText("");
        }
        return u;
    }
}
