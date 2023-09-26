CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	balance numeric(38, 2) NULL DEFAULT 0.00,
	"document" varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	user_type varchar(255) NOT NULL,
	CONSTRAINT uk_users_email UNIQUE (email),
	CONSTRAINT uk_users_document UNIQUE (document),
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_user_type_check CHECK (((user_type)::text = ANY ((ARRAY['COMMON'::character varying, 'MERCHANT'::character varying])::text[])))
);
