package com.capitulozero;

import com.capitulozero.config.Theme;
import com.capitulozero.view.DashboardView;
import com.capitulozero.view.SideBarView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;



public class MainApplication extends Application {

    private SideBarView sideBar;


    @Override
    public void start(Stage stage) throws IOException {

        // Cria o layout para a aplicacao
        HBox base = new HBox();


        // Cria a sidebar com todos os componentes
        sideBar = new SideBarView(Theme.larguraSideBar,Theme.alturaTela,Theme.larguraSideBar,Theme.alturaTela,20,Theme.COR_BACKGROUND_PRIMARIO,Pos.TOP_CENTER);

        base.getChildren().add(sideBar);
        HBox.setHgrow(sideBar, Priority.SOMETIMES);


        // Cria a vbox principal para os conteudos
        VBox content = new VBox();
        content.setPrefHeight(Theme.alturaTela);
        content.setMaxHeight(Theme.alturaTela);

        content.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
        content.setAlignment(Pos.CENTER);

        base.getChildren().add(content);
        HBox.setHgrow(content, Priority.SOMETIMES);



        double espacamento = 60;
        double larguraConteudoOpcoes = (Theme.larguraTela-Theme.larguraSideBar)-espacamento;
        double alturaConteudoOpcoes = Theme.alturaTela-espacamento;

        
        //Cria a Vbox para a troca de conteudo
        VBox conteudoOpcoes = new VBox();
        conteudoOpcoes.setPrefSize(larguraConteudoOpcoes,alturaConteudoOpcoes);
        conteudoOpcoes.setMaxHeight(alturaConteudoOpcoes);
        conteudoOpcoes.setMaxWidth(larguraConteudoOpcoes);
        //conteudoOpcoes.setSpacing(50);
        conteudoOpcoes.setBackground(new Background(new BackgroundFill(Theme.COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));

        content.getChildren().add(conteudoOpcoes);

        // Registra o container de opcoes na Sidebar e abre a aba inicial
        SideBarView.setConteudoOpcoes(conteudoOpcoes);
        SideBarView.selecionarAba("📱 Dashboard");














        Scene scene = new Scene(base, 500, 500);




        stage.setTitle("Capítulo Zero");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}