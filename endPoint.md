# END POINT

## authentification


### Login `/api/auth/login`
* **Method :** POST
* **Body :** (JSON) 
    - **email**
    - **password**
### Resultat :
**Statut 200 : OK**

    {"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqZWFuQGdtYWlsLmNvbSIsImlhdCI6MTcyMzY2NDU5MiwiZXhwIjoxNzIzNjY0NTk5fQ.sHs9l5F8l_ovZaCv2XglAbsPxbRdVVyrHINm7D8Iytk"}
**Statut 404 : Not found**

    {"message":"User not found"}
**Statut 401 : UNAUTHORIZED**

    {"message":"Wrong password"}


### *<span style="color:#f00;">Role authorized : Token dans le header (Authorization : Bearer [...token]</span>*

## USERS (Role authorized : ADMIN)

### Liste de tous les utilisateur `/secure/admin/users/getUser`
* **Method :** GET
* **Parametre : tsy voatery**
    - **page par defaut io 0(manomboka page 0)**
    - **size par defaut io 5 ra sendra tsisy size**

*Ra ohatra zao tsy asina parametre le izy de lasa page=0 de size =5*

## Resultat : 
**Status 200 : OK**

    {
        "content": [
            {
                "id": 2,
                "role": {
                    "id": 2,
                    "codeRole": "USER",
                    "description": "Role utilisateur"
                },
                "surname": "Jean",
                "firstname": "Joseph",
                "email": "jean@gmail.com",
                "contact": null,
                "photo": null,
                "password": "jean",
                "state": 1
            },...
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "unsorted": true,
                "sorted": false
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 5,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalPages": 1,
        "totalElements": 5,
        "first": true,
        "size": 5,
        "number": 0,
        "sort": {
            "empty": true,
            "unsorted": true,
            "sorted": false
        },
        "numberOfElements": 5,
        "empty": false
    }


### Ajouter un nouveau utilisateur : `/secure/admin/users/add`
* **Method :** POST
* **Body :**(JSON)
    - email 
    - surname
    - firstname
    - password
    - contact

## Resultat 
**Status 200 : OK**

    {
        "id": 7,
        "role": {
            "id": 2,
            "codeRole": "USER",
            "description": "Role utilisateur"
        },
        "surname": "fanevaaaa",
        "firstname": "mslmsd",
        "email": "mamama@gmail.com",
        "contact": "034110923",
        "photo": null,
        "password": "mamama",
        "state": 1
    }

### Effacer un utilisateur : `/secure/admin/users/delete/{id}`
* **Method :** DELETE

## Resultat 
**Status 200 : OK**

    {
        "success": 1,
        "message": "Delete success"
    }


## Software


### Ajouter un software : `/secure/admin/software/add`
* **Method :** POST
* **header :**
    - Content-type : multipart/form-data *(ovaina anio le content-type de tsy maintsy formData alefa)*
* **Parametre :**
    - photo (file)
    - name

## Resultat 
**Status 200 : OK**
- software objet JSON


### Liste un software : `/secure/admin/software/getAll`
* **Method :** GET
* **Parametre : tsy voatery**
    - **page par defaut io 0(manomboka page 0)**
    - **size par defaut io 5 ra sendra tsisy size**

*Ra ohatra zao tsy asina parametre le izy de lasa page=0 de size =5*

## Resultat : 
**Status 200 : OK**

    {
        "content": [],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageSize": 5,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalPages": 0,
        "totalElements": 0,
        "size": 5,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 0,
        "empty": true
    }


### Effacer un software : `/secure/admin/software/delete/{id}`
* **Method :** DELETE

## Resultat 
**Status 200 : OK**

    {
        "success": 1,
        "message": "Delete success"
    }

### Modifier le nom software : `/secure/admin/software/update/{id}/name`
* **Method :** PUT
* **Parametre :**
    - name

## Resultat 
**Status 200 : OK**
* **Software objet JSON**

**Status 404 : Not found ra sendra le id tsy miexiste**

### Modifier la photo software : `/secure/admin/software/update/{id}/photo`
* **Method :** PUT
* **header :**
    - Content-type : multipart/form-data *(ovaina anio le content-type de tsy maintsy formData alefa)*
* **Parametre :**
    - photo

## Resultat 
**Status 200 : OK**
* **Software objet JSON**

**Status 404 : Not found ra sendra le id tsy miexiste**


## Module
### Liste un software : `/secure/admin/module/getAll/{id}`
* **Method :** GET
* **Parametre : tsy voatery**
    - **page par defaut io 0(manomboka page 0)**
    - **size par defaut io 5 ra sendra tsisy size**

*Ra ohatra zao tsy asina parametre le izy de lasa page=0 de size =5*

## Resultat : 
**Status 200 : OK**

    {
        "content": [],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageSize": 5,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalPages": 0,
        "totalElements": 0,
        "size": 5,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 0,
        "empty": true
    }



### Effacer un module : `/secure/admin/module/delete/{id}`
* **Method :** DELETE

## Resultat 
**Status 200 : OK**

    {
        "success": 1,
        "message": "Delete success"
    }
