# Aplicação de Agência Bancária

Esta é uma aplicação simples de agência bancária escrita em Java, que permite criar contas, realizar operações de depósito, saque e transferência, além de listar as contas cadastradas.

## Funcionalidades

A aplicação possui as seguintes funcionalidades:

1. **Criação de Contas**: Permite criar novas contas informando nome, CPF e e-mail do titular.
2. **Depósito**: Permite realizar depósitos em contas existentes.
3. **Saque**: Permite fazer saques de contas existentes, desde que haja saldo disponível.
4. **Transferência**: Permite transferir valores entre contas, garantindo que o saldo seja atualizado corretamente em ambas as contas.
5. **Listagem de Contas**: Exibe informações sobre todas as contas cadastradas.

## Armazenamento de Dados

A aplicação possui duas opções de armazenamento de dados, bastando alterar os comentários do código.

1. **Em Memória**: Utiliza uma lista para armazenar as contas. Essa opção é adequada para testes e desenvolvimento, pois os dados são mantidos apenas durante a execução do programa.
2. **Banco de Dados Local**: Utiliza o PostgreSQL como banco de dados local para armazenar as contas. Essa opção é adequada para ambientes de produção, pois permite persistência dos dados mesmo após o término da execução da aplicação.

## Configuração do Banco de Dados

Certifique-se de ter o PostgreSQL instalado e configurado em sua máquina. Você precisará alterar no arquivo DataBaseService as variáveis:

- Caminho: url
- Usuário: user
- Senha: password

## Executando a Aplicação

Para executar a aplicação, siga as etapas abaixo:

1. Certifique-se de ter o JDK (Java Development Kit) instalado em sua máquina.
2. Abra o terminal ou prompt de comando.
3. Navegue até o diretório onde os arquivos da aplicação estão localizados.
4. Compile os arquivos Java e execute o Main.

Certifique-se de ter todas as dependências necessárias configuradas corretamente para o funcionamento adequado da aplicação.

**Observação**: Certifique-se de que o banco de dados PostgreSQL esteja em execução antes de executar a aplicação.

## Bibliotecas Utilizadas

A aplicação utiliza as seguintes bibliotecas:

- PostgreSQL JDBC Driver: Para realizar a conexão com o banco de dados PostgreSQL.

