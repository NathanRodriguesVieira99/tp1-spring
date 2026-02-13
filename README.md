# ⚔️ TP1 Spring Boot

> API REST para o Registro Oficial da Guilda de Aventureiros — dados em memória, sem banco externo.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen?logo=springboot)
![Lombok](https://img.shields.io/badge/Lombok-enabled-blue)

---

## Sumário

- [Como executar](#-como-executar)
- [Conceitos do Domínio](#-conceitos-do-domínio)
- [Rotas da API](#-rotas-da-api)
- [Arquivo de testes HTTP](#-arquivo-de-testes-http)
- [Padrão de Erro](#-padrão-de-erro)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Observações](#-observações)

---

## 🚀 Como executar

**Pré-requisitos:** Java 21+

```bash
# Clonar o repositório
git clone <repo-url>
cd tp1-spring

# Execute (pela IDE) ou via terminal
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080/api`.

Valide se está rodando em `http://localhost:8080/api/health`

---

## 📖 Conceitos do Domínio

### Aventureiro

| Campo         | Tipo       | Obrigatório         | Descrição             |
| ------------- | ---------- | ------------------- | --------------------- |
| `id`          | `UUID`     | Gerado pelo sistema | Identificador único   |
| `nome`        | `String`   | Sim                 | Nome do aventureiro   |
| `classe`      | `Enum`     | Sim                 | Classe do aventureiro |
| `nivel`       | `Number`   | Sim                 | Nível (≥ 1)           |
| `ativo`       | `Boolean`  | Auto (`true`)       | Status na guilda      |
| `companheiro` | `Optional` | Não                 | Companheiro associado |

**Classes permitidas:** `GUERREIRO` · `MAGO` · `ARQUEIRO` · `CLERIGO` · `LADINO`

### Companheiro (Composição)

| Campo      | Tipo     | Obrigatório | Descrição              |
| ---------- | -------- | ----------- | ---------------------- |
| `nome`     | `String` | Sim         | Nome do companheiro    |
| `especie`  | `Enum`   | Sim         | Espécie do companheiro |
| `lealdade` | `Number` | Sim         | Valor entre 0 e 100    |

**Espécies permitidas:** `LOBO` · `CORUJA` · `GOLEM` · `DRAGAO_MINIATURA`

---

## 📡 Rotas da API

### Visão geral

| #   | Operação                       | Método   | Rota                       | Status          |
| --- | ------------------------------ | -------- | -------------------------- | --------------- |
| 1   | Registrar aventureiro          | `POST`   | `/api/aventureiros/create` | ✅ Implementado |
| 2   | Listar aventureiros            | `GET`    | `/api/aventureiros`        | ✅ Implementado |
| 3   | Consultar aventureiro por ID   | `GET`    | `/api/aventureiros/{id}`   | ✅ Implementado |
| 4   | Atualizar dados do aventureiro | `PATCH`  | `/api/aventureiros/{id}`   | ✅ Implementado |
| 5   | Encerrar vínculo com a guilda  | `PATCH`  | `/api/aventureiro/{id}`    | ⬜ Pendente     |
| 6   | Recrutar novamente             | `PATCH`  | `—`                        | ⬜ Pendente     |
| 7   | Definir/substituir companheiro | `POST`   | `/api/companheiros/{id}`   | ⬜ Pendente     |
| 8   | Remover companheiro            | `DELETE` | `/api/companheiros/{id}`   | ⬜ Pendente     |

---

### 1️⃣ Registrar aventureiro

Registra um novo aventureiro na guilda.

|             |                            |
| ----------- | -------------------------- |
| **Método**  | `POST`                     |
| **Rota**    | `/api/aventureiros/create` |
| **Sucesso** | `201 Created`              |

<details>
<summary><strong>Request Body</strong></summary>

```json
{
  "nome": "Patolino",
  "classe": "MAGO",
  "nivel": 10,
  "companheiro": null
}
```

</details>

**Regras:**

- O `id` é gerado automaticamente (`UUID`)
- O aventureiro inicia com `ativo = true`
- O campo `companheiro` é forçado como vazio nesta operação
- `nome` não pode ser vazio
- `classe` deve ser uma das classes permitidas
- `nivel` deve ser ≥ 1

---

### 2️⃣ Listar aventureiros (com filtros e paginação)

Retorna aventureiros cadastrados com suporte a filtros e paginação.

|             |                     |
| ----------- | ------------------- |
| **Método**  | `GET`               |
| **Rota**    | `/api/aventureiros` |
| **Sucesso** | `200 OK`            |

**Query Params:**

| Param         | Tipo      | Default | Descrição                |
| ------------- | --------- | ------- | ------------------------ |
| `page`        | `int`     | `0`     | Número da página (≥ 0)   |
| `size`        | `int`     | `10`    | Itens por página (1–50)  |
| `classe`      | `String`  | —       | Filtrar por classe       |
| `ativo`       | `Boolean` | —       | Filtrar por status       |
| `nivelMinimo` | `Integer` | —       | Filtrar por nível mínimo |

**Exemplo:**

```
GET /api/aventureiros?page=0&size=10&classe=GUERREIRO&ativo=true
```

**Response Headers de paginação:**

| Header          | Descrição                    |
| --------------- | ---------------------------- |
| `X-Total-Count` | Total de registros filtrados |
| `X-Page`        | Página atual                 |
| `X-Size`        | Tamanho da página            |
| `X-Total-Pages` | Total de páginas             |

<details>
<summary><strong>Response Body</strong> (resumo, sem companheiro)</summary>

```json
[
  {
    "id": "da8411c2-717a-4236-8f92-8d5930c8d66b",
    "nome": "Aventureiro1",
    "classe": "GUERREIRO",
    "nivel": 0.6527025396143293,
    "ativo": true
  },
  {
    "id": "c680e602-2e1e-4b93-ba74-b4afd19663ff",
    "nome": "Aventureiro2",
    "classe": "GUERREIRO",
    "nivel": 0.598126588911085,
    "ativo": true
  },
  {
    "id": "d783608f-f0b8-4890-81eb-8f20828df7d8",
    "nome": "Aventureiro3",
    "classe": "GUERREIRO",
    "nivel": 0.7963234753338072,
    "ativo": true
  }

  // ...
]
```

</details>

---

### 3️⃣ Consultar aventureiro por ID

Retorna todas as informações do aventureiro, incluindo o companheiro.

|             |                                              |
| ----------- | -------------------------------------------- |
| **Método**  | `GET`                                        |
| **Rota**    | `/api/aventureiros/{id}`                     |
| **Sucesso** | `200 OK`                                     |
| **Erro**    | `404 Not Found` — aventureiro não encontrado |

**Exemplo:**

```
GET /api/aventureiros/75ab4bfb-7cb7-4f4a-a2a7-8f1f4f855337
```

---

### 4️⃣ Atualizar dados do aventureiro

Atualiza parcialmente os dados de um aventureiro existente.

|             |                                              |
| ----------- | -------------------------------------------- |
| **Método**  | `PATCH`                                      |
| **Rota**    | `/api/aventureiros/{id}`                     |
| **Sucesso** | `200 OK`                                     |
| **Erro**    | `404 Not Found` — aventureiro não encontrado |

<details>
<summary><strong>Request Body</strong> (todos os campos são opcionais)</summary>

```json
{
  "nome": "Perna Longa",
  "classe": "GUERREIRO",
  "nivel": 30
}
```

</details>

**Regras:**

- Apenas `nome`, `classe` e `nivel` podem ser atualizados
- Não é possível alterar `id`, `ativo` ou `companheiro`
- Campos nulos são ignorados (atualização parcial)

---

### 5️⃣ Encerrar vínculo com a guilda _(pendente)_

Altera o estado do aventureiro para `ativo = false`. O aventureiro permanece registrado no sistema.

|            |                         |
| ---------- | ----------------------- |
| **Método** | `PATCH`                 |
| **Rota**   | `/api/aventureiro/{id}` |

---

### 6️⃣ Recrutar novamente _(pendente)_

Altera o estado do aventureiro para `ativo = true`.

|            |         |
| ---------- | ------- |
| **Método** | `PATCH` |

---

### 7️⃣ Definir ou substituir companheiro _(pendente)_

Cria ou substitui o companheiro associado a um aventureiro.

|            |                          |
| ---------- | ------------------------ |
| **Método** | `POST`                   |
| **Rota**   | `/api/companheiros/{id}` |

<details>
<summary><strong>Request Body esperado</strong></summary>

```json
{
  "nome": "Felpudo",
  "especie": "LOBO",
  "lealdade": 85
}
```

</details>

---

### 8️⃣ Remover companheiro _(pendente)_

Remove o companheiro associado ao aventureiro.

|            |                          |
| ---------- | ------------------------ |
| **Método** | `DELETE`                 |
| **Rota**   | `/api/companheiros/{id}` |

---

## 🧪 Arquivo de testes HTTP

O arquivo [`endpoints.http`](endpoints.http) na raiz do projeto contém exemplos prontos para testar as rotas implementadas utilizando a extensão [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) do VS Code.

---

## ❌ Padrão de Erro

Todas as respostas de erro seguem o formato JSON padronizado pela aplicação. Dois mecanismos principais ajudam a padronizar o retorno e evitar o uso de try catch nos controllers:

- a classe `ErrorMessage` em `src/main/java/com/edu/infnet/tp1/shared/errors/ErrorMessage.java` — modelo usado por alguns `@ControllerAdvice` existentes;
- o `@ControllerAdvice` global em `src/main/java/com/edu/infnet/tp1/shared/RestControllerAdvice.java` — centraliza o tratamento das exceções lançadas pelos `Service`s e controla o body e o status HTTP.

Exemplo de resposta de erro que a aplicação retorna quando parâmetros são inválidos:

```json
{
  "status": "status code (ex:404,401)",
  "message": "message"
}
```

Como funciona:

- Nos `Services` lance exceções customizadas com mensagens claras, por exemplo:

```java
throw new AventureiroInvalidParamsException();
```

- O `@ControllerAdvice` centralizado intercepta essas exceções e transforma em um `ResponseEntity` com o JSON padronizado (`HttpStatus` + `message`). Veja `src/main/java/com/edu/infnet/tp1/shared/RestControllerAdvice.java`.

---

## 🗂️ Estrutura do Projeto

```
src/main/java/com/edu/infnet/tp1/
├── controllers/                     # Controladores REST (1 por operação)
│   ├── RegistrarAventureiroController.java
│   ├── BuscarAventureiroPorIdController.java
│   ├── ListarAventureirosController.java
│   ├── AtualizarDadosAventureiroController.java
│   ├── DefinirCompanheiroController.java
│   ├── RemoverCompanheiroController.java
│   ├── EncerrarVinculoGuildaController.java
│   └── RecrutarNovamenteController.java
├── services/                        # Regras de negócio (1 por operação)
│   ├── RegistrarAventureiroService.java
│   ├── BuscarAventureiroPorIdService.java
│   ├── ListarAventureirosService.java
│   ├── AtualizarDadosAventureiroService.java
│   ├── DefinirCompanheiroService.java
│   ├── RemoverCompanheiroService.java
│   ├── EncerrarVinculoGuildaService.java
│   └── RecrutarNovamenteService.java
├── models/                          # Entidades do domínio
│   ├── Aventureiro.java
│   └── Companheiro.java
├── enums/                           # Enumerações
│   ├── Classes.java
│   └── Especies.java
├── data/                            # Simulação de banco de dados (ArrayList)
│   └── AventureiroData.java
└── shared/
  ├── dtos/                        # Data Transfer Objects
  │   ├── AtualizarAventureiroRequestDto.java
  │   ├── PaginationQueryDto.java
  │   └── PaginationResponseDto.java
  ├── errors/                      # Padrão de erro
  │   └── ErrorMessage.java
  ├── exceptions/                  # Exceções customizadas
  │   ├── AventureiroNotFoundException.java
  │   ├── AventureiroInvalidParamsException.java
  └── RestControllerAdvice.java    # Handlers de exceção centralizados
├── Tp1Application.java              # Classe principal
```

---

## 📌 Observações

- **Sem banco de dados** — os dados são armazenados em `ArrayList` em memória (`AventureiroData`)
- A lista é inicializada com **100 aventureiros** da classe `GUERREIRO` ao iniciar a aplicação
- Arquitetura: **1 Controller + 1 Service por operação**
