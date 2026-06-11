package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;



public class MainApplication extends Application {

    private Color MARROM_CLARO = Color.web("#dda15e");
    private Color MARROM_ESCURO = Color.web("#bc6c25");



    @Override
    public void start(Stage stage) throws IOException {



        HBox header = new HBox();
        header.setPrefHeight(100);
        header.setBackground(new Background(new BackgroundFill(MARROM_ESCURO,CornerRadii.EMPTY, Insets.EMPTY)));
        header.setAlignment(Pos.CENTER_LEFT);

        HBox content = new HBox();
        content.setPrefHeight(300);
        content.setBackground(new Background(new BackgroundFill(MARROM_CLARO, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox footer = new HBox();
        footer.setPrefHeight(100);
        footer.setBackground(new Background(new BackgroundFill(MARROM_ESCURO,CornerRadii.EMPTY, Insets.EMPTY)));



        BorderPane layoutMenu = new BorderPane();
        layoutMenu.setTop(header);
        layoutMenu.setCenter(content);
        layoutMenu.setBottom(footer);
        //layoutMenu.setBackground();

        // Informacoes do header AQUI!!

        Button home = new Button("HOME");
        home.setPrefSize(100,50);
        //home.setAlignment(Pos.CENTER);
        header.getChildren().add(home);

        Button produtos = new Button("PRODUTOS");
        produtos.setPrefSize(100,50);
        //home.setAlignment(Pos.CENTER);
        header.getChildren().add(produtos);

        Button aboutUs = new Button("ABOUT US");
        aboutUs.setPrefSize(100,50);
        //aboutUs.setAlignment(Pos.CENTER);
        header.getChildren().add(aboutUs);







        Scene scene = new Scene(layoutMenu, 500, 500);
        scene.getStylesheets().add(
                getClass().getResource("style.css").toExternalForm()
        );




        stage.setTitle("Capítulo Zero");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}