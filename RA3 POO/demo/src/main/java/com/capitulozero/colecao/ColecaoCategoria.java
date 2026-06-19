package com.capitulozero.colecao;


import com.capitulozero.model.CategoriaModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoCategoria {

    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/categorias.ser";

    // Escreve uma lista de objetos no arquivo
    public static void salvarLista(ArrayList<CategoriaModel> categorias) {
        FileOutputStream f;
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(categorias);
            oos.close();
            System.out.println("Lista de categorias salva com sucesso.");
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    // Lê uma lista de objetos do arquivo
    public static ArrayList<CategoriaModel> lerLista() {
        ArrayList<CategoriaModel> lista = new ArrayList<>();
        try  {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<CategoriaModel>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona uma nova categoria à lista e regrava o arquivo
    public static void adicionarCategoria(CategoriaModel novaCategoria) {
        ArrayList<CategoriaModel> categorias = lerLista();
        categorias.add(novaCategoria);
        salvarLista(categorias);
    }
    // Deleta categoria da lista caso o código seja igual
    public static void deletarCategoria(int codigo) {
        ArrayList<CategoriaModel> categorias = lerLista();
        categorias.removeIf(c -> c.getCodigo() == codigo);
        salvarLista(categorias);
    }

    // Substitui os dados da categoria identificada pelo código original
    public static void editarCategoria(int codigoOriginal, CategoriaModel categoriaAtualizada) {
        ArrayList<CategoriaModel> categorias = lerLista();
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getCodigo() == codigoOriginal) {
                categorias.set(i, categoriaAtualizada);
                break;
            }
        }
        salvarLista(categorias);
    }
}