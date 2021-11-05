
-- product
-- insert into product(product_id, item, clazz, inventory) values (uuid_generate_v4(), 10, 'clazz1', 'product-inventory-1');
-- insert into product(product_id, item, clazz, inventory) values (uuid_generate_v4(), 10, 'clazz2', 'product-inventory-2');
-- insert into product(product_id, item, clazz, inventory) values (uuid_generate_v4(), 10, 'clazz3', 'product-inventory-3');
-- insert into product(product_id, item, clazz, inventory) values (uuid_generate_v4(), 10, 'clazz4', 'product-inventory-4');

-- times
-- insert into times(time_id, month, quarter, year) values (uuid_generate_v4(), 10, 3, 2021);
-- insert into times(time_id, month, quarter, year) values (uuid_generate_v4(), 15, 5, 2021);
-- insert into times(time_id, month, quarter, year) values (uuid_generate_v4(), 21, 7, 2021);
-- insert into times(time_id, month, quarter, year) values (uuid_generate_v4(), 27, 9, 2021);

-- locations
-- insert into locations(location_id, city, country) values (uuid_generate_v4(), 'city-1', 'country-1');
-- insert into locations(location_id, city, country) values (uuid_generate_v4(), 'city-2', 'country-2');
-- insert into locations(location_id, city, country) values (uuid_generate_v4(), 'city-3', 'country-3');
-- insert into locations(location_id, city, country) values (uuid_generate_v4(), 'city-4', 'country-4');

-- sales
insert into sales(sale_id, productjpa_product_id, timejpa_time_id, locationjpa_location_id, dollars) values (uuid_generate_v4(), '39065509-58c2-49b5-9fea-c4fdf44fe607', '1040e4b2-e96b-43eb-b99e-30b59d6a8cb9', '11be73a5-c5c2-4fbb-8a74-67a906d8f2b9', 10.05);
insert into sales(sale_id, productjpa_product_id, timejpa_time_id, locationjpa_location_id, dollars) values (uuid_generate_v4(), '3ea3122c-b27b-4a50-a244-aa202d84bab3', '81628ae1-ec86-4d4f-a89b-3f995cbebbe9', '50e0f20f-6612-4468-8df6-01c0416d3a47', 11.05);
insert into sales(sale_id, productjpa_product_id, timejpa_time_id, locationjpa_location_id, dollars) values (uuid_generate_v4(), '8885c898-4d5f-434d-9bc6-1bfbfeb0c209', '9a21fc05-e25f-45c9-ab9c-e03c27f628bc', '5bded3a8-aacb-43e0-b4c5-e49de6532f9b', 12.05);
insert into sales(sale_id, productjpa_product_id, timejpa_time_id, locationjpa_location_id, dollars) values (uuid_generate_v4(), 'c523147f-10b0-4eb7-b038-29442f83a47d', 'a0c93d61-9d93-4db9-be6d-5422b5e6ebd6', '92dea889-eb5a-47f3-91cb-28d73292bb6f', 13.05);