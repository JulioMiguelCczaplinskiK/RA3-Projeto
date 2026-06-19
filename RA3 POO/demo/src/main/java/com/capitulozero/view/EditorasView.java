package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoEditoras;
import com.capitulozero.config.Theme;
import com.capitulozero.model.EditoraModel;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class EditorasView extends VBox {

    private final ArrayList<Pane> linhasEditoras = new ArrayList<>();
    private Integer codigoEdicao = null; // guarda o código da editora sendo editada

    private TextField codigoInput;
    private TextField nomeInput;
    private TextField cnpjInput;
    private TextField enderecoInput;
    private TextField telefoneInput;
    private Label labelModoEdicao;

    public void carregarDados() {
        this.getChildren().removeAll(linhasEditoras);
        linhasEditoras.clear();

        ArrayList<EditoraModel> colecao = ColecaoEditoras.lerLista();

        for (EditoraModel editora : colecao) {
            Label labelCodigo1 = new Label("Código: " + editora.getCodigo());
            labelCodigo1.relocate(30, 0);

            Label labelNome1 = new Label("Nome: " + editora.getNome());
            labelNome1.relocate(110, 0);

            Label labelCnpj1 = new Label("CNPJ: " + editora.getCnpj());
            labelCnpj1.relocate(260, 0);

            Label labelEndereco1 = new Label("Endereço: " + editora.getEndereco());
            labelEndereco1.relocate(420, 0);

            Label labelTelefone1 = new Label("Telefone: " + editora.getTelefone());
            labelTelefone1.relocate(610, 0);

            Button editar = new Button("Editar");
            editar.setOnAction(event -> {
                codigoEdicao = editora.getCodigo();
                codigoInput.setText(String.valueOf(editora.getCodigo()));
                nomeInput.setText(editora.getNome());
                cnpjInput.setText(editora.getCnpj());
                enderecoInput.setText(editora.getEndereco());
                telefoneInput.setText(editora.getTelefone());
                labelModoEdicao.setText("Editando " + editora.getNome());
            });
            editar.relocate(780, 0);

            Button deletar = new Button("Deletar");
            deletar.setOnAction(event -> {
                ColecaoEditoras.deletar(editora.getCodigo());
                carregarDados();
            });
            deletar.relocate(860, 0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(
                    labelCodigo1,
                    labelNome1,
                    labelCnpj1,
                    labelEndereco1,
                    labelTelefone1,
                    editar,
                    deletar
            );

            this.getChildren().add(root);
            linhasEditoras.add(root);
        }
    }

    public EditorasView(double largura, double altura) {
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

        Label label = new Label("🏢 Cadastro de Editoras");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);

        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);

        Label labelCodigo = new Label("Código:");
        codigoInput = new TextField();
        codigoInput.setPromptText("Código da editora");

        Label labelNome = new Label("Nome:");
        nomeInput = new TextField();
        nomeInput.setPromptText("Nome da editora");

        Label labelCnpj = new Label("CNPJ:");
        cnpjInput = new TextField();
        cnpjInput.setPromptText("CNPJ da editora");

        Label labelEndereco = new Label("Endereço:");
        enderecoInput = new TextField();
        enderecoInput.setPromptText("Endereço da editora");

        Label labelTelefone = new Label("Telefone:");
        telefoneInput = new TextField();
        telefoneInput.setPromptText("Telefone da editora");

        Button btnEnviar = new Button("Salvar");
        btnEnviar.setOnAction(event -> {
            try {
                int intCodigo = parseInt(codigoInput.getText());
                String textoNome = nomeInput.getText();
                String textoCnpj = cnpjInput.getText();
                String textoEndereco = enderecoInput.getText();
                String textoTelefone = telefoneInput.getText();

                if (textoNome.isBlank() || textoCnpj.isBlank() || textoEndereco.isBlank() || textoTelefone.isBlank()) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos da editora.");
                    alerta.showAndWait();
                    return;
                }

                EditoraModel editora = new EditoraModel(
                        intCodigo,
                        textoNome,
                        textoCnpj,
                        textoEndereco,
                        textoTelefone
                );

                if (codigoEdicao != null) {
                    ColecaoEditoras.editar(codigoEdicao, editora);
                    codigoEdicao = null;
                    labelModoEdicao.setText("");
                } else {
                    ColecaoEditoras.adicionar(editora);
                }

                codigoInput.clear();
                nomeInput.clear();
                cnpjInput.clear();
                enderecoInput.clear();
                telefoneInput.clear();

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
                labelEndereco, enderecoInput,
                labelTelefone, telefoneInput,
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

        Label labelEndereco0 = new Label("Endereço");
        labelEndereco0.relocate(420, 0);

        Label labelTelefone0 = new Label("Telefone");
        labelTelefone0.relocate(610, 0);

        Label editar0 = new Label("Editar");
        editar0.relocate(780, 0);

        Label deletar0 = new Label("Deletar");
        deletar0.relocate(860, 0);

        cabecalho.getChildren().addAll(
                labelCodigo0,
                labelNome0,
                labelCnpj0,
                labelEndereco0,
                labelTelefone0,
                editar0,
                deletar0
        );

        this.getChildren().add(cabecalho);

        carregarDados();
    }
}
