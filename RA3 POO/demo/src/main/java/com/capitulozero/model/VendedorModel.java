package com.capitulozero.model;

import java.io.Serializable;

public class VendedorModel implements Serializable {
    private String nome;
    private int matricula;
    private double comissao;

    public VendedorModel(String nome, int matricula, double comissao){
        this.nome = nome;
        this.matricula = matricula;
        this.comissao = comissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public double getComissao() {
        return comissao;
    }

    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

}