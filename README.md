# Star Wars Resistence Social Network API ![badge](https://img.shields.io/github/languages/top/willyoliv/star_wars_resistence_api)

## Tabela de conteúdos
<!--ts-->
   * [Sobre o projeto](#sobre-o-projeto)
   * [Requisitos do projeto](#requisitos-funcionais-do-projeto)
   * [Executando o projeto](#executando-o-projeto)
   * [EndPoints](#endpoints)
     * [Adicionar rebelde](#adicionar-rebelde)
     * [Atualizar localização do rebelde](#atualizar-localização-do-rebelde)
     * [Reportar um rebelde como traidor](#reportar-um-rebelde-como-traidor)
     * [Negociar itens](#negociar-itens)
     * [Relatórios](#relatórios)
     * [Adicionais](#adicionais)
<!--te-->


## Sobre o projeto
Construção de uma API REST como Projeto final do Módulo **Desenvolvimento web** do curso da Let 's Code **Santander Coders Web Full Stack**. O projeto tem como principal objetivo pôr em prática os conceitos adquiridos em aula. A API foi construída com Springboot utilizando as dependências como H2 para banco em memória, JPA para criar as tabelas e relacionamentos entre as entidades, Mapper Struct para fazer o processo de mapeamento das entidades. A API também possui alguns testes de unidade.

*"O império continua sua luta incessante de dominar a galáxia, tentando ao máximo expandir seu território e eliminar os rebeldes. Você, como um soldado da resistência, foi designado para desenvolver um sistema
para compartilhar recursos entre os rebeldes."*

## Requisitos Funcionais do Projeto
Desenvolver uma API REST, ao qual irá armazenar informação sobre os rebeldes, bem como os recursos que eles possuem. Abaixo é apresentada a listagem dos requisitos.

- [X] **Adicionar rebelde**<br>Um rebelde deve ter um nome, idade, gênero, localização (latitude, longitude e nome, na galáxia, da base ao qual faz parte). Um rebelde também possui um inventário que deverá ser passado na requisição com
  os recursos em sua posse.
 
- [X] **Atualizar localização do rebelde**<br>Um rebelde deve possuir a capacidade de reportar sua última localização, armazenando a nova latitude/longitude/nome (não é necessário rastrear as
localizações, apenas sobrescrever a última é o suficiente).

- [X] **Reportar o rebelde como um traidor**<br>Eventualmente algum rebelde irá trair a resistência e se aliar ao império. Quando isso acontecer, nós precisamos informar que o rebelde é um traidor.
Um traidor não pode negociar os recursos com os demais rebeldes, não pode manipular seu inventário, nem ser exibido em relatórios.<br>Um rebelde é marcado como traidor quando, ao menos, três outros rebeldes
reportarem a traição.<br>Uma vez marcado como traidor, os itens do inventário se tornam inacessíveis (eles não
podem ser negociados com os demais).

- [X] **Rebeldes não podem Adicionar/Remover itens do seu inventário**<br>Seus pertences devem ser declarados quando eles são registrados no sistema. Após
isso eles só poderão mudar seu inventário através de negociação com os outros
rebeldes.

- [X] **Negociar itens**<br>Os rebeldes poderão negociar itens entre eles. Para isso, eles devem respeitar a tabela de preços abaixo, onde o valor do item é
descrito em termo de pontos.<br>Ambos os lados deverão oferecer a mesma quantidade de pontos. Por exemplo, 1 arma e 1 água (1 x 4 + 1 x 2) valem 6 comidas (6 x 1) ou 2 munições (2 x 3).
<br>A negociação em si não será armazenada, mas os itens deverão ser transferidos de um rebelde a outro.
  
  | ITEM           |  PONTOS  |
  |----------------|:--------:|
  | 1 ARMA         |     4    |
  | 1 MUNIÇÃO      |     3    |
  | 1 ÁGUA         |     2    |
  | 1 COMIDA       |     1    |

- [X] **Relatórios**<br>A API deve oferecer os seguintes relatórios:
 1. Porcentagem de traidores.
 2. Porcentagem de rebeldes.
 3. Quantidade média de cada tipo de recurso por rebelde (Ex: 2 armas por rebelde).
 4. Pontos perdidos devido a traidores.

## Executando o projeto
Abaixo são apresentados os passos necessários para que o projeto possa ser executado.

### Pré-requisitos
O projeto foi criado utilizando o **`Gradle`** como gerenciador de dependências. Como pré-requisito principal é necessário que a versão do JAVA **`JDK 17`** esteja devidamente instalada em seu ambiente de desenvolvimento. Também será necessário utilizar alguma IDE para visualizar o código como por exemplo: Intellij (utilizada), Netbeans, Eclipse ou qualquer um editor de sua preferência. 

### Rodando o projeto
```bash

# Clone este repositório
$ git clone https://github.com/willyoliv/star_wars_resistence_api.git

# Acesse a pasta do projeto no terminal/cmd
$ cd star_wars_resistence_api

# Rode o projeto
$ ./gradlew bootRun

```
**OBS.** O projeto por padrão roda em `localhost:8080`, caso a mesma esteja sendo utilizada por outro serviço é necessário alterar o arquivo `application.yml` adicionando uma nova porta seguindo o exemplo abaixo.

```
server:
  ...
  port : 8081
```

## EndPoints
Os endpoints estão organizados seguindo a ordem dos requitidos funcionais do projeto. A documentação pode ser acessada via browser por meio da seguinte rota **`localhost:8080/starwars-resistence/swagger-ui/`**
### Adicionar Rebelde
Para adicionar o rebelde é necessário informar os campos name, age, genre, location(galaxyName, latitude, longitude) e inventory(items). 

**OBS.** Os campos possuem algumas validações, valores `null` e `""` não são aceitos para campos do tipo `String`. A lista de itens deve conter obrigatoriamente os 
quatro itens `WEAPON, WATER, BULLET e FOOD` com seus respectivos valores, não podendo serem **null** ou **NEGATIVOS**. Abaixo são apresentados o endpoint e o exemplo 
de body para realizar a adição de um novo rebelde.

Método **POST** localhost:8080/starwars-resistence/rebels/

```
{
   "name": "Nome do Rebelde",
   "age": 20,
   "genre": "FEMALE",
   "location": {
      "galaxyName": "Nome da galáxia",
      "latitude": 1223432423,
      "longitude": 1321243123
   },
   "inventory": {
      "items": [
         {
            "name": "WEAPON",
            "quantity": 2
         },
         {
            "name": "BULLET",
            "quantity": 35
         },
         {
            "name": "WATER",
            "quantity": 4
         },
         {
            "name": "FOOD",
            "quantity": 10
         }
      ]
   }
}
```
### Atualizar localização do rebelde
Para atualizar a localização de um rebelde é necessário informar os novos valores para galaxyName, longitude e latitude. Também deve ser passado na URL o **`id`** do rebelde.
 Abaixo são apresentados o endpoint e o exemplo de body para realizar a atualização da localização de um rebelde.

**OBS.** O `id` deve obrigatoriamente pertencer a um rebelde existente, caso contrário a resposta para a requisição é `BAD_REQUEST`.

Método **PUT** localhost:8080/starwars-resistence/rebels/**`id`**/update-location
```
{
   "galaxyName": "Novo galáxia",
   "latitude": 50234324,
   "longitude": 27212431
}
```

### Reportar um rebelde como traidor
Para reportar um rebelde basta informa o seu id(`accusedId`) e a razão(`reason`) do reporte. Além disto, o `id` do rebelde que está acusando deve ser passado 
na URL. 

**OBS.** Os `id` e `accusedId` devem obrigatoriamente pertencer a rebeldes existentes e distintos, caso contrário a resposta para a requisição é `BAD_REQUEST`.

Método **POST** localhost:8080/starwars-resistence/rebels/**`id`**/report-traitor
```
{
   "accusedId": 1,
   "reason": "Motivo do reporte."
}
```
### Negociar itens
Para negociar os itens é necessário passar o `fromRebel` e `toRebel` contendo os repectivos **ids** e a lista de itens para a troca.
 
**OBS.** Uma série de validações é aplicada para este método. Os dois rebeldes devem existir, a quantidade de itens de cada rebelde é avaliada não sendo possível 
trocar itens que não possui ou que excedem a quantidade salva. Também é bloqueado a troca de itens com **traidores**. Sobre o body para a requisição, todos os campos
são obrigatórios e com um detalhe para a lista de itens que deve conter pelo menos **um** item e no máximo **quatro**. Abaixo é apresentado o exemplo do body para a
requisição e a URL para negociar os itens.
 
Método **POST** localhost:8080/starwars-resistence/rebels/inventory/trade
```
{
   "fromRebel": {
      "rebelId": 1,
      "items": [
         {
            "name": "WEAPON",
            "quantity": 2
         },
         {
            "name": "WATER",
            "quantity": 1
         }
      ]
   },
   "toRebel": {
      "rebelId": 2,
      "items": [
         {
            "name": "BULLET",
            "quantity": 2
         }
      ]
   }
}
```
### Relatórios
Abaixo é apresentado o endpoint para obter o relatório e um exemplo de resposta para a requisição.

Método **GET** localhost:8080/starwars-resistence/admin/report
```
{
   "percentageOfTraitors": 0.2,
   "percentageOfRebels": 0.8,
   "averageOfItems": {
      "WATER": 9.5,
      "FOOD": 9.75,
      "BULLET": 28.75,
      "WEAPON": 4.25
   },
   "lostPoints": 21
}
```
### Adicionais
* **Listagem de rebeldes**
 
  Para obter a lista completa dos rebeldes basta utilizar o endpoint abaixo.

  Método **GET** localhost:8080/starwars-resistence/rebels
* **Obter rebelde pelo id**
 
  Para obter um rebelde específico basta utilizar o endpoint abaixo, onde o **`id`** de um rebelde cadastrado deve ser passado.

  Método **GET** localhost:8080/starwars-resistence/rebels/**`id`**

