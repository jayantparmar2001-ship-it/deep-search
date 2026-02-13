-- ============================================================
-- deep-search Database Setup Script
-- Run this in MySQL to create the database and tables
-- ============================================================

-- 1. Create the database
CREATE DATABASE IF NOT EXISTS deep_search_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE deep_search_db;

-- 2. Create the users table
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100)    NOT NULL,
    email       VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    created_at  DATETIME        DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. (Optional) Insert a sample user for testing
--    Password is plain text here; use this to test login from the Android app
INSERT INTO users (name, email, password, created_at)
VALUES ('Tushar', 'tushar@example.com', 'password123', NOW())
ON DUPLICATE KEY UPDATE name = name;

