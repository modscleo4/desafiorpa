CREATE TABLE IF NOT EXISTS products (
    id          INTEGER   PRIMARY KEY AUTOINCREMENT,
    marketplace TEXT      NOT NULL,
    name        TEXT      NOT NULL,
    url         TEXT      NOT NULL,
    price       REAL      NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP
);
