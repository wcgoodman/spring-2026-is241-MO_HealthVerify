/*
	Create script for users table, for use in Registration story
 */

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    salt VARCHAR(32) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password_last_updated TIMESTAMP WITH TIME ZONE,
    datetime_registered TIMESTAMP WITH TIME ZONE NOT NULL,
    last_login TIMESTAMP WITH TIME ZONE,
    security_question VARCHAR(255) NOT NULL,
    security_answer_hash VARCHAR(255) NOT NULL
);