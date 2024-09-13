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
    password TEXT,
    state int
);

insert into users(role,surname,firstname,email,contact,photo,password,state) values (1,'Jean','Joseph','admin@gmail.com',null,null,'admin',1);



insert into users(role,surname,firstname,email,contact,photo,password,state) values   (2,'Jean','Joseph','jean@gmail.com',null,null,'jean',1),
                                                                                (2,'Jeannette','Jeanna','jeannete@gmail.com',null,null,'jeannette',1),
                                                                                (2,'Jeannette2','Jeanna','jeannete2@gmail.com',null,null,'jeannette',1),
                                                                                (2,'Jeannette3','Jeanna','jeannete3@gmail.com',null,null,'jeannette',1),
                                                                                (2,'Jeannette4','Jeanna','jeannete4@gmail.com',null,null,'jeannette',1);
   
create table customers(
    id serial primary key,
    users int REFERENCES users(id),
    surname VARCHAR(255),
    firstname VARCHAR(255),
    contact VARCHAR(255),
    address VARCHAR(255),
    state int
);


insert into customers(users,surname,firstname,contact,address,state) values   (2,'Customer1','L1',null,null,1),
                                                                        (2,'Customer2','L1',null,null,1),
                                                                        (2,'Customer3','L1',null,null,1),
                                                                        (2,'Customer4','L1',null,null,1),
                                                                        (2,'Customer5','L1',null,null,1),
                                                                        (2,'Customer6','L1',null,null,1),
                                                                        (2,'Customer7','L1',null,null,1),
                                                                        (2,'Customer8','L1',null,null,1),
                                                                        (3,'Customer1','L2',null,null,1),
                                                                        (3,'Customer2','L2',null,null,1),
                                                                        (3,'Customer3','L2',null,null,1),
                                                                        (3,'Customer4','L2',null,null,1),
                                                                        (3,'Customer5','L2',null,null,1),
                                                                        (3,'Customer6','L2',null,null,1),
                                                                        (3,'Customer7','L2',null,null,1),
                                                                        (3,'Customer8','L2',null,null,1);


create table software(
    id serial primary key,
    name VARCHAR(255), 
    dateCreation date,
    photo text,
    state int
);


create table module(
    id serial primary key,
    name VARCHAR(255),
    photo text,
    software int REFERENCES software(id),
    state int
);


create table licence(
    id serial primary key,
    users int REFERENCES users(id),
    software int REFERENCES software(id),
    startDate date,
    endDate date,
    numberActivation int 
);

create table licene_module(
    id serial primary key,
    licence int REFERENCES licence(id),
    module int REFERENCES module(id)
);

create table licence_identity(
    id serial primary key,
    licence int REFERENCES licence(id),
    id_pc VARCHAR(255),
    id_licence VARCHAR(255),
    mode_activation int,
    state int
);