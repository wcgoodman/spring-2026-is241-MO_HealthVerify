INSERT INTO users ( --creates a row in users with values from web form and specified timestamp behaviors
    email,
    password_hash,
    salt,
    first_name,
    last_name,
    password_last_updated,
    datetime_registered,
    last_login,
    security_question,
    security_answer_hash
) VALUES (
    $email,
    $pw_hash,
    $salt_key,
    $first_name,
    $last_name,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    NULL, --null until the user actually logs in
    $security_question,
    $hashed_security_answer
);

SELECT EXISTS(SELECT 1 FROM users WHERE email = $email) as "exists"; --returns boolean, checks if email already in use

SELECT salt FROM users WHERE email = $email; --returns the salt key for use in hashing provided PW to compare to password_hash

SELECT password_hash from users where email = $email; --returns the previously hashed PW+salt for comparison

UPDATE users --update query for changing password
SET
    password_hash = $new_password_hash,
    password_last_updated = CURRENT_TIMESTAMP
WHERE
    email = $email;

