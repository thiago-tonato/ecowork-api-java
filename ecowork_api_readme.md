# EcoWork API -- Documentação Completa

## 📘 Descrição do Projeto

A **EcoWork API** é uma aplicação Java com Spring Boot que gerencia
espaços de coworking sustentáveis.
Ela possibilita operações CRUD completas para gerenciamento de
organizações, reservas, salas e dados relacionados.

A API foi preparada para execução em **containers**, com deploy via
**Azure Container Registry (ACR)** e **Azure Container Instances (ACI)**, além de conexão com um banco **MySQL** executando também
em container.

------------------------------------------------------------------------

## 🏗 Arquitetura do Projeto

```
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
    │     MySQL Database           │
    │ Container Docker / ACI       │
    │      Acesso Externo          │
    └──────────────────────────────┘
```

### Componentes principais:

-   **Java 17**
-   **Spring Boot**
-   **Docker**
-   **ACR (Azure Container Registry)**
-   **ACI (Azure Container Instances)**
-   **MySQL**
-   **Maven**

------------------------------------------------------------------------

## 🛠 Tecnologias Principais

| Tecnologia    | Função                        |
| ------------- | ----------------------------- |
| Java 17       | Linguagem da API              |
| Spring Boot   | Framework principal           |
| MySQL         | Banco de dados                |
| Docker        | Empacotamento da aplicação    |
| Azure ACR     | Armazenamento das imagens     |
| Azure ACI     | Execução dos containers       |
| Maven         | Build & dependências          |

------------------------------------------------------------------------

# 🌐 Endpoints da API

### **Base URL exemplo:**

```
http://<dns-label>.eastus.azurecontainer.io:8080
```

## 📌 1. Organizações

### ➤ Criar nova organização

**POST** `/organizacao`

```json
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

| Nome                    | Descrição                      | Exemplo |
| ----------------------- | ------------------------------ | ------- |
| `MYSQL_HOST`            | Host do banco                  | `ecoworkdb.eastus.azurecontainer.io` |
| `MYSQL_PORT`            | Porta                          | `3306` |
| `MYSQL_DATABASE`        | Nome do banco                  | `ecoworkdb` |
| `MYSQL_USER`            | Usuário                        | `ecowork-user` |
| `MYSQL_PASSWORD`        | Senha                          | `ecoworkFIAP!` |
| `ACR_NAME`              | Nome do registry               | `ecoworkacr` |
| `RESOURCE_GROUP`        | Resource group do Azure        | `ecowork-rg` |
| `ACI_DNS_LABEL`         | DNS público do container       | `ecowork-api` |

------------------------------------------------------------------------

# 🗄 Como conectar no banco MySQL

### 1. A partir do terminal

```bash
mysql -h <MYSQL_HOST> -P <MYSQL_PORT> -u <MYSQL_USER> -p <MYSQL_DATABASE>
```

### 2. De uma ferramenta como DBeaver

- Host: `MYSQL_HOST`
- Port: `MYSQL_PORT`
- User: `MYSQL_USER`
- Password: `MYSQL_PASSWORD`
- Database: `MYSQL_DATABASE`

Se o banco estiver em **container no Azure**, lembre-se de liberar a
porta externamente.

------------------------------------------------------------------------

# 🐳 Como acessar a aplicação no Azure

Após o pipeline fazer o deploy no ACI, a URL exibida será:

```
http://${ACI_DNS_LABEL}.eastus.azurecontainer.io:8080
```

Exemplo real:

```
http://ecowork-api.eastus.azurecontainer.io:8080/organizacao
```

------------------------------------------------------------------------

# 📦 Como rodar localmente

## 1. Criar banco local

```bash
docker run --name ecowork-db -e MYSQL_ROOT_PASSWORD=ecoworkFIAP! -e MYSQL_DATABASE=ecoworkdb -e MYSQL_USER=ecowork-user -e MYSQL_PASSWORD=ecoworkFIAP! -p 3306:3306 -d mysql:8.0
```

## 2. Rodar aplicação

```bash
mvn spring-boot:run
```

------------------------------------------------------------------------

# 🔧 application.yaml completo

```yaml
server:
  port: 8080

spring:
  application:
    name: ecowork-api

  datasource:
    url: jdbc:mysql://${MYSQL_HOST:127.0.0.1}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:ecoworkdb}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: ${MYSQL_USER:ecowork-user}
    password: ${MYSQL_PASSWORD:ecoworkFIAP!}
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
    show-sql: true

  flyway:
    enabled: true
    locations: classpath:db/migration

logging:
  level:
    root: INFO
    org.springframework.web: INFO
    com.ecowork: DEBUG

management:
  endpoints:
    web:
      exposure:
        include: health,info
```

------------------------------------------------------------------------

# 🚀 CI/CD com Azure Pipeline

O pipeline realiza os seguintes passos:
- Build com Maven
- Roda testes
- Gera imagem Docker da aplicação
- Faz push da imagem para o ACR
- Cria ou atualiza o container no ACI
- Exibe a URL de acesso da aplicação

