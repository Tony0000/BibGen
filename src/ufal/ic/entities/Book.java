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
    private String isbn;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String publisher;

    @Column
    private int samples;

    @ManyToMany(mappedBy = "booksList")
    private List<User> listUsers;

    // Constructor no argumento
    public Book(){}

    public String getTitle(){
        return this.title;
    }
    public String getAuthor(){
        return author;
    }
    public String getPublisher(){
        return publisher;
    }
    public int getSamples(){
        return samples;
    }
    public String getIsbn(){
        return isbn;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setPublisher(String editora){
        this.publisher = editora;
    }
    public void setSamples(int samples){
        this.samples = samples;
    }
    public void setIsbn(String ISBN){
        this.isbn = ISBN;
    }

    public Book(String title, String author, String publisher,
                int samples, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.samples = samples;
        this.isbn = isbn;
    }

    public String[] getInfo(){
        String[] fields = new String[4];
        fields[0] = isbn;
        fields[1] = title;
        fields[2] = author;
        fields[3] = publisher;
        fields[4] = String.valueOf(samples);
        return fields;
    }

}
