drop database if exists proyectos;
create database proyectos;

use proyectos;

create table empleado (
	dni char(9) not null,
    nombre varchar(35) not null,
    primary key (dni)
);

create table proyecto (
	id integer auto_increment not null,
    nombre varchar(35) not null,
    fecha_inicio date not null,
    fecha_fin date,
    dni_jefe_proyecto char(9) not null,
    primary key (id),
    foreign key (dni_jefe_proyecto) references empleado(dni)
);

create table asig_proyecto (
	dni_emp char(9) not null,
    id_proyecto integer not null,
    fecha_inicio date not null,
    fecha_fin date,
    primary key (dni_emp, id_proyecto, fecha_inicio),
    foreign key (dni_emp) references empleado(dni),
    foreign key (id_proyecto) references proyecto(id)
);

create table datos_profesionales (
	dni char(9) not null,
    categoria enum('A', 'B', 'C', 'D') not null,
    sueldo_bruto decimal (8,2),
    primary key (dni),
    foreign key (dni) references empleado(dni)
);

insert into empleado(dni, nombre) 
values 	('11111111A', 'MARTIN'),
		('22222222B', 'JORGE'),
        ('33333333C', 'PEDRO');
        
insert into proyecto(nombre, fecha_inicio, fecha_fin, dni_jefe_proyecto)
values 	('PRIMER PROYECTO', '2022-04-13', null, '11111111A'),
		('SEGUNDO PROYECTO', '2020-06-28', '2021-11-02', '11111111A');
        
insert into asig_proyecto(dni_emp, id_proyecto, fecha_inicio, fecha_fin)
values	('11111111A', 1, '2022-04-13', null),
		('22222222B', 1, '2022-04-13', null),
        ('11111111A', 2, '2020-06-28', '2021-11-02'),
        ('33333333C', 2, '2020-06-28', '2021-11-02');
        
insert into datos_profesionales(dni, categoria, sueldo_bruto)
values 	('11111111A', 'A', 60000.50),
		('22222222B', 'C', 25350.10),
        ('33333333C', 'B', 36820.80);