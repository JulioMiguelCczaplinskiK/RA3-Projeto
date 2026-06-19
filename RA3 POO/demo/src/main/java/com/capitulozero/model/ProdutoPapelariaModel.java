package com.capitulozero.model;

import java.io.Serializable;

public class ProdutoPapelariaModel implements Serializable {
    private int codigo;
    private String nome;
    private String tipo;
    private String marca;
    private String categoria;
    private String fornecedor;
    private double precoUnitario;

    public ProdutoPapelariaModel(int codigo, String nome, String tipo, String marca, String categoria, String fornecedor, double precoUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
        this.marca = marca;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.precoUnitario = precoUnitario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
