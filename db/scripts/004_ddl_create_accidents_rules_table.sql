CREATE TABLE IF NOT EXISTS accidents_rules (
   accident_id INT NOT NULL REFERENCES accidents(id),
   rule_id INT NOT NULL REFERENCES rules(id),
   PRIMARY KEY (rule_id, accident_id)
);

COMMENT ON TABLE accidents_rules IS 'Таблица связей инцидентов и их правил';
COMMENT ON COLUMN accidents_rules.accident_id IS 'Ссылка на инцидент';
COMMENT ON COLUMN accidents_rules.rule_id IS 'Ссылка на правило';