CREATE SCHEMA restaurant;
ALTER DATABASE restaurant SET search_path TO restaurant;
DROP SCHEMA "public";

CREATE TABLE ingredients(
    id UUID PRIMARY KEY,
    name VARCHAR NOT NULL,
    quantity INTEGER NOT NULL,
    price NUMERIC (5,2) NOT NULL,
    created_date TIMESTAMPTZ NOT NULL,
    last_modified_date TIMESTAMPTZ NOT NULL
);