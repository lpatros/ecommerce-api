<div align="center">
  <h1 style="font-size: 32px; border: none; line-height: 0; font-weight: bold">Ecommerce API</h1>
  <p>Desenvolvimento de uma API RESTful, estruturada segundo o padrão arquitetural MVC (Model-View-Controller), com o objetivo de gerenciar produtos, pedidos, usuários e demais recursos de um sistema de e-commerce.</p>
    <div style="margin-bottom: 10px">
    <img src="https://img.shields.io/badge/Language-Java-orange.svg" alt="Java: 21"/>
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?logo=Spring&logoColor=white" alt="Spring Boot: 3.5.6"/>
    <img src="https://img.shields.io/badge/PostgreSQL-blue?logo=Postgresql&logoColor=white" alt="PostgreSQL"/>
    <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License: MIT"/>
    </div>
    <br>
</div>

# Links Rápidos

- [Descrição](#descrição)
- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Licença](#licença)

## Descrição

Este projeto é uma **API RESTful** desenvolvida com **Spring Boot** para o gerenciamento completo de um ecommerce. A aplicação implementa operações CRUD (Create, Read, Update, Delete) para categorias, produtos, usuários e pedidos, seguindo as melhores práticas de desenvolvimento e arquitetura em camadas.

A API utiliza **PostgreSQL** como banco de dados, **Flyway** para versionamento de migrations, e **Spring Data JPA** para a camada de persistência.

## Tecnologias

- **Java 21**
- **Spring Boot 3.5.6**
  - Spring Web
  - Spring Data JPA
  - Spring Boot DevTools
- **PostgreSQL** - Banco de dados relacional
- **Flyway** - Controle de versão do banco de dados
- **Lombok** - Redução de código boilerplate
- **SpringDoc OpenAPI** - Documentação automática da API (Swagger)
- **Maven** - Gerenciamento de dependências

## Funcionalidades

### Gestão de Categorias
- Criar, listar, atualizar e remover categorias de produtos
- Filtros e paginação

### Gestão de Produtos
- CRUD completo de produtos
- Associação com categorias
- Controle de estoque
- Paginação e ordenação

### Gestão de Usuários
- CRUD completo de usuários
- Validações de dados

### Gestão de Pedidos
- Criação e consulta de pedidos
- Itens de pedido associados
- Cálculo de totais

## Estrutura do Projeto

```
ecommerce-api/
├── src/
│   └── main/
│      ├── java/com/lpatros/ecommerce_api/
│      │   ├── configuration/      # Configurações da aplicação
│      │   ├── controller/         # Controllers REST
│      │   ├── dto/                # Data Transfer Objects
│      │   ├── entity/             # Entidades JPA
│      │   ├── exception/          # Tratamento de exceções
│      │   ├── mapper/             # Mapeadores DTO <-> Entity
│      │   ├── repository/         # Repositórios JPA
│      │   ├── service/            # Regras de negócio
│      │   ├── util/               # Utilitários
│      │   └── validator/          # Validadores customizados
│      └── resources/
│          ├── application.properties
│          └── db/migration/       # Scripts Flyway
│
├── pom.xml                        # Dependências Maven
└── README.md
```

## Licença

Este projeto está licenciado sob a Licença [MIT](https://github.com/lpatros/ecommerce-api/blob/main/LICENSE.txt).
