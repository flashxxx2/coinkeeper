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

CREATE TABLE files (
                       id uuid NOT NULL,
                       name character varying COLLATE pg_catalog."default" NOT NULL,
                       web_path character varying COLLATE pg_catalog."default" NOT NULL,
                       payment_id bigint NOT NULL,
                       CONSTRAINT files_id_pk PRIMARY KEY (id)
--                        CONSTRAINT payment_statistic_id_fk FOREIGN KEY (payment_id)
--                            REFERENCES public.payment_statistic (id) MATCH SIMPLE
--                            ON UPDATE RESTRICT
--                            ON DELETE RESTRICT,
);

CREATE TABLE payment_statistic (
                                   id BIGSERIAL NOT NULL,
                                   created_dt timestamp without time zone NOT NULL,
                                   sum numeric(12,2) NOT NULL,
                                   category_id bigint NOT NULL,
                                   comment varchar,
                                   file_id uuid,
                                   user_id bigint NOT NULL,
                                   CONSTRAINT payments_category_id_fk FOREIGN KEY (category_id)
                                       REFERENCES public.payment_category (id) MATCH SIMPLE
                                       ON UPDATE RESTRICT
                                       ON DELETE RESTRICT,
                                   CONSTRAINT files_id_fk FOREIGN KEY (file_id)
                                      REFERENCES public.files (id) MATCH SIMPLE
                                      ON UPDATE RESTRICT
                                      ON DELETE RESTRICT,
                                   CONSTRAINT users_id_fk FOREIGN KEY (user_id)
                                     REFERENCES public.users (id) MATCH SIMPLE
                                     ON UPDATE RESTRICT
                                     ON DELETE RESTRICT
);

INSERT INTO payment_category (id, name) VALUES (1, 'Продукты'), (2, 'Транспорт'), (3, 'Развлечения'),
                                               (4, 'Романтика'), (5, 'Еда вне дома'), (6, 'Услуги');

INSERT INTO users (username, password) VALUES ('user', '$2y$12$GmXGz3uWFWBFDJpxj/wQQuTg45KKCMhF1YqOgGP0cDYpvxxCOER4S');

INSERT INTO files (id, name, web_path, payment_id) VALUES
('02a42d4c-9cea-4bec-bf88-170cfaa5ac80', 'Lich', 'C:\Users\amaximov\IdeaProjects\ultimate\coinkeeper\media\files\02a42d4c-9cea-4bec-bf88-170cfaa5ac80.jpg', 1),
('3d863cad-a54a-40d1-96bd-589c59a0ad92', 'Meat', 'C:\Users\amaximov\IdeaProjects\ultimate\coinkeeper\media\files\3d863cad-a54a-40d1-96bd-589c59a0ad92.jpg', 2),
('711eded6-f2e3-4cae-b211-87c4842e8e90', 'Milk', 'C:\Users\amaximov\IdeaProjects\ultimate\coinkeeper\media\files\711eded6-f2e3-4cae-b211-87c4842e8e90.jpg', 3);

INSERT INTO payment_statistic (created_dt, sum, category_id, comment, file_id, user_id) VALUES
('1999-12-03T10:15:30', 123, 1, 'Хлебушек из пятерочки', '02a42d4c-9cea-4bec-bf88-170cfaa5ac80', 1),
('2005-12-03T10:15:30', 145, 2, 'На автобус', '02a42d4c-9cea-4bec-bf88-170cfaa5ac80', 1),
('2000-12-03T10:15:30', 1456, 3, 'Тусовка с друзьями', '02a42d4c-9cea-4bec-bf88-170cfaa5ac80', 1),
('2020-12-03T10:15:30', 14, 6, 'Коммуналка', '02a42d4c-9cea-4bec-bf88-170cfaa5ac80', 1);




