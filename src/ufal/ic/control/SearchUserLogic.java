package ufal.ic.control;

import ufal.ic.model.User;
import ufal.ic.view.SearchPanel;

public class SearchUserLogic {

    private SearchPanel panel;
    public SearchUserLogic(SearchPanel panel) {
        this.panel = panel;
    }

    public SearchPanel getSearchPanel(){
        return panel;
    }

    /** Search operation control */
    public User doSearch() {
        String field = GroupButtonUtil.getSelectedButtonText(panel.getRadioGroup());
        User u = null;
        if(field != null){
            switch (field) {
                case "Enrollment":
                    u = JPAUser.findBy(panel.getText());
                    break;
                case "Name":
                    u = JPAUser.queryByField(panel.getField(1), panel.getText());
                    break;
                case "Email":
                    u = JPAUser.queryByField(panel.getField(2), panel.getText());
                    break;
            }
            panel.setText("");
        }
        return u;
    }
}
