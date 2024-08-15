# END POINT

## authentification


### Login `/api/auth/login`
* **End point :** 
* **Method :** POST
* **Parameter :** (JSON) 
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


### Liste de tous les utilisateur (ADMIN & USER) `/secure/admin/users/getAll`
### Resultat : 
**Status 200 : OK**

    [
        {
            "id": 1,
            "role": {
                "id": 2,
                "codeRole": "ADMIN",
                "description": "Role admin"
            },
            "surname": "Jean",
            "firstname": "Joseph",
            "email": "jean@gmail.com",
            "contact": null,
            "photo": null,
            "password": "jean"
        },
        ...
    ]

### Liste de tous les utilisateur (USER) `/secure/admin/users/getUser`
## Resultat : 
**Status 200 : OK**

    [
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
            "password": "jean"
        },
        ...
    ]

