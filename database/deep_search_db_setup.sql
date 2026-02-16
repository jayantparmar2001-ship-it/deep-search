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
    user_id     BIGSERIAL       NOT NULL,
    name        VARCHAR(100)    NOT NULL,
    email       VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    phone       VARCHAR(20),
    created_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    CONSTRAINT uk_users_email UNIQUE (email)
);

-- Create index on email for faster lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- 3. Create the services table
CREATE TABLE IF NOT EXISTS services (
    service_id      BIGSERIAL       NOT NULL,
    service_name    VARCHAR(255)    NOT NULL,
    description     TEXT,
    price           DECIMAL(10,2)   NOT NULL,
    duration        VARCHAR(50),
    PRIMARY KEY (service_id)
);

-- 4. Create the subscription_plans table
CREATE TABLE IF NOT EXISTS subscription_plans (
    plan_id         BIGSERIAL       NOT NULL,
    plan_name       VARCHAR(255)    NOT NULL,
    monthly_price   DECIMAL(10,2)   NOT NULL,
    wash_limit      INTEGER         NOT NULL,
    validity_period VARCHAR(50),
    is_active       BOOLEAN         DEFAULT TRUE,
    PRIMARY KEY (plan_id)
);

-- 5. Create the subscriptions table
CREATE TABLE IF NOT EXISTS subscriptions (
    subscription_id BIGSERIAL       NOT NULL,
    user_id         BIGINT          NOT NULL,
    plan_id         BIGINT          NOT NULL,
    start_date      DATE            NOT NULL,
    end_date        DATE            NOT NULL,
    status          VARCHAR(50)     DEFAULT 'ACTIVE',
    washes_used     INTEGER         DEFAULT 0,
    PRIMARY KEY (subscription_id),
    CONSTRAINT fk_subscription_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_subscription_plan FOREIGN KEY (plan_id) REFERENCES subscription_plans(plan_id)
);

-- 6. Create the payments table
CREATE TABLE IF NOT EXISTS payments (
    payment_id      BIGSERIAL       NOT NULL,
    user_id         BIGINT          NOT NULL,
    subscription_id BIGINT,
    amount          DECIMAL(10,2)   NOT NULL,
    payment_date    TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    payment_method  VARCHAR(50)     NOT NULL,
    payment_status  VARCHAR(50)      DEFAULT 'PENDING',
    transaction_id  VARCHAR(255),
    PRIMARY KEY (payment_id),
    CONSTRAINT fk_payment_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_payment_subscription FOREIGN KEY (subscription_id) REFERENCES subscriptions(subscription_id)
);

-- 7. Create the customer_queries table
CREATE TABLE IF NOT EXISTS customer_queries (
    id          BIGSERIAL       NOT NULL,
    email       VARCHAR(255)    NOT NULL,
    subject     VARCHAR(255)    NOT NULL,
    message     TEXT            NOT NULL,
    created_at  TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    status      VARCHAR(50)     DEFAULT 'PENDING',
    PRIMARY KEY (id)
);

-- Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_customer_queries_email ON customer_queries(email);
CREATE INDEX IF NOT EXISTS idx_customer_queries_status ON customer_queries(status);
CREATE INDEX IF NOT EXISTS idx_customer_queries_created_at ON customer_queries(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_subscriptions_user_id ON subscriptions(user_id);
CREATE INDEX IF NOT EXISTS idx_subscriptions_status ON subscriptions(status);
CREATE INDEX IF NOT EXISTS idx_payments_user_id ON payments(user_id);
CREATE INDEX IF NOT EXISTS idx_payments_status ON payments(payment_status);

-- 8. (Optional) Insert sample data for testing
-- Sample user
INSERT INTO users (name, email, password, phone, created_at)
VALUES ('Tushar', 'tushar@example.com', 'password123', '+1234567890', NOW())
ON CONFLICT (email) DO UPDATE SET name = EXCLUDED.name;

-- Sample services
INSERT INTO services (service_name, description, price, duration)
VALUES 
    ('Basic Wash', 'Exterior wash and dry', 25.00, '30 minutes'),
    ('Premium Wash', 'Exterior wash, interior vacuum, tire shine', 50.00, '45 minutes'),
    ('Full Service', 'Complete wash, wax, interior detail', 100.00, '90 minutes')
ON CONFLICT DO NOTHING;

-- Sample subscription plans
INSERT INTO subscription_plans (plan_name, monthly_price, wash_limit, validity_period, is_active)
VALUES 
    ('Basic Plan', 49.99, 2, '1 month', TRUE),
    ('Premium Plan', 99.99, 5, '1 month', TRUE),
    ('Deluxe Plan', 149.99, 10, '1 month', TRUE)
ON CONFLICT DO NOTHING;

