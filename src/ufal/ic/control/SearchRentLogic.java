package ufal.ic.control;

import ufal.ic.model.Book;
import ufal.ic.model.User;
import ufal.ic.view.SearchPanel;

import static ufal.ic.view.ManagementRentPanel.booksRentedTable;

public class SearchRentLogic {

    private SearchPanel panel;

    public void setPanel(SearchPanel p){
        panel = p;
    }
    public SearchPanel getSearchPanel(){
        return panel;
    }

    /** Searches for an user with the given enrollment number read from the interface
     * and updates the table with the books rented by him.
     * @return User object from JPA */
    public User searchUser() {
        User user = JPAUser.findBy(panel.getText());
        TableUtil.buildTableModelF(booksRentedTable, JPABook.getRentBookColumns(), user);
        TableUtil.resizeColumnWidth(booksRentedTable);
        panel.setText("");
        return user;
    }

    /** Searches for an user with the given ISBN number read from the interface
     * @return Book object from JPA */
    public Book searchBook() {
        Book book = JPABook.findBy(panel.getText());
        panel.setText("");
        return book;
    }

}
