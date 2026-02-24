/*
 * Create script for users table, for use in Registration story
 * Updated 2026.02.24 with best practices
 */

--Uncomment below line if need to drop users table before creation
--DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    user_id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
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