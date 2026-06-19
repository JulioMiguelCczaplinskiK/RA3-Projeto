package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoLivro;
import com.capitulozero.config.Theme;
import com.capitulozero.model.LivroModel;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class LivrosView extends VBox {

    private final ArrayList<Pane> linhasLivros = new ArrayList<>();
    private Integer codigoEdicao = null; // guarda o código do livro sendo editado

    private TextField codigoInput;
    private TextField tituloInput;
    private TextField autorInput;
    private TextField editoraInput;
    private TextField categoriaInput;
    private TextField anoInput;
    private TextField precoInput;
    private Label labelModoEdicao;

    public void carregarDados() {
        this.getChildren().removeAll(linhasLivros);
        linhasLivros.clear();

        ArrayList<LivroModel> colecao = ColecaoLivro.lerLista();
        for (LivroModel livro : colecao) {
            Label labelCodigo1 = new Label("Código: " + livro.getCodigo());
            labelCodigo1.relocate(30, 0);

            Label labelTitulo1 = new Label("Título: " + livro.getTitulo());
            labelTitulo1.relocate(100, 0);

            Label labelAutor1 = new Label("Autor: " + livro.getAutor());
            labelAutor1.relocate(250, 0);

            Label labelEditora1 = new Label("Editora: " + livro.getEditora());
            labelEditora1.relocate(370, 0);

            Label labelCategoria1 = new Label("Categoria: " + livro.getCategoria());
            labelCategoria1.relocate(500, 0);

            Label labelAno1 = new Label("Ano: " + livro.getAno());
            labelAno1.relocate(640, 0);

            Label labelPreco1 = new Label("Preço: R$ " + String.format("%.2f", livro.getPreco()));
            labelPreco1.relocate(710, 0);

            Button editar = new Button("Editar");
            editar.setOnAction(event -> {
                codigoEdicao = livro.getCodigo();
                codigoInput.setText(String.valueOf(livro.getCodigo()));
                tituloInput.setText(livro.getTitulo());
                autorInput.setText(livro.getAutor());
                editoraInput.setText(livro.getEditora());
                categoriaInput.setText(livro.getCategoria());
                anoInput.setText(String.valueOf(livro.getAno()));
                precoInput.setText(String.valueOf(livro.getPreco()));
                labelModoEdicao.setText("Editando " + livro.getTitulo());
            });
            editar.relocate(830, 0);

            Button deletar = new Button("Deletar");
            deletar.setOnAction(event -> {
                ColecaoLivro.deletarLivro(livro.getCodigo());
                carregarDados();
            });
            deletar.relocate(910, 0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(
                    labelCodigo1,
                    labelTitulo1,
                    labelAutor1,
                    labelEditora1,
                    labelCategoria1,
                    labelAno1,
                    labelPreco1,
                    editar,
                    deletar
            );

            this.getChildren().add(root);
            linhasLivros.add(root);
        }
    }

    public LivrosView(double largura, double altura) {
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

        Label label = new Label("📚 Cadastro de Livros");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);

        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);

        Label labelCodigo = new Label("Código:");
        codigoInput = new TextField();
        codigoInput.setPromptText("Código do livro");

        Label labelTitulo = new Label("Título:");
        tituloInput = new TextField();
        tituloInput.setPromptText("Título do livro");

        Label labelAutor = new Label("Autor:");
        autorInput = new TextField();
        autorInput.setPromptText("Autor do livro");

        Label labelEditora = new Label("Editora:");
        editoraInput = new TextField();
        editoraInput.setPromptText("Editora do livro");

        Label labelCategoria = new Label("Categoria:");
        categoriaInput = new TextField();
        categoriaInput.setPromptText("Categoria do livro");

        Label labelAno = new Label("Ano:");
        anoInput = new TextField();
        anoInput.setPromptText("Ano de publicação");

        Label labelPreco = new Label("Preço:");
        precoInput = new TextField();
        precoInput.setPromptText("Preço do livro");

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(event -> {
            try {
                int intCodigo = parseInt(codigoInput.getText());
                String textoTitulo = tituloInput.getText();
                String textoAutor = autorInput.getText();
                String textoEditora = editoraInput.getText();
                String textoCategoria = categoriaInput.getText();
                int intAno = parseInt(anoInput.getText());
                double doublePreco = parseDouble(precoInput.getText().replace(",", "."));

                if (textoTitulo.isBlank() || textoAutor.isBlank() || textoEditora.isBlank() || textoCategoria.isBlank()) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos de texto.");
                    alerta.showAndWait();
                    return;
                }

                if (intAno <= 0 || doublePreco <= 0) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Ano e preço devem ser maiores que zero.");
                    alerta.showAndWait();
                    return;
                }

                LivroModel livro = new LivroModel(
                        intCodigo,
                        textoTitulo,
                        textoAutor,
                        textoEditora,
                        textoCategoria,
                        intAno,
                        doublePreco
                );

                if (codigoEdicao != null) {
                    ColecaoLivro.editarLivro(codigoEdicao, livro);
                    codigoEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoLivro.adicionarLivro(livro);
                }

                codigoInput.clear();
                tituloInput.clear();
                autorInput.clear();
                editoraInput.clear();
                categoriaInput.clear();
                anoInput.clear();
                precoInput.clear();
                carregarDados();

            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe Código, Ano e Preço corretamente.");
                alerta.showAndWait();
            }
        });

        this.getChildren().addAll(
                labelCodigo, codigoInput,
                labelTitulo, tituloInput,
                labelAutor, autorInput,
                labelEditora, editoraInput,
                labelCategoria, categoriaInput,
                labelAno, anoInput,
                labelPreco, precoInput,
                btnSalvar
        );

        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);

        Label labelCodigo0 = new Label("Código");
        labelCodigo0.relocate(30, 0);

        Label labelTitulo0 = new Label("Título");
        labelTitulo0.relocate(100, 0);

        Label labelAutor0 = new Label("Autor");
        labelAutor0.relocate(250, 0);

        Label labelEditora0 = new Label("Editora");
        labelEditora0.relocate(370, 0);

        Label labelCategoria0 = new Label("Categoria");
        labelCategoria0.relocate(500, 0);

        Label labelAno0 = new Label("Ano");
        labelAno0.relocate(640, 0);

        Label labelPreco0 = new Label("Preço");
        labelPreco0.relocate(710, 0);

        Label editar0 = new Label("Editar");
        editar0.relocate(830, 0);

        Label deletar0 = new Label("Deletar");
        deletar0.relocate(910, 0);

        cabecalho.getChildren().addAll(
                labelCodigo0,
                labelTitulo0,
                labelAutor0,
                labelEditora0,
                labelCategoria0,
                labelAno0,
                labelPreco0,
                editar0,
                deletar0
        );
        this.getChildren().add(cabecalho);

        carregarDados();
    }
}