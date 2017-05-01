package ufal.ic.entities;

import java.util.List;

/**
 * Created by manoel on 30/04/2017.
 */
public class Book {

    private String title;
    private List<String> authors;
    private String editora;
    private String status;
    private int numExemplares;
    private int edition;
    private int year;
    private int ISBN;


    public Book(String title, List<String> authors, String editora, String status,
                int numExemplares, int edition, int year, int ISBN) {
        this.title = title;
        this.authors = authors;
        this.editora = editora;
        this.status = status;
        this.numExemplares = numExemplares;
        this.edition = edition;
        this.year = year;
        this.ISBN = ISBN;
    }
}
