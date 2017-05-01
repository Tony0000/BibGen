package ufal.ic.entities;

import java.util.Date;

/**
 * Created by manoel on 30/04/2017.
 */
public abstract class Person {
    public String name;
    public Date birthDate;
    public String address;
    public String phone;
    public String email;
    public String login;
    public String pass;

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
