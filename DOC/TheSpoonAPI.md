
# TheSpoonAPI

APIs to interact with the web service of TheSpoon, this API allows you to build your own customized TheSpoon clients. Its open to all the developers who wish to build applications based on our platform.

# Authorization

Certain endpoints are restricted to authorized users. To successfully send a request to these endpoints, ensure that you include a valid authentication bearer token in the request header's authorization field

# Error handling

When working with our API, errors may occur. These should be correctly handled on the client. Each error is identified by specific parameters:

## Error code

HTTP Status numerical code, provides information about the type of error: Server error, client input error or database error.

## Error payload

A JSON payload containing further information about the error occured. For example:

``` json
{
    "status": "BAD_REQUEST",
    "timestamp": "30-12-2023 07:21:08",
    "message": "Validation error",
    "debugMessage": null,
    "subErrors": [
        {
            "object": "signupRequest",
            "field": "Telefono",
            "rejectedValue": "",
            "message": "Numero di telefono non valido, inserire un numero italiano"
        }
    ]
}

 ```

## Error code

Some examples of error codes you may encounter using the API

### 400 Bad request

The request contains some errors. The data must be corrected before reattempting the query.

#### **Example of Errors**:

- Validation error
- Password mismatch error
    

### 403 Forbidden

An attempt to login with wrong credentials or request a functionality available only to authorized user without a proper/valid token.

### 404 Not found

An attempt to request a non-existent resource.

### 500 Internal Server Error

There was an internal server error while we were processing your request.

<!--- If we have only one group/collection, then no need for the "ungrouped" heading -->



## Endpoints

* [Auth](#auth)
    1. [Login](#1-login)
    1. [SignUp](#2-signup)
* [DashboardPersonale](#dashboardpersonale)
    1. [Get Ristoratore Details](#1-get-ristoratore-details)
    1. [Update Ristoratore Details](#2-update-ristoratore-details)
    1. [Update Password](#3-update-password)
* [Ristorante](#ristorante)
    1. [Insert Ristorante](#1-insert-ristorante)
    1. [Get Ristoranti By IdRistoratore](#2-get-ristoranti-by-idristoratore)
    1. [Update Ristorante](#3-update-ristorante)
    1. [Search Ristorante](#4-search-ristorante)
    1. [Get Ristorante By ID](#5-get-ristorante-by-id)
    1. [Insert Menu](#6-insert-menu)
    1. [Add Product To Menu](#7-add-product-to-menu)
    1. [Remove Product from Menu](#8-remove-product-from-menu)
    1. [Get Menu By ID](#9-get-menu-by-id)
    1. [Get Menus By ID Ristorante](#10-get-menus-by-id-ristorante)
    1. [Get Prodotti By ID Menu](#11-get-prodotti-by-id-menu)
    1. [Insert Tavolo](#12-insert-tavolo)
    1. [Get Tavoli By Ristorante ID](#13-get-tavoli-by-ristorante-id)
    1. [Get Tavolo](#14-get-tavolo)
* [Prodotto](#prodotto)
    1. [Insert Prodotto](#1-insert-prodotto)
    1. [Remove Prodotto](#2-remove-prodotto)
    1. [Get Prodotto By ID Prodotto](#3-get-prodotto-by-id-prodotto)

--------



## Auth

The following methods are related to the login subsystem and contain authentication-related endpoints.

These endpoints are crucial for managing user authentication and securing access to TheSpoon's services.



### 1. Login


Enpoint handling user authentication, it requires valid credentials and if successful return an authentication token.

### Params

| Name | Type | Description |
| --- | --- | --- |
| Email | String | The user email used for login into TheSpoon services |
| rePassword | String | User password used for login into TheSpoon services |

### Response

200 OK

``` json
    "token": "TokenString"

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Credentials not valid |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/auth/login
```



***Body:***

```js        
{"email":"kim@dami.it", "password":"KimDamiPass"}
```



### 2. SignUp


Endpoint handling user registration. To create a new user account, a valid signup request must be submitted. Upon successful registration, an authentication token will be provided.

### Params

| Name | Type | Description |
| --- | --- | --- |
| Email | String | The user email used for login into TheSpoon services |
| Password | String | User password used for login into TheSpoon services |
| rePassword | String | Confirm user password |
| Nome | String | Display name to be used for TheSpoon services |
| Cognome | String | Display family name to be used for TheSpoon services |
| Telefono | String | Italian mobile phone number to be used for TheSpoon for TheSpoon services |
| Data_Nascita | LocalDate | Birthdate, in the format: YYYY-MM-DD |

### Response

200 OK

``` json
"token": "TokenString"

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Credentials not valid |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/auth/signup
```



***Body:***

```js        
{
    "email": "kim@dami.it",
    "password": "KimDamiPass",
    "rePassword": "KimDamiPass",
    "nome": "Dami",
    "cognome": "Kim",
    "telefono": "00000000000000",
    "data_Nascita": "1997-05-29"
}
```



## DashboardPersonale

The following methods are related to the DashboardPersonale subsystem and contain endpoints for updating and retrieving personal data.

These endpoints are crucial for managing user personal data stored within TheSpoon's services.



### 1. Get Ristoratore Details


This endpoint allows users to retrieve their personal data stored within TheSpoon's services.

### Params

Authentication token inside the header's authentication field.

### Response

200 OK

``` json
{
    "data_Nascita": [
        1997,
        5,
        29
    ],
    "telefono": "00000000000000",
    "nome": "Kim",
    "email": "kim@dami.it",
    "cognome": "Dami"
}

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |


***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:8080/dashboard/ristoratoreDetails
```



### 2. Update Ristoratore Details


This endpoint allows users to update their personal data stored within TheSpoon's services.

### Params

All parameters are optional, and users can choose which ones to include for the fields they want to update.

| Name | Type | Description |
| --- | --- | --- |
| Email | String | New email address to be used for TheSpoon services |
| Nome | String | New display name to be used for TheSpoon services |
| Cognome | String | New display family-name to be used for TheSpoon services |
| Telefono | String | New italian mobile phone number to be used for TheSpoon for TheSpoon services |
| Data_Nascita | LocalDate | New birthdate, in the format: YYYY-MM-DD |

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/dashboard/updateRistoratoreDetails
```



***Body:***

```js        
{
    "nome": "Kim",
    "cognome": "Dami",
    "email": "kim@dami.it",
    "telefono": "00000000000000",
    "data_Nascita": "1997-05-29"
}
```



### 3. Update Password


This endpoint allows users to update their password.

### Params

| Name | Type | Description |
| --- | --- | --- |
| Password | String | New password to be used for TheSpoon services |
| rePassword | String | Confirm new passeword be used for TheSpoon services |

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/dashboard/updatePassword
```



***Body:***

```js        
{
    "password": "KimDami1998",
    "rePassword": "KimDami1998"
}
```



## Ristorante

The following methods are related to the Ristorante subsystem and contain endpoints for inserting, updating, deleting and retrieving restaurants data.

These endpoints are crucial for managing restautants data stored within TheSpoon's services.



### 1. Insert Ristorante


This endpoint allows users to insert their restaurant into TheSpoon's services.

### Params

| Name | Type | Description |
| --- | --- | --- |
| Nome | String | The restaurant's display name used for TheSpoon services |
| N_Civico | String | The restaurant's street number |
| Cap | Integer | The restaurant's postal code, in italian CAP (Codice di Avviamento Postale) |
| Via | String | The restaurant's address |
| Provincia | String | The restaurant's province |
| Telefono | String | Italian mobile phone number associated to the restaurant |

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/ristorante/insertRistorante
```



***Body:***

```js        
{
    "Nome": "Mr Q.",
    "N_Civico": "107",
    "Cap": 80146,
    "Via": "Via Emanuele Gianturco",
    "Provincia": "NA",
    "Telefono": "0000000000"
}
```



### 2. Get Ristoranti By IdRistoratore


This endpoint allows users to retrieve a list of restaurant associated with a Ristoratore ID

### Params

The Ristoratore ID as a path variable.

### Response

200 OK

``` json
[
    {
        "id": 11,
        "nome": "Mumu Bubble Tea",
        "telefono": "3516707777",
        "n_Civico": "21",
        "provincia": "NA",
        "via": "Via Cesare Battisti",
        "cap": 80134
    },
    {
        "id": 12,
        "nome": "Pizzeria Tutino",
        "telefono": "3516707777",
        "n_Civico": "79",
        "provincia": "NA",
        "via": "Via Cesare Carmignano",
        "cap": 80142
    },
    {
        "id": 14,
        "nome": "Pizzeria la Figlia del Presidente",
        "telefono": "00393331005060",
        "n_Civico": "24",
        "provincia": "NA",
        "via": "Via del grande archi",
        "cap": 80138
    },
    {
        "id": 16,
        "nome": "LinCal Bakery",
        "telefono": "00393331005060",
        "n_Civico": "2",
        "provincia": "CD",
        "via": "Xipu Street, Pidu District",
        "cap": 80049
    }
]

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:8080/ristorante/restaurantsList/{IdRistoratore}
```



### 3. Update Ristorante


This endpoint allows users to update the details of their own restaurant.

### Params

The Restaurant ID as a path variable.

All parameters are optional, and users can choose which ones to include for the fields they want to update.

| Name | Type | Description |
| --- | --- | --- |
| Nome | String | The restaurant's display name used for TheSpoon services |
| N_Civico | String | The restaurant's street number |
| Cap | Integer | The restaurant's postal code, in italian CAP (Codice di Avviamento Postale) |
| Via | String | The restaurant's address |
| Provincia | String | The restaurant's province |
| Telefono | String | Italian mobile phone number associated to the restaurant |

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |
| 403 | Forbidden | Your authentication token is not valid |
| 404 | Not Found | Can't find any restaurant with the given ID owned by the user that made the request |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/ristorante/updateRistorante/{id_ristorante}
```



***Body:***

```js        
{
    "n_Civico": "224",
    "cap": 80028,
    "via": "Ponte di Ferro",
    "provincia": "NA",
    "telefono": "00393331005060"
}
```



### 4. Search Ristorante


This endpoint allows users to update the details of their own restaurant.

### Params

The Restaurant name as a path variable.

All parameters are optional, and users can choose which ones to include for the fields they want to update. The JSON must be always provided, even if empty.

| Name | Type | Description |
| --- | --- | --- |
| N_Civico | String | The restaurant's street number |
| Cap | Integer | The restaurant's postal code, in italian CAP (Codice di Avviamento Postale) |
| Via | String | The restaurant's address |
| Provincia | String | The restaurant's province |

### Response

200 OK

``` json
[  
{  
"id": 12,  
"nome": "Pizzeria Tutino",  
"telefono": "3516707777",  
"n_Civico": "79",  
"provincia": "NA",  
"via": "Via Cesare Carmignano",  
"cap": 80142  
},  
{  
"id": 13,  
"nome": "Pizzeria la Figlia del Presidente",  
"telefono": "00393331005060",  
"n_Civico": "224",  
"provincia": "NA",  
"via": "Ponte di Ferro",  
"cap": 80028  
},  
{  
"id": 14,  
"nome": "Pizzeria la Figlia del Presidente",  
"telefono": "00393331005060",  
"n_Civico": "24",  
"provincia": "NA",  
"via": "Via del grande archi",  
"cap": 80138  
}  
]

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: GET
Type: RAW
URL: http://localhost:8080/ristorante/ricercaRistorante/"Pizzeria"
```



***Body:***

```js        
{
    "n_Civico": "2",
    "cap": 80049,
    "via": "Xipu Street, Pidu District",
    "provincia": "CD"
}
```



### 5. Get Ristorante By ID


This endpoint allows users to retrieve the details of a restaurant given his ID

### Params

The Restaurant ID as a path variable.

### Response

200 OK

``` json
{
    "id": 15,
    "nome": "LinCal Bakery",
    "telefono": "00393331005060",
    "n_Civico": "2",
    "provincia": "CD",
    "via": "Xipu Street, Pidu District",
    "cap": 80049
}

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:8080/ristorante/getRistorante/{IdRistorante}
```



### 6. Insert Menu


This endpoint allows users to insert their restaurant's menus into TheSpoon's services.

### Params

| Name | Type | Description |
| --- | --- | --- |
| Nome | String | The restaurant's menu display name used for TheSpoon services |
| Descrizione | String | A description of the restaurant's menu |
| IdRistorante | Integer | The restautant's ID |

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |
| 403 | Forbidden | Your authentication token is not valid |
| 404 | Not found | Can't find any restaurant with the given ID owned by the user that made the request |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/ristorante/insertMenu
```



***Body:***

```js        
{
    "nome": "All You Can Eat",
    "descrizione": "Mangia quanto vuoi da Mumu",
    "idRistorante": 11
}
```



### 7. Add Product To Menu


This endpoint allows users to add a product to the menus of their own restaurant.

### Params

The Menu ID, Product ID and Restaurant ID as a path variable.

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |
| 404 | Not Found | Can't find any restaurant with the given ID owned by the user that made the request The given menu is not associated with the restaurant The menu or the product doesn't exist |


***Endpoint:***

```bash
Method: POST
Type: 
URL: http://localhost:8080/ristorante/addProductToMenu/{id_menu}/{id_prodotto}/{id_ristorante}
```



### 8. Remove Product from Menu


This endpoint allows users to remove a product from the menus of their own restaurant.

### Params

The Menu ID, Product ID and Restaurant ID as a path variable.

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |
| 404 | Not Found | Can't find any restaurant with the given ID owned by the user that made the request The given menu is not associated with the restaurant The menu or the product doesn't exist |


***Endpoint:***

```bash
Method: DELETE
Type: 
URL: http://localhost:8080/ristorante/removeProductMenu/1/1/11
```



### 9. Get Menu By ID


This endpoint allows users to retrieve the details of a menu given his ID

### Params

The Menu ID as a path variable.

### Response

200 OK

``` json
{
    "id": 1,
    "nome": "All You Can Eat",
    "categoria": "Mangia quanto vuoi da Mumu"
}

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |
| 404 | Not Found | Can't find any menu with the given ID |


***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:8080/ristorante/getMenuByID/1
```



### 10. Get Menus By ID Ristorante


This endpoint allows users to retrieve the menus given the Restaurant's ID

### Params

The Restaurant's ID as a path variable.

### Response

200 OK

``` json
[
    {
        "nome": "All You Can Eat",
        "categoria": "Mangia quanto vuoi da Mumu",
        "id": 1
    },
    {
        "nome": "Menu alla carta",
        "categoria": "Pay as you eat",
        "id": 2
    }
]

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:8080/ristorante/getMenuByIDRistorante/{IdRistorante}
```



### 11. Get Prodotti By ID Menu


This endpoint allows users to retrieve the products associated within a menu given a menu ID

### Params

The Menu's ID as a path variable.

### Response

200 OK

``` json
[
    {
        "nome": "Bubble Tea Taro",
        "descrizione": "Bubble Tea gusto taro, con palline di tapioca.",
        "prezzo": 5.5,
        "id": 1
    },
    {
        "nome": "Pizza Fritta con cicoli",
        "descrizione": "Pizza fritta con pomodoro, mozzarella, ricotta, cicoli e doppio olio",
        "prezzo": 5.5,
        "id": 4
    }
]

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |
| 404 | Not Found | Can't find any menus with the given ID |


***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:8080/ristorante/getProdottiByIDMenu/{IdMenu}
```



### 12. Insert Tavolo


This endpoint allows users to add a table to their restaurant into TheSpoon's services.

### Params

| Name | Type | Description |
| --- | --- | --- |
| Numero Tavolo | String | The number of the table, used also as table ID |
| Stato | Byte | table status, 0 available, 1 occupied |
| Capacita | Integer | The table capacity |
| idRistorante | String | The restaurant's ID |

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |
| 404 | Not Found | Restaurant not found or not owned by the users that made the request |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/ristorante/insertTavolo
```



***Body:***

```js        
{
    "numeroTavolo": "2",
    "stato": "0",
    "capacita": 4,
    "idRistorante": 11
}
```



### 13. Get Tavoli By Ristorante ID


This endpoint allows users to retrieve all the restaurant's tables given his ID

### Params

The restaurant's ID as a path variable.

### Response

200 OK

``` json
[
    {
        "numeroTavolo": "1",
        "capacita": 4,
        "stato": 0
    },
    {
        "numeroTavolo": "2",
        "capacita": 4,
        "stato": 0
    }
]

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |
| 404 | Not Found | Can't find any restaurants with the given ID |


***Endpoint:***

```bash
Method: GET
Type: 
URL: 
```



### 14. Get Tavolo


This endpoint allows users to retrieve the details of a table given his ID and the restaurant's ID

### Params

The Menu ID, the restaurant's ID as a path variable.

### Response

200 OK

``` json
{
    "numeroTavolo": "1",
    "capacita": 4,
    "stato": 0
}

 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |
| 404 | Not Found | Can't find any table with the given IDs |


***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:8080/ristorante/getTavoloById/{numero_tavolo}/{id_ristorante}
```



## Prodotto

The following methods are related to the Prodotto subsystem and contain endpoints for inserting, updating, deleting and retrieving data products.

These endpoints are crucial for managing products data stored within TheSpoon's services.



### 1. Insert Prodotto


This endpoint allows users to add a product into TheSpoon's services.

### Params

| Name | Type | Description |
| --- | --- | --- |
| Nome | String | The product name |
| Descrizione | String | A description of the product |
| Prezzo | Float | The product's price |

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: POST
Type: RAW
URL: http://localhost:8080/prodotto/insertProdotto
```



***Body:***

```js        
{
    "nome": "Pizza Fritta con cicoli",
    "descrizione": "Pizza fritta con pomodoro, mozzarella, ricotta, cicoli e doppio olio",
    "prezzo": 5.50
}
```



### 2. Remove Prodotto


This endpoint allows users to remove a product from TheSpoon's services.

### Params

The product's ID as path variable.

Authentication token inside the header's authentication field.

### Response

200 OK

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 403 | Forbidden | Your authentication token is not valid |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |


***Endpoint:***

```bash
Method: DELETE
Type: 
URL: http://localhost:8080/prodotto/removeProdotto/4
```



### 3. Get Prodotto By ID Prodotto


This endpoint allows users to retrieve the details of a product given his ID.

### Params

The Product ID as a path variable.

### Response

200 OK

``` json
{
    "id": 5,
    "nome": "Bubble Tea The Verde",
    "prezzo": 5.5,
    "descrizione": "Bubble Tea, gusto the verde con palline di tapioca"
}
 ```

### Possible Errors

| Code | Type | Description |
| --- | --- | --- |
| 400 | Bad Request | The request body was not valid, some field are missing or malformed |
| 404 | Not Found | Can't find any product with the given ID |


***Endpoint:***

```bash
Method: GET
Type: 
URL: http://localhost:8080/prodotto/getProdotto/{id_prodotto}
```



---
[Back to top](#thespoonapi)

>Generated at 2024-01-14 23:48:51 by [docgen](https://github.com/thedevsaddam/docgen)
