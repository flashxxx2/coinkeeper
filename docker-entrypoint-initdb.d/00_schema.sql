CREATE TABLE payment_category (
                                  id BIGSERIAL NOT NULL,
                                  name character varying COLLATE pg_catalog."default" NOT NULL,
                                  CONSTRAINT payment_category_id_pk PRIMARY KEY (id));

CREATE TABLE users (
                       id BIGSERIAL NOT NULL,
                       username varchar NOT NULL,
                       password varchar NOT NULL,
                       CONSTRAINT users_id_pk PRIMARY KEY (id)
);

CREATE TABLE payment_statistic (
                                   id BIGSERIAL NOT NULL,
                                   created_dt timestamp without time zone NOT NULL,
                                   sum numeric(12,2) NOT NULL,
                                   category_id bigint NOT NULL,
                                   comment varchar,
                                   user_id bigint,
                                   CONSTRAINT payment_id_pk PRIMARY KEY (id),
                                   CONSTRAINT payments_category_id_fk FOREIGN KEY (category_id)
                                   REFERENCES public.payment_category (id) MATCH SIMPLE
                                   ON UPDATE RESTRICT
                                   ON DELETE RESTRICT,
                                   CONSTRAINT users_id_fk FOREIGN KEY (user_id)
                                     REFERENCES public.users (id) MATCH SIMPLE
                                     ON UPDATE RESTRICT
                                     ON DELETE RESTRICT
);

CREATE TABLE files (
                       id BIGSERIAL NOT NULL,
                       file_name character varying COLLATE pg_catalog."default" NOT NULL,
                       url character varying COLLATE pg_catalog."default" NOT NULL,
                       payment_id BIGSERIAL,
                       CONSTRAINT files_id_pk PRIMARY KEY (id)
);

INSERT INTO payment_category (id, name) VALUES (1, 'Продукты'), (2, 'Транспорт'), (3, 'Развлечения'),
                                               (4, 'Романтика'), (5, 'Еда вне дома'), (6, 'Услуги');

INSERT INTO users (username, password) VALUES ('user', '$2y$12$GmXGz3uWFWBFDJpxj/wQQuTg45KKCMhF1YqOgGP0cDYpvxxCOER4S');

INSERT INTO payment_statistic (created_dt, sum, category_id, comment, user_id) VALUES
('1999-12-03T10:15:30', 123, 1, 'Хлебушек из пятерочки', 1),
('2005-12-03T10:15:30', 145, 2, 'На автобус', 1),
('2000-12-03T10:15:30', 1456, 3, 'Тусовка с друзьями', 1),
('2020-12-03T10:15:30', 14, 6, 'Коммуналка', 1);

INSERT INTO files (id, file_name, url, payment_id) VALUES
(1, 'Lich', 'C:\Users\amaximov\AppData\Local\Temp\upload13826495109969179102/lich.png', 1),
(2, 'Lich', 'C:\Users\amaximov\AppData\Local\Temp\upload13826495109969179102/lich.png', 2),
(3, 'Lich', 'C:\Users\amaximov\AppData\Local\Temp\upload13826495109969179102/lich.png', 3);




