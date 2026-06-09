# RA3-Projeto POO 🎓

> Aplicação desktop desenvolvida em Java para demonstrar a implementação de CRUD completo com persistência em arquivos e interface gráfica nativa.

O **RA3-Projeto POO** é um sistema desktop focado na gestão de entidades de um projeto acadêmico (como Clientes, Fornecedores, ou entidades específicas de um caso de negócio). A proposta é desenvolver interfaces gráficas totalmente codificadas (sem FXML/SceneBuilder) que permitam a criação, consulta, atualização e exclusão de registros, armazenados em arquivos locais com robusto tratamento de exceções.

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
- [Modelo de Dados](#-modelo-de-dados)
- [Configuração e Instalação](#️-configuração-e-instalação)
- [Validação e Formatação](#-validação-e-formatação)
- [Entrega e Avaliação](#-entrega-e-avaliação)
- [Autores](#️-autores)

---

## 📖 Visão Geral

Este projeto consiste na implementação de um sistema de gerenciamento de dados utilizando **JavaFX** para a interface gráfica e **Persistência em Arquivo** para o armazenamento de dados. O foco principal é a separação clara entre a camada de domínio (Model) e a camada de apresentação (View), garantindo que a lógica de negócio esteja desacoplada da interface.

O sistema permite que cada aluno da equipe desenvolva o CRUD completo para pelo menos duas classes de modelo, com validações de dados, formatação específica para o português do Brasil e tratamento de erros robusto.

---

## 🚧 Status do Projeto

O projeto está em fase de desenvolvimento e implementação final. As frentes atuais incluem:

- Estrutura de classes de domínio (Model) com pelo menos 3 atributos.
- Implementação de interfaces gráficas puramente via código Java (sem FXML).
- Persistência de dados em arquivos de texto/binários.
- Tratamento de exceções global e específico por campo.
- Validação de datas (DD/MM/AAAA) e campos numéricos.

> **Atenção:** O uso de SceneBuilder ou FXML resultará em nota zero. Todo o layout deve ser construído via código.

---

## 🚀 Tecnologias Utilizadas

### Core
- **Linguagem:** Java 17 ou superior
- **Interface Gráfica:** JavaFX (API nativa)
- **Persistência:** I/O Streams (File, BufferedReader, BufferedWriter, etc.)

### Ferramentas
- **Gerenciador de Dependências:** Maven ou Gradle (opcional, dependendo do ambiente)
- **IDE:** IntelliJ IDEA, Eclipse ou NetBeans
- **Controle de Versão:** Git

---

## ⚠️ Requisitos do Projeto

Para atender aos critérios de avaliação, o sistema deve obedecer estritamente às seguintes regras:

1. **Classes Model:** Mínimo de 2 classes por aluno, cada uma com no mínimo **3 atributos**.
2. **Funcionalidades CRUD:**
   - **Inserção:** Formulário para cadastrar novos registros.
   - **Consulta:** Tabela para listar registros e selecionar um item.
   - **Atualização:** Edição de dados a partir da tela de consulta.
   - **Exclusão:** Remoção de registros a partir da tela de consulta.
3. **Interface Gráfica:**
   - Todos os componentes devem ser instanciados e configurados via código Java.
   - **Proibido:** `FXMLLoader`, `FXML`, `SceneBuilder`.
   - Campos numéricos devem aceitar apenas números.
   - Campos de data devem usar formato **DD/MM/AAAA**.
4. **Persistência:** Dados salvos em arquivos locais.
5. **Tratamento de Exceções:** Obrigatório para todas as operações de I/O e validação.
6. **Coerência:** As classes criadas devem fazer sentido para o contexto do projeto (ex: não criar "Vendedores de Seguros" em um projeto de PetStore sem justificativa).

---

## 📂 Estrutura de Pastas

O projeto segue uma arquitetura em camadas para separar a lógica de domínio da interface.

```text
projeto-javafx/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/projeto/model/        # Classes de Domínio (Entidades)
│   │   │   │   ├── Cliente.java
│   │   │   │   ├── Fornecedor.java
│   │   │   │   └── ...
│   │   │   ├── com/projeto/view/         # Classes de Interface (JavaFX via código)
│   │   │   │   ├── TelaCliente.java
│   │   │   │   ├── TelaFornecedor.java
│   │   │   │   └── ...
│   │   │   ├── com/projeto/controller/   # Lógica de controle (opcional)
│   │   │   ├── com/projeto/util/         # Utilitários (Tratamento de exceções, Leitura/Escrita)
│   │   │   │   ├── ArquivoUtil.java
│   │   │   │   └── Validador.java
│   │   │   └── com/projeto/Main.java     # Classe Principal (Entrada do programa)
│   │   └── resources/
│   │       └── dados/                    # Pasta para arquivos de persistência (.txt, .dat)
│   │           ├── clientes.txt
│   │           └── fornecedores.txt
├── docs/                                 # Documentação do projeto (PDF)
│   └── projeto.pdf
└── README.md
