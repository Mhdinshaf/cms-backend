CREATE TABLE country (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) NOT NULL
);

CREATE TABLE city (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES country(id)
);

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    nic VARCHAR(20) NOT NULL UNIQUE
);


CREATE TABLE customer_mobile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    mobile_number VARCHAR(15) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);


CREATE TABLE customer_address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city_id INT,
    country_id INT,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (city_id) REFERENCES city(id),
    FOREIGN KEY (country_id) REFERENCES country(id)
);


CREATE TABLE customer_family (
    customer_id BIGINT NOT NULL,
    family_member_id BIGINT NOT NULL,
    PRIMARY KEY (customer_id, family_member_id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (family_member_id) REFERENCES customer(id)
);