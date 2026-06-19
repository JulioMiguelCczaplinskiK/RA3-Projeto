package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoProdutoPapelaria;
import com.capitulozero.config.Theme;
import com.capitulozero.model.ProdutoPapelariaModel;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static java.lang.Double.parseDouble;

public class ProdutoPapelariaView extends VBox {

    private final ArrayList<Pane> linhasProdutos = new ArrayList<>();
    private Integer matriculaEdicao = null; // guarda o código do produto sendo editado

    private TextField codigoInput;
    private TextField nomeInput;
    private TextField tipoInput;
    private TextField marcaInput;
    private TextField categoriaInput;
    private TextField fornecedorInput;
    private TextField precoUnitarioInput;
    private Label labelModoEdicao;

    public void carregarDados() {
        this.getChildren().removeAll(linhasProdutos);
        linhasProdutos.clear();

        ArrayList<ProdutoPapelariaModel> colecao = ColecaoProdutoPapelaria.lerLista();
        for (ProdutoPapelariaModel produto : colecao) {
            Label labelCodigo1 = new Label("Código: " + produto.getCodigo());
            labelCodigo1.relocate(30, 0);

            Label labelNome1 = new Label("Nome: " + produto.getNome());
            labelNome1.relocate(90, 0);

            Label labelTipo1 = new Label("Tipo: " + produto.getTipo());
            labelTipo1.relocate(190, 0);

            Label labelMarca1 = new Label("Marca: " + produto.getMarca());
            labelMarca1.relocate(270, 0);

            Label labelCategoria1 = new Label("Categoria: " + produto.getCategoria());
            labelCategoria1.relocate(350, 0);

            Label labelFornecedor1 = new Label("Fornecedor: " + produto.getFornecedor());
            labelFornecedor1.relocate(450, 0);

            Label labelPreco1 = new Label("Preço: R$ " + String.format("%.2f", produto.getPrecoUnitario()));
            labelPreco1.relocate(570, 0);

            Button editar = new Button("Editar");
            editar.setOnAction(event -> {
                matriculaEdicao = produto.getCodigo();
                codigoInput.setText(String.valueOf(produto.getCodigo()));
                nomeInput.setText(produto.getNome());
                tipoInput.setText(produto.getTipo());
                marcaInput.setText(produto.getMarca());
                categoriaInput.setText(produto.getCategoria());
                fornecedorInput.setText(produto.getFornecedor());
                precoUnitarioInput.setText(String.valueOf(produto.getPrecoUnitario()));
                labelModoEdicao.setText("Editando " + produto.getNome());
            });
            editar.relocate(670, 0);

            Button deletar = new Button("Deletar");
            deletar.setOnAction(event -> {
                ColecaoProdutoPapelaria.deletar(produto.getCodigo());
                carregarDados();
            });
            deletar.relocate(750, 0);

            Pane root = new Pane(); // Pane é um container que permite posicionar elementos em coordenadas exatas
            root.setPrefHeight(30);
            root.getChildren().addAll(labelCodigo1, labelNome1, labelTipo1, labelMarca1, labelCategoria1, labelFornecedor1, labelPreco1, editar, deletar);
            this.getChildren().add(root);
            linhasProdutos.add(root);
        }
    }

    public ProdutoPapelariaView(double largura, double altura) {
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

        Label label = new Label("✏️ Produto Papelaria");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);

        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);

        // Inputs
        Label labelCodigo = new Label("Código:");
        codigoInput = new TextField();
        codigoInput.setPromptText("Código do produto");

        Label labelNome = new Label("Nome:");
        nomeInput = new TextField();
        nomeInput.setPromptText("Nome do produto");

        Label labelTipo = new Label("Tipo:");
        tipoInput = new TextField();
        tipoInput.setPromptText("Tipo do produto");

        Label labelMarca = new Label("Marca:");
        marcaInput = new TextField();
        marcaInput.setPromptText("Marca do produto");

        Label labelCategoria = new Label("Categoria:");
        categoriaInput = new TextField();
        categoriaInput.setPromptText("Categoria do produto");

        Label labelFornecedor = new Label("Fornecedor:");
        fornecedorInput = new TextField();
        fornecedorInput.setPromptText("Fornecedor do produto");

        Label labelPreco = new Label("Preço Unitário:");
        precoUnitarioInput = new TextField();
        precoUnitarioInput.setPromptText("Preço unitário");

        Button btnEnviar = new Button("Salvar");
        btnEnviar.setOnAction(event -> {
            try {
                int intCodigo = parseInt(codigoInput.getText());
                String textoNome = nomeInput.getText();
                String textoTipo = tipoInput.getText();
                String textoMarca = marcaInput.getText();
                String textoCategoria = categoriaInput.getText();
                String textoFornecedor = fornecedorInput.getText();
                double doublePreco = parseDouble(precoUnitarioInput.getText());

                ProdutoPapelariaModel produto = new ProdutoPapelariaModel(
                        intCodigo, textoNome, textoTipo, textoMarca, textoCategoria, textoFornecedor, doublePreco
                );

                if (matriculaEdicao != null) {
                    ColecaoProdutoPapelaria.editar(matriculaEdicao, produto);
                    matriculaEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoProdutoPapelaria.adicionar(produto);
                }

                codigoInput.clear();
                nomeInput.clear();
                tipoInput.clear();
                marcaInput.clear();
                categoriaInput.clear();
                fornecedorInput.clear();
                precoUnitarioInput.clear();
                carregarDados();

            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe os valores corretamente!");
                alerta.showAndWait();
            }
        });

        

        this.getChildren().addAll( //Uma forma mais facil de colocar todos itens
                labelCodigo, codigoInput,
                labelNome, nomeInput,
                labelTipo, tipoInput,
                labelMarca, marcaInput,
                labelCategoria, categoriaInput,
                labelFornecedor, fornecedorInput,
                labelPreco, precoUnitarioInput,
                btnEnviar
        );

        // Cabeçalho da tabela
        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);
        Label labelCodigo0 = new Label("Código");
        labelCodigo0.relocate(30, 0);
        Label labelNome0 = new Label("Nome");
        labelNome0.relocate(90, 0);
        Label labelTipo0 = new Label("Tipo");
        labelTipo0.relocate(190, 0);
        Label labelMarca0 = new Label("Marca");
        labelMarca0.relocate(270, 0);
        Label labelCategoria0 = new Label("Categoria");
        labelCategoria0.relocate(350, 0);
        Label labelFornecedor0 = new Label("Fornecedor");
        labelFornecedor0.relocate(450, 0);
        Label labelPreco0 = new Label("Preço");
        labelPreco0.relocate(570, 0);
        Label editar0 = new Label("Editar");
        editar0.relocate(670, 0);
        Label deletar0 = new Label("Deletar");
        deletar0.relocate(750, 0);
        cabecalho.getChildren().addAll(labelCodigo0, labelNome0, labelTipo0, labelMarca0, labelCategoria0, labelFornecedor0, labelPreco0, editar0, deletar0);
        this.getChildren().add(cabecalho);

        carregarDados();
    }
}
