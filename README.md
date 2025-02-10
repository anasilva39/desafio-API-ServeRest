Desafio API ServeRest

Este repositório contém o código para automação de testes de API utilizando Rest Assured e JUnit 5 para testar os endpoints de usuários do sistema ServeRest. O projeto foi desenvolvido para validar as operações CRUD (Create, Read, Update, Delete) e para testar um cenário de erro (tentativa de criar um usuário com e-mail já cadastrado).

Requisitos

- Java 17
- Maven 3.8 ou superior
- IDE recomendada: IntelliJ IDEA
- Internet para acessar a API ServeRest

Como executar os testes

 1. Clonar o repositório


git clone https://github.com/anasilva39/desafio-API-ServeRest.git
cd desafio-API_ServeRest

2. Compilar e executar os testes
No terminal, dentro do diretório do projeto, execute o seguinte comando para compilar o projeto e rodar os testes:
mvn clean test
3. Verificar os resultados dos testes
Após a execução dos testes, você poderá verificar os resultados no console. Cada teste está configurado para mostrar informações detalhadas de cada requisição e resposta.

Estrutura do projeto
src/test/java/tests/UsuarioTests.java: Contém os testes automatizados para a API ServeRest.
pom.xml: Arquivo de configuração do Maven, contendo as dependências necessárias para o projeto (Rest Assured, JUnit 5 e Hamcrest).

Dependências
Este projeto utiliza as seguintes dependências:

Rest Assured: Para fazer as requisições HTTP e validar as respostas.
JUnit 5: Para gerenciar e executar os testes.
Hamcrest: Para as asserções e validações no corpo das respostas.

Cenários de teste
Criar um novo usuário: Valida a criação de um novo usuário com sucesso.
Listar todos os usuários: Verifica se a lista de usuários está sendo retornada corretamente.
Buscar usuário por ID: Testa a busca de um usuário existente por ID.
Atualizar usuário: Valida a atualização dos dados de um usuário.
Deletar usuário: Testa a exclusão de um usuário.
Tentar criar um usuário com e-mail já cadastrado: Verifica se o sistema retorna o erro adequado quando um e-mail já cadastrado é utilizado.
