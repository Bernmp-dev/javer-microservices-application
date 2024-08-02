# Javer Microservices Application

## Proposta do Projeto

Este projeto é uma aplicação baseada em microserviços para a gestão de clientes do banco Javer. A arquitetura de microserviços permite escalabilidade, manutenibilidade e distribuição independente de cada serviço.

## Ferramentas Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Cloud**
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
  O Proxy Service atua como um proxy que encaminha as solicitações para os serviços backend apropriados. Ele também pode fornecer funcionalidades como cache, compressão e segurança.
</details>

## Rodando a Aplicação com Docker Compose

<details>
  <summary>Clique para expandir</summary>

### Passo a Passo para Rodar a Aplicação

1. **Certifique-se de que o Docker e o Docker Compose estão instalados:**
    - Para verificar se o Docker está instalado, execute:
      ```sh
      docker --version
      ```
    - Para verificar se o Docker Compose está instalado, execute:
      ```sh
      docker-compose --version
      ```

2. **Navegue até o diretório do projeto:**
    - Use o terminal ou o prompt de comando para navegar até o diretório onde o arquivo `docker-compose.yaml` está localizado. Por exemplo:
      ```sh
      cd /caminho/para/seu/projeto
      ```

3. **Construa as imagens do Docker:**
    - Caso ainda não tenha construído as imagens Docker, execute o comando abaixo para construir todas as imagens definidas no `docker-compose.yaml`:
      ```sh
      docker-compose build
      ```

4. **Inicie os contêineres:**
    - Para iniciar todos os contêineres definidos no `docker-compose.yaml`, execute:
      ```sh
      docker-compose up -d
      ```
    - O parâmetro `-d` faz com que os contêineres sejam executados em segundo plano (modo "detached").

5. **Verifique os logs dos contêineres:**
    - Para verificar os logs de um contêiner específico, você pode usar o comando `docker-compose logs`. Por exemplo, para verificar os logs do `javer-persistence-service`, execute:
      ```sh
      docker-compose logs javer-persistence-service
      ```

6. **Parar e remover os contêineres:**
    - Para parar todos os contêineres em execução, use:
      ```sh
      docker-compose down
      ```

</details>

## Rodando a Aplicação através do Terminal

<details>
  <summary>Clique para expandir</summary>

### Passo a Passo

**Pré-requisitos:** Certifique-se de ter o MySQL na porta ```3306```, instalado localmente ou rodando através de um container Docker.

1. Clone o repositório:
   ```bash
   git clone <URL do repositório>
   cd javer-microservices-application
   ```
2. Navegue até o diretório do microserviço que deseja iniciar, por exemplo:
   ```bash
   cd javer-eureka-server
   ```
3. Compile e execute o microserviço com Maven:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
4. Repita os passos 2 e 3 para cada microserviço que deseja iniciar.
</details>


## Testando os Endpoints com Insomnia

<details>
  <summary>Clique para expandir</summary>

### Como Testar os Endpoints

Assim que você estiver com a aplicação rodando, você pode testar os endpoints através do Insomnia, Postman ou qualquer outra ferramenta de sua preferência.

- **Microserviço de Persistência (Persistence Service):**
   - Porta: `8000`
   - Acesse diretamente o microserviço de persistência utilizando a URL:
     ```sh
     http://localhost:8000
     ```

- **Microserviço Proxy (Proxy Service):**
   - Porta: `8100`
   - Acesse diretamente o microserviço proxy utilizando a URL:
     ```sh
     http://localhost:8100
     ```

- **Acesso via Gateway (Gateway Service):**
   - Porta: `9000`
   - Acesse os dois microserviços através do gateway utilizando os seguintes endpoints:
      - Para o microserviço de persistência:
        ```sh
        http://localhost:9000/persistence
        ```
      - Para o microserviço proxy:
        ```sh
        http://localhost:9000/proxy
        ```

### Exemplos de Requisições

- **Requisição ao Microserviço de Persistência:**
   - Endpoint: `http://localhost:8000/customer`
   - Método: `GET`, `POST`, etc.
   - Corpo da Requisição (se necessário):
     ```json
     {
       "exemplo": "valor"
     }
     ```

- **Requisição ao Microserviço Proxy:**
   - Endpoint: `http://localhost:8100/customer`
   - Método: `GET`, `POST`, etc.
   - Corpo da Requisição (se necessário):
     ```json
     {
       "exemplo": "valor"
     }
     ```

- **Requisição via Gateway ao Microserviço de Persistência:**
   - Endpoint: `http://localhost:9000/persistence/customer`
   - Método: `GET`, `POST`, etc.
   - Corpo da Requisição (se necessário):
     ```json
     {
       "exemplo": "valor"
     }
     ```

- **Requisição via Gateway ao Microserviço Proxy:**
   - Endpoint: `http://localhost:9000/proxy/customer`
   - Método: `GET`, `POST`, etc.
   - Corpo da Requisição (se necessário):
     ```json
     {
       "exemplo": "valor"
     }
     ```

</details>

## Acessando o Swagger UI

<details>
  <summary>Clique para expandir</summary>

### Como Acessar o Swagger UI

O Swagger UI é uma ferramenta que permite testar e visualizar os endpoints da API de forma interativa. Para acessar o Swagger UI do microserviço de persistência, siga as instruções abaixo:

- **Microserviço de Persistência (Persistence Service):**
   - Porta: `8000`
   - URL:
     ```sh
     http://localhost:8000/swagger-ui.html
     ```

- **Acesso via Gateway (Gateway Service):**
   - Porta: `9000`
   - URL:
     ```sh
     http://localhost:9000/persistence/swagger-ui.html
     ```

### Credenciais de Acesso

Ao acessar o Swagger UI, você precisará passar as seguintes credenciais de autenticação:

- **Usuário:** `admin`
- **Senha:** `admin`

</details>

<br>
<br>
<br>
