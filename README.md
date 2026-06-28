# RTH - Farmácia de Manipulação

Sistema de gerenciamento para farmácia de manipulação, desenvolvido em Java com interface gráfica Swing e banco de dados MySQL. Trabalho final da disciplina de Banco de Dados.

**Grupo 11 — Integrantes:** Henrique Júnio, Rafael Ferreira, Thiago Bresolini

---

## Sobre o Sistema

O sistema permite gerenciar o fluxo completo de uma farmácia de manipulação: cadastro de clientes, profissionais, fornecedores, insumos, lotes, fórmulas manipuladas e pedidos.

### Módulos disponíveis

| Módulo | Funcionalidades |
|---|---|
| Clientes | Cadastro completo com endereço e contato |
| Profissionais | Cadastro com suporte a farmacêutico (CRF obrigatório) |
| Fornecedores | Dados comerciais e de contato |
| Insumos / Materiais | Matérias-primas, excipientes, embalagens |
| Lotes de Insumos | Controle de estoque com datas de fabricação e validade |
| Fórmulas Manipuladas | Fórmulas com composição (insumos + dosagens) |
| Pedidos | Pedidos com múltiplas fórmulas, cálculo de total automático |
| Relatórios | 4 relatórios via views SQL do banco |

---

## Pré-requisitos

- **Java JDK 11+** (testado com JDK 21)
- **MySQL 8.0+** rodando em `localhost:3306`
- Banco de dados `rth` criado a partir do arquivo `Dump20260628.sql`

---

## Configuração do Banco de Dados

1. Abra o MySQL Workbench (ou outro cliente MySQL)
2. Execute o arquivo `Dump20260628.sql` para criar o banco e inserir os dados de exemplo:
   ```sql
   source Dump20260628.sql;
   ```
3. Verifique que o banco `rth` foi criado com as tabelas:
   `cliente`, `profissional`, `fornecedor`, `insumo_material`, `lote_insumo`, `formula_manipulada`, `formula_composicao`, `pedido`, `formula_pedido`

4. Se a sua senha do MySQL for diferente de `root`, edite o arquivo:
   `FarmaciaManipulacao/src/util/Conexao.java`
   ```java
   String senha = "root"; // altere aqui
   ```

---

## Como Rodar — VSCode

> **Requisito:** Instalar a extensão **Extension Pack for Java** (Microsoft) no VSCode.

1. No VSCode, abra **somente** a pasta do projeto Java:
   - `File → Open Folder` → selecione a pasta `FarmaciaManipulacao/`
2. Aguarde o VSCode indexar o projeto (barra inferior ficará azul/verde)
3. Pressione **F5** ou clique em `Run → Start Debugging`
4. Selecione a configuração **"Farmácia de Manipulação"**

Alternativamente, abra o arquivo `Principal.java` e clique no botão **▶ Run** que aparece acima do método `main`.

---

## Como Rodar — Apache NetBeans

1. Abra o NetBeans
2. `File → Open Project` → selecione a pasta `FarmaciaManipulacao/`
3. O NetBeans reconhecerá automaticamente como projeto Ant
4. Clique em **Run Project** (F6)

> **Nota:** Se o NetBeans reclamar do driver MySQL, clique com o botão direito no projeto → `Properties → Libraries → Add JAR/Folder` e adicione `FarmaciaManipulacao/lib/mysql-connector-j-9.7.0.jar`.

---

## Estrutura do Projeto

```
FarmaciaManipulacao/
├── lib/
│   └── mysql-connector-j-9.7.0.jar   # Driver JDBC MySQL
├── src/
│   ├── util/
│   │   └── Conexao.java               # Conexão com o banco
│   ├── model/                         # Classes de dados (POJOs)
│   │   ├── ClienteModel.java
│   │   ├── ProfissionalModel.java
│   │   ├── FornecedorModel.java
│   │   ├── InsumoMaterialModel.java
│   │   ├── LoteInsumoModel.java
│   │   ├── FormulaManipuladaModel.java
│   │   ├── FormulaComposicaoModel.java
│   │   ├── PedidoModel.java
│   │   └── FormulaPedidoModel.java
│   ├── controller/                    # Regras de negócio + CRUD
│   │   ├── ClienteController.java
│   │   ├── ProfissionalController.java
│   │   ├── FornecedorController.java
│   │   ├── InsumoMaterialController.java
│   │   ├── LoteInsumoController.java
│   │   ├── FormulaManipuladaController.java
│   │   ├── FormulaComposicaoController.java
│   │   ├── PedidoController.java
│   │   └── FormulaPedidoController.java
│   └── view/                          # Telas Swing (GUI)
│       ├── Principal.java             # Janela principal (MDI)
│       ├── ClienteView.java
│       ├── ProfissionalView.java
│       ├── FornecedorView.java
│       ├── InsumoMaterialView.java
│       ├── LoteInsumoView.java
│       ├── FormulaManipuladaView.java
│       ├── PedidoView.java
│       └── RelatorioView.java
├── .vscode/
│   ├── settings.json                  # Configuração do classpath VSCode
│   └── launch.json                    # Configuração de execução VSCode
└── nbproject/                         # Configurações Apache NetBeans
```

---

## Banco de Dados

- **SGBD:** MySQL 8.0
- **Banco:** `rth`
- **Conexão padrão:** `localhost:3306` — usuário `root`, senha `root`
- **Script:** `Dump20260628.sql`

### Tabelas

| Tabela | Descrição |
|---|---|
| `cliente` | Dados dos clientes |
| `profissional` | Farmacêuticos e atendentes |
| `fornecedor` | Fornecedores de insumos |
| `insumo_material` | Matérias-primas e materiais |
| `lote_insumo` | Lotes com validade e estoque |
| `formula_manipulada` | Fórmulas criadas |
| `formula_composicao` | Composição das fórmulas (N:N) |
| `pedido` | Pedidos dos clientes |
| `formula_pedido` | Fórmulas por pedido (N:N) |

### Views (Relatórios)

| View | Descrição |
|---|---|
| `vw_pedidos_completos` | Todos os pedidos com dados do cliente e atendente |
| `vw_lotes_vencimento` | Lotes com validade nos próximos 60 dias |
| `vw_formula_composicao` | Composição completa das fórmulas |
| `vw_pedidos_cidade` | Pedidos acima de R$ 50 agrupados por cidade |
