CREATE TABLE IF NOT EXISTS accident_types (
   id SERIAL PRIMARY KEY,
   name VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE accident_types IS 'Типы инцидентов';
COMMENT ON COLUMN accident_types.id IS 'Идентификатор типа';
COMMENT ON COLUMN accident_types.name IS 'Наименование типа';

CREATE TABLE IF NOT EXISTS rules (
   id SERIAL PRIMARY KEY,
   name VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE rules IS 'Правило инцидентов';
COMMENT ON COLUMN rules.id IS 'Идентификатор правила';
COMMENT ON COLUMN rules.name IS 'Наименование правила';

CREATE TABLE authorities (
  id SERIAL PRIMARY KEY,
  authority VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE authorities IS 'Роли';
COMMENT ON COLUMN authorities.id IS 'Идентификатор роли';
COMMENT ON COLUMN authorities.authority IS 'Роль';

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

CREATE TABLE IF NOT EXISTS accidents (
   id SERIAL PRIMARY KEY,
   name VARCHAR NOT NULL,
   description VARCHAR NOT NULL,
   address VARCHAR NOT NULL,
   accident_type_id INT NOT NULL REFERENCES accident_types(id),
   status VARCHAR DEFAULT 'ACCEPTED',
   created TIMESTAMP NOT NULL,
   accident_date_time TIMESTAMP NOT NULL,
   user_id int NOT NULL REFERENCES users(id)
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

CREATE TABLE IF NOT EXISTS accidents_rules (
   accident_id INT NOT NULL REFERENCES accidents(id),
   rule_id INT NOT NULL REFERENCES rules(id),
   PRIMARY KEY (rule_id, accident_id)
);

COMMENT ON TABLE accidents_rules IS 'Таблица связей инцидентов и их правил';
COMMENT ON COLUMN accidents_rules.accident_id IS 'Ссылка на инцидент';
COMMENT ON COLUMN accidents_rules.rule_id IS 'Ссылка на правило';


INSERT INTO accident_types (name) VALUES ('Несколько автомобилей');
INSERT INTO accident_types (name) VALUES ('Автомобиль и человек');
INSERT INTO accident_types (name) VALUES ('Автомобиль и велосипед');

INSERT INTO rules (name) VALUES ('Правило. 1');
INSERT INTO rules (name) VALUES ('Правило. 2');
INSERT INTO rules (name) VALUES ('Правило. 3');

INSERT INTO authorities (authority) VALUES ('ROLE_USER');
INSERT INTO authorities (authority) VALUES ('ROLE_ADMIN');
INSERT INTO authorities (authority) VALUES ('ROLE_INSPECTOR');

INSERT INTO users (username, enabled, password, authority_id)
VALUES ('root', true, '$2a$10$JMfHNfUXe5crLcU/vbEt9.oQ4vh0ZkClcXI8POPMq/ItMm4W4nwlu',
(SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));
INSERT INTO users (username, enabled, password, authority_id)
VALUES ('user', true, '$2a$10$JMfHNfUXe5crLcU/vbEt9.oQ4vh0ZkClcXI8POPMq/ItMm4W4nwlu',
(SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));
INSERT INTO users (username, enabled, password, authority_id)
VALUES ('insp', true, '$2a$10$JMfHNfUXe5crLcU/vbEt9.oQ4vh0ZkClcXI8POPMq/ItMm4W4nwlu',
(SELECT id FROM authorities WHERE authority = 'ROLE_INSPECTOR'));

INSERT INTO accidents (name, description, address, accident_type_id, created, accident_date_time, user_id)
    VALUES ('ДТП легкового автомобиля с велосипедистом', 'При проезде на красный сигнал светофора автомобиль марки Ford прибегнул к экстренному торможению и совершил наезд на велосипедиста, двигавшегося в поперечном направлении',
    'г. Москва, ул. Свободы, д. 68', 3, NOW(), timestamp '2023-01-10 00:51:14', 2);
INSERT INTO accidents (name, description, address, accident_type_id, created, accident_date_time, user_id)
    VALUES ('Наезд легкового автомобиля на пешехода', 'Автомобиль Lada Приора, при проезде пешеходного перехода, не предоставил преимущество пешеходу, совершив на него наезд',
    'г. Москва, пр. Фрунзе, д. 103', 2, NOW(), timestamp '2023-02-15 00:17:35', 2);
INSERT INTO accidents (name, description, address, accident_type_id, created, accident_date_time, user_id)
    VALUES ('ДТП с грузовым автомобилем и легковым', 'Автомобиль ГАЗ-3221 не соблюдал дистанцию и при остановке на перекрестке на запрещающий сигнал светофора, произвел столкновение с автомобилем Kia',
    'г. Москва, ул. Папанина, д. 14', 1, NOW(), timestamp '2023-03-01 00:10:11', 2);

INSERT INTO accidents_rules (accident_id, rule_id) VALUES (1, 1);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (1, 2);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (2, 2);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (2, 3);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (3, 1);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (3, 3);