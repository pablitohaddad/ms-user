# API de Users
 
Bem-vindo à API de users, uma solução robusta desenvolvida em Spring Boot para gerenciar informações de usuários. Esta API oferece endpoints para realizar operações como adição, recuperação e atualização de usuários

## Colaborador
-------------------------------------
| E-mail              | Usuário GitHub |
|---------------------|----------------|
| pablo.haddad.pb@compasso.com.br   |pablitohaddad   |


 
## Requisitos
 
- Java 17 
- Banco de dados MySQL
- Docker
- RabbitMQ
- MongoDB
- Spring Boot
 
## Configuração
 
1. Clone o repositório:
 
```bash
git clone https://github.com/pablitohaddad/ms-user.git
```
 
2. Configure o banco de dados no arquivo `application.properties`.
 
3. Execute a aplicação:
 
```bash
mvn spring-boot:run
```
 
A aplicação estará disponível em http://localhost:8080/docs-msuser.html
 
## Endpoints da API de Usuario
 
### `GET /products/{id}`
 
Recupera um usuario específico por ID.
 
**Exemplo de resposta:**
```json
{
      "id": 1,
      "firstName": "Pablo",
       "email": "pablo@email.com"
    },
```
 
### `POST /users`
 
Adiciona um novo user.
 
**Corpo da solicitação:**
```json
{
  "firstName": "Pablo",
  "lastName": "Haddad",
  "cpf": "000.000-00",
  "birthdate": "2004-12-09",
  "email": "pablo@email.com",
  "cep": "78211-286",
  "password": "12345678",
  "active": true
}
```

### `PUT /users/{id}`

Atualizar informações de um user específico por ID.

**Corpo da solicitação:**
```json
{
    "firstName": "Joao",
    "lastName": "Augusto",
    "birthdate": "2004-05-18",
    "email": "joaoaugusto@email.com",
    "cep": "78211-286"
}
```

### `PUT /users/{id}/{oldPassword}`

Atualizar a senha de um user específico por ID.

**Corpo da solicitação:**
```json
{
    "password": "12345678"
}
```

### Dificuldades.


Não obtive uma cobertura ideal de testes, tive dificuldades ao cria-los. Perdi muito tempo neles. Consegui fazer as conexões, criar algumas validações de exceções, mas me perdi totalmente nessa parte de testes. Isso comprometeu muito a minha entrega. Obtive erros com o Swagger, na hora de implementar no localhost. Fora esses empecilhos, acredito que a entrega básica eu consegui fazer. 

### Desculpas.

Estou ciente que não entreguei o necessário, nem entreguei aquilo que era esperado. Também sei que erros como esse podem custar o meu emprego no futuro. Mas também, creio que essas dificuldades possam me trazer aprendizado, então, em uma próxima oportunidade, não irei cometer os mesmos erros. Peço perdão aos meus instrutores pelos erros citados e por futuros erros encontrados em meu projeto.




