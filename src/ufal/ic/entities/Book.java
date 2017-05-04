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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_book")
    private Long id;

    @Column
    private String title;
    @ElementCollection(targetClass=String.class)
    private List<String> authors;

    @Column
    private String editora;

    @Column
    private String status;

    @Column
    private int numExemplares;

    @Column
    private int edition;

    @Column
    private int year;

    @Column
    private int ISBN;


    @ManyToMany(mappedBy = "booksList")
    private List<User> listUsers;

    // Constructor no argumento
    public Book(){}

    //getters and setters
    public Long getId(){
        return id;
    }

    public String getTitle(){
        return this.title;
    }
    public List<String> getAuthors(){
        return authors;
    }
    public String getEditora(){
        return editora;
    }
    public String getStatus(){
        return status;
    }
    public int getNumExemplares(){
        return numExemplares;
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


    public void setId(Long id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthors(List<String> authors){
        this.authors = authors;
    }
    public void setEditora(String editora){
        this.editora = editora;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setNumExemplares(int numExemplares){
        this.numExemplares = numExemplares;
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
