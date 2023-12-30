
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

## **Example of Errors**:

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



---
[Back to top](#thespoonapi)

>Generated at 2023-12-30 23:20:02 by [docgen](https://github.com/thedevsaddam/docgen)
