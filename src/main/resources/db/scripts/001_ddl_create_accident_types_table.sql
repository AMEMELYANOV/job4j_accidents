CREATE TABLE IF NOT EXISTS accident_types (
   id SERIAL PRIMARY KEY,
   name VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE accident_types IS 'Типы инцидентов';
COMMENT ON COLUMN accident_types.id IS 'Идентификатор типа';
COMMENT ON COLUMN accident_types.name IS 'Наименование типа';