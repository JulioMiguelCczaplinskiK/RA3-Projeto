package com.capitulozero.model;

import java.io.Serializable;

public class AutorModel implements Serializable {
    private String nome;
    private String nacionalidade;
    private long codigo;
    private String datanascimento;
    public AutorModel(String nome, String nacionalidade, long codigo, String datanascimento) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.codigo = codigo;
        this.datanascimento = datanascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getDataNascimento() {
        return datanascimento;
    }

    public void setDataNascimento(String datanascimento) {
        this.datanascimento = datanascimento;
    }
}
