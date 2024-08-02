# Javer Microservices Application

## Proposta do Projeto

Este projeto é uma aplicação baseada em microserviços para a gestão dos serviços do banco Javer. A arquitetura de microserviços permite escalabilidade, manutenibilidade e distribuição independente de cada serviço.

## Ferramentas Utilizadas

- **Java 17**
- **Spring Boot 3.3.1**
- **Spring Cloud 2023.0.3**
- **OpenFeign**
- **Eureka Server**
- **Load Balancer**
- **Spring Cloud Gateway**
- **Spring Data JPA**
- **Spring Security**
- **Flyway**
- **MySQL**
- **H2 Database**
- **Docker**
- **Docker Compose**
- **Maven**

## Microserviços Criados

<details>
  <summary><strong>Eureka Server</strong></summary>
    <br>
    O Eureka Server atua como um serviço de registro onde todos os outros microserviços (clientes Eureka) podem registrar-se e descobrir uns aos outros. Ele facilita a comunicação e o balanceamento de carga entre os microserviços.
</details>
    <br>

<details>
  <summary><strong>Gateway Service</strong></summary>
    <br>
  O Gateway Service é o ponto de entrada para todas as solicitações externas aos microserviços. Ele roteia as solicitações para os microserviços apropriados e pode aplicar políticas de segurança, controle de tráfego e outras regras.
</details>
    <br>

<details>
  <summary><strong>Persistence Service</strong></summary>
    <br>
  O Persistence Service é responsável por interagir com o banco de dados. Ele utiliza o Spring Data JPA para realizar operações de CRUD (Create, Read, Update, Delete). Além disso, ele usa Flyway para migrações de banco de dados.
</details>
    <br>

<details>
  <summary><strong>Proxy Service</strong></summary>
    <br>
  O Proxy Service atua como um proxy reverso que encaminha as solicitações para os serviços backend apropriados. Ele também pode fornecer funcionalidades como cache, compressão e segurança.
</details>