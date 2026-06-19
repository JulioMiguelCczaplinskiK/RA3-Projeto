package com.capitulozero.colecao;

import com.capitulozero.model.EditoraModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoEditoras {

    private static File getArquivo() {
        File pastaColecao = new File("src/main/java/com/capitulozero/colecao");

        if (!pastaColecao.exists()) {
            pastaColecao.mkdirs();
        }

        return new File(pastaColecao, "editoras.ser");
    }

    public static ArrayList<EditoraModel> lerLista() {
        File arquivo = getArquivo();

        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<EditoraModel>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private static void salvarLista(ArrayList<EditoraModel> listaEditoras) {
        File arquivo = getArquivo();

        try (ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            saida.writeObject(listaEditoras);
        } catch (IOException e) {
            System.out.println("Erro ao salvar editoras: " + e.getMessage());
        }
    }

    public static void adicionar(EditoraModel editora) {
        ArrayList<EditoraModel> listaEditoras = lerLista();
        listaEditoras.add(editora);
        salvarLista(listaEditoras);
    }

    public static void editar(int codigoOriginal, EditoraModel editoraAtualizada) {
        ArrayList<EditoraModel> listaEditoras = lerLista();

        for (int i = 0; i < listaEditoras.size(); i++) {
            if (listaEditoras.get(i).getCodigo() == codigoOriginal) {
                listaEditoras.set(i, editoraAtualizada);
                salvarLista(listaEditoras);
                return;
            }
        }
    }

    public static void deletar(int codigo) {
        ArrayList<EditoraModel> listaEditoras = lerLista();
        listaEditoras.removeIf(editora -> editora.getCodigo() == codigo);
        salvarLista(listaEditoras);
    }
}
