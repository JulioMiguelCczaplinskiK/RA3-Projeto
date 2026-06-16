package com.capitulozero.view;

import com.capitulozero.config.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class DashboardView extends VBox{

    private ArrayList<String> opcoes;

    public DashboardView(VBox pai, double larguraPreferivel, double alturaPreferivel, double larguraMaxima, double alturaMaxima, Insets padding, Color corFundo, Color corTexto, Pos alinhamento){
        this.setPrefSize(larguraPreferivel, alturaPreferivel);
        this.setMaxSize(larguraMaxima, alturaMaxima);
        this.setPadding(padding);
        this.setBackground(new Background(new BackgroundFill(corFundo, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(alinhamento);
        //conteudoAberto = "📱 Dashboard";



        opcoes = new ArrayList<>(List.of("📱 Dashboard","📚 Livros","👤 Autores","🛒 Carrinho","💵 Vendas","👥 Clientes","🙎‍♂️ ABOUT US"));

        criarPreset();
    }

    public void criarPreset(){
        VBox temp = new VBox();
        temp.setMaxSize(500,500);
        temp.setAlignment(Pos.CENTER);
        temp.setBackground(new Background(new BackgroundFill(Theme.COR_TEXT_PRIMARIO, CornerRadii.EMPTY, Insets.EMPTY)));

        Label tempLabel = new Label("Dash board!");
        tempLabel.setTextFill(Color.BLACK);

        temp.getChildren().add(tempLabel);

        this.getChildren().add(temp);
    }




}
