# 💳 Carteira Digital API

API REST de uma carteira digital desenvolvida com **Java e Spring Boot**, com foco em boas práticas de desenvolvimento backend, segurança, persistência de dados e regras de negócio financeiras.

O projeto permite o cadastro de usuários com criação automática de carteira, autenticação segura, proteção de senhas com BCrypt e, em sua evolução, contará com operações de depósito, transferências entre carteiras, movimentações financeiras e consulta de extrato.

> 🚧 Projeto em desenvolvimento.

---

## 📌 Objetivo do Projeto

O objetivo é desenvolver uma API de carteira digital capaz de simular operações financeiras essenciais, aplicando conceitos utilizados em aplicações backend reais.

O sistema será responsável por:

- cadastrar usuários;
- criar automaticamente uma carteira para cada usuário;
- autenticar usuários;
- proteger senhas utilizando BCrypt;
- gerar tokens JWT;
- consultar saldo da carteira;
- realizar depósitos;
- transferir valores entre usuários;
- registrar todas as movimentações financeiras;
- consultar extrato;
- controlar saldo e integridade das transações;
- tratar erros de negócio de forma padronizada;
- proteger endpoints utilizando Spring Security.

O projeto também tem como objetivo aplicar conceitos como:

- arquitetura em camadas;
- API REST;
- DTOs;
- ORM;
- JPA/Hibernate;
- transações;
- autenticação;
- autorização;
- tratamento global de exceções;
- validação de dados;
- segurança de senhas;
- JWT;
- regras de negócio financeiras.

---

# 🛠️ Tecnologias Utilizadas

## ☕ Backend

- **Java**
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA**
- **Spring Security**
- **Jakarta Validation**
- **Hibernate ORM**
- **Maven**
- **Lombok**

## 🔐 Segurança

- **BCrypt**
- **Spring Security**
- **JWT - JSON Web Token**
- **Auth0 Java JWT**

## 🗄️ Banco de Dados

- **PostgreSQL**
- **JPA / Hibernate**
- **JDBC**
- **HikariCP**

## 🧰 Ferramentas

- **IntelliJ IDEA**
- **DBeaver**
- **Postman**
- **Git**
- **GitHub**

---

# 🏗️ Arquitetura

O projeto utiliza uma arquitetura organizada em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
PostgreSQL
```

Cada camada possui uma responsabilidade específica.

### Controller

Responsável pela comunicação HTTP da aplicação.

Exemplo:

```text
POST /usuarios
POST /auth/login
```

Recebe os dados da requisição, chama a camada de serviço e devolve uma resposta HTTP.

---

### Service

Responsável pelas regras de negócio da aplicação.

Exemplos:

- verificar se um e-mail já está cadastrado;
- verificar se um CPF já está cadastrado;
- criptografar senhas;
- autenticar usuários;
- criar carteiras;
- validar saldo;
- realizar transferências.

---

### Repository

Responsável pelo acesso aos dados.

Os repositories utilizam **Spring Data JPA**, permitindo consultas utilizando interfaces que estendem:

```java
JpaRepository
```

Exemplo:

```java
Optional<UsuarioEntity> findByEmail(String email);
```

O Spring Data JPA interpreta o nome do método e gera automaticamente a consulta necessária.

---

### Entity

Representa as tabelas do banco de dados através do mapeamento objeto-relacional realizado pelo JPA/Hibernate.

Exemplo:

```java
@Entity
@Table(name = "tb_usuarios")
public class UsuarioEntity {
}
```

---

### DTO

Os DTOs são utilizados para separar os dados recebidos e retornados pela API das entidades persistidas no banco.

Exemplos:

```text
UsuarioCadastroDTO
UsuarioResponseDTO
LoginDTO
LoginResponseDTO
ErroResponseDTO
ValidacaoErroResponseDTO
```

Isso evita expor diretamente as entidades da aplicação.

---

# 🗄️ Estrutura do Banco de Dados

O sistema utiliza quatro tabelas principais:

```text
tb_usuarios
tb_carteiras
tb_transferencias
tb_movimentacoes
```

---

## 👤 Usuários

Tabela responsável pelos dados dos usuários.

Principais campos:

```text
cod_usuario
nome
email
cpf
senha
status
data_criacao
data_atualizacao
```

E-mail e CPF possuem restrição de unicidade.

A senha não é armazenada em texto puro.

---

## 💳 Carteiras

Cada usuário possui uma única carteira.

Principais campos:

```text
cod_carteira
cod_usuario
saldo
status
data_criacao
data_atualizacao
```

O saldo é armazenado utilizando:

```sql
NUMERIC(19,2)
```

e representado no Java através de:

```java
BigDecimal
```

evitando problemas de precisão relacionados ao uso de `double` ou `float` em valores financeiros.

---

## 💸 Transferências

Responsável por registrar transferências entre carteiras.

Principais campos:

```text
cod_transferencia
cod_carteira_origem
cod_carteira_destino
valor
descricao
status
chave_idempotencia
data_criacao
data_conclusao
```

---

## 📊 Movimentações

Responsável pelo histórico financeiro de cada carteira.

Tipos de movimentação:

```text
DEPOSITO
TRANSFERENCIA_ENTRADA
TRANSFERENCIA_SAIDA
```

Principais campos:

```text
cod_movimentacao
cod_carteira
cod_transferencia
tipo
valor
saldo_anterior
saldo_posterior
descricao
data_criacao
```

---

# 🔐 Segurança de Senhas

As senhas dos usuários são protegidas utilizando **BCrypt**.

Durante o cadastro:

```text
senha digitada
      ↓
BCrypt
      ↓
hash
      ↓
banco de dados
```

Exemplo:

```text
Senha@123
```

não é armazenada diretamente.

O banco recebe um hash semelhante a:

```text
$2a$10$...
```

Durante o login, a senha é verificada utilizando:

```java
passwordEncoder.matches(
    senhaDigitada,
    senhaHash
);
```

Assim, a senha original nunca precisa ser recuperada do banco.

---

# 🔑 Autenticação

O sistema possui autenticação utilizando e-mail e senha.

Fluxo:

```text
POST /auth/login
        ↓
LoginDTO
        ↓
busca usuário pelo e-mail
        ↓
verifica BCrypt
        ↓
credenciais válidas
        ↓
gera JWT
        ↓
retorna token
```

Caso o e-mail ou a senha estejam incorretos, a API retorna:

```http
401 Unauthorized
```

A resposta não informa especificamente se o erro ocorreu no e-mail ou na senha, evitando exposição desnecessária de informações sobre usuários cadastrados.

---

# 🎟️ JWT

Após a autenticação, a aplicação gera um **JSON Web Token**.

Exemplo de resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

O token contém informações como:

```text
issuer
subject
email
data de criação
data de expiração
```

Dados sensíveis como senha e CPF não são armazenados no JWT.

O token será enviado nas requisições protegidas através do header:

```http
Authorization: Bearer TOKEN
```

---

# ✅ Validação de Dados

A aplicação utiliza **Jakarta Bean Validation**.

Exemplo:

```java
@NotBlank
@Email
@CPF
@Size
```

As validações acontecem antes da execução da regra de negócio.

Exemplo:

```json
{
  "nome": "",
  "email": "email-invalido",
  "cpf": "123",
  "senha": "12"
}
```

pode gerar:

```http
400 Bad Request
```

com os campos inválidos informados na resposta.

---

# ⚠️ Tratamento Global de Exceções

A API possui tratamento global através de:

```java
@RestControllerAdvice
```

e:

```java
@ExceptionHandler
```

Isso permite centralizar o tratamento de erros da aplicação.

Exemplos tratados:

```text
EmailJaCadastradoException
CpfJaCadastradoException
CredenciaisInvalidasException
MethodArgumentNotValidException
```

Exemplo de resposta:

```json
{
  "timestamp": "2026-09-14T16:30:00",
  "status": 409,
  "erro": "Conflict",
  "mensagem": "E-mail já cadastrado",
  "path": "/usuarios"
}
```

---

# 🔄 Transações

Operações que precisam manter consistência utilizam:

```java
@Transactional
```

Por exemplo, durante o cadastro:

```text
cria usuário
     ↓
cria carteira
```

Caso a criação da carteira falhe, a operação pode sofrer rollback, evitando que o usuário fique criado sem uma carteira correspondente.

Esse conceito será ainda mais importante nas transferências:

```text
debita carteira origem
        ↓
credita carteira destino
        ↓
registra transferência
        ↓
registra movimentações
```

Todas essas etapas precisam fazer parte da mesma transação.

---

# 🌐 Endpoints

## Cadastro de usuário

```http
POST /usuarios
```

### Request

```json
{
  "nome": "Mariana Souza",
  "email": "mariana@email.com",
  "cpf": "52998224725",
  "senha": "Senha@123"
}
```

### Response

```json
{
  "codUsuario": 1,
  "nome": "Mariana Souza",
  "email": "mariana@email.com",
  "codCarteira": 1
}
```

Status:

```http
201 Created
```

---

## Login

```http
POST /auth/login
```

### Request

```json
{
  "email": "mariana@email.com",
  "senha": "Senha@123"
}
```

### Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Status:

```http
200 OK
```

---

# 🧠 Regras de Negócio

Algumas regras implementadas ou previstas para o sistema:

- um CPF não pode pertencer a mais de um usuário;
- um e-mail não pode pertencer a mais de um usuário;
- cada usuário possui apenas uma carteira;
- a carteira inicia com saldo zero;
- valores monetários utilizam `BigDecimal`;
- senhas nunca são armazenadas em texto puro;
- credenciais inválidas retornam `401 Unauthorized`;
- valores de transferência devem ser maiores que zero;
- uma carteira não poderá transferir para ela mesma;
- transferências somente poderão acontecer com saldo suficiente;
- carteiras bloqueadas não poderão realizar operações;
- todas as movimentações financeiras serão registradas;
- uma transferência deverá ocorrer dentro de uma única transação;
- operações duplicadas serão protegidas através de idempotência.

---

# 📂 Estrutura do Projeto

```text
src/main/java/br/com/api/carteira/digital
│
├── config
│   └── SecurityConfig
│
├── controller
│   ├── UsuarioController
│   └── AuthController
│
├── dto
│   ├── UsuarioCadastroDTO
│   ├── UsuarioResponseDTO
│   ├── LoginDTO
│   ├── LoginResponseDTO
│   ├── ErroResponseDTO
│   └── ValidacaoErroResponseDTO
│
├── model
│   ├── UsuarioEntity
│   ├── CarteiraEntity
│   ├── TransferenciaEntity
│   └── MovimentacaoEntity
│
├── repository
│   ├── UsuarioRepository
│   ├── CarteiraRepository
│   ├── TransferenciaRepository
│   └── MovimentacaoRepository
│
├── security
│   └── TokenService
│
├── service
│   ├── UsuarioService
│   └── AuthService
│
└── util
    ├── enums
    │   ├── StatusUsuario
    │   ├── StatusCarteira
    │   ├── StatusTransferencia
    │   └── TipoMovimentacao
    │
    └── exception
        ├── GlobalExceptionHandler
        ├── EmailJaCadastradoException
        ├── CpfJaCadastradoException
        └── CredenciaisInvalidasException
```

---

# ⚙️ Configuração

Exemplo de `application.properties`:

```properties
spring.application.name=carteira-digital

spring.datasource.url=jdbc:postgresql://localhost:5432/carteira_digital
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

api.security.token.secret=${JWT_SECRET}
```

Variáveis de ambiente necessárias:

```text
DB_PASSWORD
JWT_SECRET
```

Nunca envie os valores reais dessas variáveis para o repositório.

---

# ▶️ Executando o Projeto

Clone o repositório:

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta:

```bash
cd carteira-digital
```

Configure o PostgreSQL e crie:

```text
carteira_digital
```

Configure as variáveis de ambiente:

```bash
export DB_PASSWORD=sua_senha
export JWT_SECRET=sua_chave_secreta
```

Execute:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

# 🚧 Roadmap

## Usuário e autenticação

- [x] Cadastro de usuário
- [x] Criação automática de carteira
- [x] Validação de e-mail duplicado
- [x] Validação de CPF duplicado
- [x] Bean Validation
- [x] BCrypt
- [x] Login
- [x] Tratamento global de exceções
- [x] Estrutura para JWT
- [ ] Validação de JWT em requisições
- [ ] Filtro de autenticação JWT
- [ ] Integração completa com Spring Security

## Carteira

- [x] Estrutura da carteira
- [ ] Consultar carteira do usuário autenticado
- [ ] Consultar saldo
- [ ] Bloquear carteira
- [ ] Encerrar carteira

## Operações financeiras

- [ ] Depósito
- [ ] Transferência entre carteiras
- [ ] Validação de saldo
- [ ] Movimentações financeiras
- [ ] Extrato
- [ ] Paginação do extrato
- [ ] Idempotência
- [ ] Controle de concorrência

## Qualidade

- [ ] Testes unitários com JUnit
- [ ] Mockito
- [ ] Testes de integração
- [ ] Testcontainers
- [ ] Swagger / OpenAPI
- [ ] Flyway
- [ ] Docker
- [ ] Docker Compose

---

# 🔮 Próximas Funcionalidades

Entre as próximas evoluções previstas estão:

- autenticação completa com JWT;
- filtro de autenticação;
- consulta da carteira do usuário autenticado;
- depósitos;
- transferências;
- extrato financeiro;
- paginação;
- limite diário;
- idempotência;
- controle de concorrência;
- auditoria;
- documentação com Swagger;
- testes automatizados;
- migrations com Flyway;
- containerização com Docker.

---

# 📚 Conceitos Aplicados

Durante o desenvolvimento deste projeto são utilizados conceitos como:

```text
API REST
Spring MVC
Arquitetura em camadas
DTO Pattern
Repository Pattern
Dependency Injection
IoC
Bean Validation
JPA
Hibernate
ORM
Spring Data JPA
PostgreSQL
Transações
Rollback
BCrypt
Spring Security
JWT
Exception Handling
HTTP Status Codes
BigDecimal
Optional
Enums
Builder Pattern
```

---

# 🎯 Motivação

Este projeto foi desenvolvido com foco no aprofundamento de conhecimentos em **desenvolvimento backend com Java e Spring Boot**, aplicando conceitos próximos aos encontrados em sistemas reais.

Além da implementação das funcionalidades, o projeto busca reforçar conhecimentos em:

- modelagem de banco de dados;
- orientação a objetos;
- segurança;
- APIs REST;
- autenticação;
- persistência;
- tratamento de erros;
- transações financeiras;
- organização arquitetural;
- boas práticas de desenvolvimento.

---

# 👩‍💻 Autora

Desenvolvido por **Luana Porto**.

Projeto desenvolvido para estudo e portfólio na área de desenvolvimento de software com foco em **Java Backend / Full Stack**.
