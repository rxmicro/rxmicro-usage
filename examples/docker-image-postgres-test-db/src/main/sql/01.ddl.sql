CREATE TYPE public.role AS ENUM (
    'CEO', 'Lead_Engineer', 'Systems_Architect'
);

ALTER TYPE public.role OWNER TO rxmicro;
------------------------------------------------------------------------------------------------------------------------

CREATE TABLE public.account (
	id int8 NOT NULL,
	email varchar(50) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	balance numeric(10,2) NOT NULL DEFAULT 0.00,
	role public.role NOT NULL,
	CONSTRAINT account_email_uniq UNIQUE (email),
	CONSTRAINT account_id_pk PRIMARY KEY (id)
);

ALTER TABLE public.account OWNER TO rxmicro;
GRANT ALL ON TABLE public.account TO rxmicro;

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE public.product (
	id int4 NOT NULL,
	"name" varchar(255) NOT NULL,
	price numeric(6,2) NOT NULL,
	count int4 NOT NULL DEFAULT 1,
	CONSTRAINT product_id_pk PRIMARY KEY (id)
);

ALTER TABLE public.product OWNER TO rxmicro;
GRANT ALL ON TABLE public.product TO rxmicro;

------------------------------------------------------------------------------------------------------------------------

CREATE TABLE public."order" (
	id bigserial NOT NULL,
	id_account int8 NOT NULL,
	id_product int8 NOT NULL,
	count int4 NOT NULL DEFAULT 1,
	created timestamp NOT NULL DEFAULT now(),
	CONSTRAINT order_id_pk PRIMARY KEY (id),
	CONSTRAINT order_fk FOREIGN KEY (id_account) REFERENCES account(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT order_fk_1 FOREIGN KEY (id_product) REFERENCES product(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

ALTER TABLE public."order" OWNER TO rxmicro;
GRANT ALL ON TABLE public."order" TO rxmicro;

------------------------------------------------------------------------------------------------------------------------

CREATE SEQUENCE public.account_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	CACHE 1
	NO CYCLE;

ALTER SEQUENCE public.account_seq OWNER TO rxmicro;
GRANT ALL ON SEQUENCE public.account_seq TO rxmicro;
