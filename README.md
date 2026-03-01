# ⚔️ TP1 Spring Boot

> API REST para o Registro Oficial da Guilda de Aventureiros — integração com PostgreSQL e domínio de Aventuras & Missões.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?logo=postgresql)
![Lombok](https://img.shields.io/badge/Lombok-enabled-blue)

---

## Sumário

- [Como executar](#-como-executar)
- [Conceitos do Domínio](#-conceitos-do-domínio)
- [Rotas da API](#-rotas-da-api)
- [Padrão de Erro](#-padrão-de-erro)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Observações](#-observações)

---

## 🚀 Como executar

**Pré-requisitos:**

- Java 21+
- Docker & Docker Compose

```bash
# Clonar o repositório
git clone <repo-url>
cd tp1-spring

# Inicie o banco de dados (PostgreSQL)
docker-compose up -d

# Execute a aplicação (pela IDE) ou via terminal
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080/api`.

Valide se está rodando em `http://localhost:8080/api/health`

---

## 📖 Conceitos do Domínio

### Aventureiro

| Campo         | Tipo       | Obrigatório         | Descrição             |
| ------------- | ---------- | ------------------- | --------------------- |
| `id`          | `Long`     | Gerado pelo sistema | Identificador único   |
| `nome`        | `String`   | Sim                 | Nome do aventureiro   |
| `classe`      | `Enum`     | Sim                 | Classe do aventureiro |
| `nivel`       | `Integer`  | Sim                 | Nível (≥ 1)           |
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

### Missão

| Campo         | Tipo     | Obrigatório | Descrição                              |
| ------------- | -------- | ----------- | -------------------------------------- |
| `id`          | `Long`   | Gerado      | Identificador único                    |
| `titulo`      | `String` | Sim         | Título da missão                       |
| `status`      | `Enum`   | Sim         | Status (ABERTA/EM_PROGRESSO/CONCLUIDA) |
| `nivelPerigo` | `Enum`   | Sim         | Nível (BAIXO/MEDIO/ALTO/CRITICO)       |
| `dataInicio`  | `Date`   | Não         | Data de início da missão               |
| `dataTermino` | `Date`   | Não         | Data de término da missão              |

### Participação em Missão

| Campo            | Tipo          | Obrigatório | Descrição                     |
| ---------------- | ------------- | ----------- | ----------------------------- |
| `id`             | `Long`        | Gerado      | Identificador único           |
| `missao`         | `Missao`      | Sim         | Referência à missão           |
| `aventureiro`    | `Aventureiro` | Sim         | Aventureiro participante      |
| `papel`          | `Enum`        | Sim         | Papel (LIDER/MEMBRO/SUPORTE)  |
| `recompensaOuro` | `BigDecimal`  | Não         | Ouro recebido como recompensa |
| `destaque`       | `Boolean`     | Auto        | Se teve performance destaque  |

---

## 📡 Rotas da API

### Visão geral

| #   | Operação                       | Método   | Rota                                    | Status          |
| --- | ------------------------------ | -------- | --------------------------------------- | --------------- |
| 1   | Registrar aventureiro          | `POST`   | `/api/aventureiros/create`              | ✅ Implementado |
| 2   | Listar aventureiros            | `GET`    | `/api/aventureiros`                     | ✅ Implementado |
| 3   | Consultar aventureiro por ID   | `GET`    | `/api/aventureiros/{id}`                | ✅ Implementado |
| 4   | Atualizar dados do aventureiro | `PATCH`  | `/api/aventureiros/{id}`                | ✅ Implementado |
| 5   | Encerrar vínculo com a guilda  | `PATCH`  | `/api/aventureiros/guilda/remove/{id}`  | ✅ Implementado |
| 6   | Recrutar novamente             | `PATCH`  | `/api/aventureiros/guilda/recruit/{id}` | ✅ Implementado |
| 7   | Definir/substituir companheiro | `POST`   | `/api/companheiros/create/{id}`         | ✅ Implementado |
| 8   | Remover companheiro            | `DELETE` | `/api/companheiros/delete/{id}`         | ✅ Implementado |
| 9   | Buscar aventureiro por nome    | `GET`    | `/api/aventureiros/buscar?nome=...`     | ✅ Implementado |
| 10  | Detalhes completo aventureiro  | `GET`    | `/api/aventureiros/{id}/detalhes`       | ✅ Implementado |
| 11  | Listar missões                 | `GET`    | `/api/missoes`                          | ✅ Implementado |
| 12  | Detalhes de uma missão         | `GET`    | `/api/missoes/{id}/detalhes`            | ✅ Implementado |
| 13  | Ranking de participação        | `GET`    | `/api/aventureiros/ranking`             | ✅ Implementado |
| 14  | Relatório de missões           | `GET`    | `/api/missoes/relatorio`                | ✅ Implementado |

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

<details>
<summary><strong>Response Body</strong></summary>

```json
{
  "id": "75ab4bfb-7cb7-4f4a-a2a7-8f1f4f855337",
  "nome": "Patolino",
  "classe": "MAGO",
  "nivel": 10,
  "ativo": true,
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
GET /api/aventureiros?page=0&size=10&classe=GUERREIRO&ativo=true&nivelMinimo=1
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
GET /api/aventureiros/1
```

<details>
<summary><strong>Response Body</strong></summary>

```json
{
  "id": 1,
  "nome": "Filipe",
  "classe": "GUERREIRO",
  "nivel": 42,
  "ativo": true,
  "companheiro": {
    "id": 1,
    "nome": "Felpudo",
    "especie": "LOBO",
    "lealdade": 85
  }
}
```

</details>

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

<details>
<summary><strong>Response Body</strong></summary>

```json
{
  "id": 2,
  "nome": "Perna Longa",
  "classe": "GUERREIRO",
  "nivel": 30,
  "ativo": true,
  "companheiro": null
}
```

</details>

**Regras:**

- Apenas `nome`, `classe` e `nivel` podem ser atualizados
- Não é possível alterar `id`, `ativo` ou `companheiro`
- Campos nulos são ignorados (atualização parcial)

---

### 5️⃣ Encerrar vínculo com a guilda

Altera o estado do aventureiro para `ativo = false`. O aventureiro permanece registrado no sistema.

|             |                                                                      |
| ----------- | -------------------------------------------------------------------- |
| **Método**  | `PATCH`                                                              |
| **Rota**    | `/api/aventureiros/guilda/remove/{id}`                               |
| **Sucesso** | `200 OK` — retorna o aventureiro atualizado                          |
| **Erro**    | `400 Bad Request` (UUID inválido) · `404 Not Found` (não encontrado) |

**Exemplo (curl):**

```bash
curl -i -X PATCH "http://localhost:8080/api/aventureiros/guilda/remove/1"
```

**Comportamento:**

- Se o `id` for válido e o aventureiro estiver `ativo`, o campo `ativo` será definido como `false` e o recurso atualizado será retornado.
- Se o `id` estiver ausente na rota o endpoint não será casado (HTTP 404 no roteamento); para evitar isso, envie sempre o `id` na URL.
- Se o `id` tiver formato inválido o servidor responde `400 Bad Request`.

---

### 6️⃣ Recrutar novamente

Reativa um aventureiro previamente removido da guilda (define `ativo = true`).

|             |                                                                      |
| ----------- | -------------------------------------------------------------------- |
| **Método**  | `PATCH`                                                              |
| **Rota**    | `/api/aventureiros/guilda/recruit/{id}`                              |
| **Sucesso** | `200 OK` — retorna o aventureiro atualizado                          |
| **Erro**    | `400 Bad Request` (UUID inválido) · `404 Not Found` (não encontrado) |

**Exemplo (curl):**

```bash
curl -i -X PATCH "http://localhost:8080/api/aventureiros/guilda/recruit/1"
```

**Comportamento:**

- Se o `id` for válido e o aventureiro estiver `ativo = false`, o campo `ativo` será definido como `true` e o recurso atualizado será retornado.
- Se o `id` não for encontrado, retorna `404 Not Found`.

---

### 7️⃣ Definir ou substituir companheiro

Cria ou substitui o companheiro associado a um aventureiro.

|             |                                                                                     |
| ----------- | ----------------------------------------------------------------------------------- |
| **Método**  | `POST`                                                                              |
| **Rota**    | `/api/companheiros/create/{id}`                                                     |
| **Sucesso** | `201 Created` — retorna o `companheiro` criado                                      |
| **Erro**    | `400 Bad Request` (params inválidos) · `404 Not Found` (aventureiro não encontrado) |

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

<details>
<summary><strong>Response Body</strong></summary>

```json
{
  "nome": "Felpudo",
  "especie": "LOBO",
  "lealdade": 85
}
```

</details>

---

### 8️⃣ Remover companheiro

Remove o companheiro associado ao aventureiro e retorna o aventureiro atualizado.

|             |                                                                      |
| ----------- | -------------------------------------------------------------------- |
| **Método**  | `DELETE`                                                             |
| **Rota**    | `/api/companheiros/delete/{id}`                                      |
| **Sucesso** | `200 OK` — retorna o aventureiro sem o `companheiro`                 |
| **Erro**    | `400 Bad Request` (UUID inválido) · `404 Not Found` (não encontrado) |

**Exemplo (curl):**

```bash
curl -i -X DELETE "http://localhost:8080/api/companheiros/delete/1"
```

**Comportamento:**

- Se o `id` for válido e o aventureiro possuir um companheiro, o campo `companheiro` será definido como `null` e o aventureiro atualizado será retornado.
- Se o `id` não existir, retorna `404 Not Found`.

---

### 9️⃣ Buscar aventureiro por nome

Busca aventureiros usando filtro textual parcial no nome.

|             |                            |
| ----------- | -------------------------- |
| **Método**  | `GET`                      |
| **Rota**    | `/api/aventureiros/buscar` |
| **Sucesso** | `200 OK`                   |

| Param  | Tipo     | Obrigatório | Descrição                  |
| ------ | -------- | ----------- | -------------------------- |
| `nome` | `String` | Sim         | Termo de busca parcial     |
| `page` | `int`    | Não         | Página (default: 0)        |
| `size` | `int`    | Não         | Itens/página (default: 10) |

**Exemplo:**

```
GET /api/aventureiros/buscar?nome=Filipe&page=0&size=10
```

<details>
<summary><strong>Response Body</strong></summary>

```json
{
  "content": [
    {
      "id": 1,
      "nome": "Filipe",
      "classe": "GUERREIRO",
      "nivel": 42,
      "ativo": true,
      "companheiro": {
        "id": 1,
        "nome": "Felpudo",
        "especie": "LOBO",
        "lealdade": 85
      }
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "currentPage": 0
}
```

</details>

---

### 1️⃣0️⃣ Detalhes completo do aventureiro

Retorna informações completas incluindo total de participações em missões e a última missão em que participou.

|             |                                              |
| ----------- | -------------------------------------------- |
| **Método**  | `GET`                                        |
| **Rota**    | `/api/aventureiros/{id}/detalhes`            |
| **Sucesso** | `200 OK`                                     |
| **Erro**    | `404 Not Found` — aventureiro não encontrado |

**Exemplo:**

```
GET /api/aventureiros/1/detalhes
```

<details>
<summary><strong>Response Body</strong></summary>

```json
{
  "id": 1,
  "nome": "Filipe",
  "classe": "GUERREIRO",
  "nivel": 42,
  "ativo": true,
  "companheiro": {
    "id": 1,
    "nome": "Felpudo",
    "especie": "LOBO",
    "lealdade": 85
  },
  "totalParticipacoes": 15,
  "ultimaMissao": "Explorar a Caverna Escura"
}
```

</details>

---

### 1️⃣1️⃣ Listar missões

Lista todas as missões com paginação.

|             |                |
| ----------- | -------------- |
| **Método**  | `GET`          |
| **Rota**    | `/api/missoes` |
| **Sucesso** | `200 OK`       |

| Param  | Tipo  | Obrigatório | Descrição                  |
| ------ | ----- | ----------- | -------------------------- |
| `page` | `int` | Não         | Página (default: 0)        |
| `size` | `int` | Não         | Itens/página (default: 10) |

**Exemplo:**

```
GET /api/missoes?page=0&size=10
```

<details>
<summary><strong>Response Body</strong></summary>

```json
{
  "content": [
    {
      "id": 1,
      "titulo": "Explorar a Caverna Escura",
      "status": "CONCLUIDA",
      "nivelPerigo": "ALTO"
    },
    {
      "id": 2,
      "titulo": "Derrotar o Dragão de Fogo",
      "status": "ATIVA",
      "nivelPerigo": "CRITICO"
    }
  ],
  "totalElements": 2,
  "totalPages": 1,
  "currentPage": 0
}
```

</details>

---

### 1️⃣2️⃣ Detalhes de uma missão

Retorna informações completas de uma missão.

|             |                                         |
| ----------- | --------------------------------------- |
| **Método**  | `GET`                                   |
| **Rota**    | `/api/missoes/{id}/detalhes`            |
| **Sucesso** | `200 OK`                                |
| **Erro**    | `404 Not Found` — missão não encontrada |

**Exemplo:**

```
GET /api/missoes/1/detalhes
```

<details>
<summary><strong>Response Body</strong></summary>

```json
{
  "id": 1,
  "titulo": "Explorar a Caverna Escura",
  "status": "CONCLUIDA",
  "nivelPerigo": "ALTO"
}
```

</details>

---

### 1️⃣3️⃣ Ranking de participação

Retorna ranking de aventureiros por participações, recompensas e destaques.

|             |                             |
| ----------- | --------------------------- |
| **Método**  | `GET`                       |
| **Rota**    | `/api/aventureiros/ranking` |
| **Sucesso** | `200 OK`                    |

<details>
<summary><strong>Response Body</strong></summary>

```json
[
  {
    "aventureiroId": 1,
    "nome": "Filipe",
    "totalParticipacoes": 15,
    "somaRecompensas": "7500.00",
    "totalDestaques": 5
  },
  {
    "aventureiroId": 2,
    "nome": "Perna Longa",
    "totalParticipacoes": 12,
    "somaRecompensas": "6000.00",
    "totalDestaques": 3
  }
]
```

</details>

---

### 1️⃣4️⃣ Relatório de missões

Retorna relatório agregado de missões com estatísticas por missão.

|             |                          |
| ----------- | ------------------------ |
| **Método**  | `GET`                    |
| **Rota**    | `/api/missoes/relatorio` |
| **Sucesso** | `200 OK`                 |

<details>
<summary><strong>Response Body</strong></summary>

```json
[
  {
    "id": 1,
    "titulo": "Explorar a Caverna Escura",
    "status": "CONCLUIDA",
    "nivelPerigo": "ALTO",
    "totalParticipantes": 4,
    "totalRecompensas": "5000.00"
  },
  {
    "id": 2,
    "titulo": "Derrotar o Dragão de Fogo",
    "status": "ATIVA",
    "nivelPerigo": "CRITICO",
    "totalParticipantes": 6,
    "totalRecompensas": "15000.00"
  }
]
```

</details>

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

---

## 🧪 Arquivo de testes HTTP

- Nos `Services` lance exceções customizadas com mensagens claras, por exemplo:

```java
throw new AventureiroInvalidParamsException();
```

- O `@ControllerAdvice` centralizado intercepta essas exceções e transforma em um `ResponseEntity` com o JSON padronizado (`HttpStatus` + `message`). Veja `src/main/java/com/edu/infnet/tp1/shared/RestControllerAdvice.java`.

---

## 🗂️ Estrutura do Projeto

```
src/main/java/com/edu/infnet/tp1/
├── controllers/
│   ├── aventura/
│   │   ├── RegistrarAventureiroController.java
│   │   ├── BuscarAventureiroPorIdController.java
│   │   ├── ListarAventureirosController.java
│   │   ├── AtualizarDadosAventureiroController.java
│   │   ├── DefinirCompanheiroController.java
│   │   ├── RemoverCompanheiroController.java
│   │   ├── EncerrarVinculoGuildaController.java
│   │   ├── RecrutarNovamenteController.java
│   │   ├── BuscaAventureiroPorNomeController.java
│   │   ├── VisualizacaoAventureiroCompletaController.java
│   │   ├── ListarMissoesController.java
│   │   ├── BuscaMissaoPorIdController.java
│   │   ├── RankingParticipacaoController.java
│   │   └── RelatorioMissoesController.java
│   └── HealthCheck.java
├── services/
│   ├── aventura/
│   │   ├── RegistrarAventureiroService.java
│   │   ├── BuscarAventureiroPorIdService.java
│   │   ├── ListarAventureirosService.java
│   │   ├── AtualizarDadosAventureiroService.java
│   │   ├── DefinirCompanheiroService.java
│   │   ├── RemoverCompanheiroService.java
│   │   ├── EncerrarVinculoGuildaService.java
│   │   ├── RecrutarNovamenteService.java
│   │   ├── BuscaAventureiroPorNomeService.java
│   │   ├── VisualizacaoAventureiroCompletaService.java
│   │   ├── ListarMissoesService.java
│   │   ├── BuscaMissaoPorIdService.java
│   │   ├── RankingParticipacaoService.java
│   │   └── RelatorioMissoesService.java
│   └── organizacao/
├── repositories/
│   ├── aventura/
│   │   ├── AventureiroRepository.java
│   │   ├── CompanheiroRepository.java
│   │   ├── MissaoRepository.java
│   │   └── ParticipacaoMissaoRepository.java
│   └── organizacao/
│       ├── OrganizacaoRepository.java
│       ├── UsuarioRepository.java
│       ├── RoleRepository.java
│       ├── PermissionRepository.java
│       └── UsuarioRoleRepository.java
├── models/
│   ├── aventura/
│   │   ├── Aventureiro.java
│   │   ├── Companheiro.java
│   │   ├── Missao.java
│   │   └── ParticipacaoMissao.java
│   └── organizacao/
│       ├── Organizacao.java
│       ├── Usuario.java
│       ├── Role.java
│       ├── Permission.java
│       └── UsuarioRole.java
├── enums/
│   ├── Classes.java
│   ├── Especies.java
│   ├── NivelPerigo.java
│   ├── StatusMissao.java
│   └── PapelMissao.java
├── data/
│   └── AventureiroData.java
└── shared/
    ├── dtos/
    │   ├── AtualizarAventureiroRequestDto.java
    │   ├── PaginationQueryDto.java
    │   ├── PaginationResponseDto.java
    │   ├── BuscaAventureiroPorNomeDto.java
    │   ├── AventureiroDetalhesDto.java
    │   ├── MissaoFiltroDto.java
    │   ├── MissaoResponseDto.java
    │   ├── RankingParticipacaoDto.java
    │   ├── RelatorioMissaoDto.java
    │   ├── CompanheiroResponseDto.java
    │   └── AventureiroResponseDto.java
    ├── errors/
    │   └── ErrorMessage.java
    ├── exceptions/
    │   ├── AventureiroNotFoundException.java
    │   ├── AventureiroInvalidParamsException.java
    │   ├── CompanheiroInvalidParamsException.java
    │   ├── InvalidQueryParamException.java
    │   └── MissaoNotFoundException.java
    └── RestControllerAdvice.java

src/main/resources/
├── application.properties
└── application-test.properties

docker-compose.yml
endpoints.http
pom.xml
```

---

## 📌 Observações

- **Banco de dados:** PostgreSQL 17 em Docker (schema `aventura` para domínio novo, schema `audit` para legado)
- **Arquitetura:** 3 camadas (Controllers → Services → Repositories → JPA Entities)
- **Padrão:** 1 Controller + 1 Service por operação
- **Tratamento de Exceções:** Centralizado no `@ControllerAdvice`
- **DTOs:** Segregação de dados por domínio (aventura vs organizacao)

---
