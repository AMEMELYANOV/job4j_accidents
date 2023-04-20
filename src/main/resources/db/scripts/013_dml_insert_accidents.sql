INSERT INTO accidents (name, description, address, accident_type_id, status, created, accident_date_time, user_id, file_id)
    VALUES ('ДТП легкового автомобиля с велосипедистом', 'При проезде на красный сигнал светофора автомобиль марки Ford прибегнул к экстренному торможению и совершил наезд на велосипедиста, двигавшегося в поперечном направлении',
    'г. Москва, ул. Свободы, д. 68', 3, 'NEW', NOW(), timestamp '2023-01-10 00:51:14', 2, 1);
INSERT INTO accidents (name, description, address, accident_type_id, status, created, accident_date_time, user_id, file_id)
    VALUES ('Наезд легкового автомобиля на пешехода', 'Автомобиль Lada Приора, при проезде пешеходного перехода, не предоставил преимущество пешеходу, совершив на него наезд',
    'г. Москва, пр. Фрунзе, д. 103', 2, 'NEW', NOW(), timestamp '2023-02-15 00:17:35', 2, 2);
INSERT INTO accidents (name, description, address, accident_type_id, status, created, accident_date_time, user_id, file_id)
    VALUES ('ДТП с грузовым автомобилем и легковым', 'Автомобиль ГАЗ-3221 не соблюдал дистанцию и при остановке на перекрестке на запрещающий сигнал светофора, произвел столкновение с автомобилем Kia',
    'г. Москва, ул. Папанина, д. 14', 1, 'NEW', NOW(), timestamp '2023-03-01 00:10:11', 2, 3);