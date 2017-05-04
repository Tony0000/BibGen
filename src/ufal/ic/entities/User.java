package ufal.ic.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Maximus on 03/05/2017.
 */
@Entity
@Table(name = "usuario")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private String course;

    @Column
    private String period;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_book",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_book")
    )
    private List<Book> booksList;

    // constructor no argument
    public User(){}

    public User(String name, String course, String period){
        this.name = name;
        this.course = course;
        this.period = period;
    }


    public String getName(){
        return name;
    }
    public String getCourse(){
        return course;
    }
    public String getPeriod(){
        return period;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setCourse(String course){
       this.course = course;
    }
    public void setPeriod(String period){
        this.period = period;
    }
}
