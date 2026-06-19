package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoCategoria;
import com.capitulozero.config.Theme;
import com.capitulozero.model.CategoriaModel;
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
public class CategoriaView extends VBox {

    private final ArrayList<Pane> linhasCategorias = new ArrayList<>();
    private Integer codigoEdicao = null;

    private TextField nomeInput;
    private TextField codigoInput;
    private TextField descricaoInput;
    private TextField tipoInput;
    private Label labelModoEdicao;

    public void carregarDados(){
        this.getChildren().removeAll(linhasCategorias);
        linhasCategorias.clear();

        ArrayList<CategoriaModel> colecao = ColecaoCategoria.lerLista();
        for (CategoriaModel categoria1 : colecao){
            // código
            Label labelCodigo1 = new Label("Código: " + categoria1.getCodigo());
            labelCodigo1.relocate(30,0);
            // nome
            Label labelNome1 = new Label("Nome: " + categoria1.getNome());
            labelNome1.relocate(130,0);
            // descrição
            Label labelDescricao1 = new Label("Descrição: " + categoria1.getDescricao());
            labelDescricao1.relocate(230,0);
            // tipo
            Label labelTipo1 = new Label("Tipo: " + categoria1.getTipo());
            labelTipo1.relocate(430,0);

            Button editar = new Button("Editar");
            editar.setOnAction(e -> {
                codigoEdicao = categoria1.getCodigo();
                codigoInput.setText(String.valueOf(categoria1.getCodigo()));
                nomeInput.setText(categoria1.getNome());
                descricaoInput.setText(categoria1.getDescricao());
                tipoInput.setText(categoria1.getTipo());
                labelModoEdicao.setText("Editando " + categoria1.getNome());
            });
            editar.relocate(530,0);
            Button deletar = new Button("Deletar");
            deletar.setOnAction(e -> {
                ColecaoCategoria.deletarCategoria(categoria1.getCodigo());
                carregarDados();
            });
            deletar.relocate(630,0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(labelCodigo1,labelNome1,labelDescricao1,labelTipo1,editar,deletar);
            this.getChildren().add(root);
            linhasCategorias.add(root);
        }
    }
    public CategoriaView(double largura, double altura) {
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
        File arquivo = new File(pastaColecao, "categorias.ser");
        Label label = new Label("📁 Cadastro de Categorias");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);
        // título do modo de edição (fica vazio fora do modo de edição)
        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);
        // código
        Label labelCodigo = new Label("Código:");
        codigoInput = new TextField();
        codigoInput.setPromptText("Código da categoria");
        // nome
        Label labelNome = new Label("Nome:");
        nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        // descrição
        Label labelDescricao = new Label("Descrição:");
        descricaoInput = new TextField();
        descricaoInput.setPromptText("Descrição");
        // tipo
        Label labelTipo = new Label("Tipo:");
        tipoInput = new TextField();
        tipoInput.setPromptText("Tipo");
        // Envio

        Button submitButton = new Button("Salvar");
        submitButton.setOnAction(e -> {
            try {
                // primeiro pega os dados
                int intCodigo = parseInt(codigoInput.getText());
                String textoNome = nomeInput.getText();
                String textoDescricao = descricaoInput.getText();
                String textoTipo = tipoInput.getText();
                CategoriaModel categoria = new CategoriaModel(intCodigo, textoNome, textoDescricao, textoTipo);

                if (codigoEdicao != null) {
                    ColecaoCategoria.editarCategoria(codigoEdicao, categoria);
                    codigoEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoCategoria.adicionarCategoria(categoria);
                }

                codigoInput.clear();
                nomeInput.clear();
                descricaoInput.clear();
                tipoInput.clear();

                carregarDados();
            } catch (NumberFormatException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe o valor de Código corretamente.");
                alerta.showAndWait();
            }
        });
        this.getChildren().add(labelCodigo);
        this.getChildren().add(codigoInput);
        this.getChildren().add(labelNome);
        this.getChildren().add(nomeInput);
        this.getChildren().add(labelDescricao);
        this.getChildren().add(descricaoInput);
        this.getChildren().add(labelTipo);
        this.getChildren().add(tipoInput);
        this.getChildren().add(submitButton);

        // Cabeçalho da tabela: criado uma única vez, fica fixo no topo da lista
        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);
        Label labelCodigo0 = new Label("Código");
        labelCodigo0.relocate(30,0);
        Label labelNome0 = new Label("Nome");
        labelNome0.relocate(130,0);
        Label labelDescricao0 = new Label("Descrição");
        labelDescricao0.relocate(230,0);
        Label labelTipo0 = new Label("Tipo");
        labelTipo0.relocate(430,0);
        Label editar0 = new Label("Editar");
        editar0.relocate(530,0);
        Label deletar0 = new Label("Deletar");
        deletar0.relocate(630,0);
        cabecalho.getChildren().addAll(labelCodigo0, labelNome0, labelDescricao0, labelTipo0, editar0, deletar0);
        this.getChildren().add(cabecalho);

        carregarDados();
    }
}