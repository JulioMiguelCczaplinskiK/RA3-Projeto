package com.capitulozero.colecao;


import com.capitulozero.model.ClienteModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoClientes {

    private static final String CAMINHO_ARQUIVO = "clientes.ser";

    // Escreve uma lista de objetos no arquivo
    public static void salvarLista(ArrayList<ClienteModel> clientes) {
        FileOutputStream f;
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(clientes);
            oos.close();
            System.out.println("Lista de clientes salva com sucesso.");
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    // Lê uma lista de objetos do arquivo
    public static ArrayList<ClienteModel> lerLista() {
        ArrayList<ClienteModel> lista = new ArrayList<>();
        try  {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<ClienteModel>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona uma nova pessoa à lista e regrava o arquivo
    public static void adicionarCliente(ClienteModel novoCliente) {
        ArrayList<ClienteModel> clientes = lerLista();
        clientes.add(novoCliente);
        salvarLista(clientes);
    }
}
