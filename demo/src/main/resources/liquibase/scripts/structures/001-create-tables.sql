drop table if exists productElasticsearch;
create table productElasticsearch
(
    product_id uuid primary key not null,
    item       integer,
    clazz    varchar(255),
    inventory  varchar(255)
);

drop table if exists locations;
create table locations
(
    location_id uuid primary key not null,
    country     varchar(255),
    city        varchar(255)
);

drop table if exists times;
create table times
(
    time_id uuid primary key not null,
    month   integer,
    quarter integer,
    year    integer
);

drop table if exists sales;
create table sales
(
    dollars money,
    product_id uuid,
    location_id uuid,
    time_id uuid
);
