CREATE SCHEMA restaurant;
ALTER DATABASE restaurant SET search_path TO restaurant;
DROP SCHEMA "public";

CREATE TABLE ingredients
(
    id                 UUID PRIMARY KEY,
    name               VARCHAR       NOT NULL,
    quantity           INTEGER       NOT NULL,
    price              NUMERIC(5, 2) NOT NULL,
    created_date       TIMESTAMPTZ   NOT NULL,
    last_modified_date TIMESTAMPTZ   NOT NULL
);

CREATE TABLE recipe
(
    id                 UUID PRIMARY KEY,
    name               VARCHAR       NOT NULL,
    total_price        NUMERIC(5, 2) NOT NULL  DEFAULT 0.00,
    created_date       TIMESTAMPTZ   NOT NULL,
    last_modified_date TIMESTAMPTZ   NOT NULL
);

CREATE TABLE ingredient_recipe
(
    fk_ingredient_id UUID REFERENCES ingredients(id),
    fk_recipe_id     UUID REFERENCES recipe(id),
    ingredient_quantity INTEGER NOT NULL
);