drop database rsvp;
create database rsvp;

use rsvp;

create table users(
	username varchar(32),
	password char(60) not null,
    email varchar(32),
	enabled boolean not null,
	admin boolean default false,
	created_on datetime not null,
	updated_on datetime,
	version bigint not null default 0,
	primary key(username)
);

insert into users (username, password, admin, enabled, created_on) values ("joe", "$2a$06$qeMki2S0.yErToAXJ5CQD.nGwqn3ydIiIKdD7UJ.i9k3Lv6f36mvu", true, true, now());
insert into users (username, password, admin, enabled, created_on) values ("paige", "$2a$06$qeMki2S0.yErToAXJ5CQD.nGwqn3ydIiIKdD7UJ.i9k3Lv6f36mvu", true, true, now());

select * from users;

create table groups(
	code varchar(32),
    group_name varchar(256),
    
	primary key(code)
);

create table guests(
	id serial,
	name varchar(256),
    group_code varchar(32),
    kid boolean,
    under_21 boolean,
    plus_one boolean not null default false,
    
	primary key(id),
	foreign key( group_code ) references groups( code )
);

create table group_response(
	id serial,
    group_code varchar(32),
    email varchar(256),
    dietary_restrictions varchar(1024),
    comments varchar(1024),
	created_on datetime not null,
	updated_on datetime,
    
	primary key(id),
	foreign key( group_code ) references groups( code )
);

create table guest_response(
	id serial,
    group_response bigint unsigned,
    guest bigint unsigned,
    attending boolean,
    plus_one_name varchar(256),
    
	primary key(id),
	foreign key( group_response ) references group_response( id ),
	foreign key( guest ) references guests( id )
);

select * from groups;
select * from guests;

select * from group_response;
select * from guest_response;

select group_response.created_on, group_response.group_code, guests.name, guest_response.attending from group_response 
INNER JOIN guest_response on guest_response.group_response = group_response.id
INNER JOIN guests on guest_response.guest = guests.id;

    
