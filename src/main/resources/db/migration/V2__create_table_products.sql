CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    stock INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    category_id BIGINT REFERENCES categories(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    constraint positive_stock CHECK (stock >= 0),
    constraint positive_price CHECK (price >= 0),
    constraint unique_product_name UNIQUE (name)
);