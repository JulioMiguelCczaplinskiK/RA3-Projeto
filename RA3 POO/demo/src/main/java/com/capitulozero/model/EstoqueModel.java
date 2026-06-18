package com.capitulozero.model;

import java.io.Serializable;

public class EstoqueModel implements Serializable { // esse Serializable é a interface que permite transformar o objeto em uma sequencia de bytes para podermos armazenar e requerir eles depois

    private int codigo;
    private String item;
    private int quantidadeDisponivel;

    public EstoqueModel (int codigo, String item, int quantidadeDisponivel) {
        this.codigo = codigo;
        this.item = item;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
