package com.capitulozero.view;

import com.capitulozero.config.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class SideBarView extends VBox {

    private ArrayList<String> opcoes;
    private static List<ButtonView> botoes; // Lista para guardar as referências dos botões

    private double larguraPreferivelBotao;

    public static String conteudoAberto;

    public SideBarView(double larguraPreferivel, double alturaPreferivel, double larguraMaxima, double alturaMaxima,double padding, Color corFundo, Pos alinhamento){
        this.setPrefSize(larguraPreferivel, alturaPreferivel);
        this.setMaxSize(larguraMaxima, alturaMaxima);
        this.setPadding(new Insets(padding));
        this.setBackground(new Background(new BackgroundFill(corFundo, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(alinhamento);
        conteudoAberto = "📱 Dashboard";


        // BOTOES
        botoes = new ArrayList<>();
        opcoes = new ArrayList<>(List.of("📱 Dashboard","📚 Livros","👤 Autores","🛒 Carrinho","💵 Vendas","👥 Clientes","🙎‍♂️ ABOUT US"));
        larguraPreferivelBotao = larguraPreferivel;


        // Cria setor do perfil do usuario
        this.criarPerfil();
        // Cria titulo do menu
        //criarMenuTitulo(larguraSideBar);
        // Cria os botoes ja padronizados
        this.criarBotoes();

    }



    public void criarPerfil(){

        PerfilBoxView perfilBox = new PerfilBoxView(this,larguraPreferivelBotao,50, Theme.COR_BACKGROUND_PRIMARIO,Pos.TOP_RIGHT);


        // ESSA PARTE PRECISA SER DIMINUIDA
        HBox menuBox = new HBox();
        menuBox.setPrefSize(larguraPreferivelBotao, 30);
        menuBox.setBackground(new Background(new BackgroundFill(Theme.COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        menuBox.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(menuBox);

        Label menuPrincipal = new Label("MENU PRINCIPAL");
        menuPrincipal.setTextFill(Theme.COR_TEXT_PRIMARIO);
        menuPrincipal.setAlignment(Pos.CENTER_LEFT);
        menuBox.getChildren().add(menuPrincipal);
        // ACREDITO SER POSSIVER DIMINUIR

    }

    public void criarBotoes(){
        for (String opcao : opcoes){

            ButtonView botao = new ButtonView(this,opcao, larguraPreferivelBotao, 50,larguraPreferivelBotao,100,Theme.COR_BACKGROUND_SECUNDARIO,Theme.COR_TEXT_PRIMARIO,Pos.CENTER_LEFT);

            // Adiciona à lista para conseguir alterar cad abotao individualmente
            botoes.add(botao);

        }
        atualizarEstadosBotoes();
    }

    // Metodo chamado quando um botão é clicado
    public static void selecionarAba(String novaAba) {
        conteudoAberto = novaAba;
        atualizarEstadosBotoes(); // Atualiza visualmente todos
    }

    // Percorre todos os botões e atualiza a cor baseada no estado atual
    private static void atualizarEstadosBotoes() {
        for (ButtonView botao : botoes) {
            boolean estaSelecionado = botao.getText().equals(conteudoAberto);
            botao.atualizarVisual(estaSelecionado);
        }
    }













}