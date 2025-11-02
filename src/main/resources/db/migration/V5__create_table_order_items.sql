CREATE TABLE IF NOT EXISTS order_items (
    id BIGSERIAL PRIMARY KEY,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    product_id BIGINT REFERENCES products(id),
    order_id BIGINT REFERENCES orders(id),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);