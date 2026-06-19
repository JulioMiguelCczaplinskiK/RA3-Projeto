package com.capitulozero.colecao;


import com.capitulozero.model.AutorModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoAutor {

    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/autor.ser";

    // Escreve uma lista de objetos no arquivo
    public static void salvarLista(ArrayList<AutorModel> autor) {
        FileOutputStream f;
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(autor);
            oos.close();
            System.out.println("Lista de autor salva com sucesso.");
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    // Lê uma lista de objetos do arquivo
    public static ArrayList<AutorModel> lerLista() {
        ArrayList<AutorModel> lista = new ArrayList<>();
        try  {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<AutorModel>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona um novo cliente à lista e regrava o arquivo
    public static void adicionarAutor(AutorModel novoAutor) {
        ArrayList<AutorModel> autor = lerLista();
        autor.add(novoAutor);
        salvarLista(autor);
    }
    // Deleta cliente da lista caso a matrícula seja igual
    public static void deletarAutor(long codigo) {
        ArrayList<AutorModel> autor = lerLista();
        autor.removeIf(v -> v.getCodigo() == codigo);
        salvarLista(autor);
    }

    // Substitui os dados do vendedor identificado pela matrícula original
    public static void editarAutor(long codigoOriginal, AutorModel autorAtualizado) {
        ArrayList<AutorModel> autor = lerLista();
        for (int i = 0; i < autor.size(); i++) {
            if (autor.get(i).getCodigo() == codigoOriginal) {
                autor.set(i, autorAtualizado);
                break;
            }
        }
        salvarLista(autor);
    }
}