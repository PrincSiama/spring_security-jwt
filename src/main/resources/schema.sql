create sequence if not exists seq1 start with 1;
														-- USERS

create table if not exists users (
id int default nextval('seq1') primary key,
firstname varchar(100) not null,
lastname varchar(100) not null,
email varchar(100) not null unique,
birthday date,
password varchar(255) not null,
registration_date date
);

														-- USER_ROLES
create table if not exists user_roles (
user_id int not null references users(id),
role varchar(100) not null default 'USER',
primary key(user_id, role)
);