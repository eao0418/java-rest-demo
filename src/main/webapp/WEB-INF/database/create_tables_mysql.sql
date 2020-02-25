CREATE DATABASE IF NOT EXISTS rest_app;
-- Creates a service account on the localhost.  
-- Update the host, user, and password as needed.
CREATE USER IF NOT EXISTS 'rest_app_account'@'localhost' IDENTIFIED BY 'password';
-- The grant must be updated if the user or host values are changed.
GRANT ALL ON rest_app.* TO 'rest_app_account'@'localhost';

CREATE TABLE IF NOT EXISTS rest_app.uid (
	user_id varchar(50) NOT NULL,
    uid_number int NOT NULL,
    assignment_time BIGINT,
    modify_time BIGINT,
    PRIMARY KEY (user_id)
    );