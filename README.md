# Chat Multicast Em java
## Autor - wallace barbosa 

## Status

> Status do Projeto: Em desenvolvimento :warning:


## Requsitos

Os requisitos básicos são:

1 - O servidor deve gerenciar múltiplas salas de bate papo.

2 - O cliente deve ser capaz de solicitar a lista de salas.

3 - O cliente deve ser capaz de solicitar acesso à uma das salas de bate papo.

3 - O servidor deve manter uma lista dos membros da sala.

4 - O cliente deve ser capaz de enviar mensagens para a sala.

5 - O cliente deve ser capaz de sair da sala de bate papo.

## Instruções de utilização

Realizar o build do projeto para que a pasta bin/out seja criado
> cd  /PROJETO  - Pasta onde está localizada o código 

### Execução do server
> cd  /bin
##
> java MulticastServer

### Execução do client
> cd  /PROJETO/bin 
##
> java MulticastClient 

### Protocolos

* CRIAR SALA : create-room { nome }
* LISTAR SALAS : list-room 
* ENTRAR NA SALA : getin-room { nome da sala
* LISTAR MEMBROS DA SALA : list-members
* SAIR DA SALA : out-room
* ENVIAR MENSAGEM : sendm { texto da mensagem }
