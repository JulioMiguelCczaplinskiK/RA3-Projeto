package com.capitulozero.model;

import java.io.Serializable;

public class LivroModel implements Serializable {
    private int codigo;
    private String titulo;
    private String autor;
    private String editora;
    private String categoria;
    private int ano;
    private double preco;

    public LivroModel(int codigo, String titulo, String autor, String editora, String categoria, int ano,
                      double preco) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.categoria = categoria;
        this.ano = ano;
        this.preco = preco;
    }

    public LivroModel(String titulo2, int ano2, double preco2) {
        this.titulo = titulo;
        this.ano = ano;
        this.preco = preco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}