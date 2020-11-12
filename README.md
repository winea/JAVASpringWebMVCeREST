# JAVASpringWebMVCeREST
Exemplo de modelo MVC e REST com Spring

gerar projeto spring initializr:  https://start.spring.io/
spring versao: 2.0.3
com gradle versao: 4.5.1
java 8

## I) Spring Boot

### Arquitetura MVC:

```
Model <=> Controller <=> View
  |                       |
  BD                     User
```

#### View -> camadas interface usuario, front-end

#### Controller -> camada que se comunica com a View e faz as requisicoes do usuario

#### Model -> camada negocios, trata os dados e devolve para controller


## II) REST
#### metodos GET, POST, PUT, DELETE e PATCH
### GET
GET status 200, 201, 404, etc...
GET /cities 
GET /cities/2
GET ex headers: "http//localhost:8080/v1/cities/1" -H ** "accept: application/json" ** -H
** "Authorization: Bearer 112rew244rew445wrewtht87" **

GET practices?from=net: /getAllCars,   /createNewCar,  /deleteAllRedCars,  /books/33/pages, /cities?region=north,
    /region?page=0&size=1&sort=name,desc,  /cities?page=0&size=1&sort=name,asc&state_id=5
    curl -X GET /cities?api_version=1.0.0

### PUT
PUT payload(corpo da requisicao) UserRequest, UserEntity, UserResponse


## CRUD
 Uma forma fácil de fazer isso é criar uma interface que extende a interface JpaRepository (do Spring Data)
A interface JpaRepository precisa de dois parâmetros do tipo Generics: o primeiro é a entidade JPA que
 representa a tabela e o segundo é o tipo da chave primária (o mesmo tipo do atributo id)

 
#### Obs: Não estava querendo fazer o import org.springframework.data.jpa.repository.JpaRepository;
Em build.gradle nas dependencias adicione para funcionar:

```
	compile('org.springframework.boot:spring-boot-starter-data-jpa')
	compile('javax.xml.bind:jaxb-api:2.3.0')
	compile("com.h2database:h2")
```

### CRUD Metodos JpaRepository:

#### Listando todos os contatos (GET /contacts)
O método findAll é direto ao ponto: utiliza o método findAll da interface JpaRepository que faz um select * from contacts.
```
@GetMapping
public List findAll(){
   return repository.findAll();
}
```

#### Obtendo um contato especídifo pelo ID (GET /contacts/{id})
Seguindo os conceitos RESTful, é necessário passar na URL o ID do registro. A anotação @PathVariable vincula 
o parâmetro passado pelo método com a variável do path. Note que o parâmetro long id tem o mesmo nome do path 
declarado em @GetMapping(path = {"/{id}"}).

```
@GetMapping(path = {"/{id}"})
public ResponseEntity findById(@PathVariable long id){
   return repository.findById(id)
           .map(record -> ResponseEntity.ok().body(record)) //200
           .orElse(ResponseEntity.notFound().build()); //404
}
```

#### Criando um novo contato (POST /contacts)

O método create também é bem direto ao ponto: apenas chama o método save da interface JpaRepository. 
Após criar o registro na tabela, retorna o contato com o atributo id populado e o registro é retornado no corpo 
de resposta.

```
@PostMapping
public Contact create(@RequestBody Contact contact){
   return repository.save(contact);
}
```
A anotação @RequestBody indica que o parâmetro contact será vinculado do corpo da requisição. 
Isso significa que o método espera o seguinte conteúdo do corpo da requisição (em formato JSON):

```
{
   "name": "Java",
   "email": "java@email.com",
   "phone": "(111) 111-1111"
}
```

#### Atualizando um contato (PUT /contacts)

Para atualizar um registro, é necessário informar seu ID no caminho da URL 
. Caso deseje usar um nome de variável diferente do que foi utilizado também pode utilizar o seguinte 
código @PathVariable("recordID") long id, desde que otherID também seja o nome em @PutMapping(value="/{otherID}").
Além do ID, também é necessário passar o objeto com os dados atualizados.

```
@PutMapping(value="/{id}")
public ResponseEntity update(@PathVariable("id") long id,
                                      @RequestBody Contact contact) {
   return repository.findById(id)
           .map(record -> {
               record.setName(contact.getName());
               record.setEmail(contact.getEmail());
               record.setPhone(contact.getPhone());
               Contact updated = repository.save(record);
               return ResponseEntity.ok().body(updated);
           }).orElse(ResponseEntity.notFound().build());
}
```

#### Removendo um contato pelo ID (DELETE /contacts/{id})

Para remover um contato pelo ID, utiliza-se o id que foi passado como parâmetro para procurar se 
o registro existe na base. Caso exista, utiliza-se o método deleteById da interface JpaRepository e retorna o 
status HTTP 200 para indicar sucesso. Caso o registro não exista, retorna um erro HTTP 404.

```
@DeleteMapping(path ={"/{id}"})
public ResponseEntity <?> delete(@PathVariable long id) {
   return repository.findById(id)
           .map(record -> {
               repository.deleteById(id);
               return ResponseEntity.ok().build();
           }).orElse(ResponseEntity.notFound().build());
}
```

