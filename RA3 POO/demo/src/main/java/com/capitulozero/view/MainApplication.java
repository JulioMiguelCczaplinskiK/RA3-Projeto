package com.capitulozero.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;



public class MainApplication extends Application {

    private int larguraSideBar;

    public Color COR_BACKGROUND_PRIMARIO = Color.web("#121212");
    public Color COR_BACKGROUND_SECUNDARIO = Color.web("#1E1E1E");
    public Color COR_BACKGROUND_TERCEARIO = Color.web("#2C2C2C");
    public Color COR_TEXT_PRIMARIO = Color.web("#E0E0E0");
    public double larguraTela;
    public double alturaTela;


    @Override
    public void start(Stage stage) throws IOException {

        Rectangle2D limites = Screen.getPrimary().getVisualBounds();

        larguraTela = limites.getWidth();
        alturaTela = limites.getHeight();

        larguraSideBar = 250;


        VBox sideBar = new VBox();
        sideBar.setPrefSize(larguraSideBar,alturaTela);
        sideBar.setMaxSize(larguraSideBar,alturaTela);
        sideBar.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        sideBar.setAlignment(Pos.TOP_CENTER);


        VBox content = new VBox();
        content.setPrefHeight(alturaTela);
        content.setMaxHeight(alturaTela);
        content.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));

        // layout principal

        HBox base = new HBox();
        base.getChildren().add(sideBar);
        base.getChildren().add(content);
        base.setHgrow(sideBar, Priority.SOMETIMES);
        base.setHgrow(content, Priority.SOMETIMES);

        // Componentes

        HBox perfilBox = new HBox();
        perfilBox.setPrefSize(larguraSideBar,50);
        perfilBox.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        perfilBox.setAlignment(Pos.TOP_RIGHT);
        sideBar.getChildren().add(perfilBox);

        Label nomePerfil = new Label("Capital Zero");
        nomePerfil.setTextFill(COR_TEXT_PRIMARIO);
        //nomePerfil.setAlignment(Pos.CENTER);
        perfilBox.getChildren().add(nomePerfil);





        HBox menuBox = new HBox();
        menuBox.setPrefSize(larguraSideBar, 30);
        menuBox.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        menuBox.setAlignment(Pos.TOP_CENTER);
        sideBar.getChildren().add(menuBox);

        Label menuPrincipal = new Label("MENU PRINCIPAL");
        menuPrincipal.setTextFill(COR_TEXT_PRIMARIO);
        menuPrincipal.setAlignment(Pos.CENTER_LEFT);
        menuBox.getChildren().add(menuPrincipal);


        double larguraBotoes = larguraSideBar - (30%larguraSideBar);

        Button dashboard = new Button("📱 Dashboard");
        dashboard.setPrefSize(100,50);
        dashboard.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_SECUNDARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        dashboard.setMaxWidth(larguraBotoes);
        dashboard.setAlignment(Pos.CENTER_LEFT);
        dashboard.setTextFill(COR_TEXT_PRIMARIO);
        sideBar.getChildren().add(dashboard);

        Button livros = new Button("📚 Livros");
        livros.setPrefSize(100,50);
        livros.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        livros.setMaxWidth(larguraBotoes);
        livros.setAlignment(Pos.CENTER_LEFT);
        livros.setTextFill(COR_TEXT_PRIMARIO);
        sideBar.getChildren().add(livros);

        Button autores = new Button("👤 Autores");
        autores.setPrefSize(100,50);
        autores.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        autores.setMaxWidth(larguraBotoes);
        autores.setAlignment(Pos.CENTER_LEFT);
        autores.setTextFill(COR_TEXT_PRIMARIO);
        sideBar.getChildren().add(autores);

        Button carrinho = new Button("🛒 Carrinho");
        carrinho.setPrefSize(100,50);
        carrinho.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        carrinho.setMaxWidth(larguraBotoes);
        carrinho.setAlignment(Pos.CENTER_LEFT);
        carrinho.setTextFill(COR_TEXT_PRIMARIO);
        sideBar.getChildren().add(carrinho);

        Button vendas = new Button("💵 Vendas");
        vendas.setPrefSize(100,50);
        vendas.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        vendas.setMaxWidth(larguraBotoes);
        vendas.setAlignment(Pos.CENTER_LEFT);
        vendas.setTextFill(COR_TEXT_PRIMARIO);
        sideBar.getChildren().add(vendas);

        Button clientes = new Button("👥 Clientes");
        clientes.setPrefSize(100,50);
        clientes.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        clientes.setMaxWidth(larguraBotoes);
        clientes.setAlignment(Pos.CENTER_LEFT);
        clientes.setTextFill(COR_TEXT_PRIMARIO);
        sideBar.getChildren().add(clientes);

        Button aboutUs = new Button("🙎‍♂️ ABOUT US");
        aboutUs.setPrefSize(100,50);
        aboutUs.setBackground(new Background(new BackgroundFill(COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        aboutUs.setMaxWidth(larguraBotoes);
        aboutUs.setAlignment(Pos.CENTER_LEFT);
        aboutUs.setTextFill(COR_TEXT_PRIMARIO);
        sideBar.getChildren().add(aboutUs);








        Scene scene = new Scene(base, 500, 500);
        //scene.getStylesheets().add(
                //getClass().getResource("style.css").toExternalForm()
        //);




        stage.setTitle("Capítulo Zero");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}