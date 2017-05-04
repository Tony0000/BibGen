package ufal.ic.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by manoel on 30/04/2017.
 */

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id_person")
    private Long idPerson;

    @Column
    public String name;
    public Date birthDate;
    public String address;
    public String phone;
    public String email;
    public String login;
    public String pass;

    // Constructor no argumento for Hibernate
    public Person(){}

    //getters and setters
    public Long getIdPerson(){
        return idPerson;
    }
    public String getName(){
        return this.name;
    }
    public Date getBirthDate(){
        return this.birthDate;
    }
    public String getAddress(){
        return this.address;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getEmail(){
        return this.email;
    }
    public String getLogin(){
        return this.login;
    }
    public String getPass(){
        return this.pass;
    }

    public void setId(Long idPerson){
        this.idPerson = idPerson;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBirthDate( Date birthDate){
        this.birthDate = birthDate;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setLogin(String login){
        this.login = login;
    }
    public void setPass(String pass){
        this.pass = pass;
    }

    public Person(String name, Date birthDate, String address,
                  String phone, String email){
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    protected void changePassword(String novaSenha){
        pass = novaSenha;
    }

    protected boolean doLogin(String login, String senha){
        if(this.login == login){
            if(this.pass == senha){
                return true;
            }
            return false;
        }else{
            return false;
        }
    }


}
