package ufal.ic.entities;

import java.util.Date;

/**
 * Created by manoel on 30/04/2017.
 */
public class Librarian extends Person {

    public String codFunc;

    public Librarian(String nome, Date dataNascimento, String endereco,
                     String contato, String email, String codFunc){
        super(nome, dataNascimento, endereco, contato, email);
        this.codFunc = codFunc;
    }

    protected void listarLivros(String titulo){

    }

    protected void listarLivros(int ISBN){

    }

    public void listarEmprestimos(){

    }

    public void listarReservas(){

    }


    protected void consultarUsuario(String nome){

    }

    protected void consultarUsuario(int matricula){

    }

    protected void cadastrarLivro(){

    }

    public void reservarLivro(){

    }
}
