
# Oishi Pizza Server

> Oishi-pizza-server is the back-end side of web-application for ordering pizza.
> This application allow to user see the items in the menu and order them.
> How this actually works you can see on this address: https://polar-spire-76257.herokuapp.com/

## Build Setup
In order to launch it you need to have maven installed on your system.
After you have it please run those commands to launch the server side.

```
$ mvn clean install
$ mvn spring-boot:run
```

## Accessible endpoints
| Endpoint| Method   | Request  | Response  | 
|---|---|---|---|
| /menu/all | GET | empty  | List of items  | 
|  /menu/save | POST   | Item  | HttpEntity  | 
| /menu/delete | DELETE  | empty  | HttpEntity  | 
|  /menu/delete/{id} | DELETE  | id  | HttpEntity  | 
|  /resources/image/{imageName} | GET  | imageName  | image as InputStreamResource  | 
|  /order/history | GET  | empty  |  List of orders in a form of OrderClient | 
|  /order/save | POST  | OrderRequest  | HttpEntity  |
|  /order/order-items | GET  | empty  | List of relations between order and items  |
