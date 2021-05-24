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
                                   CONSTRAINT payments_category_id_fk FOREIGN KEY (category_id)
                                       REFERENCES public.payment_category (id) MATCH SIMPLE
                                       ON UPDATE RESTRICT
                                       ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS users (
                       id BIGSERIAL NOT NULL,
                       username varchar NOT NULL,
                       password varchar NOT NULL
);



