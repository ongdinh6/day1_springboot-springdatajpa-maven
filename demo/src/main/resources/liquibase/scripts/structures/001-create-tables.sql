drop table if exists accounts;
create table accounts (
    id serial PRIMARY KEY,
    username text,
    password text,
    phone_number text
);