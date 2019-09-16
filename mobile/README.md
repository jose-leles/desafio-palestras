# desafio-palestras

# Atention
-----
You need to make sure that your connection string by web.config is really aponting to your database

# Desenvolvimento

Padroes de projeto no codigo: delegate e dao

Arquitetura utilizada: MVC 

Navegação utlizando FragmentManager, utilizando o conceito de Stack do FragmentManager fica mais fácil de realizar a navegação do app sem transitar entre activitys

Consumo da WEB API utilizando Retrofit, criando a propria Fabric para trabalhar com o json do response http e utlizando delegate para tranferir a responsabilidade de converter o json para quem for consumir, que são as classes no package de consumers 

Responsabilidade da troca de urls feita via properties lidas dinamicamente pelo app 

