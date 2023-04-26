CREATE TABLE IF NOT EXISTS accidents (
   id SERIAL PRIMARY KEY,
   name VARCHAR NOT NULL,
   description VARCHAR NOT NULL,
   address VARCHAR NOT NULL,
   accident_type_id INT NOT NULL REFERENCES accident_types(id),
   status VARCHAR,
   created TIMESTAMP NOT NULL,
   accident_date_time TIMESTAMP NOT NULL,
   user_id INT NOT NULL REFERENCES users(id),
   file_id INT REFERENCES files(id),
   inspector_username VARCHAR,
   inspector_сomment VARCHAR
);

COMMENT ON TABLE accidents IS 'Инциденты';
COMMENT ON COLUMN accidents.id IS 'Идентификатор инцидентов';
COMMENT ON COLUMN accidents.name IS 'Наименование инцидента';
COMMENT ON COLUMN accidents.description IS 'Описание инцидента';
COMMENT ON COLUMN accidents.address IS 'Адрес инцидента';
COMMENT ON COLUMN accidents.accident_type_id IS 'Ссылка на тип инцидента';
COMMENT ON COLUMN accidents.status IS 'Статус инцидента';
COMMENT ON COLUMN accidents.created IS 'Дата и время заведения или редактирования в приложении';
COMMENT ON COLUMN accidents.accident_date_time IS 'Дата и время инцидента';
COMMENT ON COLUMN accidents.user_id IS 'Пользователь инцидента';
COMMENT ON COLUMN accidents.file_id IS 'Файл фотографии инцидента';
COMMENT ON COLUMN accidents.inspector_username IS 'Имя инспектора занимающегося инцидентом';
COMMENT ON COLUMN accidents.inspector_сomment IS 'Комментарий инспектора по инциденту';