CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         product_code VARCHAR(255) NOT NULL,
                         product_name VARCHAR(255) NOT NULL,
                         product_description VARCHAR(1000) NOT NULL,
                         product_price DOUBLE NOT NULL,
                         product_quantity INT NOT NULL,
                         inventory_status VARCHAR(255) NOT NULL,
                         product_category VARCHAR(255) NOT NULL,
                         product_image VARCHAR(255),
                         product_rating INT,
                         internal_reference VARCHAR(255) NOT NULL UNIQUE,
                         shell_id BIGINT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         CONSTRAINT UK_product_code UNIQUE (product_code)
);

