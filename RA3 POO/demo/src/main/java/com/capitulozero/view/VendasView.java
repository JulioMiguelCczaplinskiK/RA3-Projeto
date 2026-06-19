package com.capitulozero.view;

import com.capitulozero.colecao.ColecaoClientes;
import com.capitulozero.colecao.ColecaoLivro;
import com.capitulozero.colecao.ColecaoVendas;
import com.capitulozero.colecao.ColecaoVendedores;
import com.capitulozero.config.Theme;
import com.capitulozero.model.ClienteModel;
import com.capitulozero.model.LivroModel;
import com.capitulozero.model.VendaModel;
import com.capitulozero.model.VendedorModel;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendasView extends VBox {

    private final ArrayList<Pane> linhasVendas = new ArrayList<>();
    private Integer codigoEdicao = null;

    // ID exibido como label (gerado automaticamente, não editável pelo usuário)
    private Label codigoGeradoLabel;
    private TextField dataInput;
    // Cliente: dropdown com nomes da ColecaoClientes
    private ComboBox<String> clienteInput;
    // Vendedor: dropdown com nomes da ColecaoVendedores
    private ComboBox<String> vendedorInput;
    // Itens vendidos: lista de livros com multi-seleção
    private ListView<String> itensVendidosInput;
    // Valor total: label calculado automaticamente
    private Label valorTotalLabel;
    private Label labelModoEdicao;

    // Referência aos livros carregados para calcular preços
    private ArrayList<LivroModel> livrosDisponiveis = new ArrayList<>();

    /**
     * Calcula o próximo ID: max(códigos existentes) + 1, ou 1 se não houver vendas
     */
    private int proximoId() {
        ArrayList<VendaModel> lista = ColecaoVendas.lerLista();
        if (codigoEdicao != null) {
            // Em modo de edição, mantém o ID atual
            return codigoEdicao;
        }
        return lista.stream().mapToInt(VendaModel::getCodigo).max().orElse(0) + 1;
    }

    /** Recarrega os itens dos dropdowns com dados atuais das coleções */
    private void atualizarDropdowns() {
        // Clientes
        ArrayList<ClienteModel> clientes = ColecaoClientes.lerLista();
        clienteInput.setItems(FXCollections.observableArrayList(
                clientes.stream().map(ClienteModel::getNome).collect(Collectors.toList())));

        // Vendedores
        ArrayList<VendedorModel> vendedores = ColecaoVendedores.lerLista();
        vendedorInput.setItems(FXCollections.observableArrayList(
                vendedores.stream().map(VendedorModel::getNome).collect(Collectors.toList())));

        // Livros
        livrosDisponiveis = ColecaoLivro.lerLista();

        itensVendidosInput.setItems(FXCollections.observableArrayList(
                livrosDisponiveis.stream().map(LivroModel::getTitulo).collect(Collectors.toList())));
    }

    /** Recalcula e exibe o valor total somando os preços dos livros selecionados */
    private void recalcularValorTotal() {
        List<String> selecionados = itensVendidosInput.getSelectionModel().getSelectedItems();
        double total = livrosDisponiveis.stream()
                .filter(l -> selecionados.contains(l.getTitulo()))
                .mapToDouble(LivroModel::getPreco)
                .sum();
        valorTotalLabel.setText(String.format("R$ %.2f", total));
    }

    public void carregarDados() {
        this.getChildren().removeAll(linhasVendas);
        linhasVendas.clear();

        ArrayList<VendaModel> colecao = ColecaoVendas.lerLista();
        for (VendaModel venda1 : colecao) {
            // código
            Label labelCodigo1 = new Label(String.valueOf(venda1.getCodigo()));
            labelCodigo1.relocate(30, 0);
            // data
            Label labelData1 = new Label(venda1.getData());
            labelData1.relocate(130, 0);
            // cliente
            Label labelCliente1 = new Label(venda1.getCliente());
            labelCliente1.relocate(230, 0);
            // vendedor
            Label labelVendedor1 = new Label(venda1.getVendedor());
            labelVendedor1.relocate(330, 0);
            // itens vendidos
            Label labelItens1 = new Label(venda1.getItensVendidos());
            labelItens1.relocate(430, 0);
            // valor total
            Label labelValor1 = new Label(String.format("R$ %.2f", venda1.getValorTotal()));
            labelValor1.relocate(530, 0);

            Button editar = new Button("Editar");
            editar.setOnAction(e -> {
                codigoEdicao = venda1.getCodigo();
                // Mostra o ID atual no label
                codigoGeradoLabel.setText("ID: " + venda1.getCodigo());
                dataInput.setText(venda1.getData());
                // Seleciona o cliente no ComboBox
                clienteInput.setValue(venda1.getCliente());
                // Seleciona o vendedor no ComboBox
                vendedorInput.setValue(venda1.getVendedor());
                // Seleciona os livros na ListView (itens separados por ", ")
                itensVendidosInput.getSelectionModel().clearSelection();
                String[] itens = venda1.getItensVendidos().split(", ");
                for (String item : itens) {
                    int idx = itensVendidosInput.getItems().indexOf(item.trim());
                    if (idx >= 0) {
                        itensVendidosInput.getSelectionModel().select(idx);
                    }
                }
                recalcularValorTotal();
                labelModoEdicao.setText("Editando ID " + venda1.getCodigo());
            });
            editar.relocate(630, 0);

            Button deletar = new Button("Deletar");
            deletar.setOnAction(e -> {
                ColecaoVendas.deletarVenda(venda1.getCodigo());
                carregarDados();
                // Atualiza o label do próximo ID após deletar
                if (codigoEdicao == null) {
                    codigoGeradoLabel.setText("ID: " + proximoId());
                }
            });
            deletar.relocate(730, 0);

            Pane root = new Pane();
            root.setPrefHeight(30);
            root.getChildren().addAll(labelCodigo1, labelData1, labelCliente1,
                    labelVendedor1, labelItens1, labelValor1,
                    editar, deletar);
            this.getChildren().add(root);
            linhasVendas.add(root);
        }
    }

    public VendasView(double largura, double altura) {
        this.getStylesheets().add(
                getClass().getResource("/com/capitulozero/style/style.css").toExternalForm());
        this.setPrefSize(largura, altura);
        this.setMaxSize(largura, altura);
        this.setPadding(new Insets(20));
        this.setBackground(
                new Background(new BackgroundFill(Theme.COR_BACKGROUND_TERCEARIO, CornerRadii.EMPTY, Insets.EMPTY)));
        File pastaColecao = new File("src/main/java/com/capitulozero/colecao");
        if (!pastaColecao.exists()) {
            pastaColecao.mkdirs();
        }

        Label label = new Label("💵 Cadastro de Vendas");
        label.setTextFill(Theme.COR_TEXT_PRIMARIO);
        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        this.getChildren().add(label);

        // título do modo de edição (fica vazio fora do modo de edição)
        labelModoEdicao = new Label();
        labelModoEdicao.setStyle("-fx-font-weight: bold;");
        this.getChildren().add(labelModoEdicao);

        // ID gerado automaticamente — apenas exibido, não editável
        Label labelCodigo = new Label("Código (ID automático):");
        codigoGeradoLabel = new Label();
        codigoGeradoLabel.setStyle("-fx-font-weight: bold;");

        // data
        Label labelData = new Label("Data:");
        dataInput = new TextField();
        dataInput.setPromptText("Data (dd/mm/aaaa)");

        // cliente — ComboBox populado com ColecaoClientes
        Label labelCliente = new Label("Cliente:");
        clienteInput = new ComboBox<>();
        clienteInput.setPromptText("Selecione um cliente");
        clienteInput.setPrefWidth(200);

        // vendedor — ComboBox populado com ColecaoVendedores
        Label labelVendedor = new Label("Vendedor:");
        vendedorInput = new ComboBox<>();
        vendedorInput.setPromptText("Selecione um vendedor");
        vendedorInput.setPrefWidth(200);

        // itens vendidos — ListView com multi-seleção de livros (Ctrl+Click ou
        // Shift+Click)
        Label labelItens = new Label("Itens Vendidos (Ctrl+Click para selecionar múltiplos livros):");
        itensVendidosInput = new ListView<>();
        itensVendidosInput.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itensVendidosInput.setPrefHeight(120);

        // valor total — Label calculado automaticamente
        Label labelValor = new Label("Valor Total:");
        valorTotalLabel = new Label("R$ 0,00");
        valorTotalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Atualiza o valor total sempre que a seleção de livros mudar
        itensVendidosInput.getSelectionModel().getSelectedItems().addListener(
                (javafx.collections.ListChangeListener<String>) change -> recalcularValorTotal());

        // Carrega os dropdowns com dados das coleções
        atualizarDropdowns();

        // Define o ID inicial após carregar as coleções
        codigoGeradoLabel.setText("ID: " + proximoId());

        // Botão Salvar
        Button submitButton = new Button("Salvar");
        submitButton.setOnAction(e -> {
            int intCodigo = codigoEdicao != null ? codigoEdicao : proximoId();
            String textoData = dataInput.getText();
            String textoCliente = clienteInput.getValue() != null ? clienteInput.getValue() : "";
            String textoVendedor = vendedorInput.getValue() != null ? vendedorInput.getValue() : "";
            // Junta os títulos dos livros selecionados separados por ", "
            String textoItens = String.join(", ", itensVendidosInput.getSelectionModel().getSelectedItems());
            // Valor total a partir do label calculado (remove "R$ " e troca vírgula por
            // ponto)
            double doubleValor = Double.parseDouble(
                    valorTotalLabel.getText().replace("R$ ", "").replace(",", "."));

            VendaModel venda = new VendaModel(intCodigo, textoData, textoCliente,
                    textoVendedor, textoItens, doubleValor);

            if (codigoEdicao != null) {
                ColecaoVendas.editarVenda(codigoEdicao, venda);
                codigoEdicao = null;
                labelModoEdicao.setText("");
            } else {
                ColecaoVendas.adicionarVenda(venda);
            }

            dataInput.clear();
            clienteInput.setValue(null);
            vendedorInput.setValue(null);
            itensVendidosInput.getSelectionModel().clearSelection();
            valorTotalLabel.setText("R$ 0,00");

            carregarDados();
            // Atualiza o próximo ID disponível
            codigoGeradoLabel.setText("ID: " + proximoId());
        });

        this.getChildren().add(labelCodigo);
        this.getChildren().add(codigoGeradoLabel);
        this.getChildren().add(labelData);
        this.getChildren().add(dataInput);
        this.getChildren().add(labelCliente);
        this.getChildren().add(clienteInput);
        this.getChildren().add(labelVendedor);
        this.getChildren().add(vendedorInput);
        this.getChildren().add(labelItens);
        this.getChildren().add(itensVendidosInput);
        this.getChildren().add(labelValor);
        this.getChildren().add(valorTotalLabel);
        this.getChildren().add(submitButton);

        // Cabeçalho da tabela: criado uma única vez, fica fixo no topo da lista
        Pane cabecalho = new Pane();
        cabecalho.setPrefHeight(30);
        Label labelCodigo0 = new Label("Código");
        labelCodigo0.relocate(30, 0);
        Label labelData0 = new Label("Data");
        labelData0.relocate(130, 0);
        Label labelCliente0 = new Label("Cliente");
        labelCliente0.relocate(230, 0);
        Label labelVendedor0 = new Label("Vendedor");
        labelVendedor0.relocate(330, 0);
        Label labelItens0 = new Label("Itens Vendidos");
        labelItens0.relocate(430, 0);
        Label labelValor0 = new Label("Valor Total");
        labelValor0.relocate(530, 0);
        Label editar0 = new Label("Editar");
        editar0.relocate(630, 0);
        Label deletar0 = new Label("Deletar");
        deletar0.relocate(730, 0);
        cabecalho.getChildren().addAll(labelCodigo0, labelData0, labelCliente0,
                labelVendedor0, labelItens0, labelValor0,
                editar0, deletar0);
        this.getChildren().add(cabecalho);

        carregarDados();
    }
}