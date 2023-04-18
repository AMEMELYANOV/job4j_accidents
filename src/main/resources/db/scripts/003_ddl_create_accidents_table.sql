CREATE TABLE IF NOT EXISTS accidents (
   id SERIAL PRIMARY KEY,
   name VARCHAR NOT NULL,
   description VARCHAR NOT NULL,
   address VARCHAR NOT NULL,
   accident_type_id INT NOT NULL,
   status VARCHAR DEFAULT 'ACCEPTED',
   created TIMESTAMP NOT NULL,
   FOREIGN KEY (accident_type_id) REFERENCES accident_types(id)
);

COMMENT ON TABLE accidents IS 'Инциденты';
COMMENT ON COLUMN accidents.id IS 'Идентификатор инцидентов';
COMMENT ON COLUMN accidents.name IS 'Наименование инцидента';
COMMENT ON COLUMN accidents.description IS 'Описание инцидента';
COMMENT ON COLUMN accidents.address IS 'Адрес инцидента';
COMMENT ON COLUMN accidents.accident_type_id IS 'Ссылка на тип инцидента';
COMMENT ON COLUMN accidents.status IS 'Статус инцидента';
COMMENT ON COLUMN accidents.created IS 'Дата и время инцидента';