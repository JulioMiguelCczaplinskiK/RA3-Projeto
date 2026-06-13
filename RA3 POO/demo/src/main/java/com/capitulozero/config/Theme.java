// Arquivo: com.capitulozero.config.Theme.java
package com.capitulozero.config;

import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Theme {
    //  UTILIZADAS NO SISTEMA
    // CORES - BACKGROUND
    public static final Color COR_BACKGROUND_PRIMARIO = Color.web("#121212");
    public static final Color COR_BACKGROUND_SECUNDARIO = Color.web("#1E1E1E");
    public static final Color COR_BACKGROUND_TERCEARIO = Color.web("#2C2C2C");

    // CORES - FONT
    public static final Color COR_TEXT_PRIMARIO = Color.web("#E0E0E0");

    // TAMANHO DA TELA DO USUARIO
    public static Rectangle2D telaDoUsuario = Screen.getPrimary().getVisualBounds();

    public static final double larguraTela = telaDoUsuario.getWidth();
    public static final double alturaTela = telaDoUsuario.getHeight();

    // SIDEBAR (MENU)
    public static final double larguraSideBar = 250;

}