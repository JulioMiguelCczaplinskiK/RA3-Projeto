package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoFornecedores;
import com.capitulozero.config.Theme;
import com.capitulozero.model.FornecedorModel;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class FornecedoresView extends VBox {

    private final ArrayList<Pane> linhasFornecedores = new ArrayList<>();
    private Integer codigoEdicao = null; // guarda o código do fornecedor sendo editado

    private TextField codigoInput;
    private TextField nomeInput;
    private TextField cnpjInput;
    private TextField telefoneInput;
    private TextField emailInput;
    private Label labelModoEdicao;

    public void carregarDados() {
        this.getChildren().removeAll(linhasFornecedores);
        linhasFornecedores.clear();

        ArrayList<FornecedorModel> colecao = ColecaoFornecedores.lerLista();

        for (FornecedorModel fornecedor : colecao) {
            Label labelCodigo1 = new Label("Código: " + fornecedor.getCodigo());
            labelCodigo1.relocate(30, 0);

            Label labelNome1 = new Label("Nome: " + fornecedor.getNome());
            labelNome1.relocate(110, 0);

            Label labelCnpj1 = new Label("CNPJ: " + fornecedor.getCnpj());
            labelCnpj1.relocate(260, 0);

            Label labelTelefone1 = new Label("Telefone: " + fornecedor.getTelefone());
            labelTelefone1.relocate(430, 0);

            Label labelEmail1 = new Label("E-mail: " + fornecedor.getEmail());
            labelEmail1.relocate(600, 0);

            Button editar = new Button("Editar");
            editar.setOnAction(event -> {
                codigoEdicao = fornecedor.getCodigo();
                codigoInput.setText(String.valueOf(fornecedor.getCodigo()));
                nomeInput.setText(fornecedor.getNome());
                cnpjInput.setText(fornecedor.getCnpj());
                telefoneInput.setText(fornecedor.getTelefone());
                emailInput.setText(fornecedor.getEmail());
                labelModoEdicao.setText("Editando " + fornecedor.getNome());
            });
            editar.relocate(780, 0);

            Button deletar = new Button("Deletar");
            deletar.setOnAction(event -> {
                ColecaoFornecedores.deletar(fornecedor.getCodigo());
                carregarDados();
            });
            deletar.relocate(860, 0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(
                    labelCodigo1,
                    labelNome1,
                    labelCnpj1,
                    labelTelefone1,
                    labelEmail1,
                    editar,
                    deletar
            );

            this.getChildren().add(root);
            linhasFornecedores.add(root);
        }
    }

    public FornecedoresView(double largura, double altura) {
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

        Label label = new Label("🚚 Cadastro de Fornecedores");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);

        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);

        Label labelCodigo = new Label("Código:");
        codigoInput = new TextField();
        codigoInput.setPromptText("Código do fornecedor");

        Label labelNome = new Label("Nome:");
        nomeInput = new TextField();
        nomeInput.setPromptText("Nome do fornecedor");

        Label labelCnpj = new Label("CNPJ:");
        cnpjInput = new TextField();
        cnpjInput.setPromptText("CNPJ do fornecedor");

        Label labelTelefone = new Label("Telefone:");
        telefoneInput = new TextField();
        telefoneInput.setPromptText("Telefone do fornecedor");

        Label labelEmail = new Label("E-mail:");
        emailInput = new TextField();
        emailInput.setPromptText("E-mail do fornecedor");

        Button btnEnviar = new Button("Salvar");
        btnEnviar.setOnAction(event -> {
            try {
                int intCodigo = parseInt(codigoInput.getText());
                String textoNome = nomeInput.getText();
                String textoCnpj = cnpjInput.getText();
                String textoTelefone = telefoneInput.getText();
                String textoEmail = emailInput.getText();

                if (textoNome.isBlank() || textoCnpj.isBlank() || textoTelefone.isBlank() || textoEmail.isBlank()) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos do fornecedor.");
                    alerta.showAndWait();
                    return;
                }

                if (!textoEmail.contains("@")) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe um e-mail válido.");
                    alerta.showAndWait();
                    return;
                }

                FornecedorModel fornecedor = new FornecedorModel(
                        intCodigo,
                        textoNome,
                        textoCnpj,
                        textoTelefone,
                        textoEmail
                );

                if (codigoEdicao != null) {
                    ColecaoFornecedores.editar(codigoEdicao, fornecedor);
                    codigoEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoFornecedores.adicionar(fornecedor);
                }

                codigoInput.clear();
                nomeInput.clear();
                cnpjInput.clear();
                telefoneInput.clear();
                emailInput.clear();

                carregarDados();
            } catch (NumberFormatException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Informe o código corretamente.");
                alerta.showAndWait();
            }
        });

        this.getChildren().addAll(
                labelCodigo, codigoInput,
                labelNome, nomeInput,
                labelCnpj, cnpjInput,
                labelTelefone, telefoneInput,
                labelEmail, emailInput,
                btnEnviar
        );

        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);

        Label labelCodigo0 = new Label("Código");
        labelCodigo0.relocate(30, 0);

        Label labelNome0 = new Label("Nome");
        labelNome0.relocate(110, 0);

        Label labelCnpj0 = new Label("CNPJ");
        labelCnpj0.relocate(260, 0);

        Label labelTelefone0 = new Label("Telefone");
        labelTelefone0.relocate(430, 0);

        Label labelEmail0 = new Label("E-mail");
        labelEmail0.relocate(600, 0);

        Label editar0 = new Label("Editar");
        editar0.relocate(780, 0);

        Label deletar0 = new Label("Deletar");
        deletar0.relocate(860, 0);

        cabecalho.getChildren().addAll(
                labelCodigo0,
                labelNome0,
                labelCnpj0,
                labelTelefone0,
                labelEmail0,
                editar0,
                deletar0
        );

        this.getChildren().add(cabecalho);

        carregarDados();
    }
}
