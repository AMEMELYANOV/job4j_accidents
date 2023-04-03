CREATE TABLE IF NOT EXISTS rules (
   id SERIAL PRIMARY KEY,
   name varchar UNIQUE NOT NULL
);

COMMENT ON TABLE rules IS 'Правило инцидентов';
COMMENT ON COLUMN rules.id IS 'Идентификатор правила';
COMMENT ON COLUMN rules.name IS 'Наименование правила';