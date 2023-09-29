# PicPay Simplificado

> Desafio de Backend

## Tecnologias

- Java
- Spring Framework
- Spring Boot
- PostgreSQL
- Docker
- Flyway (DB migrations)
- OpenAPI (Swagger UI)
- Junit 5 (Unit tests)
- RestAssured (Integration tests)

### Bibliotecas

- Spring WEB
- Spring Data JPA
- Spring Validation
- Spring Dev Tools
- Spring Test
- PostgreSQL Driver
- Lombok
- Flyway
- Springdoc (Swagger UI)
- RestAssured

## Como rodar

- requisito: ter java 21 instalado
- requisito: docker e docker compose instalado
- clonar este repositorio
- docker compose up -d
- aguardar container do postgresql subir
- ./mvnw package
- java -jar target/picpay-simplificado-0.0.1-SNAPSHOT.jar

## API URL

- http://localhost:8080

## OpenAPI Docs

- http://localhost:8080/swagger-ui/index.html
