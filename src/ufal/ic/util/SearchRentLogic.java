package ufal.ic.util;

import ufal.ic.entities.Book;
import ufal.ic.entities.User;
import ufal.ic.gui.SearchPanel;

import static ufal.ic.gui.ManagementRentPanel.booksRentedTable;

public class SearchRentLogic {

    static SearchPanel panel;

    public void setPanel(SearchPanel p){
        panel = p;
    }
    public SearchPanel getSearchPanel(){
        return panel;
    }

    /** Search operation logic */
    public static String doSearch() {
        String field = GroupButtonUtil.getSelectedButtonText(panel.buttonGroup);
        String result = "";
        if (field.equals("enrollment")) {
            result = searchUser();

        } else if (field.equals("ISBN")) {
            result = searchBook();
        }
        panel.inputText.setText("");
        return result;
    }

    /** Search operation logic */
    public static String searchUser() {
        User user = JPAUser.findBy(panel.inputText.getText());
        TableUtil.buildTableModelF(booksRentedTable, JPABook.getRentBookColumns(), user);
        TableUtil.resizeColumnWidth(booksRentedTable);
        panel.inputText.setText("");
        return user.toString()+",u";
    }

    /** Search operation logic */
    public static String searchBook() {
        Book book = JPABook.findBy(panel.inputText.getText());
        panel.inputText.setText("");
        return book.toString()+",b";
    }

}
