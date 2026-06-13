package com.capitulozero.view;

import com.capitulozero.config.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ButtonView extends Button{

    public ButtonView(VBox pai, String text, double larguraPreferivel, double alturaPreferivel, double larguraMaxima, double alturaMaxima, Color corFundo, Color corTexto, Pos alinhamento){
        this.setText(text);
        this.setPrefSize(larguraPreferivel,alturaPreferivel);
        this.setMaxWidth(larguraMaxima);
        this.setBackground(new Background(new BackgroundFill(corFundo, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setTextFill(corTexto);
        this.setAlignment(alinhamento);
        atualizarVisual(false);

        this.setOnAction(event -> {

            // Avisa a Sidebar que esta opção foi clicada
            SideBarView.selecionarAba(text);

        });
        pai.getChildren().add(this);
    }

    // Atualiza o Background para mostra que o botao foi selecionado
    public void atualizarVisual(boolean selecionado) {
        Color corFundoAtual = selecionado ? Theme.COR_BACKGROUND_SECUNDARIO : Theme.COR_BACKGROUND_PRIMARIO;

        this.setBackground(new Background(new BackgroundFill(corFundoAtual, CornerRadii.EMPTY, Insets.EMPTY)));

        // Opcional: Destacar fonte se estiver selecionado
        if (selecionado) {
            this.setStyle("-fx-font-weight: bold; -fx-border-left: 4px solid #FFFFFF;"); // Borda lateral opcional
        } else {
            this.setStyle("-fx-font-weight: normal; -fx-border-left: 4px solid transparent;");
        }
    }
}
