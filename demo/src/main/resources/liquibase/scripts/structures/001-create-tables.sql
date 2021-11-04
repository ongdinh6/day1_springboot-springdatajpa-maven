drop table if exists sales;
create table sales (
    sale_id uuid primary key not null ,
    dollars money
);

drop table if exists product;
create table product (
    product_id uuid primary key not null ,
    item integer,
    clazz varchar(255),
    inventory varchar(255),
    sale_id uuid not null references sales(sale_id)
);

drop table if exists locations;
create table locations (
     location_id uuid primary key not null ,
     country varchar(255),
     city varchar(255),
     sale_id uuid not null references sales(sale_id)
);

drop table  if exists times;
create table times (
    time_id uuid primary key not null ,
    month integer,
    quarter integer,
    year integer,
    sale_id uuid not null references sales(sale_id)
)