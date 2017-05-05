package ufal.ic.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by manoel on 30/04/2017.
 */
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    private int ISBN;

    @Column
    private String title;
    @ElementCollection(targetClass=String.class)
    private List<String> authors;

    @Column
    private String publisher;

    @Column
    private int samples;

    @Column
    private int edition;

    @Column
    private int year;




    @ManyToMany(mappedBy = "booksList")
    private List<User> listUsers;

    // Constructor no argumento
    public Book(){}

    public String getTitle(){
        return this.title;
    }
    public List<String> getAuthors(){
        return authors;
    }
    public String getPublisher(){
        return publisher;
    }
    public int getSamples(){
        return samples;
    }
    public int getEdition(){
        return edition;
    }
    public int getYear(){
        return year;
    }
    public int getISBN(){
        return ISBN;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthors(List<String> authors){
        this.authors = authors;
    }
    public void setPublisher(String editora){
        this.publisher = editora;
    }
    public void setSamples(int numExemplares){
        this.samples = numExemplares;
    }
    public void setEdition(int edition){
        this.edition = edition;
    }
    public void setYear(int year){
        this.year = year;
    }
    public void setISBN(int ISBN){
        this.ISBN = ISBN;
    }

    public Book(String title, List<String> authors, String publisher,
                int samples, int edition, int year, int ISBN) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.samples = samples;
        this.edition = edition;
        this.year = year;
        this.ISBN = ISBN;
    }

}
