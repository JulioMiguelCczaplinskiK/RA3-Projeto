package com.capitulozero.model;

import java.io.Serializable;

public class VendaModel implements Serializable {
    private int codigo;
    private String data;
    private String cliente;
    private String vendedor;
    private String itensVendidos;
    private double valorTotal;

    public VendaModel(int codigo, String data, String cliente, String vendedor, String itensVendidos,
                      double valorTotal) {
        this.codigo = codigo;
        this.data = data;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.itensVendidos = itensVendidos;
        this.valorTotal = valorTotal;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getItensVendidos() {
        return itensVendidos;
    }

    public void setItensVendidos(String itensVendidos) {
        this.itensVendidos = itensVendidos;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}