package com.capitulozero.colecao;


import com.capitulozero.model.ClienteModel;
import com.capitulozero.model.VendedorModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoClientes {

    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/clientes.ser";

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

    // Adiciona um novo cliente à lista e regrava o arquivo
    public static void adicionarCliente(ClienteModel novoCliente) {
        ArrayList<ClienteModel> clientes = lerLista();
        clientes.add(novoCliente);
        salvarLista(clientes);
    }
    // Deleta cliente da lista caso a matrícula seja igual
    public static void deletarCliente(long cpf) {
        ArrayList<ClienteModel> clientes = lerLista();
        clientes.removeIf(v -> v.getCpf() == cpf);
        salvarLista(clientes);
    }

    // Substitui os dados do vendedor identificado pela matrícula original
    public static void editarCliente(long cpfOriginal, ClienteModel clienteAtualizado) {
        ArrayList<ClienteModel> clientes = lerLista();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf() == cpfOriginal) {
                clientes.set(i, clienteAtualizado);
                break;
            }
        }
        salvarLista(clientes);
    }
}