CREATE TABLE transactions (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	amount numeric(38, 2) NOT NULL,
	"timestamp" timestamp(6) NOT NULL,
	receiver_id uuid NOT NULL,
	sender_id uuid NOT NULL,
	CONSTRAINT transactions_pkey PRIMARY KEY (id),
	CONSTRAINT fk_transactions_users_sender FOREIGN KEY (sender_id) REFERENCES public.users(id),
	CONSTRAINT fk_transactions_users_receiver FOREIGN KEY (receiver_id) REFERENCES public.users(id)
);
