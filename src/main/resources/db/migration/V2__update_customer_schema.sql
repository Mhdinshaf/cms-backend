DROP TABLE IF EXISTS customer_family;

ALTER TABLE customer ADD COLUMN parent_customer_id BIGINT;
ALTER TABLE customer ADD CONSTRAINT fk_parent_customer FOREIGN KEY (parent_customer_id) REFERENCES customer(id);

ALTER TABLE customer_address DROP FOREIGN KEY `3`;
ALTER TABLE customer_address DROP COLUMN country_id;