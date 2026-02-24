/*
	Create script for users table, for use in Registration story
 */

CREATE TABLE IF NOT EXISTS users (
    user_id PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    salt TEXT NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    password_last_updated TIMESTAMPTZ,
    datetime_registered TIMESTAMPTZ NOT NULL,
    last_login TIMESTAMPTZ,
    security_question TEXT NOT NULL,
    security_answer_hash TEXT NOT NULL
);