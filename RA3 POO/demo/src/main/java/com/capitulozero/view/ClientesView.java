package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoClientes;
import com.capitulozero.colecao.ColecaoVendedores;
import com.capitulozero.config.Theme;
import com.capitulozero.model.ClienteModel;
import com.capitulozero.model.VendedorModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.io.*;

import static java.lang.Integer.parseInt;
public class ClientesView extends VBox {

    private final ArrayList<Pane> linhasClientes = new ArrayList<>();
    private Long cpfEdicao = null;

    private TextField nomeInput;
    private TextField cpfInput;
    private TextField telefoneInput;
    private TextField enderecoInput;
    private Label labelModoEdicao;

    public void carregarDados(){
        this.getChildren().removeAll(linhasClientes);
        linhasClientes.clear();

        ArrayList<ClienteModel> colecao = ColecaoClientes.lerLista();
        for (ClienteModel cliente1 : colecao){
            // nome
            Label labelNome1 = new Label("Nome: " + cliente1.getNome());
            labelNome1.relocate(30,0);
            // cpf
            Label labelCpf1 = new Label("CPF: " + cliente1.getCpf());
            labelCpf1.relocate(130,0);
            // telefone
            Label labelTelefone1 = new Label("Telefone: " + cliente1.getTelefone());
            labelTelefone1.relocate(230,0);
            // Endereço
            Label labelEndereco1 = new Label("Endereço: " + cliente1.getEndereco());
            labelEndereco1.relocate(330,0);

            Button editar = new Button("Editar");
            editar.setOnAction(e -> {
                cpfEdicao = cliente1.getCpf();
                nomeInput.setText(cliente1.getNome());
                telefoneInput.setText(Long.toString(cliente1.getTelefone()));
                cpfInput.setText(String.valueOf(cliente1.getCpf()));
                enderecoInput.setText(cliente1.getEndereco());
                labelModoEdicao.setText("Editando " + cliente1.getNome());
            });
            editar.relocate(430,0);
            Button deletar = new Button("Deletar");
            deletar.setOnAction(e -> {
                ColecaoClientes.deletarCliente(cliente1.getCpf());
                carregarDados();
            });
            deletar.relocate(530,0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(labelNome1,labelCpf1,labelTelefone1,labelEndereco1,editar,deletar);
            this.getChildren().add(root);
            linhasClientes.add(root);
        }
    }
    public ClientesView(double largura, double altura) {
        this.getStylesheets().add(
                getClass().getResource("/com/capitulozero/style/style.css").toExternalForm()
        );
        this.setPrefSize(largura, altura);
        this.setMaxSize(largura, altura);
        this.setPadding(new Insets(20));
        this.setBackground(new Background(new BackgroundFill(Theme.COR_BACKGROUND_TERCEARIO, CornerRadii.EMPTY, Insets.EMPTY)));
        File pastaColecao = new File("src/main/java/com/capitulozero/colecao");
        if (!pastaColecao.exists()) {
            pastaColecao.mkdirs();
        }
        File arquivo = new File(pastaColecao, "colecao.ser");
        Label label = new Label("👥 Cadastro de Clientes");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);
        // título do modo de edição (fica vazio fora do modo de edição)
        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);
        // nome
        Label labelNome = new Label("Nome:");
        nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        // CPF
        Label labelCpf = new Label("CPF:");
        cpfInput = new TextField();
        cpfInput.setPromptText("CPF (ex : 257.280.180-92) : ");
        // Telefone
        Label labelTelefone = new Label("Telefone:");
        telefoneInput = new TextField();
        telefoneInput.setPromptText("Telefone (ex: (41) 98494-1140) :");
        // Endereço
        Label labelEndereco = new Label("Endereço:");
        enderecoInput = new TextField();
        enderecoInput.setPromptText("Endereço (ex: Rua bonita) :");

        // Envio

        Button submitButton = new Button("Salvar");
        submitButton.setOnAction(e -> {
            try {
                // primeiro pega os dados
                String textoNome = nomeInput.getText();
                long longCpf = Long.parseLong(cpfInput.getText());
                long longTelefone = Long.parseLong(telefoneInput.getText());
                String endereco = enderecoInput.getText();
                ClienteModel cliente = new ClienteModel(textoNome, longCpf, longTelefone, endereco);

                if (cpfEdicao != null) {
                    ColecaoClientes.editarCliente(cpfEdicao, cliente);
                    cpfEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoClientes.adicionarCliente(cliente);
                }

                nomeInput.clear();
                cpfInput.clear();
                telefoneInput.clear();
                enderecoInput.clear();

                carregarDados();
            } catch (NumberFormatException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe os valores de Matrícula e Comissão corretamente.");
                alerta.showAndWait();
            }
        });
        this.getChildren().add(labelNome);
        this.getChildren().add(nomeInput);
        this.getChildren().add(labelCpf);
        this.getChildren().add(cpfInput);
        this.getChildren().add(labelTelefone);
        this.getChildren().add(telefoneInput);
        this.getChildren().add(labelEndereco);
        this.getChildren().add(enderecoInput);
        this.getChildren().add(submitButton);

        // Cabeçalho da tabela: criado uma única vez, fica fixo no topo da lista
        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);
        Label labelNome0 = new Label("Nome");
        labelNome0.relocate(30,0);
        Label labelCpf0 = new Label("CPF");
        labelCpf0.relocate(130,0);
        Label labelTelefone0 = new Label("Telefone");
        labelTelefone0.relocate(230,0);
        Label labelEndereco0 = new Label("Endereço");
        labelEndereco0.relocate(330,0);
        Label editar0 = new Label("Editar");
        editar0.relocate(430,0);
        Label deletar0 = new Label("Deletar");
        deletar0.relocate(530,0);
        cabecalho.getChildren().addAll(labelNome0, labelCpf0, labelTelefone0,labelEndereco0 ,editar0, deletar0);
        this.getChildren().add(cabecalho);

        carregarDados();
    }
}