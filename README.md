# Desafio Palestras

## Introdução

Consiste em um teste onde foi desenvolvido uma Web API feita em ASP.Net C# consumindo um banco em SQL Server e um App mobile feito em Android Java com o intuito de divulgação e controle de inscrições de um evento de ciclo de palestras de uma empresa Ficticia X.


## Como rodar?

### Primeiro passo 
rodar o banco de dados no SQL Server com o script que esta no diretorio <a href="https://github.com/jose-leles/desafio-palestras/blob/master/banco/Database.sql"> /banco/Database.sql </a>



### Segundo passo
Mudar a connectionString no arquivo web.config da web api asp.net nesta branch o banco se chama **ProvaMobileComUsuario** pois eu fiz algumas alteracoes no mesmo
``` 
  <configuration>
    <connectionStrings>
      <add name="conexaoProvaMobile" connectionString="server=JOSELELES-PC\SQLEXPRESS;database=ProvaMobileComUsuario;Integrated Security=true;" providerName="System.Data.SqlClient"/>
    </connectionStrings>
    ...
```
  No meu caso eu estava rodando local com a autenticacao da maquina.
  
  Para autenticação utilizando usuario sql server pode se utilizar da seguinte forma **connectionString="Server=HOST;Database=BANCO;User Id=USER;
Password=SENHA;"**

  Este arquivo se encontra no seguinte diretorio: <a href="https://github.com/jose-leles/desafio-palestras/blob/master/web-api/fiapDesafio/WebApiDesafio/Web.config"> /web-api/asp.net/fiapDesafio/WebApiDesafio/web.config </a>



### Terceiro passo
Mudar a url a qual o app está apontando para consumir a web API.
Para mais detalhes sobre este modo de controlar as url de consumo veja readme do app mobile <a href="https://github.com/jose-leles/desafio-palestras/blob/master/mobile/README.md">readme.md</a>
``` 
# deve conter a barra no final
url_base=http://YOUR_SERVER_ADDRESS/
```
<a href="https://github.com/jose-leles/desafio-palestras/blob/master/mobile/fiapDesafio/app/src/main/assets/homologacao.properties"> /mobile/android/fiapDesafio/assets/homologacao.properties </a>
