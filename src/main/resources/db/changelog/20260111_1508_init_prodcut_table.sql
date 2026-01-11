CREATE TABLE x6_product.product (
                              id SERIAL PRIMARY KEY ,
                              name VARCHAR NOT NULL,
                              description VARCHAR ,
                              price DECIMAL(12,2) NOT NULL ,
                              category VARCHAR
);