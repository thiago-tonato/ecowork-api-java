# EcoWork API -- Documentação Completa

## 📘 Descrição do Projeto

A **EcoWork API** é uma aplicação Java com Spring Boot que gerencia
espaços de coworking sustentáveis.\
Ela possibilita operações CRUD completas para gerenciamento de
organizações, reservas, salas e dados relacionados.

A API foi preparada para execução em **containers**, com deploy via
**Azure Container Registry (ACR)** e **Azure Container Instances
(ACI)**, além de conexão com um banco **PostgreSQL** executando também
em container.

------------------------------------------------------------------------

## 🏗 Arquitetura do Projeto

    ┌────────────────────────┐
    │        Front-end       │
    │ (React/Qualquer outro) │
    └───────────┬────────────┘
                |
                v
    ┌──────────────────────────────┐
    │        EcoWork API           │
    │   Java + Spring Boot         │
    │   Container Docker / ACI     │
    └───────────┬──────────────────┘
                |
                v
    ┌──────────────────────────────┐
    │     PostgreSQL Database      │
    │ Container Docker / ACI       │
    │      Acesso Externo          │
    └──────────────────────────────┘

### Componentes principais:

-   **Java 17**
-   **Spring Boot**
-   **Docker**
-   **ACR (Azure Container Registry)**
-   **ACI (Azure Container Instances)**
-   **PostgreSQL**
-   **Maven**

------------------------------------------------------------------------

## 🛠 Tecnologias Principais

  Tecnologia    Função
  ------------- ----------------------------
  Java 17       Linguagem da API
  Spring Boot   Framework principal
  PostgreSQL    Banco de dados
  Docker        Empacotamento da aplicação
  Azure ACR     Armazenamento das imagens
  Azure ACI     Execução dos containers
  Maven         Build & dependências

------------------------------------------------------------------------

# 🌐 Endpoints da API

### **Base URL exemplo:**

    http://<dns-label>.westeurope.azurecontainer.io:8080

## 📌 1. Organizações

### ➤ Criar nova organização

**POST** `/organizacao`

``` json
{
  "nome": "Empresa XPTO",
  "responsavel": "João Silva",
  "email": "contato@empresa.com",
  "telefone": "1199999999"
}
```

### ➤ Listar todas

**GET** `/organizacao`

### ➤ Buscar por ID

**GET** `/organizacao/{id}`

### ➤ Atualizar

**PUT** `/organizacao/{id}`

### ➤ Deletar

**DELETE** `/organizacao/{id}`

------------------------------------------------------------------------

# 🛢 Variáveis necessárias (Azure DevOps Library)

  -----------------------------------------------------------------------------------------------
  Nome                    Descrição                      Exemplo
  ----------------------- ------------------------------ ----------------------------------------
  `POSTGRES_HOST`         Host do banco                  `meubanco.postgres.database.azure.com`

  `POSTGRES_PORT`         Porta                          `5432`

  `POSTGRES_DB`           Nome do banco                  `ecowork`

  `POSTGRES_USER`         Usuário                        `admin`

  `POSTGRES_PASSWORD`     Senha                          `S3nh@F0rte`

  `ACR_NAME`              Nome do registry               `ecoworkregistry`

  `ACR_LOGIN_SERVER`      URL do ACR                     `ecoworkregistry.azurecr.io`

  `RESOURCE_GROUP`        Resource group do Azure        `rg-ecowork`

  `ACI_DNS_LABEL`         DNS público do container       `ecoworkapi123`

  `servicePrincipalId`    AppId do SPN                   \-

  `servicePrincipalKey`   Secret do SPN                  \-

  `tenantId`              Tenant ID                      \-
  -----------------------------------------------------------------------------------------------

------------------------------------------------------------------------

# 🗄 Como conectar no banco PostgreSQL

### 1. A partir do terminal

    psql -h <POSTGRES_HOST> -p <POSTGRES_PORT> -U <POSTGRES_USER> -d <POSTGRES_DB>

### 2. De uma ferramenta como DBeaver

-   Host: `POSTGRES_HOST`
-   Port: `5432`
-   User: `POSTGRES_USER`
-   Password: `POSTGRES_PASSWORD`
-   Database: `POSTGRES_DB`

Se o banco estiver em **container no Azure**, lembre-se de liberar a
porta externamente.

------------------------------------------------------------------------

# 🐳 Como acessar a aplicação no Azure

Após o pipeline fazer o deploy no ACI, a URL exibida será:

    http://$(ACI_DNS_LABEL).westeurope.azurecontainer.io:8080

Exemplo real:

    http://ecoworkapi123.westeurope.azurecontainer.io:8080/organizacao

------------------------------------------------------------------------

# 📦 Como rodar localmente

## 1. Criar banco local

``` bash
docker run --name ecowork-db -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=ecowork -p 5432:5432 -d postgres
```

## 2. Rodar aplicação

    mvn spring-boot:run

------------------------------------------------------------------------

# 🔧 application.yaml completo

``` yaml
spring:
  datasource:
    url: jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}
    username: ${POSTGRES_USER}
    password: ${POSTGRES_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
    show-sql: true

server:
  port: 8080
```

------------------------------------------------------------------------

# 🚀 CI/CD com Azure Pipeline

O pipeline: - Builda o Maven - Roda testes - Gera imagem Docker - Faz
push pro ACR - Cria/atualiza o ACI - Exibe URL de acesso
