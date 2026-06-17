package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoVendedores;
import com.capitulozero.config.Theme;
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
public class VendedoresView extends VBox {

    private final ArrayList<Pane> linhasVendedores = new ArrayList<>();
    private Integer matriculaEdicao = null;

    private TextField nomeInput;
    private TextField matriculaInput;
    private TextField comissaoInput;
    private Label labelModoEdicao;

    public void carregarDados(){
        this.getChildren().removeAll(linhasVendedores);
        linhasVendedores.clear();

        ArrayList<VendedorModel> colecao = ColecaoVendedores.lerLista();
        for (VendedorModel vendedor1 : colecao){
            // nome
            Label labelNome1 = new Label("Nome: " + vendedor1.getNome());
            labelNome1.relocate(30,0);
            // matricula
            Label labelMatricula1 = new Label("Matrícula: " + vendedor1.getMatricula());
            labelMatricula1.relocate(130,0);
            // comissão
            Label labelComissao1 = new Label("Comissão: " + vendedor1.getComissao());
            labelComissao1.relocate(230,0);

            Button editar = new Button("Editar");
            editar.setOnAction(e -> {
                matriculaEdicao = vendedor1.getMatricula();
                nomeInput.setText(vendedor1.getNome());
                matriculaInput.setText(String.valueOf(vendedor1.getMatricula()));
                comissaoInput.setText(String.valueOf(vendedor1.getComissao()));
                labelModoEdicao.setText("Editando " + vendedor1.getNome());
            });
            editar.relocate(330,0);
            Button deletar = new Button("Deletar");
            deletar.setOnAction(e -> {
                ColecaoVendedores.deletarVendedor(vendedor1.getMatricula());
                carregarDados();
            });
            deletar.relocate(430,0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(labelNome1,labelMatricula1,labelComissao1,editar,deletar);
            this.getChildren().add(root);
            linhasVendedores.add(root);
        }
    }
    public VendedoresView(double largura, double altura) {
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
        File arquivo = new File(pastaColecao, "vendedores.ser");
        Label label = new Label("👥 Cadastro de Vendedores");
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
        // matricula
        Label labelMatricula = new Label("Matricula:");
        matriculaInput = new TextField();
        matriculaInput.setPromptText("Código de matrícula");
        // comissão
        Label labelComissao = new Label("Comissão:");
        comissaoInput = new TextField();
        comissaoInput.setPromptText("Comissão");
        // Envio

        Button submitButton = new Button("Salvar");
        submitButton.setOnAction(e -> {
            try {
                // primeiro pega os dados
                String textoNome = nomeInput.getText();
                int intMatricula = parseInt(matriculaInput.getText());
                double doubleComissao = Double.parseDouble(comissaoInput.getText());
                VendedorModel vendedor = new VendedorModel(textoNome, intMatricula, doubleComissao);

                if (matriculaEdicao != null) {
                    ColecaoVendedores.editarVendedor(matriculaEdicao, vendedor);
                    matriculaEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoVendedores.adicionarVendedor(vendedor);
                }

                nomeInput.clear();
                matriculaInput.clear();
                comissaoInput.clear();

                carregarDados();
            } catch (NumberFormatException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe os valores de Matrícula e Comissão corretamente.");
                alerta.showAndWait();
            }
        });
        this.getChildren().add(labelNome);
        this.getChildren().add(nomeInput);
        this.getChildren().add(labelMatricula);
        this.getChildren().add(matriculaInput);
        this.getChildren().add(labelComissao);
        this.getChildren().add(comissaoInput);
        this.getChildren().add(submitButton);

        // Cabeçalho da tabela: criado uma única vez, fica fixo no topo da lista
        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);
        Label labelNome0 = new Label("Nome");
        labelNome0.relocate(30,0);
        Label labelMatricula0 = new Label("Matrícula");
        labelMatricula0.relocate(130,0);
        Label labelComissao0 = new Label("Comissão");
        labelComissao0.relocate(230,0);
        Label editar0 = new Label("Editar");
        editar0.relocate(330,0);
        Label deletar0 = new Label("Deletar");
        deletar0.relocate(430,0);
        cabecalho.getChildren().addAll(labelNome0, labelMatricula0, labelComissao0, editar0, deletar0);
        this.getChildren().add(cabecalho);

        carregarDados();
    }
}