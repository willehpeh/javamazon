ALTER TABLE product
    ADD COLUMN category_id UUID NOT NULL REFERENCES category (id)