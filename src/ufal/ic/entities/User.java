package ufal.ic.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Maximus on 03/05/2017.
 */
@Entity
@Table(name = "USER")
public class User {

    @Id
    private String enrollment;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String course;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_book",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_book")
    )
    private List<Book> booksList;

    // constructor no argument
    public User(){}

    public User(String enrollment, String name, String email, String course){
        this.enrollment = enrollment;
        this.name = name;
        this.email = email;
        this.course = course;
    }


    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getCourse(){
        return course;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
       this.email = email;
    }
    public void setCourse(String period){
        this.course = period;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String matricula) {
        this.enrollment = matricula;
    }

    @Override
    public String toString(){return "Name: "+getName()+"\nMatricula: "+getEnrollment()+"\nEmail: "+ getEmail()+"\nPeriodo: "+ getCourse();}
}
