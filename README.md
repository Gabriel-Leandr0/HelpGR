# HelpGR

## Objetivo
Este projeto foi desenvolvido com o objetivo de criar uma API para gerenciamento de chamados, permitindo que clientes possam registrar solicitações de suporte ou atendimento técnico, além de possibilitar aos atendentes a gestão desses chamados.

## Tecnologias Utilizadas
- Java
- Quarkus
- MongoDB
- Swagger

## Funcionamento do HelpGR
A API de chamados foi desenvolvida para oferecer as seguintes funcionalidades:

- Cadastro de clientes/atendentes.
- Buscar clientes/atendentes.
- Atualizar clientes/atendentes
- Registro de chamados por parte dos clientes.
- Associação de um chamado a um cliente existente através do e-mail.
- Listagem de todos os chamados existentes.
- Busca de chamados por e-mail do cliente/atendente.
- Listagem de chamados por status.
- Listagem de chamados por data de abertura/data de encerramento.
- Atualização de informações dos chamados, como status e solução.
- Exclusão de chamados.
- Documentação completa das rotas da API através do Swagger.
- O projeto foi estruturado de forma a permitir o cadastro de clientes e atendentes, associando chamados aos clientes por meio de seu e-mail. Os chamados podem ter status e soluções atualizadas pelos atendentes, enquanto os clientes podem buscar seus chamados e criar novos

## Rotas 
A documentação completa das rotas da API pode ser encontrada na interface do Swagger. Para acessar a documentação, basta rodar o projeto localmente e acessar o seguinte endereço: [http://localhost:8080/swagger-ui/](http://localhost:8080/q/swagger-ui/).

## UserFlow

### Cliente
![UserFlow_Cliente drawio](https://github.com/Gabriel-Leandr0/HelpGR/assets/121676186/94671e7f-786d-433f-b79e-f186c0efb0fa)

### Atendente
![UserFlow_Atendente drawio](https://github.com/Gabriel-Leandr0/HelpGR/assets/121676186/a88073ac-c048-4e5c-9eb0-8fe73a3429d5)

## Pontos de melhoria
Este projeto é um passo inicial para o gerenciamento de chamados, mas ainda há espaço para melhorias, tais como:

- Aprimoramento da validação de dados e tratamento de erros.
- Implementação de autenticação e autorização para proteger as rotas sensíveis.
- Implementação de notificações por e-mail ou outros meios.
- Melhorias na interface do Swagger.
- Implementação de testes automatizados para garantir a robustez da aplicação.
- Este projeto serve como base para aprimoramentos futuros e para demonstrar o uso de tecnologias como Quarkus e MongoDB no contexto de gerenciamento de chamados.


# Quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/helpgr-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- MongoDB client ([guide](https://quarkus.io/guides/mongodb)): Connect to MongoDB in either imperative or reactive style
- MongoDB with Panache ([guide](https://quarkus.io/guides/mongodb-panache)): Simplify your persistence code for MongoDB via the active record or the repository pattern
- SmallRye Health ([guide](https://quarkus.io/guides/smallrye-health)): Monitor service health

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### SmallRye Health

Monitor your application's health using SmallRye Health

[Related guide section...](https://quarkus.io/guides/smallrye-health)
