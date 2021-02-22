DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals(user_id, date_time, description, calories)
VALUES (100000, '2019-May-30 10:00', 'Завтрак', 500),
       (100000, '2019-May-30 13:00', 'Обед', 1000),
       (100000, '2019-May-30 20:00', 'Ужин', 500),
       (100000, '2019-May-31 10:00', 'Завтрак', 1000),
       (100000, '2019-May-31 13:00', 'Обед', 500),
       (100000, '2019-May-31 20:00', 'Ужин', 510),
       (100001, '2020-Jan-31 12:00', 'Обед', 400),
       (100001, '2020-Jan-31 19:00', 'Ужин', 1610);

