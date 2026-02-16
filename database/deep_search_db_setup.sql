-- ============================================================
-- deep-search Database Setup Script
-- Run this in PostgreSQL to create the database and tables
-- ============================================================

-- 1. Create the database (run this as postgres superuser)
-- CREATE DATABASE deep_search_db
--     WITH ENCODING 'UTF8'
--     LC_COLLATE = 'en_US.utf8'
--     LC_CTYPE = 'en_US.utf8';

-- Note: If database already exists, connect to it:
-- \c deep_search_db

-- 2. Create the users table
CREATE TABLE IF NOT EXISTS users (
    id          BIGSERIAL       NOT NULL,
    name        VARCHAR(100)    NOT NULL,
    email       VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    created_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email)
);

-- Create index on email for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- 3. (Optional) Insert a sample user for testing
--    Password is plain text here; use this to test login from the Android app
INSERT INTO users (name, email, password, created_at)
VALUES ('Tushar', 'tushar@example.com', 'password123', NOW())
ON CONFLICT (email) DO UPDATE SET name = EXCLUDED.name;

