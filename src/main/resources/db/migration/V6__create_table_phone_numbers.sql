CREATE TABLE IF NOT EXISTS phone_numbers (
    id BIGSERIAL PRIMARY KEY,
    country_code VARCHAR(10) NOT NULL,
    area_code VARCHAR(10) NOT NULL,
    number VARCHAR(20) NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    constraint unique_phone_number UNIQUE (country_code, area_code, number)
)