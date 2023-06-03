CREATE SCHEMA product

CREATE TABLE product.product (
    id serial PRIMARY KEY,
    name VARCHAR ( 50 ),
    description VARCHAR ( 50 ),
    value float,
    amount integer
);