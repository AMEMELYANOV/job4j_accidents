INSERT INTO users (username, enabled, password, authority_id)
VALUES ('root', true, '$2a$10$JMfHNfUXe5crLcU/vbEt9.oQ4vh0ZkClcXI8POPMq/ItMm4W4nwlu',
(SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));
INSERT INTO users (username, enabled, password, authority_id)
VALUES ('user', true, '$2a$10$JMfHNfUXe5crLcU/vbEt9.oQ4vh0ZkClcXI8POPMq/ItMm4W4nwlu',
(SELECT id FROM authorities WHERE authority = 'ROLE_ADMIN'));
INSERT INTO users (username, enabled, password, authority_id)
VALUES ('insp', true, '$2a$10$JMfHNfUXe5crLcU/vbEt9.oQ4vh0ZkClcXI8POPMq/ItMm4W4nwlu',
(SELECT id FROM authorities WHERE authority = 'ROLE_INSPECTOR'));