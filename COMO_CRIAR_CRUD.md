# Como criar um CRUD completo neste projeto (JavaFX)

Guia de referência baseado no CRUD de Vendedores (`VendedorModel` / `ColecaoVendedores` / `VendedoresView`). Use como modelo para criar qualquer outra entidade (ex: Produtos, Pedidos, Fornecedores...).

Convenção de nomes usada abaixo: troque `Entidade` pelo nome da sua entidade (ex: `Produto`).

---

## 1. Criar o Model (`model/EntidadeModel.java`)

Classe simples, `Serializable` (porque a persistência usa serialização de objetos), com campos + getters/setters.

```java
package com.capitulozero.model;

import java.io.Serializable;

public class EntidadeModel implements Serializable {
    String nome;
    int matricula;
    double comissao;

    public EntidadeModel(String nome, int matricula, double comissao) {
        this.nome = nome;
        this.matricula = matricula;
        this.comissao = comissao;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getMatricula() { return matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }

    public double getComissao() { return comissao; }
    public void setComissao(double comissao) { this.comissao = comissao; }
}
```

> Troque os campos pelos da sua entidade real. O importante é manter pelo menos **um campo único** (aqui é `matricula`) — ele vai servir de "ID" pra editar/deletar depois.

---

## 2. Criar a Coleção / persistência (`colecao/ColecaoEntidades.java`)

Essa classe é responsável por ler e escrever a lista inteira no arquivo `.ser`, e expõe métodos de alto nível (`adicionar`, `deletar`, `editar`).

```java
package com.capitulozero.colecao;

import com.capitulozero.model.EntidadeModel;

import java.io.*;
import java.util.ArrayList;

public class ColecaoEntidades {

    private static final String CAMINHO_ARQUIVO = "src/main/java/com/capitulozero/colecao/entidades.ser";

    public static void salvarLista(ArrayList<EntidadeModel> itens) {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(itens);
            oos.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista: " + e.getMessage());
        }
    }

    public static ArrayList<EntidadeModel> lerLista() {
        ArrayList<EntidadeModel> lista = new ArrayList<>();
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (arq.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq));
                lista = (ArrayList<EntidadeModel>) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler lista: " + e.getMessage());
        }
        return lista;
    }

    // Create
    public static void adicionar(EntidadeModel novoItem) {
        ArrayList<EntidadeModel> itens = lerLista();
        itens.add(novoItem);
        salvarLista(itens);
    }

    // Delete (pelo campo único, aqui matricula)
    public static void deletar(int matricula) {
        ArrayList<EntidadeModel> itens = lerLista();
        itens.removeIf(i -> i.getMatricula() == matricula);
        salvarLista(itens);
    }

    // Update (acha pelo ID antigo e substitui o objeto inteiro)
    public static void editar(int matriculaOriginal, EntidadeModel itemAtualizado) {
        ArrayList<EntidadeModel> itens = lerLista();
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getMatricula() == matriculaOriginal) {
                itens.set(i, itemAtualizado);
                break;
            }
        }
        salvarLista(itens);
    }
}
```

**Pontos importantes:**
- `salvarLista` **sempre sobrescreve o arquivo inteiro** com a lista recebida — por isso `adicionar`/`deletar`/`editar` sempre fazem o padrão **ler tudo → modificar em memória → salvar tudo de novo**.
- O campo único (`matricula`) faz o papel de "chave primária". Não use `serialVersionUID` pra isso — ele é da *classe*, igual em todos os objetos, não identifica instâncias.

---

## 3. Criar a View (`view/EntidadesView.java`)

A view tem 3 partes: formulário de cadastro/edição, cabeçalho fixo da "tabela", e as linhas dinâmicas geradas a partir da coleção.

### 3.1 Esqueleto da classe

```java
public class EntidadesView extends VBox {

    private final ArrayList<Pane> linhas = new ArrayList<>();
    private Integer matriculaEdicao = null; // null = modo "criar"; != null = modo "editar"

    private TextField nomeInput;
    private TextField matriculaInput;
    private TextField comissaoInput;
    private Label labelModoEdicao;

    public void carregarDados() { ... }   // recria as linhas a partir da coleção

    public EntidadesView(double largura, double altura) { ... } // monta o form + cabeçalho
}
```

Os inputs e o `labelModoEdicao` são **campos da classe** (não variáveis locais do construtor) porque o botão "Editar", criado dentro de `carregarDados()`, precisa acessá-los.

### 3.2 `carregarDados()` — popula as linhas

```java
public void carregarDados() {
    this.getChildren().removeAll(linhas);
    linhas.clear();

    ArrayList<EntidadeModel> colecao = ColecaoEntidades.lerLista();
    for (EntidadeModel item : colecao) {
        Label labelNome = new Label("Nome: " + item.getNome());
        labelNome.relocate(30, 0);
        Label labelMatricula = new Label("Matrícula: " + item.getMatricula());
        labelMatricula.relocate(130, 0);
        Label labelComissao = new Label("Comissão: " + item.getComissao());
        labelComissao.relocate(230, 0);

        Button editar = new Button("Editar");
        editar.setOnAction(e -> {
            matriculaEdicao = item.getMatricula();
            nomeInput.setText(item.getNome());
            matriculaInput.setText(String.valueOf(item.getMatricula()));
            comissaoInput.setText(String.valueOf(item.getComissao()));
            labelModoEdicao.setText("Editando " + item.getNome());
        });
        editar.relocate(330, 0);

        Button deletar = new Button("Deletar");
        deletar.setOnAction(e -> {
            ColecaoEntidades.deletar(item.getMatricula());
            carregarDados();
        });
        deletar.relocate(430, 0);

        Pane root = new Pane();
        root.setPrefHeight(30);
        root.getChildren().addAll(labelNome, labelMatricula, labelComissao, editar, deletar);
        this.getChildren().add(root);
        linhas.add(root); // rastreado pra poder limpar sem afetar o cabeçalho
    }
}
```

**Regras que evitam os bugs mais comuns:**
- `removeAll(linhas)` + `linhas.clear()` no início — sem isso, cada clique em "Salvar" duplica todas as linhas.
- `y` sempre `0` dentro do `Pane` de cada linha — cada linha é um `Pane` novo com coordenadas próprias; não acumule `y` entre iterações.
- Cada `Label`/`Button` é `relocate`d individualmente — confira que você está chamando `relocate` na variável certa (erro clássico: relocar a mesma label duas vezes por engano).

### 3.3 Construtor — formulário + cabeçalho fixo

```java
public EntidadesView(double largura, double altura) {
    this.getStylesheets().add(
        getClass().getResource("/com/capitulozero/style/style.css").toExternalForm()
    );
    this.setPrefSize(largura, altura);
    this.setMaxSize(largura, altura);
    this.setPadding(new Insets(20));
    this.setBackground(new Background(new BackgroundFill(Theme.COR_BACKGROUND_TERCEARIO, CornerRadii.EMPTY, Insets.EMPTY)));

    Label titulo = new Label("Cadastro de Entidades");
    titulo.setTextFill(Theme.COR_TEXT_PRIMARIO);
    titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    this.getChildren().add(titulo);

    // título do modo de edição (some quando não está editando)
    labelModoEdicao = new Label();
    labelModoEdicao.setStyle("-fx-font-weight: bold;");
    this.getChildren().add(labelModoEdicao);

    // campos do formulário
    Label labelNome = new Label("Nome:");
    nomeInput = new TextField();
    Label labelMatricula = new Label("Matrícula:");
    matriculaInput = new TextField();
    Label labelComissao = new Label("Comissão:");
    comissaoInput = new TextField();

    Button submitButton = new Button("Salvar");
    submitButton.setOnAction(e -> {
        String nome = nomeInput.getText();
        int matricula = Integer.parseInt(matriculaInput.getText());
        double comissao = Double.parseDouble(comissaoInput.getText());
        EntidadeModel item = new EntidadeModel(nome, matricula, comissao);

        if (matriculaEdicao != null) {
            ColecaoEntidades.editar(matriculaEdicao, item);
            matriculaEdicao = null;
            labelModoEdicao.setText("");
        } else {
            ColecaoEntidades.adicionar(item);
        }

        nomeInput.clear();
        matriculaInput.clear();
        comissaoInput.clear();
        carregarDados();
    });

    this.getChildren().add(labelNome);
    this.getChildren().add(nomeInput);
    this.getChildren().add(labelMatricula);
    this.getChildren().add(matriculaInput);
    this.getChildren().add(labelComissao);
    this.getChildren().add(comissaoInput);
    this.getChildren().add(submitButton);

    // cabeçalho da "tabela" — criado uma única vez, fica fixo no topo
    Pane cabecalho = new Pane();
    cabecalho.setPrefHeight(30);
    Label h1 = new Label("Nome");       h1.relocate(30, 0);
    Label h2 = new Label("Matrícula");  h2.relocate(130, 0);
    Label h3 = new Label("Comissão");   h3.relocate(230, 0);
    Label h4 = new Label("Editar");     h4.relocate(330, 0);
    Label h5 = new Label("Deletar");    h5.relocate(430, 0);
    cabecalho.getChildren().addAll(h1, h2, h3, h4, h5);
    this.getChildren().add(cabecalho); // entra ANTES de carregarDados()

    carregarDados(); // já abre populado com o que tiver salvo
}
```

**Por que o cabeçalho fica fixo:** ele é adicionado uma única vez à `VBox`, fora de `carregarDados()`, e **nunca** entra na lista `linhas` — então `removeAll(linhas)` nunca o remove.

---

## 4. Linkar o CSS (opcional, mas recomendado)

Se quiser estilizar (cor de texto, fonte) por CSS em vez de `setTextFill`/`setStyle` espalhados:

```css
/* src/main/resources/com/capitulozero/style/style.css */
.label {
    -fx-text-fill: white;
}
```

E garanta que o construtor da view chama `this.getStylesheets().add(...)` (já está no passo 3.3).

---

## 5. Registrar a aba na Sidebar (`view/SideBarView.java`)

1. Adicionar o nome da aba na lista de opções:
```java
opcoes = new ArrayList<>(List.of(
    "📱 Dashboard", "📚 Livros", "👤 Autores", "🛒 Carrinho",
    "💵 Vendas", "👥 Entidades", "🙎‍♂️ ABOUT US"
));
```

2. Adicionar o `case` no `switch` que troca o conteúdo, usando **exatamente a mesma string** da lista de opções (esse é um erro comum: a string do `case` ficar diferente da string da lista, e a aba nunca abrir, cai no `default`):
```java
case "👥 Entidades":
    conteudoOpcoes.getChildren().add(new EntidadesView(w, h));
    break;
```

---

## 6. (Opcional) Atualizar `DashboardView.java`

Se o Dashboard também lista as opções (pra exibir cards/atalhos, por exemplo), adicione a mesma entrada lá também, mantendo a string idêntica à da Sidebar.

---

## 7. Testar o fluxo completo

- [ ] Abrir a aba nova pela sidebar e ver o formulário + cabeçalho aparecendo.
- [ ] Cadastrar um item → ele aparece na lista, inputs voltam vazios.
- [ ] Cadastrar um segundo item → o primeiro **continua** na lista (não duplicado, não perdido).
- [ ] Clicar "Editar" num item → inputs preenchem com os dados dele, título "Editando X" aparece.
- [ ] Clicar "Salvar" no modo edição → o item é atualizado (não cria um novo), título de edição desaparece, inputs voltam vazios.
- [ ] Clicar "Deletar" → item sai da lista e do arquivo `.ser`.
- [ ] Fechar e abrir a aplicação de novo → os dados persistem (foram lidos do `.ser`).

---

## Erros comuns a evitar

| Sintoma | Causa provável |
|---|---|
| Só o último item fica salvo | Algum método de "limpar tela" sendo chamado *dentro* do `for`, ou a coleção não está lendo a lista existente antes de adicionar |
| Itens duplicam a cada clique em "Salvar" | Esqueceu de limpar as linhas antigas (`removeAll(linhas)`) antes de repopular |
| Colunas desalinhadas mesmo com o mesmo `y` | `relocate` chamado na label errada (copy-paste com a variável trocada) |
| Aba não abre / cai em "em construção" | String do `case` no `switch` diferente da string na lista `opcoes` |
| `NullPointerException` ao linkar CSS | Caminho do `getResource(...)` não corresponde exatamente à pasta dentro de `resources` |