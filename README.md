# 🌐 Rede Social Universitária — Grafo + POO

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Neo4j](https://img.shields.io/badge/Neo4j-008CC1?style=for-the-badge&logo=neo4j&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-78C257?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge&logo=lombok&logoColor=white)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge)

> API REST desenvolvida com Java, Spring Boot e Neo4j (banco de grafo) para modelar uma rede social universitária, com foco em conexões entre usuários, sugestão de amigos em comum e publicações com estrutura variável.

---

## 📋 Sobre o Projeto

Este projeto nasceu de um trabalho acadêmico de modelagem de banco de dados, cujo desafio era escolher os modelos de dados mais adequados para uma rede social universitária. Após análise comparativa entre banco relacional, orientado a documentos, chave-valor e grafo, o grupo optou por uma combinação de **Grafo** e **Orientação a Objeto**, por dois motivos centrais:

- **Relacionamentos entre usuários** (seguidores, amigos em comum, sugestões) são resolvidos por **travessia de conexões** — o sistema percorre as ligações entre pessoas diretamente, em vez de comparar atributos em tabelas, o que é naturalmente mais rápido e escalável em um banco de grafo (Neo4j).
- **Publicações** têm estrutura variável (texto, imagem, vídeo, enquete), cada uma com campos próprios — resolvido com **herança em Java** (uma classe mãe `Post` com os atributos comuns, e classes filhas para cada tipo específico).

---

## ✅ Funcionalidades

- Cadastro, busca, listagem e remoção de usuários
- Cadastro, busca, listagem e remoção de publicações (com herança para texto, imagem e enquete)
- Sistema de "seguir" entre usuários, representado como aresta no grafo
- Consulta de **amigos em comum** via travessia de grafo (Cypher)
- Validações de regra de negócio (auto-seguir, seguir duplicado, campos obrigatórios)
- Persistência real de dados com Neo4j, rodando localmente via Docker

---

## 🏗️ Arquitetura

```
src/
├── Controller/   → Endpoints da API
├── Service/      → Regras de negócio e validações
├── Repository/   → Interfaces Spring Data Neo4j + consultas Cypher
└── Model/        → Entidades do grafo (@Node) e relacionamentos (@Relationship)
```

---

## 🛠️ Tecnologias Utilizadas

- **Java** — Linguagem principal
- **Spring Boot** — Framework web
- **Spring Data Neo4j** — Persistência sobre banco de grafo
- **Neo4j** — Banco de dados orientado a grafo
- **Cypher** — Linguagem de consulta do Neo4j
- **Docker Compose** — Orquestração do banco de dados local
- **JUnit 5 + Mockito** — Testes automatizados (unitários)
- **Lombok** — Redução de código boilerplate
- **Maven** — Gerenciamento de dependências

---

## ⚙️ Pré-requisitos

- Java 17+
- Docker Desktop instalado e rodando
- Maven

---

## 🚀 Como Rodar

```bash
# Clone o repositório
git clone https://github.com/EdsonEmanuelReis/SiteExemplo
cd SiteExemplo
```

Suba o banco de dados Neo4j localmente com Docker:

```bash
docker compose up -d
```

> Isso inicia um container Neo4j em `localhost:7687` (conexão) e `localhost:7474` (interface visual, o Neo4j Browser).

Configure a conexão em `src/main/resources/application.properties` (já vem preenchido por padrão, ajuste se necessário):

```properties
spring.neo4j.uri=bolt://localhost:7687
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=senha123
```

```bash
# Abra no IntelliJ IDEA e rode a classe principal
# Ou rode via terminal:
mvn spring-boot:run
```

A API estará disponível em:
```
http://localhost:8080
```

Para parar o banco de dados:
```bash
docker compose down
```

---

## 📡 Principais Rotas

> Ajuste os caminhos abaixo conforme os nomes reais definidos no seu `Controller`.

### Usuário
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/usuario/salvar` | Cadastra um usuário |
| GET | `/usuario/buscar?id=1` | Busca usuário por id |
| GET | `/usuario/listar` | Lista todos os usuários |
| DELETE | `/usuario/excluir?id=1` | Remove um usuário |
| POST | `/usuario/seguir?idSeguido=1&idSeguindo=2` | Um usuário passa a seguir outro |
| GET | `/usuario/amigosEmComum?idA=1&idB=2` | Retorna amigos em comum entre dois usuários |

### Post
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/post/salvar` | Cadastra uma publicação |
| GET | `/post/buscar?id=1` | Busca publicação por id |
| GET | `/post/listar` | Lista todas as publicações |
| DELETE | `/post/excluir?id=1` | Remove uma publicação |

---

## 🧪 Testes Automatizados

O projeto conta com testes unitários (JUnit 5 + Mockito) cobrindo as regras de negócio do `UsuarioService`, incluindo:

- ✅ Um usuário consegue seguir outro com sucesso
- ❌ Um usuário não pode seguir a si mesmo
- ❌ Um usuário não pode seguir alguém que não existe
- ❌ Um usuário não pode seguir a mesma pessoa duas vezes

Para rodar os testes:
```bash
mvn test
```

---

## 🧠 Por que Grafo?

Uma consulta como "encontrar amigos em comum entre dois usuários" é resolvida em Neo4j com uma travessia direta pelas conexões, sem a necessidade de múltiplos `JOIN`s como em um banco relacional:

```cypher
MATCH (a {id: $idA})-[:SEGUE]->(c), (b {id: $idB})-[:SEGUE]->(c)
RETURN c
```

Essa consulta busca uma pessoa `c` que seja seguida tanto pelo usuário `a` quanto pelo usuário `b`. Como o Neo4j armazena as conexões como ponteiros diretos entre os nós (index-free adjacency), esse tipo de busca mantém desempenho constante mesmo com o crescimento da rede — diferente de um banco relacional, onde o custo da busca cresce junto com o volume de dados.

---

## 🔮 Planejamento Futuro

| Área | Funcionalidades |
|------|----------------|
| 🎨 Front-end | Interface web simples para demonstração visual das conexões |
| 🧪 Testes | Testes de integração com banco Neo4j real, cobertura do `PostService` |
| 📡 API | Validações com Bean Validation, exceções globais, Swagger/OpenAPI |
| 🌱 Dados | Script de seed com dados de exemplo para facilitar testes locais |

---

## 👨‍💻 Autor

Projeto acadêmico desenvolvido para a disciplina de Modelagem de Banco de Dados.

---

> ⚠️ Projeto em fase de aprendizado e desenvolvimento contínuo. Novas funcionalidades e melhorias serão adicionadas ao longo do tempo.
