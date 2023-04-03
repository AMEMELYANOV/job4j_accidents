CREATE TABLE IF NOT EXISTS accident_types (
   id SERIAL PRIMARY KEY,
   name varchar UNIQUE NOT NULL
);

COMMENT ON TABLE accident_types IS 'Типы инцидентов';
COMMENT ON COLUMN accident_types.id IS 'Идентификатор типа';
COMMENT ON COLUMN accident_types.name IS 'Наименование типа';

CREATE TABLE IF NOT EXISTS rules (
   id SERIAL PRIMARY KEY,
   name varchar UNIQUE NOT NULL
);

COMMENT ON TABLE rules IS 'Правила инцидентов';
COMMENT ON COLUMN rules.id IS 'Идентификатор правила';
COMMENT ON COLUMN rules.name IS 'Наименование правила';

CREATE TABLE IF NOT EXISTS accidents (
   id SERIAL PRIMARY KEY,
   name varchar NOT NULL,
   description varchar NOT NULL,
   address varchar NOT NULL,
   accident_type_id INT NOT NULL,
   FOREIGN KEY (accident_type_id) REFERENCES accident_types(id)
);

COMMENT ON TABLE accidents IS 'Инциденты';
COMMENT ON COLUMN accidents.id IS 'Идентификатор инцидентов';
COMMENT ON COLUMN accidents.name IS 'Наименование инцидента';
COMMENT ON COLUMN accidents.description IS 'Описание инцидента';
COMMENT ON COLUMN accidents.address IS 'Адрес инцидента';
COMMENT ON COLUMN accidents.accident_type_id IS 'Ссылка на тип инцидента';

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

INSERT INTO accidents (name, description, address, accident_type_id)
    VALUES ('ДТП лекгкового автомобиля с велосипедистом', 'При проезде на красный сигнал светофора автомобиль
    марки Ford прибегнул к экстренному торможению и совершил наезд на велосипедиста, двигавшегося в поперечном
    направлении', 'г. Москва, ул. Свободы, д. 68', 3);
INSERT INTO accidents (name, description, address, accident_type_id)
    VALUES ('Наезд легкового автомобиля на пешехода', 'Автомобиль Lada Приора, при проезде пешеходного перехода,
     не предоставил преимущество пешеходу, совершив на него наезд', 'г. Москва, пр. Фрунзе, д. 103', 2);
INSERT INTO accidents (name, description, address, accident_type_id)
    VALUES ('ДТП с грузовым автомобилем и легковым', 'Автомобиль ГАЗ-3221 не соблюдал дистанцию и при остановке на
    на перекрестке на запрещающий сигнал светофора, произвел столкновение с автомобилем Kia',
        'г. Москва, ул. Папанина, д. 14', 1);

INSERT INTO accidents_rules (accident_id, rule_id) VALUES (1, 1);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (1, 2);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (2, 2);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (2, 3);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (3, 1);
INSERT INTO accidents_rules (accident_id, rule_id) VALUES (3, 3);