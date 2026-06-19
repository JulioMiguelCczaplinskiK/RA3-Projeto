package com.capitulozero.colecao;

import com.capitulozero.model.LivroModel;
import java.io.*;
import java.util.ArrayList;

public class ColecaoLivros {
    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/livros.ser";

    public static void salvarLista(ArrayList<LivroModel> livros) {
        FileOutputStream f;
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(livros);
            oos.close();
            System.out.println("Lista de livros salva com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<LivroModel> lerLista() {
        ArrayList<LivroModel> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq));
                lista = (ArrayList<LivroModel>) ois.readObject();
                ois.close();
                System.out.println("Lista de livros carregada com sucesso.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler livros: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona um novo livro à lista e regrava o arquivo
    public static void adicionarLivro(LivroModel livro) {
        ArrayList<LivroModel> lista = lerLista();
        lista.add(livro);
        salvarLista(lista);
    }

    // Deleta livro da lista caso o código seja igual
    public static void deletarLivro(int codigo) {
        ArrayList<LivroModel> lista = lerLista();
        lista.removeIf(l -> l.getCodigo() == codigo);
        salvarLista(lista);
    }

    // Substitui os dados do livro identificado pelo código original
    public static void editarLivro(int codigo, LivroModel novo) {
        ArrayList<LivroModel> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo() == codigo) {
                lista.set(i, novo);
                break;
            }
        }
        salvarLista(lista);
    }
}
