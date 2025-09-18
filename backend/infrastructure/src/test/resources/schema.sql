CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image BYTEA,
    category VARCHAR(255),
    price DECIMAL(10, 2),
    quantity INTEGER,
    internal_reference VARCHAR(255),
    shell_id INTEGER,
    inventory_status VARCHAR(50),
    rating INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);