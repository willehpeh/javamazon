CREATE TABLE product
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    active      BOOLEAN        NOT NULL DEFAULT true,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW()
);