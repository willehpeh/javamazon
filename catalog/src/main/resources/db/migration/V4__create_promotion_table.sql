CREATE TABLE promotion
(
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    discount_percentage INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    target_data JSONB
)