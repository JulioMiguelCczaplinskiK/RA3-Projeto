package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoEstoque;
import com.capitulozero.config.Theme;
import com.capitulozero.model.EstoqueModel;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class EstoqueView extends VBox {

    private final ArrayList<Pane> linhasEstoque = new ArrayList<>();
    private Integer matriculaEdicao = null; // guarda o código do item sendo editado

    private TextField codigoInput;
    private TextField itemInput;
    private TextField quantidadeDisponivelInput;
    private Label labelModoEdicao;

    public void carregarDados() {
        this.getChildren().removeAll(linhasEstoque);
        linhasEstoque.clear();

        ArrayList<EstoqueModel> colecao = ColecaoEstoque.lerLista();
        for (EstoqueModel estoque1 : colecao) {
            // Codigo
            Label labelCodigo1 = new Label("Código: " + estoque1.getCodigo());
            labelCodigo1.relocate(30, 0);

            // Item
            Label labelItem1 = new Label("Item: " + estoque1.getItem());
            labelItem1.relocate(130, 0);

            // Quantidade
            Label labelQuantidadeDiponivel1 = new Label("Qtd: " + estoque1.getQuantidadeDisponivel());
            labelQuantidadeDiponivel1.relocate(280, 0);

            Button editar = new Button("Editar");
            editar.setOnAction(event -> {
                matriculaEdicao = estoque1.getCodigo();
                codigoInput.setText(String.valueOf(estoque1.getCodigo()));
                itemInput.setText(estoque1.getItem());
                quantidadeDisponivelInput.setText(String.valueOf(estoque1.getQuantidadeDisponivel()));
                labelModoEdicao.setText("Editando " + estoque1.getItem());
            });
            editar.relocate(380, 0);

            Button deletar = new Button("Deletar");
            deletar.setOnAction(event -> {
                ColecaoEstoque.deletar(estoque1.getCodigo());
                carregarDados();
            });
            deletar.relocate(460, 0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(labelCodigo1, labelItem1, labelQuantidadeDiponivel1, editar, deletar);
            this.getChildren().add(root);
            linhasEstoque.add(root);
        }
    }

    public EstoqueView(double largura, double altura) {
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

        Label label = new Label("📦 Estoque");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);

        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);

        // código
        Label labelCodigo = new Label("Código:");
        codigoInput = new TextField();
        codigoInput.setPromptText("Código do Item");

        // Item
        Label labelItem = new Label("Item:");
        itemInput = new TextField();
        itemInput.setPromptText("Nome do Item");

        // Quantidade disponivel
        Label labelQuantidadeDisponivel = new Label("Quantidade Disponível:");
        quantidadeDisponivelInput = new TextField();
        quantidadeDisponivelInput.setPromptText("Quantidade disponível em estoque");

        Button btnEnviar = new Button("Salvar");
        btnEnviar.setOnAction(event -> {
            try {
                int intCodigo = parseInt(codigoInput.getText());
                String textoItem = itemInput.getText();
                int intQuantidadeDisponivel = parseInt(quantidadeDisponivelInput.getText());

                EstoqueModel estoque = new EstoqueModel(intCodigo, textoItem, intQuantidadeDisponivel);

                if (matriculaEdicao != null) {
                    ColecaoEstoque.editar(matriculaEdicao, estoque);
                    matriculaEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoEstoque.adicionar(estoque);
                }

                codigoInput.clear();
                itemInput.clear();
                quantidadeDisponivelInput.clear();
                carregarDados();

            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe os valores corretamente!");
                alerta.showAndWait();
            }
        });

        this.getChildren().add(labelCodigo);
        this.getChildren().add(codigoInput);
        this.getChildren().add(labelItem);
        this.getChildren().add(itemInput);
        this.getChildren().add(labelQuantidadeDisponivel);
        this.getChildren().add(quantidadeDisponivelInput);
        this.getChildren().add(btnEnviar);

        // Cabeçalho da tabela
        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);
        Label labelCodigo0 = new Label("Código");
        labelCodigo0.relocate(30, 0);
        Label labelItem0 = new Label("Item");
        labelItem0.relocate(130, 0);
        Label labelQuantidade0 = new Label("Quantidade");
        labelQuantidade0.relocate(280, 0);
        Label editar0 = new Label("Editar");
        editar0.relocate(380, 0);
        Label deletar0 = new Label("Deletar");
        deletar0.relocate(460, 0);
        cabecalho.getChildren().addAll(labelCodigo0, labelItem0, labelQuantidade0, editar0, deletar0);
        this.getChildren().add(cabecalho);

        carregarDados();
    }
}
