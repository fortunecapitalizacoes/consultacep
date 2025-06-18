# 🧱 API de Consulta e Armazenamento de CEP

Esta é uma API REST desenvolvida em **Spring Boot**, que permite consultar um CEP em um servidor externo (mockado com WireMock) e salvar o endereço retornado no **MongoDB**. O projeto adota **arquitetura hexagonal**, **DDD (Domain-Driven Design)** e os princípios do **SOLID** para garantir uma base de código limpa, desacoplada e fácil de manter.


![Diagrama da Arquitetura](https://raw.githubusercontent.com/fortunecapitalizacoes/Pedidos/refs/heads/main/Pedido.jpg)

---

## 🚀 Funcionalidade

- Recebe um CEP via endpoint REST
- Consulta o endereço correspondente via um serviço externo (mockado)
- Salva os dados retornados no banco MongoDB
- Expõe a API documentada via Swagger

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- MongoDB
- Docker + Docker Compose
- WireMock (mock do serviço externo de CEP)
- springdoc-openapi (Swagger)
- Arquitetura Hexagonal (Ports and Adapters)
- DDD + SOLID

---

## 📦 Arquitetura

O projeto está estruturado segundo os princípios da **arquitetura hexagonal**, com foco em:

- **Isolamento do domínio:** toda lógica de negócio está centralizada em pacotes `domain` e `application`.
- **Adaptação de infraestrutura:** comunicação com MongoDB e serviços externos ocorre por meio de "adapters".
- **Interfaces (ports):** o domínio se comunica com o mundo externo apenas via contratos definidos (interfaces).
- **Inversão de dependências (DIP):** as dependências são injetadas no domínio via configuração.

---

## 🗂️ Estrutura de Pastas (resumo)

```
src/
├── application/         # Serviços de aplicação (casos de uso)
├── domain/              # Entidades e contratos (ports)
├── infrastructure/
│   ├── adapter/
│   │   ├── in/          # Controller REST
│   │   └── out/         # MongoDB e integração com serviço externo
├── config/              # Beans e configs do Spring
└── Application.java     # Ponto de entrada
```

---

## ⚙️ Como Rodar o Projeto

### Pré-requisitos

- Docker e Docker Compose instalados
- Java 17+

### Subir a infraestrutura (MongoDB + WireMock)

Na raiz do projeto, execute:

```bash
docker-compose up -d
```

Isso iniciará:

- **MongoDB** na porta `27017`
- **WireMock** na porta `8080`, com mocks de CEP já configurados

### Subir a aplicação Spring Boot

Você pode rodar via Maven ou sua IDE:

```bash
./mvnw spring-boot:run
```

A aplicação iniciará na porta `8090`.

---

## 🔍 Testar o Endpoint

### Buscar e salvar um CEP

```http
GET http://localhost:8090/api/cep/01001000
```

A aplicação consultará o WireMock e salvará o endereço retornado no MongoDB.

---

## 📘 Documentação Swagger

Acesse a documentação interativa em:

```
http://localhost:8090/swagger-ui.html
```

Ou:

```
http://localhost:8090/swagger-ui/index.html
```

---

## 📄 Exemplo de Mock WireMock

Os mocks estão localizados nas pastas `__files` e `mappings`. Exemplo de mock:

```json
{
  "cep": "01001-000",
  "logradouro": "Praça da Sé",
  "bairro": "Sé",
  "localidade": "São Paulo",
  "uf": "SP"
}
```

---

## 📌 Observações

- O serviço externo é simulado com **WireMock**.
- A persistência é feita com **MongoDB**, acessado por meio de uma porta de saída (interface).
- O código segue rigorosamente os princípios **SOLID**:
  - **S**: Separação clara de responsabilidades
  - **O**: Fácil de estender novos comportamentos
  - **L**: Substituições de interfaces funcionam sem impacto no domínio
  - **I**: Interfaces coesas, como `CepUseCase`
  - **D**: Inversão de dependência aplicada com injeção via Spring

---

## 🧑‍💻 Autor

Cleiton Silva  
[linkedin.com/in/hulk-silva](https://linkedin.com/in/hulk-silva)  
[github.com/infortic](https://github.com/infortic)