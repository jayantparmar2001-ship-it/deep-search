# Deep Search API Documentation

## Car Wash Subscription Service - Complete CRUD Operations

### Base URL
```
https://deep-search-z3bh.onrender.com
```

---

## 1. Services API

### Create Service
```http
POST /api/services
Content-Type: application/json

{
  "serviceName": "Basic Wash",
  "description": "Exterior wash and dry",
  "price": 25.00,
  "duration": "30 minutes"
}
```

### Get All Services
```http
GET /api/services
```

### Get Service by ID
```http
GET /api/services/{serviceId}
```

### Search Services
```http
GET /api/services/search?name=wash
```

### Update Service
```http
PUT /api/services/{serviceId}
Content-Type: application/json

{
  "serviceName": "Premium Wash",
  "description": "Updated description",
  "price": 50.00,
  "duration": "45 minutes"
}
```

### Delete Service
```http
DELETE /api/services/{serviceId}
```

---

## 2. Subscription Plans API

### Create Subscription Plan
```http
POST /api/subscription-plans
Content-Type: application/json

{
  "planName": "Basic Plan",
  "monthlyPrice": 49.99,
  "washLimit": 2,
  "validityPeriod": "1 month"
}
```

### Get All Plans
```http
GET /api/subscription-plans
```

### Get Active Plans
```http
GET /api/subscription-plans/active
```

### Get Plan by ID
```http
GET /api/subscription-plans/{planId}
```

### Update Plan
```http
PUT /api/subscription-plans/{planId}
Content-Type: application/json

{
  "planName": "Premium Plan",
  "monthlyPrice": 99.99,
  "washLimit": 5,
  "validityPeriod": "1 month"
}
```

### Activate Plan
```http
POST /api/subscription-plans/{planId}/activate
```

### Deactivate Plan
```http
POST /api/subscription-plans/{planId}/deactivate
```

### Delete Plan
```http
DELETE /api/subscription-plans/{planId}
```

---

## 3. Subscriptions API

### Subscribe (Create Subscription)
```http
POST /api/subscriptions/subscribe
Content-Type: application/json

{
  "userId": 1,
  "planId": 1,
  "startDate": "2026-02-16",
  "endDate": "2026-03-16"
}
```

### Get All Subscriptions
```http
GET /api/subscriptions
```

### Get Subscription by ID
```http
GET /api/subscriptions/{subscriptionId}
```

### Get Subscriptions by User ID
```http
GET /api/subscriptions/user/{userId}
```

### Get Active Subscriptions by User
```http
GET /api/subscriptions/user/{userId}/active
```

### Renew Subscription
```http
POST /api/subscriptions/{subscriptionId}/renew?months=1
```

### Cancel Subscription
```http
POST /api/subscriptions/{subscriptionId}/cancel
```

### Delete Subscription
```http
DELETE /api/subscriptions/{subscriptionId}
```

---

## 4. Payments API

### Process Payment
```http
POST /api/payments/process
Content-Type: application/json

{
  "userId": 1,
  "subscriptionId": 1,
  "amount": 49.99,
  "paymentMethod": "CREDIT_CARD",
  "transactionId": "TXN123456"
}
```

### Get All Payments
```http
GET /api/payments
```

### Get Payment by ID
```http
GET /api/payments/{paymentId}
```

### Get Payments by User ID
```http
GET /api/payments/user/{userId}
```

### Get Payments by User ID and Status
```http
GET /api/payments/user/{userId}/status/{status}
```

### Update Payment Status
```http
PUT /api/payments/{paymentId}/status?status=COMPLETED
```

### Delete Payment
```http
DELETE /api/payments/{paymentId}
```

---

## 5. Authentication API (Existing)

### Register
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "+1234567890"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

---

## 6. Customer Queries API (Existing)

### Submit Query
```http
POST /api/queries/submit
Content-Type: application/json

{
  "email": "user@example.com",
  "subject": "Question about service",
  "message": "I have a question..."
}
```

---

## Example cURL Commands

### Create a Service
```bash
curl -X POST https://deep-search-z3bh.onrender.com/api/services \
  -H "Content-Type: application/json" \
  -d '{
    "serviceName": "Basic Wash",
    "description": "Exterior wash and dry",
    "price": 25.00,
    "duration": "30 minutes"
  }'
```

### Create a Subscription Plan
```bash
curl -X POST https://deep-search-z3bh.onrender.com/api/subscription-plans \
  -H "Content-Type: application/json" \
  -d '{
    "planName": "Basic Plan",
    "monthlyPrice": 49.99,
    "washLimit": 2,
    "validityPeriod": "1 month"
  }'
```

### Subscribe to a Plan
```bash
curl -X POST https://deep-search-z3bh.onrender.com/api/subscriptions/subscribe \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "planId": 1
  }'
```

### Process Payment
```bash
curl -X POST https://deep-search-z3bh.onrender.com/api/payments/process \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "subscriptionId": 1,
    "amount": 49.99,
    "paymentMethod": "CREDIT_CARD",
    "transactionId": "TXN123456"
  }'
```

---

## Response Formats

### Success Response
```json
{
  "serviceId": 1,
  "serviceName": "Basic Wash",
  "description": "Exterior wash and dry",
  "price": 25.00,
  "duration": "30 minutes"
}
```

### Error Response
```json
{
  "timestamp": "2026-02-16T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Service not found with ID: 999",
  "path": "/api/services/999"
}
```

---

## Database Schema

### Tables Created:
1. **users** - User accounts
2. **services** - Available car wash services
3. **subscription_plans** - Subscription plan definitions
4. **subscriptions** - User subscriptions
5. **payments** - Payment transactions
6. **customer_queries** - Customer support queries

All tables include proper foreign key relationships and indexes for optimal performance.


