DROP TABLE IF EXISTS payment_category CASCADE;
DROP TABLE IF EXISTS payment_statistic CASCADE;
DROP TABLE IF EXISTS users CASCADE;


CREATE TABLE payment_category (
                                  id BIGSERIAL NOT NULL,
                                  name character varying COLLATE pg_catalog."default" NOT NULL,
                                  CONSTRAINT payment_category_id_pk PRIMARY KEY (id));

CREATE TABLE payment_statistic (
                                   id BIGSERIAL NOT NULL,
                                   created_dt timestamp with time zone default current_timestamp,
                                   sum numeric(12,2) NOT NULL,
                                   category_id bigint NOT NULL,
                                   comment varchar,
                                   CONSTRAINT payments_category_id_fk FOREIGN KEY (category_id)
                                       REFERENCES public.payment_category (id) MATCH SIMPLE
                                       ON UPDATE RESTRICT
                                       ON DELETE RESTRICT
);

CREATE TABLE users (
                       id BIGSERIAL NOT NULL,
                       username varchar NOT NULL,
                       password varchar NOT NULL
);


INSERT INTO payment_category (id, name) VALUES (1, 'Продукты'), (2, 'Транспорт'), (3, 'Развлечения'),
                                               (4, 'Романтика'), (5, 'Еда вне дома'), (6, 'Услуги');

INSERT INTO users (username, password) VALUES ('user', '$2y$12$GmXGz3uWFWBFDJpxj/wQQuTg45KKCMhF1YqOgGP0cDYpvxxCOER4S');

INSERT INTO payment_statistic (created_dt, sum, category_id, comment) VALUES
('1999-12-03T10:15:30', 123, 1, 'Хлебушек из пятерочки'),
('2005-12-03T10:15:30', 145, 2, 'На автобус'),
('2000-12-03T10:15:30', 1456, 3, 'Тусовка с друзьями'),
('2020-12-03T10:15:30', 14, 6, 'Коммуналка');


