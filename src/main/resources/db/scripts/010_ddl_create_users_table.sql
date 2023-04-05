CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL unique,
  password VARCHAR NOT NULL,
  enabled BOOLEAN DEFAULT TRUE,
  authority_id INT NOT NULL REFERENCES authorities(id)
);

COMMENT ON TABLE users IS 'Пользователи';
COMMENT ON COLUMN users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.username IS 'Имя пользователя';
COMMENT ON COLUMN users.password IS 'Пароль пользователя';
COMMENT ON COLUMN users.enabled IS 'Статус пользователя';
COMMENT ON COLUMN users.authority_id IS 'Ссылка на роль пользователя';