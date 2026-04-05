# RPG Guild API

API REST para gerenciamento da Guilda de Aventureiros —missões, aventureiros e painel tático operacional.

## Tecnologias

| Tecnologia    | Versão | Propósito                 |
| ------------- | ------ | ------------------------- |
| Java          | 21     | Linguagem principal       |
| Spring Boot   | 4.0.2  | Framework                 |
| PostgreSQL    | 17     | Banco de dados relacional |
| Redis         | 7.4    | Cache                     |
| Elasticsearch | 9.x    | Busca e analytics         |
| Kibana        | 9.3.1  | Visualização de dados     |
| Lombok        | —      | Redução de boilerplate    |

---

## Índice

- [RPG Guild API](#rpg-guild-api)
  - [Tecnologias](#tecnologias)
  - [Índice](#índice)
  - [Pré-requisitos](#pré-requisitos)
  - [Como executar](#como-executar)
  - [Arquitetura](#arquitetura)
  - [Domínio](#domínio)
    - [Schema `aventura` (domínio principal)](#schema-aventura-domínio-principal)
    - [Schema `audit`](#schema-audit)
    - [Schema `operacoes`](#schema-operacoes)
    - [Enums](#enums)
  - [Endpoints](#endpoints)
    - [Health Check](#health-check)
    - [Aventureiros](#aventureiros)
    - [Companheiros](#companheiros)
    - [Missões](#missões)
  - [Tratamento de erros](#tratamento-de-erros)
  - [Estrutura do projeto](#estrutura-do-projeto)
  - [Cache](#cache)

---

## Pré-requisitos

- Java 21+
- Docker & Docker Compose
- Maven 3.9+

---

## Como executar

```bash
# 1. Subir infraestrutura (PostgreSQL, Redis, Elasticsearch, Kibana)
cd docker && docker-compose up -d

# 2. Executar aplicação
cd .. && ./mvnw spring-boot:run
```

**URLs padrão:**

| Serviço       | URL                              |
| ------------- | -------------------------------- |
| API           | http://localhost:8080/api        |
| Health Check  | http://localhost:8080/api/health |
| Kibana        | http://localhost:5601            |
| Elasticsearch | http://localhost:9200            |
| Redis         | localhost:6379                   |

---

## Arquitetura

```
presentation/     → Controllers (REST) e DTOs
application/      → Services (lógica de negócio)
infrastructure/   → Repositories (acesso a dados)
domain/           → Entities, Enums, Value Objects
shared/           → Exceptions e Error handling
```

**Camadas:**

- **Controllers** → Recebem requisições HTTP e delegam aos Services
- **Services** → Contêm lógica de negócio, lançam exceções customizadas
- **Repositories** → Acesso ao banco via JPA/Hibernate

**Padrões:**

- 1 Controller + 1 Service por operação
- `@ControllerAdvice` centraliza tratamento de exceções
- Cache Redis para operações pesadas (ex: painel tático)

---

## Domínio

### Schema `aventura` (domínio principal)

| Entidade             | Descrição                                              |
| -------------------- | ------------------------------------------------------ |
| `Aventureiro`        | Membro da guilda com nome, classe, nível e companheiro |
| `Companheiro`        | Entidadecompanheiro do aventureiro (composição)        |
| `Missao`             | Tarefa assignada a aventureiros                        |
| `ParticipacaoMissao` | Associa aventureiros a missões com papel e recompensa  |

### Schema `audit`

| Entidade      | Descrição                                 |
| ------------- | ----------------------------------------- |
| `Organizacao` | Organização que agrupa usuários           |
| `Usuario`     | Usuário do sistema com roles e permissões |

### Schema `operacoes`

| Entidade                  | Descrição                                  |
| ------------------------- | ------------------------------------------ |
| `vw_painel_tatico_missao` | View materializada para painel operacional |

### Enums

| Enum           | Valores                                    |
| -------------- | ------------------------------------------ |
| `Classes`      | GUERREIRO, MAGO, ARQUEIRO, CLERIGO, LADINO |
| `Especies`     | LOBO, CORUJA, GOLEM, DRAGAO_MINIATURA      |
| `StatusMissao` | ABERTA, EM_PROGRESSO, CONCLUIDA            |
| `NivelPerigo`  | BAIXO, MEDIO, ALTO, CRITICO                |
| `PapelMissao`  | LIDER, MEMBRO, SUPORTE                     |

---

## Endpoints

### Health Check

| Método | Rota          | Descrição                      |
| ------ | ------------- | ------------------------------ |
| GET    | `/api/health` | Verifica se a API está rodando |

### Aventureiros

| Método | Rota                                    | Descrição                                |
| ------ | --------------------------------------- | ---------------------------------------- |
| POST   | `/api/aventureiros/create`              | Registra novo aventureiro                |
| GET    | `/api/aventureiros`                     | Lista aventureiros (paginado, filtrável) |
| GET    | `/api/aventureiros/{id}`                | Busca aventureiro por ID                 |
| PATCH  | `/api/aventureiros/{id}`                | Atualiza dados do aventureiro            |
| GET    | `/api/aventureiros/buscar`              | Busca por nome (parcial)                 |
| GET    | `/api/aventureiros/{id}/detalhes`       | Detalhes completos + missões             |
| PATCH  | `/api/aventureiros/guilda/remove/{id}`  | Encerra vínculo com a guilda             |
| PATCH  | `/api/aventureiros/guilda/recruit/{id}` | Recruta novamente                        |
| GET    | `/api/aventureiros/ranking`             | Ranking por participação                 |

**Filtros disponíveis em `/api/aventureiros`:**

| Parâmetro     | Tipo    | Descrição                               |
| ------------- | ------- | --------------------------------------- |
| `page`        | int     | Número da página (default: 0)           |
| `size`        | int     | Itens por página (default: 10, max: 50) |
| `classe`      | String  | Filtrar por classe                      |
| `ativo`       | boolean | Filtrar por status                      |
| `nivelMinimo` | int     | Nível mínimo                            |

### Companheiros

| Método | Rota                                       | Descrição                    |
| ------ | ------------------------------------------ | ---------------------------- |
| POST   | `/api/companheiros/create/{aventureiroId}` | Define/substitui companheiro |
| DELETE | `/api/companheiros/delete/{aventureiroId}` | Remove companheiro           |

### Missões

| Método | Rota                         | Descrição                              |
| ------ | ---------------------------- | -------------------------------------- |
| GET    | `/api/missoes`               | Lista missões (paginado)               |
| GET    | `/api/missoes/{id}/detalhes` | Detalhes da missão + participantes     |
| GET    | `/api/missoes/relatorio`     | Relatório agregado de missões          |
| GET    | `/api/missoes/top15dias`     | Painel tático — top 10 últimos 15 dias |

**Filtros disponíveis em `/api/missoes`.**

| Parâmetro     | Tipo   | Descrição                      |
| ------------- | ------ | ------------------------------ |
| `page`        | int    | Número da página (default: 0)  |
| `size`        | int    | Itens por página (default: 10) |
| `status`      | String | Filtrar por status             |
| `nivelPerigo` | String | Filtrar por nível de perigo    |

---

## Tratamento de erros

Todas as exceções são capturadas pelo `@ControllerAdvice` e retornam JSON padronizado:

```json
{
  "status": 404,
  "message": "O aventureiro não foi encontrado."
}
```

**Exceções mapeadas:**

| Exceção                             | HTTP Status |
| ----------------------------------- | ----------- |
| `AventureiroNotFoundException`      | 404         |
| `MissaoNotFoundException`           | 404         |
| `AventureiroInvalidParamsException` | 401         |
| `CompanheiroInvalidParamsException` | 401         |
| `InvalidQueryParamException`        | 401         |

---

## Estrutura do projeto

```
tp1-spring/
├── docker/
│   └── docker-compose.yaml          # PostgreSQL, Redis, ES, Kibana
├── src/main/java/com/edu/infnet/tp1/
│   ├── application/services/        # Lógica de negócio
│   │   ├── aventura/
│   │   └── painelTaticoMissao/
│   ├── domain/
│   │   ├── enums/                  # Classes, Especies, StatusMissao...
│   │   └── models/                 # Entidades JPA
│   │       ├── aventura/            # Aventureiro, Missao, Companheiro...
│   │       ├── organizacao/         # Usuario, Organizacao, Role...
│   │       └── painelTaticoMissao/  # View de analytics
│   ├── infrastructure/repositories/ # JPA Repositories
│   ├── presentation/controllers/    # REST Controllers
│   │   ├── aventura/
│   │   ├── painelTaticoMissao/
│   │   └── HealthCheck.java
│   └── shared/                     # Exceções, Error handling
├── src/main/resources/
│   └── application.properties       # Configurações
├── src/test/
├── endpoints.http                   # Collection para REST Client (VS Code)
├── pom.xml
└── README.md
```

**Schemas do banco:**

| Schema      | Uso                                     |
| ----------- | --------------------------------------- |
| `aventura`  | Domínio principal (Aventureiro, Missao) |
| `audit`     | Domínio legado (Usuario, Organizacao)   |
| `operacoes` | Analytics (vw_painel_tatico_missao)     |

---

## Cache

O Redis armazena em cache operações pesadas:

- **Chave:** `topMissoes15Dias`
- **TTL:** 60 segundos
- **Estratégia:** `@Cacheable` via Spring Cache
