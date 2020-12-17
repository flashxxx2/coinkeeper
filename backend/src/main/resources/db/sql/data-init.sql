CREATE TABLE payment_statuses (
     id BIGSERIAL NOT NULL,
     name character varying COLLATE pg_catalog."default" NOT NULL,
     CONSTRAINT payment_statuses_id_pk PRIMARY KEY (id)
);

CREATE TABLE payment_statistic (
    id BIGSERIAL NOT NULL,
        created_dt timestamp without time zone NOT NULL,
        sum numeric(12,2) NOT NULL,
        commission numeric(12,2),
        status_id bigint NOT NULL,
        CONSTRAINT payments_id_pk PRIMARY KEY (id),
        CONSTRAINT payments_status_id_fk FOREIGN KEY (status_id)
            REFERENCES public.payment_statuses (id) MATCH SIMPLE
            ON UPDATE RESTRICT
            ON DELETE RESTRICT
);

CREATE TABLE users (
    id BIGSERIAL NOT NULL,
       username varchar NOT NULL,
       password varchar NOT NULL
);

INSERT INTO payment_statuses (id, name) VALUES (1, 'IN_PROCESS'), (2, 'SUCCESS'), (3, 'CANCEL');

INSERT INTO users (username, password) VALUES ('user', '$2y$12$GmXGz3uWFWBFDJpxj/wQQuTg45KKCMhF1YqOgGP0cDYpvxxCOER4S');

INSERT INTO payment_statistic (created_dt, sum, commission, status_id) VALUES
        ('1999-12-03T10:15:30', 123, 5, 1),
        ('2005-12-03T10:15:30', 145, 12, 2),
        ('2000-12-03T10:15:30', 1456, 1, 2),
        ('2020-12-03T10:15:30', 145645, 52, 1);




