package com.capitulozero.colecao;

import com.capitulozero.model.ProdutoPapelariaModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoProdutoPapelaria {

    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/produtos_papelaria.ser";

    public static void salvarLista(ArrayList<ProdutoPapelariaModel> produtos) {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(produtos);
            oos.close();
            System.out.println("Lista de produtos de papelaria salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public static ArrayList<ProdutoPapelariaModel> lerLista() {
        ArrayList<ProdutoPapelariaModel> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq));
                lista = (ArrayList<ProdutoPapelariaModel>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    public static void adicionar(ProdutoPapelariaModel novoProduto) {
        ArrayList<ProdutoPapelariaModel> produtos = lerLista();
        produtos.add(novoProduto);
        salvarLista(produtos);
    }

    public static void deletar(int codigo) {
        ArrayList<ProdutoPapelariaModel> produtos = lerLista();
        produtos.removeIf(p -> p.getCodigo() == codigo);
        salvarLista(produtos);
    }

    public static void editar(int codigoOriginal, ProdutoPapelariaModel produtoAtualizado) {
        ArrayList<ProdutoPapelariaModel> produtos = lerLista();
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo() == codigoOriginal) {
                produtos.set(i, produtoAtualizado);
                break;
            }
        }
        salvarLista(produtos);
    }
}
