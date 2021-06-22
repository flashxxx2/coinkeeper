CREATE TABLE IF NOT EXISTS payment_category (
                                  id BIGSERIAL NOT NULL,
                                  name character varying COLLATE pg_catalog."default" NOT NULL,
                                  CONSTRAINT payment_category_id_pk PRIMARY KEY (id));


CREATE TABLE IF NOT EXISTS payment_statistic (
                                   id BIGSERIAL NOT NULL,
                                   created_dt timestamp without time zone NOT NULL,
                                   sum numeric(12,2) NOT NULL,
                                   category_id bigint NOT NULL,
                                   comment varchar,
                                   user_name varchar,
                                   CONSTRAINT payment_id_pk PRIMARY KEY (id),
                                   CONSTRAINT payments_category_id_fk FOREIGN KEY (category_id)
                                   REFERENCES public.payment_category (id) MATCH SIMPLE
                                   ON UPDATE RESTRICT
                                   ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS files (
                       id BIGSERIAL NOT NULL,
                       file_name varchar,
                       url character varying COLLATE pg_catalog."default" NOT NULL,
                       payment_id bigint,
                       CONSTRAINT payment_id_fk FOREIGN KEY (payment_id)
                           REFERENCES public.payment_statistic(id) MATCH SIMPLE
                           ON UPDATE RESTRICT
                           ON DELETE RESTRICT,
                       CONSTRAINT files_id_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS analitics (
                       id BIGSERIAL NOT NULL,
                       user_name varchar,
                       balance bigint,
                       planned_consumption bigint,
                       fact_consumption bigint,
                       expensive_purchase bigint,
                       consumption_category varchar
);

INSERT INTO payment_category (id, name) VALUES (1, 'Продукты'), (2, 'Транспорт'), (3, 'Развлечения'),
                                               (4, 'Романтика'), (5, 'Еда вне дома'), (6, 'Услуги'),
                                               (7, 'Неопределенная категория');

INSERT INTO payment_statistic (created_dt, sum, category_id, comment, user_name) VALUES
('2021-01-03', 123, 1, 'Хлебушек из пятерочки', 'user'),
('2021-02-03', 145, 2, 'На автобус', 'user'),
('2021-03-03', 1456, 3, 'Тусовка с друзьями', 'user'),
('2021-05-03', 141, 6, 'Коммуналка', 'user'),
('2021-04-03', 23, 4, 'Чупа Чупс купил', 'user'),
('2021-06-03', 143, 5, 'Семки у бабули', 'user');

INSERT INTO analitics(user_name, balance, planned_consumption, fact_consumption, expensive_purchase, consumption_category) VALUES
('user', 10000, 9000, 2000, 700, 'Развлечения');

INSERT INTO files (file_name, url, payment_id) VALUES
('Хлеб', 'https://lh3.googleusercontent.com/proxy/0Z3qIcPGQ0LzZef2N3PJxd1129FzOIkR7fs21ZoWeNBAuC0TZTFlgqPxcJZODBi-ZtzWIa1ib6VR2GdbHNBk', 1),
('на автобус до работы', 'https://aif-s3.aif.ru/images/006/881/3e1ae0a675e159a82d2faac531f5511b.jpg', 2),
('Пятерка', 'https://check.ofd.ru/assets/top-check.1d7181b.png', 3),
('хз', 'https://allcashbacks.com/web/uploads/new_uploads/2-chek.jpg', 3),
('ЖКХ', 'https://чкаловский.екатеринбург.рф/media/news/news_55654_image_900x_.jpg', 4),
('Семки', 'https://st2.depositphotos.com/4203211/8303/i/600/depositphotos_83030796-stock-photo-woman-holds-fresh-sunflower-seeds.jpg', 6),
('Чупик', 'http://pngimg.com/uploads/chupa_chups/small/chupa_chups_PNG4.png', 5);




