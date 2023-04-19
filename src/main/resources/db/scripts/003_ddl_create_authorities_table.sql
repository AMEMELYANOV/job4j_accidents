CREATE TABLE authorities (
  id SERIAL PRIMARY KEY,
  authority VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE authorities IS 'Роли';
COMMENT ON COLUMN authorities.id IS 'Идентификатор роли';
COMMENT ON COLUMN authorities.authority IS 'Роль';