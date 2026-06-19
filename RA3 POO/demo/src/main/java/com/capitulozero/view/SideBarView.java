package com.capitulozero.view;

import com.capitulozero.config.Theme;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SideBarView extends VBox {

    private static final String ABA_DASHBOARD = "📱 Dashboard";
    private static final String ABA_LIVROS = "📚 Livros";
    private static final String ABA_AUTORES = "👤 Autores";
    private static final String ABA_CATEGORIAS = "📁 Categorias";
    private static final String ABA_VENDAS = "💵 Vendas";
    private static final String ABA_CLIENTES = "👥 Clientes";
    private static final String ABA_VENDEDORES = "👥 Vendedores";
    private static final String ABA_ESTOQUE = "📦 Estoque";
    private static final String ABA_PRODUTO_PAPELARIA = "✏️ Papelaria";
    private static final String ABA_EDITORAS = "🏢 Editoras";
    private static final String ABA_FORNECEDORES = "🚚 Fornecedores";
    private static final String ABA_SOBRE = "🙎‍♂️ Sobre Nós";

    private final List<String> opcoes;
    private static List<ButtonView> botoes;

    public static String conteudoAberto;
    private static VBox conteudoOpcoes;

    private final double larguraPreferivelBotao;

    public static void setConteudoOpcoes(VBox vBox) {
        conteudoOpcoes = vBox;

        if (conteudoAberto == null || conteudoAberto.isBlank()) {
            conteudoAberto = ABA_DASHBOARD;
        }

        abrirConteudo(conteudoAberto);
        atualizarEstadosBotoes();
    }

    public SideBarView(
            double larguraPreferivel,
            double alturaPreferivel,
            double larguraMaxima,
            double alturaMaxima,
            double padding,
            Color corFundo,
            Pos alinhamento
    ) {
        this.larguraPreferivelBotao = larguraPreferivel;
        this.opcoes = new ArrayList<>(List.of(
                ABA_DASHBOARD,
                ABA_LIVROS,
                ABA_AUTORES,
                ABA_CATEGORIAS,
                ABA_CLIENTES,
                ABA_VENDEDORES,
                ABA_EDITORAS,
                ABA_FORNECEDORES,
                ABA_PRODUTO_PAPELARIA,
                ABA_ESTOQUE,
                ABA_VENDAS,
                ABA_SOBRE
        ));

        botoes = new ArrayList<>();
        conteudoAberto = ABA_DASHBOARD;

        configurarContainer(larguraPreferivel, alturaPreferivel, larguraMaxima, alturaMaxima, padding, corFundo, alinhamento);
        criarPerfil();
        criarMenu();
    }

    private void configurarContainer(
            double larguraPreferivel,
            double alturaPreferivel,
            double larguraMaxima,
            double alturaMaxima,
            double padding,
            Color corFundo,
            Pos alinhamento
    ) {
        this.setPrefSize(larguraPreferivel, alturaPreferivel);
        this.setMaxSize(larguraMaxima, alturaMaxima);
        this.setPadding(new Insets(padding));
        this.setSpacing(4);
        this.setBackground(new Background(new BackgroundFill(corFundo, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setAlignment(alinhamento);
    }

    private void criarPerfil() {
        new PerfilBoxView(
                this,
                larguraPreferivelBotao,
                50,
                Theme.COR_BACKGROUND_PRIMARIO,
                Pos.CENTER
        );
    }

    private void criarMenu() {
        adicionarTituloSecao("MENU PRINCIPAL");
        adicionarBotao(ABA_DASHBOARD);

        adicionarTituloSecao("CADASTROS");
        adicionarBotao(ABA_LIVROS);
        adicionarBotao(ABA_AUTORES);
        adicionarBotao(ABA_CATEGORIAS);
        adicionarBotao(ABA_CLIENTES);
        adicionarBotao(ABA_VENDEDORES);
        adicionarBotao(ABA_EDITORAS);
        adicionarBotao(ABA_FORNECEDORES);
        adicionarBotao(ABA_PRODUTO_PAPELARIA);

        adicionarTituloSecao("OPERAÇÕES");
        adicionarBotao(ABA_ESTOQUE);
        adicionarBotao(ABA_VENDAS);

        adicionarTituloSecao("SISTEMA");
        adicionarBotao(ABA_SOBRE);

        atualizarEstadosBotoes();
    }

    private void adicionarTituloSecao(String texto) {
        HBox caixaTitulo = new HBox();
        caixaTitulo.setPrefSize(larguraPreferivelBotao, 28);
        caixaTitulo.setPadding(new Insets(8, 0, 4, 10));
        caixaTitulo.setAlignment(Pos.CENTER_LEFT);
        caixaTitulo.setBackground(new Background(new BackgroundFill(
                Theme.COR_BACKGROUND_PRIMARIO,
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        Label titulo = new Label(texto);
        titulo.setTextFill(Theme.COR_TEXT_PRIMARIO);
        titulo.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-opacity: 0.75;");

        caixaTitulo.getChildren().add(titulo);
        this.getChildren().add(caixaTitulo);
    }

    private void adicionarBotao(String texto) {
        ButtonView botao = new ButtonView(
                this,
                texto,
                larguraPreferivelBotao,
                42,
                larguraPreferivelBotao,
                42,
                Theme.COR_BACKGROUND_SECUNDARIO,
                Theme.COR_TEXT_PRIMARIO,
                Pos.CENTER_LEFT
        );

        botao.setPadding(new Insets(0, 0, 0, 12));
        botoes.add(botao);
    }

    public static void selecionarAba(String novaAba) {
        conteudoAberto = novaAba;

        abrirConteudo(novaAba);
        atualizarEstadosBotoes();
    }

    private static void abrirConteudo(String aba) {
        if (conteudoOpcoes == null) {
            return;
        }

        conteudoOpcoes.getChildren().clear();

        double largura = obterLarguraConteudo();
        double altura = obterAlturaConteudo();

        switch (aba) {
            case ABA_DASHBOARD:
                conteudoOpcoes.getChildren().add(new DashboardView(
                        conteudoOpcoes,
                        largura,
                        altura,
                        largura,
                        altura,
                        new Insets(10),
                        Theme.COR_BACKGROUND_TERCEARIO,
                        Theme.COR_TEXT_PRIMARIO,
                        Pos.CENTER
                ));
                break;

            case ABA_LIVROS:
                conteudoOpcoes.getChildren().add(new LivrosView(largura, altura));
                break;

            case ABA_AUTORES:
                conteudoOpcoes.getChildren().add(new AutoresView(largura, altura));
                break;

            case ABA_CATEGORIAS:
            case "📁 Categoria":
                conteudoOpcoes.getChildren().add(new CategoriaView(largura, altura));
                break;

            case ABA_VENDAS:
                conteudoOpcoes.getChildren().add(new VendasView(largura, altura));
                break;

            case ABA_CLIENTES:
                conteudoOpcoes.getChildren().add(new ClientesView(largura, altura));
                break;

            case ABA_VENDEDORES:
            case "👥 Vendedor":
                conteudoOpcoes.getChildren().add(new VendedoresView(largura, altura));
                break;

            case ABA_ESTOQUE:
            case "Estoque":
                conteudoOpcoes.getChildren().add(new EstoqueView(largura, altura));
                break;

            case ABA_PRODUTO_PAPELARIA:
            case "Produto Papelaria":
                conteudoOpcoes.getChildren().add(new ProdutoPapelariaView(largura, altura));
                break;

            case ABA_EDITORAS:
                conteudoOpcoes.getChildren().add(new EditorasView(largura, altura));
                break;

            case ABA_FORNECEDORES:
                conteudoOpcoes.getChildren().add(new FornecedoresView(largura, altura));
                break;

            case ABA_SOBRE:
            case "🙎‍♂️ ABOUT US":
                conteudoOpcoes.getChildren().add(new AboutUsView(largura, altura));
                break;

            default:
                exibirTelaEmConstrucao(aba);
                break;
        }
    }

    private static double obterLarguraConteudo() {
        double largura = conteudoOpcoes.getPrefWidth();

        if (largura <= 0) {
            largura = conteudoOpcoes.getWidth();
        }

        if (largura <= 0) {
            largura = 900;
        }

        return largura;
    }

    private static double obterAlturaConteudo() {
        double altura = conteudoOpcoes.getPrefHeight();

        if (altura <= 0) {
            altura = conteudoOpcoes.getHeight();
        }

        if (altura <= 0) {
            altura = 650;
        }

        return altura;
    }

    private static void exibirTelaEmConstrucao(String aba) {
        Label label = new Label("Aba '" + aba + "' em construção...");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 20px;");
        conteudoOpcoes.getChildren().add(label);
    }

    private static void atualizarEstadosBotoes() {
        if (botoes == null) {
            return;
        }

        for (ButtonView botao : botoes) {
            boolean estaSelecionado = botao.getText().equals(conteudoAberto);
            botao.atualizarVisual(estaSelecionado);
        }
    }
}