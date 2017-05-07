package ufal.ic.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Maximus on 07/05/2017.
 */
@Entity
@Table(name = "SCHEDULE_BOOK")
public class ScheduleBook {

    public ScheduleBook(){ }

    private long id;
    private User user;
    private Book book;

    private Date dataReserva;

    @Id
    @GeneratedValue
    @Column(name = "USER_BOOK__SCHEDULE_ID")
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

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }


}
