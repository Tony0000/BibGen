package ufal.ic.entities;

import java.util.Date;

/**
 * Created by manoel on 30/04/2017.
 */
public class Administrator extends Person {

    public Administrator(String nome, Date dataNascimento, String endereco,
                         String contato, String email) {
        super(nome, dataNascimento, endereco, contato, email);
    }

    protected void cadastrarFuncionario(){

    }

    protected void cadastrarAdmin(String nome, Date dataNascimento, String endereco,
                                  String contato, String email){

    }
}
