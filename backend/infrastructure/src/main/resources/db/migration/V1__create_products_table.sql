CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    image BYTEA,
    category VARCHAR(100) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    internal_reference VARCHAR(100) NOT NULL,
    shell_id INTEGER NOT NULL,
    inventory_status VARCHAR(20) NOT NULL,
    rating INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_products_code ON products(code);