package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoAutor;
import com.capitulozero.config.Theme;
import com.capitulozero.model.AutorModel;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.io.*;

import static java.lang.Long.parseLong;

public class AutoresView extends VBox {

    private final ArrayList<Pane> linhasAutores = new ArrayList<>();
    private Long codigoEdicao = null;

    private TextField codigoInput;
    private TextField nomeInput;
    private TextField nacionalidadeInput;
    private TextField datanascimentoInput;
    private Label labelModoEdicao;

    public void carregarDados() {
        this.getChildren().removeAll(linhasAutores);
        linhasAutores.clear();

        ArrayList<AutorModel> colecao = ColecaoAutor.lerLista();
        for (AutorModel autor : colecao) {
            Label labelCodigo = new Label("Código: " + autor.getCodigo());
            labelCodigo.relocate(30, 0);
            Label labelNome = new Label("Nome: " + autor.getNome());
            labelNome.relocate(130, 0);
            Label labelNacionalidade = new Label("Nacionalidade: " + autor.getNacionalidade());
            labelNacionalidade.relocate(230, 0);
            Label labelData = new Label("Data Nasc.: " + autor.getDataNascimento());
            labelData.relocate(360, 0);

            Button editar = new Button("Editar");
            editar.setOnAction(e -> {
                codigoEdicao = autor.getCodigo();
                codigoInput.setText(String.valueOf(autor.getCodigo()));
                nomeInput.setText(autor.getNome());
                nacionalidadeInput.setText(autor.getNacionalidade());
                datanascimentoInput.setText(autor.getDataNascimento());
                labelModoEdicao.setText("Editando " + autor.getNome());
            });
            editar.relocate(460, 0);

            Button deletar = new Button("Deletar");
            deletar.setOnAction(e -> {
                ColecaoAutor.deletarAutor(autor.getCodigo());
                carregarDados();
            });
            deletar.relocate(510, 0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(labelCodigo, labelNome, labelNacionalidade, labelData, editar, deletar);
            this.getChildren().add(root);
            linhasAutores.add(root);
        }
    }

    public AutoresView(double largura, double altura) {
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

        Label titulo = new Label("👤 Cadastro de Autores");
        titulo.setTextFill(Theme.COR_TEXT_PRIMARIO);
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(titulo);

        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);

        Label labelCodigo = new Label("Código:");
        codigoInput = new TextField();
        codigoInput.setPromptText("Código");
        Label labelNome = new Label("Nome:");
        nomeInput = new TextField();
        nomeInput.setPromptText("Nome do autor");
        Label labelNacionalidade = new Label("Nacionalidade:");
        nacionalidadeInput = new TextField();
        nacionalidadeInput.setPromptText("Nacionalidade");
        Label labelData = new Label("Data de Nascimento:");
        datanascimentoInput = new TextField();
        datanascimentoInput.setPromptText("DD/MM/AAAA");

        Button submitButton = new Button("Salvar");
        submitButton.setOnAction(e -> {
            try {
                long longCodigo = parseLong(codigoInput.getText());
                String textoNome = nomeInput.getText();
                String textoNacionalidade = nacionalidadeInput.getText();
                String textoData = datanascimentoInput.getText();
                AutorModel autor = new AutorModel(textoNome, textoNacionalidade, longCodigo, textoData);

                if (codigoEdicao != null) {
                    ColecaoAutor.editarAutor(codigoEdicao, autor);
                    codigoEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoAutor.adicionarAutor(autor);
                }

                codigoInput.clear();
                nomeInput.clear();
                nacionalidadeInput.clear();
                datanascimentoInput.clear();

                carregarDados();
            } catch (NumberFormatException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe o Código corretamente.");
                alerta.showAndWait();
            }
        });

        this.getChildren().add(labelCodigo);
        this.getChildren().add(codigoInput);
        this.getChildren().add(labelNome);
        this.getChildren().add(nomeInput);
        this.getChildren().add(labelNacionalidade);
        this.getChildren().add(nacionalidadeInput);
        this.getChildren().add(labelData);
        this.getChildren().add(datanascimentoInput);
        this.getChildren().add(submitButton);

        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);
        Label h1 = new Label("Código");        h1.relocate(30, 0);
        Label h2 = new Label("Nome");          h2.relocate(130, 0);
        Label h3 = new Label("Nacionalidade");  h3.relocate(230, 0);
        Label h4 = new Label("Data Nasc.");    h4.relocate(360, 0);
        Label h5 = new Label("Editar");        h5.relocate(460, 0);
        Label h6 = new Label("Deletar");       h6.relocate(510, 0);
        cabecalho.getChildren().addAll(h1, h2, h3, h4, h5, h6);
        this.getChildren().add(cabecalho);

        carregarDados();
    }
}
