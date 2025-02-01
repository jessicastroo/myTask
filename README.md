  *Sistema de Gerenciamento de Tarefas*
O Sistema de Gerenciamento de Tarefas é uma aplicação construída com Java 17, Spring Boot e PostgreSQL, com o objetivo de fornecer uma solução simples e eficiente para o gerenciamento de tarefas. A aplicação permite aos usuários criar, editar, visualizar, excluir e filtrar tarefas, além de gerenciar informações dos usuários responsáveis por essas tarefas.

_____________________________________________________
  *Como Rodar o Projeto Localmente*
  Siga as instruções abaixo para rodar o projeto em sua máquina local.

  *Pré-requisitos*
-Java 17: Verifique se o JDK 17 está instalado em sua máquina. Você pode verificar com o seguinte comando:

    java -version

-Caso não tenha o JDK 17, faça o download no site oficial.

-Maven: Certifique-se de que o Maven está instalado. Você pode verificar com o comando:

    mvn -version

-Caso não tenha, siga as instruções no site oficial do Maven.

-PostgreSQL: O projeto usa PostgreSQL como banco de dados. Certifique-se de que o PostgreSQL está instalado e funcionando. Caso não tenha, siga as instruções no site oficial do PostgreSQL.

*Passos para rodar o projeto localmente*
Clone o repositório
Clone este repositório para a sua máquina local usando o Git:


    git clone https://github.com/jessicastroo/myTask.git
    cd myTask

*Configure o banco de dados PostgreSQL
Crie um banco de dados no PostgreSQL com o nome de sua preferência.*

-No arquivo src/main/resources/application.properties, configure as credenciais de acesso ao banco de dados. Exemplo:

    spring.application.name=sistema-gerenciamento-tarefas
    spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    server.port=8081

*Compile o projeto
Com o Maven instalado, execute o seguinte comando para compilar o projeto:*

    mvn clean install

-Execute o projeto

-Após a compilação, execute o aplicativo Spring Boot com o seguinte comando:

    mvn spring-boot:run

-O servidor Spring Boot será iniciado na porta 8081 por padrão.

*Acesse o sistema
Após iniciar o servidor, acesse a documentação da API e as rotas da aplicação nos links abaixo:*

-Documentação interativa (Swagger UI):
    http://localhost:8081/swagger-ui.html

-Esquema da API em JSON:
    http://localhost:8081/v3/api-docs

*Testando a aplicação
A aplicação já vem com uma suíte de testes unitários utilizando JUnit. Para rodá-los, basta executar o comando:*

    mvn test

_________________________________________________________
*Endpoints da API
A aplicação oferece endpoints para gerenciar tarefas e usuários. Todos os endpoints estão documentados no Swagger, mas abaixo estão alguns exemplos de uso:*


POST /api/tasks: Criar uma nova tarefa.

GET /api/tasks: Obter todas as tarefas.

GET /api/tasks/{id}: Obter uma tarefa específica pelo ID.

PUT /api/tasks/{id}: Atualizar uma tarefa existente.

DELETE /api/tasks/{id}: Deletar uma tarefa.

Esses e outros endpoints podem ser visualizados e testados diretamente na documentação gerada pelo Swagger.
