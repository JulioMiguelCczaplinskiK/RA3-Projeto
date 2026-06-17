package com.capitulozero.colecao;


import com.capitulozero.model.VendedorModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoVendedores {

    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/vendedores.ser";

    // Escreve uma lista de objetos no arquivo
    public static void salvarLista(ArrayList<VendedorModel> vendedores) {
        FileOutputStream f;
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(vendedores);
            oos.close();
            System.out.println("Lista de vendedores salva com sucesso.");
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    // Lê uma lista de objetos do arquivo
    public static ArrayList<VendedorModel> lerLista() {
        ArrayList<VendedorModel> lista = new ArrayList<>();
        try  {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO));
                lista = (ArrayList<VendedorModel>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    // Adiciona um novo vendedor à lista e regrava o arquivo
    public static void adicionarVendedor(VendedorModel novoVendedor) {
        ArrayList<VendedorModel> vendedores = lerLista();
        vendedores.add(novoVendedor);
        salvarLista(vendedores);
    }
    // Deleta vendedor da lista caso a matrícula seja igual
    public static void deletarVendedor(int matricula) {
        ArrayList<VendedorModel> vendedores = lerLista();
        vendedores.removeIf(v -> v.getMatricula() == matricula);
        salvarLista(vendedores);
    }

    // Substitui os dados do vendedor identificado pela matrícula original
    public static void editarVendedor(int matriculaOriginal, VendedorModel vendedorAtualizado) {
        ArrayList<VendedorModel> vendedores = lerLista();
        for (int i = 0; i < vendedores.size(); i++) {
            if (vendedores.get(i).getMatricula() == matriculaOriginal) {
                vendedores.set(i, vendedorAtualizado);
                break;
            }
        }
        salvarLista(vendedores);
    }
}