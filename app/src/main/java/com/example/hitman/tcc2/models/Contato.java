package com.example.hitman.tcc2.models;

import java.util.Date;

/**
 * Created by Hitman on 26/12/2015.
 */
public class Contato {

    int id;
    String nome, email, senha, date, endereco;


    public Contato(){
        this.id = 0;
    }

    public Contato(int id, Date date1){
        this.id = 0;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}// fim da class Contato
