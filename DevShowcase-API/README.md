# DevShowcase API

Projeto da primeira etapa: modelagem de domínio, persistência e endpoints básicos.

## Stack
Java 17 + Spring Boot 3.5.5 + Spring Web + Spring Data JPA + Bean Validation + H2.

## Executar
```bash
mvn spring-boot:run
```
API: http://localhost:8080

## Endpoints
POST /api/profiles
GET /api/profiles/{id}
POST /api/technologies
GET /api/technologies
POST /api/projects
GET /api/projects

## Relacionamentos
Profile 1:N Project
Project N:N Technology
Project 1:N Feedback

## Exemplos
POST /api/profiles
```json
{"name":"Kailany Samilly Macedo Freitas","bio":"Estudante e desenvolvedora em formação.","githubUrl":"https://github.com/exemplo","linkedinUrl":"https://www.linkedin.com/in/exemplo"}
```

POST /api/technologies
```json
{"name":"Java"}
```

POST /api/projects
```json
{"title":"DevShowcase","description":"API para apresentação de desenvolvedores e projetos.","repositoryUrl":"https://github.com/exemplo/devshowcase-api","deployUrl":"https://example.com","profileId":1,"technologyIds":[1]}
```

## H2 Console
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:file:./data/devshowcase
User: sa
Password: vazio.
