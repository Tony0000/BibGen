package ufal.ic.control;

import ufal.ic.model.Book;
import ufal.ic.view.SearchPanel;

public class SearchBookLogic {

    private SearchPanel panel;
    public SearchBookLogic(SearchPanel panel) {
        this.panel = panel;
    }

    public SearchPanel getSearchPanel(){
        return panel;
    }

    /** Search operation control */
    public Book doSearch() {
        String field = GroupButtonUtil.getSelectedButtonText(panel.getRadioGroup());
        Book b = null;
        if(field != null){
            switch (field) {
                case "ISBN":
                    b = JPABook.findBy(panel.getText());
                    break;
                case "Title":
                    b = JPABook.queryByField(panel.getField(1), panel.getText());
                    break;
                case "Author":
                    b = JPABook.queryByField(panel.getField(2), panel.getText());
                    break;
            }
            panel.setText("");
        }
        return b;
    }
}
