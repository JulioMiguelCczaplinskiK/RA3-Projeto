package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoClientes;
import com.capitulozero.config.Theme;
import com.capitulozero.model.ClienteModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.io.*;

import static java.lang.Integer.parseInt;
public class ClientesView extends VBox {
    public ClientesView(double largura, double altura) {
        this.setPrefSize(largura, altura);
        this.setMaxSize(largura, altura);
        this.setPadding(new Insets(20));
        this.setBackground(new Background(new BackgroundFill(Theme.COR_BACKGROUND_TERCEARIO, CornerRadii.EMPTY, Insets.EMPTY)));
        File pastaColecao = new File("src/main/java/com/capitulozero/colecao");
        if (!pastaColecao.exists()) {
            pastaColecao.mkdirs();
        }
        File arquivo = new File(pastaColecao, "clientes.ser");
        Label label = new Label("👥 Cadastro de Clientes");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);
        // nome
        Label labelNome = new Label("Nome:");
        TextField nomeInput = new TextField();
        nomeInput.setPromptText("Nome");
        // matricula
        Label labelMatricula = new Label("Matricula:");
        TextField matriculaInput = new TextField();
        matriculaInput.setPromptText("Código de matrícula");
        // comissão
        Label labelComissao = new Label("Comissão:");
        TextField comissaoInput = new TextField();
        comissaoInput.setPromptText("Comissão");
        // Envio
        Button submitButton = new Button("Salvar");
        submitButton.setOnAction(e -> {
            // primeiro pega os dados
            String textoNome = nomeInput.getText();
            int intMatricula = parseInt(matriculaInput.getText());
            double doubleComissao = Double.parseDouble(comissaoInput.getText());
            ClienteModel cliente = new ClienteModel(textoNome, intMatricula, doubleComissao);
            ColecaoClientes.adicionarCliente(cliente);
            ArrayList<ClienteModel> colecao = ColecaoClientes.lerLista();
            for (ClienteModel cliente1 : colecao){
                System.out.println(cliente1.getNome());
                // nome
                Label labelNome1 = new Label("Nome" + cliente1.getNome());
                // matricula
                Label labelMatricula1 = new Label("Matricula:" + cliente1.getMatricula());
                // comissão
                Label labelComissao1 = new Label("Comissão:" + cliente1.getComissao());
                this.getChildren().add(labelNome1);
                this.getChildren().add(labelMatricula1);
                this.getChildren().add(labelComissao1);

            }

        });
        this.getChildren().add(labelNome);
        this.getChildren().add(nomeInput);
        this.getChildren().add(labelMatricula);
        this.getChildren().add(matriculaInput);
        this.getChildren().add(labelComissao);
        this.getChildren().add(comissaoInput);
        this.getChildren().add(submitButton);


    }
}