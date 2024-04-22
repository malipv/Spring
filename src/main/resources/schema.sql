create table if not exists users(
id integer primary key generated  by default as identity
, username varchar(250) not null
, fio varchar(250)
);

create table if not exists logins(
id integer primary key generated  by default as identity
, access_date timestamp
, user_id integer
, application varchar(100)
, foreign key(user_id)
references users(id)
on delete cascade
);