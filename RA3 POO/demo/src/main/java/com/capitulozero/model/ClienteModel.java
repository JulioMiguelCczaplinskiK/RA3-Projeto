package com.capitulozero.model;

import java.io.Serializable;

public class ClienteModel implements Serializable {
    String nome;
    int cpf;
    double comissao;

    public ClienteModel(String nome, int matricula, double comissao){
        this.nome = nome;
        this.comissao = comissao;
    }


}
