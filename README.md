# RA3-Projeto POO: Capítulo Zero 📚

> Aplicação desktop desenvolvida em Java para gestão completa de uma livraria, com CRUD nativo, persistência em arquivos e interface gráfica 100% codificada.

O **Capítulo Zero** é um sistema desktop robusto focado na gestão de uma livraria, permitindo o controle total de estoque, vendas, clientes e fornecedores. A proposta é desenvolver interfaces gráficas totalmente codificadas (sem FXML/SceneBuilder) que permitam a criação, consulta, atualização e exclusão de registros, armazenados em arquivos locais com tratamento de exceções e identidade visual personalizada.

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-17+-007396?logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17+-007396?logo=java&logoColor=white)
![Build](https://img.shields.io/badge/build-passing-brightgreen)

---

## 📌 Índice

- [Visão Geral](#-visão-geral)
- [Status do Projeto](#-status-do-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Requisitos do Projeto](#-requisitos-do-projeto)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Fluxo da Aplicação](#-fluxo-da-aplicação)
- [Modelo de Dados e Módulos](#-modelo-de-dados-e-módulos)
- [Identidade Visual](#-identidade-visual)
- [Configuração e Instalação](#️-configuração-e-instalação)
- [Validação e Formatação](#-validação-e-formatação)
- [Controle de Versão e Git](#-controle-de-versão-e-git)
- [Autores](#️-autores)

---

## 📖 Visão Geral

Este projeto consiste na implementação de um sistema de gerenciamento para uma livraria fictícia ou real, utilizando **JavaFX** para a interface gráfica e **Persistência em Arquivo** para o armazenamento de dados. O foco principal é a separação clara entre a camada de domínio (Model) e a camada de apresentação (View), garantindo que a lógica de negócio esteja desacoplada da interface.

O sistema foi desenhado para uma equipe de 6 integrantes, onde cada um desenvolve o CRUD completo para duas classes de modelo específicas (Totalizando 12 módulos), com validações de dados, formatação de datas em português do Brasil e tratamento de erros robusto.

---

## 🚧 Status do Projeto

O projeto está em fase de desenvolvimento e implementação final. As frentes atuais incluem:

- Estrutura de classes de domínio (Model) para 12 entidades (Livros, Autores, Clientes, etc.) com pelo menos 3 atributos cada.
- Implementação de interfaces gráficas puramente via código Java (sem FXML/SceneBuilder).
- Persistência de dados em arquivos de texto/binários.
- Tratamento de exceções global e específico por campo.
- Validação de datas (DD/MM/AAAA) e campos numéricos.
- Aplicação da paleta de cores temática da livraria.

> **Atenção:** O uso de SceneBuilder ou FXML resultará em nota zero. Todo o layout deve ser construído via código Java.

---

## 🚀 Tecnologias Utilizadas

### Core
- **Linguagem:** Java 17 ou superior
- **Interface Gráfica:** JavaFX (API nativa - `GridPane`, `VBox`, `TableView`, etc.)
- **Persistência:** I/O Streams (`File`, `BufferedReader`, `BufferedWriter`, `ObjectOutputStream`)

### Ferramentas
- **Gerenciador de Dependências:** Maven ou Gradle
- **IDE:** IntelliJ IDEA, Eclipse ou NetBeans
- **Controle de Versão:** Git

---

## ⚠️ Requisitos do Projeto

Para atender aos critérios de avaliação, o sistema deve obedecer estritamente às seguintes regras:

1.  **Classes Model:** Mínimo de **2 classes por aluno** (Total de 12 para a equipe), cada uma com no mínimo **3 atributos**.
2.  **Funcionalidades CRUD:**
    - **Inserção:** Formulário para cadastrar novos registros.
    - **Consulta:** Tabela para listar registros e selecionar um item.
    - **Atualização:** Edição de dados a partir da tela de consulta.
    - **Exclusão:** Remoção de registros a partir da tela de consulta.
3.  **Interface Gráfica:**
    - Todos os componentes devem ser instanciados e configurados via código Java.
    - **Proibido:** `FXMLLoader`, `FXML`, `SceneBuilder`.
    - Campos numéricos devem aceitar apenas números.
    - Campos de data devem usar formato **DD/MM/AAAA**.
4.  **Persistência:** Dados salvos em arquivos locais (sem banco de dados relacional).
5.  **Tratamento de Exceções:** Obrigatório para todas as operações de I/O e validação.
6.  **Coerência:** As classes criadas devem fazer sentido para o contexto de uma livraria (ex: Livro, Autor, Venda, Estoque).

---

## 📂 Estrutura de Pastas

O projeto segue uma arquitetura em camadas para separar a lógica de domínio da interface.

```text
CapituloZero/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/capitulozero/
│   │   │   │   ├── model/              # Classes de Domínio (Entidades)
│   │   │   │   │   ├── Livro.java
│   │   │   │   │   ├── Autor.java
│   │   │   │   │   ├── Cliente.java
│   │   │   │   │   ├── Venda.java
│   │   │   │   │   └── ... (12 classes no total)
│   │   │   │   ├── view/               # Classes de Interface (JavaFX via código)
│   │   │   │   │   ├── TelaLivro.java
│   │   │   │   │   ├── TelaAutor.java
│   │   │   │   │   ├── TelaCliente.java
│   │   │   │   │   └── ...
│   │   │   │   ├── util/               # Utilitários (Tratamento de exceções, Leitura/Escrita, Estilos)
│   │   │   │   │   ├── ArquivoUtil.java
│   │   │   │   │   ├── Validador.java
│   │   │   │   │   └── Estilos.java
│   │   │   │   └── Main.java           # Classe Principal (Entrada do programa e Menu)
│   │   └── resources/
│   │       └── dados/                  # Pasta para arquivos de persistência
│   │           ├── livros.txt
│   │           ├── autores.txt
│   │           └── ...
├── docs/                               # Documentação do projeto
└── README.md
