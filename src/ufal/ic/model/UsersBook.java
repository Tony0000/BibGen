package ufal.ic.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Maximus on 06/05/2017.
 */
@Entity
@Table(name = "USERS_BOOKS")
public class UsersBook {

    private long id;
    private User user;
    private Book book;

    // additional fields
    private Date dataLocacao;
    private Date dataEntrega;

    public UsersBook(){  }

    @Id
    @GeneratedValue
    @Column(name = "USER_BOOK_ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }


    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }



}
