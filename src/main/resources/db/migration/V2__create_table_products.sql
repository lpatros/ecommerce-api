CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    stock NUMERIC NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(512),
    category_id BIGINT REFERENCES categories(id),
    status BOOLEAN NOT NULL DEFAULT TRUE
);)