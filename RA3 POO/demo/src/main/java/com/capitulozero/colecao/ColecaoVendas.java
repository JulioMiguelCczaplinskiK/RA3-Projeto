package com.capitulozero.colecao;

import com.capitulozero.model.VendaModel;
import java.io.*;
import java.util.ArrayList;

public class ColecaoVendas {
    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/vendas.ser";

    // Save list of vendas
    public static void salvarLista(ArrayList<VendaModel> vendas) {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(vendas);
            oos.close();
            System.out.println("Lista de vendas salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista de vendas: " + e.getMessage());
        }
    }

    // Load list of vendas
    public static ArrayList<VendaModel> lerLista() {
        ArrayList<VendaModel> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq));
                lista = (ArrayList<VendaModel>) ois.readObject();
                ois.close();
                System.out.println("Lista de livros carregada com sucesso.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista de vendas: " + e.getMessage());
        }
        return lista;
    }

    public static void adicionarVenda(VendaModel novaVenda) {
        ArrayList<VendaModel> vendas = lerLista();
        vendas.add(novaVenda);
        salvarLista(vendas);
    }

    public static void deletarVenda(int codigo) {
        ArrayList<VendaModel> vendas = lerLista();
        vendas.removeIf(v -> v.getCodigo() == codigo);
        salvarLista(vendas);
    }

    public static void editarVenda(int codigoOriginal, VendaModel vendaAtualizada) {
        ArrayList<VendaModel> vendas = lerLista();
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getCodigo() == codigoOriginal) {
                vendas.set(i, vendaAtualizada);
                break;
            }
        }
        salvarLista(vendas);
    }
}
