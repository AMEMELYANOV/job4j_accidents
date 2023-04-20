CREATE TABLE IF NOT EXISTS files (
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    path VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE files IS 'Файлы';
COMMENT ON COLUMN files.id IS 'Идентификатор файлов';
COMMENT ON COLUMN files.name IS 'Наименование файла';
COMMENT ON COLUMN files.path IS 'Путь хранения файла';