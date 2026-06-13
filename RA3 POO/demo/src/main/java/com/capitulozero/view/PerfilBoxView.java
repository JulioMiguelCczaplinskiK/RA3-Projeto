package com.capitulozero.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PerfilBoxView extends HBox{



    public PerfilBoxView(VBox pai, double larguraMaxima, double alturaMaxima, Color corFundo, Pos alinhamento){
        this.setPrefSize(larguraMaxima,alturaMaxima);
        this.setBackground(new Background(new BackgroundFill(corFundo, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(alinhamento);
        pai.getChildren().add(this);



        criarNomeAplicacao();

    }


    public void criarNomeAplicacao(){
        Label nomeAplicacao = new Label("Capital Zero");
        nomeAplicacao.setTextFill(Color.web("#E0E0E0"));
        this.getChildren().add(nomeAplicacao);

    }
}
