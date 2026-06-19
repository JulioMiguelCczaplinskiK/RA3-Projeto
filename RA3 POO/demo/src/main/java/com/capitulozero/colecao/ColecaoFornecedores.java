package com.capitulozero.colecao;

import com.capitulozero.model.FornecedorModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoFornecedores {

    private static File getArquivo() {
        File pastaColecao = new File("src/main/java/com/capitulozero/colecao");

        if (!pastaColecao.exists()) {
            pastaColecao.mkdirs();
        }

        return new File(pastaColecao, "fornecedores.ser");
    }

    public static ArrayList<FornecedorModel> lerLista() {
        File arquivo = getArquivo();

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<FornecedorModel>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private static void salvarLista(ArrayList<FornecedorModel> listaFornecedores) {
        File arquivo = getArquivo();

        try (ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            saida.writeObject(listaFornecedores);
        } catch (IOException e) {
            System.out.println("Erro ao salvar fornecedores: " + e.getMessage());
        }
    }

    public static void adicionar(FornecedorModel fornecedor) {
        ArrayList<FornecedorModel> listaFornecedores = lerLista();
        listaFornecedores.add(fornecedor);
        salvarLista(listaFornecedores);
    }

    public static void editar(int codigoOriginal, FornecedorModel fornecedorAtualizado) {
        ArrayList<FornecedorModel> listaFornecedores = lerLista();

        for (int i = 0; i < listaFornecedores.size(); i++) {
            if (listaFornecedores.get(i).getCodigo() == codigoOriginal) {
                listaFornecedores.set(i, fornecedorAtualizado);
                salvarLista(listaFornecedores);
                return;
            }
        }
    }

    public static void deletar(int codigo) {
        ArrayList<FornecedorModel> listaFornecedores = lerLista();
        listaFornecedores.removeIf(fornecedor -> fornecedor.getCodigo() == codigo);
        salvarLista(listaFornecedores);
    }
}
