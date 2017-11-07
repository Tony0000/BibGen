package ufal.ic.util;

import ufal.ic.entities.Book;
import ufal.ic.gui.SearchPanel;

public class SearchBookLogic {

    static SearchPanel panel;
    public SearchBookLogic(SearchPanel panel) {
        SearchBookLogic.panel = panel;
    }

    public SearchPanel getSearchPanel(){
        return panel;
    }

    /** Search operation logic */
    public static Book doSearch() {
        String field = GroupButtonUtil.getSelectedButtonText(panel.buttonGroup);
        Book b = null;
        if(field != null){
            switch (field) {
                case "ISBN":
                    b = BookUtil.findBy(panel.inputText.getText());
                    break;
                case "Title":
                    b = BookUtil.queryByField(panel.listOfFields.get(1).toLowerCase(), panel.inputText.getText());
                    break;
                case "Author":
                    b = BookUtil.queryByField(panel.listOfFields.get(2).toLowerCase(), panel.inputText.getText());
                    break;
            }
            panel.inputText.setText("");
        }
        return b;
    }
}
