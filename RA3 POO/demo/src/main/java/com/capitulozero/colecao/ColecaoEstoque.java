package com.capitulozero.colecao;

import com.capitulozero.model.EstoqueModel;
import com.capitulozero.model.VendedorModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoEstoque {
    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/estoque.ser";

    public static void salvarNaLista(ArrayList<EstoqueModel> estoque) {

        FileOutputStream f;

        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if(!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(estoque);
            oos.close();

            System.out.println("Lista de estoque salva com sucesso.");
        }catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());

        }catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public static ArrayList<EstoqueModel> lerLista() {
        ArrayList<EstoqueModel> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<EstoqueModel>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return lista;
    }

    public static void adicionar(EstoqueModel novoItem) {
        ArrayList<EstoqueModel> estoque = lerLista();
        estoque.add(novoItem);
        salvarNaLista(estoque);
    }

    public static void deletar(int codigo) {
        ArrayList<EstoqueModel> estoque = lerLista();
        estoque.removeIf(e -> e.getCodigo() == codigo);
        salvarNaLista(estoque);
    }

    public static void editar(int codigoOriginal, EstoqueModel estoqueAtualizado) {
        ArrayList<EstoqueModel> estoque = lerLista();
        for (int i = 0; i < estoque.size(); i++) {
            if (estoque.get(i).getCodigo() == codigoOriginal) {
                estoque.set(i, estoqueAtualizado);
                break;
            }
        }
        salvarNaLista(estoque);
    }
}
