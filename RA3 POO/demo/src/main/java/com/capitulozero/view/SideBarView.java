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

    public static String conteudoAberto;
    private static VBox conteudoOpcoes;
    private double larguraPreferivelBotao;

    public static void setConteudoOpcoes(VBox vBox) {
        conteudoOpcoes = vBox;
    }

    public SideBarView(double larguraPreferivel, double alturaPreferivel, double larguraMaxima, double alturaMaxima,double padding, Color corFundo, Pos alinhamento){
        this.setPrefSize(larguraPreferivel, alturaPreferivel);
        this.setMaxSize(larguraMaxima, alturaMaxima);
        this.setPadding(new Insets(padding));
        this.setBackground(new Background(new BackgroundFill(corFundo, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(alinhamento);
        conteudoAberto = "📱 Dashboard";


        // BOTOES
        botoes = new ArrayList<>();
        opcoes = new ArrayList<>(List.of("📱 Dashboard","📚 Livros","👤 Autores","🛒 Carrinho","💵 Vendas","👥 Clientes","👥 Vendedor","Estoque","Produto Papelaria","🙎‍♂️ ABOUT US"));
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
        // ACREDITO SER POSSIVER DIMINUIR
        HBox menuBox = new HBox();
        menuBox.setPrefSize(larguraPreferivelBotao, 30);
        menuBox.setBackground(new Background(new BackgroundFill(Theme.COR_BACKGROUND_PRIMARIO,CornerRadii.EMPTY, Insets.EMPTY)));
        menuBox.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(menuBox);

        Label menuPrincipal = new Label("MENU PRINCIPAL");
        menuPrincipal.setTextFill(Theme.COR_TEXT_PRIMARIO);
        menuPrincipal.setAlignment(Pos.CENTER_LEFT);
        menuBox.getChildren().add(menuPrincipal);
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

        if (conteudoOpcoes != null) {
            conteudoOpcoes.getChildren().clear();
            double w = conteudoOpcoes.getPrefWidth();
            double h = conteudoOpcoes.getPrefHeight();

            switch (novaAba) {
                case "📱 Dashboard":
                    conteudoOpcoes.getChildren().add(new DashboardView(
                        conteudoOpcoes,
                        w, h, w, h,
                        new Insets(10),
                        Theme.COR_BACKGROUND_TERCEARIO,
                        Theme.COR_TEXT_PRIMARIO,
                        Pos.CENTER
                    ));
                    break;
                case "📚 Livros":
                    conteudoOpcoes.getChildren().add(new LivrosView(w, h));
                    break;
                case "👤 Autores":
                    conteudoOpcoes.getChildren().add(new AutoresView(w, h));
                    break;
                case "📁 Categoria":
                    conteudoOpcoes.getChildren().add(new CategoriaView(w, h));
                    break;
                case "🛒 Carrinho":
                    conteudoOpcoes.getChildren().add(new CarrinhoView(w, h));
                    break;
                case "💵 Vendas":
                    conteudoOpcoes.getChildren().add(new VendasView(w, h));
                    break;
                case "👥 Clientes":
                    conteudoOpcoes.getChildren().add(new ClientesView(w, h));
                    break;
                case "👥 Vendedor":
                    conteudoOpcoes.getChildren().add(new VendedoresView(w, h));
                    break;
                case "Estoque":
                    conteudoOpcoes.getChildren().add(new EstoqueView(w, h));
                    break;
                case "Produto Papelaria":
                    conteudoOpcoes.getChildren().add(new ProdutoPapelariaView(w, h));
                    break;
                case "🙎‍♂️ ABOUT US":
                    conteudoOpcoes.getChildren().add(new AboutUsView(w, h));
                    break;
                default:
                    Label label = new Label("Aba '" + novaAba + "' em construção...");
                    label.setTextFill(Theme.COR_TEXT_PRIMARIO);
                    label.setStyle("-fx-font-size: 20px;");
                    conteudoOpcoes.getChildren().add(label);
                    break;
            }
        }
    }

    // Percorre todos os botões e atualiza a cor baseada no estado atual
    private static void atualizarEstadosBotoes() {
        for (ButtonView botao : botoes) {
            boolean estaSelecionado = botao.getText().equals(conteudoAberto);
            botao.atualizarVisual(estaSelecionado);
        }
    }













}