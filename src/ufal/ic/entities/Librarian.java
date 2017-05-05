package ufal.ic.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by manoel on 30/04/2017.
 */
@Entity
@Table(name = "LIBRARIAN")
public class Librarian{

    @Id
    public String login;

    @Column
    public String senha;

    public Librarian(String login, String senha){
        this.login = login;
        this.senha = senha;
    }

    public Librarian(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
