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

    @OneToMany(mappedBy = "book")
    private List<UsersBook> usersBookList;

    @OneToMany(mappedBy = "book")
    private List<ScheduleBook> usersBookListScheduled;

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

    public List<UsersBook> getUsersBookList() {
        return usersBookList;
    }
    public void setUsersBookList(List<UsersBook> usersBookList) {
        this.usersBookList = usersBookList;
    }

    public List<ScheduleBook> getUsersBookListScheduled() { return usersBookListScheduled; }
    public void setUsersBookListScheduled(List<ScheduleBook> usersBookListScheduled) { this.usersBookListScheduled = usersBookListScheduled;  }

    public Book(String isbn, String title, String author, String publisher,
                int samples) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.samples = samples;
        this.isbn = isbn;
    }

    public String[] getInfo(){
        String[] fields = new String[5];
        fields[0] = isbn;
        fields[1] = title;
        fields[2] = author;
        fields[3] = publisher;
        fields[4] = String.valueOf(samples);
        return fields;
    }

    @Override
    public String toString(){
        return "ISBN: "+getIsbn()+"\nTitle: "+getTitle()+"\nAuthor: "+getAuthor()+"\nPublisher"+getPublisher()+"\nSamples: "+getSamples();
    }
}
