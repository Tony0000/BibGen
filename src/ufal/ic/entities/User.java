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

    @Column
    private Integer penalty = 0;


    @OneToMany(mappedBy = "user")
    private List<UsersBook> usersBooks;

    @OneToMany(mappedBy = "user")
    private List<ScheduleBook> usersBooksSchedule;

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

    public List<UsersBook> getUsersBooks() { return usersBooks;   }

    public void setUsersBooks(List<UsersBook> usersBooks) { this.usersBooks = usersBooks; }

    public List<ScheduleBook> getUsersBooksSchedule() { return usersBooksSchedule;  }

    public void setUsersBooksSchedule(List<ScheduleBook> usersBooksSchedule) { this.usersBooksSchedule = usersBooksSchedule;  }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }



    @Override
    public String toString(){return "Name: "+getName()+"\nMatricula: "+getEnrollment()+"\nEmail: "+ getEmail()+"\nPeriodo: "+ getCourse();}

    public String[] getInfo(){
        String[] fields = new String[5];
        fields[0] = enrollment;
        fields[1] = name;
        fields[2] = email;
        fields[3] = course;
        fields[4] = "R$ " + penalty.toString();
        return fields;
    }
}
