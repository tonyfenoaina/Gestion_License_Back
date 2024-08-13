CREATE DATABASE licence;

\c licence;


create table roles(
    id serial primary key,
    codeRole VARCHAR(20),
    description VARCHAR(255)
);

insert into roles(codeRole,description) values ('ADMIN','Role administrateur'),('USER','Role utilisateur'); 

create table users(
    id serial primary key,
    role int REFERENCES roles(id),
    surname VARCHAR(255),
    firstname VARCHAR(255),
    email VARCHAR(255),
    contact VARCHAR(255),
    photo text,
    password TEXT
);

insert into users(role,surname,firstname,email,contact,photo,password) values (1,'Jean','Joseph','admin@gmail.com',null,null,'admin');



insert into users(role,surname,firstname,email,contact,photo,password) values   (2,'Jean','Joseph','jean@gmail.com',null,null,'jean'),
                                                                                (2,'Jeannette','Jeanna','jeannete@gmail.com',null,null,'jeannette'),
                                                                                (2,'Jeannette2','Jeanna','jeannete2@gmail.com',null,null,'jeannette'),
                                                                                (2,'Jeannette3','Jeanna','jeannete3@gmail.com',null,null,'jeannette'),
                                                                                (2,'Jeannette4','Jeanna','jeannete4@gmail.com',null,null,'jeannette'),
                                                                                (2,'Jeannette5','Jeanna','jeannete5@gmail.com',null,null,'jeannette');

create view v_users as 
    select 
        users.id,
        roles.id as idRoles,
        roles.codeRole,
        roles.description as descriptionRole,
        users.surname,
        users.firstname,
        users.email,
        users.contact,
        users.photo,
        users.password
    from 
        users
        left join roles on roles.id=users.role;




create table software(
    id serial primary key,
    name VARCHAR(200),
    photo text,
    creationDate date
);