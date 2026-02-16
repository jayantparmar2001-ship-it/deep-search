# Fix: 'url' must start with "jdbc" Error

## Problem
Render provides database connection strings in PostgreSQL URI format:
```
postgresql://user:pass@host:port/dbname
```

But Spring Boot/HikariCP requires JDBC URL format:
```
jdbc:postgresql://host:port/dbname
```

## Solution: Manual Configuration in Render Dashboard

Since `render.yaml` doesn't support string manipulation, you need to manually configure the JDBC URL.

### Step 1: Get Database Connection Details

1. Go to your **PostgreSQL database** service in Render
2. Click on **"Connect"** or **"Info"** tab
3. Note these values:
   - **Internal Database URL** (format: `postgresql://user:pass@host:port/dbname`)
   - **Host** (e.g., `dpg-xxxxx-a.oregon-postgres.render.com`)
   - **Port** (usually `5432`)
   - **Database** (e.g., `deep_search_db`)
   - **User**
   - **Password**

### Step 2: Convert to JDBC URL Format

**Example conversion:**

If Render shows:
```
postgresql://deepsearch:password@dpg-xxxxx-a.oregon-postgres.render.com:5432/deep_search_db
```

Convert to:
```
jdbc:postgresql://dpg-xxxxx-a.oregon-postgres.render.com:5432/deep_search_db
```

**Formula:**
- Remove `postgresql://`
- Remove `user:pass@` part
- Add `jdbc:` prefix
- Keep `host:port/dbname` part

### Step 3: Set Environment Variables in Render

1. Go to your **Web Service** in Render
2. Navigate to **Environment** tab
3. Add/Update these variables:

```
SPRING_DATASOURCE_URL = jdbc:postgresql://[YOUR_HOST]:5432/deep_search_db
SPRING_DATASOURCE_USERNAME = [YOUR_USER]
SPRING_DATASOURCE_PASSWORD = [YOUR_PASSWORD]
SPRING_DATASOURCE_DRIVER_CLASS_NAME = org.postgresql.Driver
SPRING_JPA_DATABASE_PLATFORM = org.hibernate.dialect.PostgreSQLDialect
SERVER_PORT = 8080
```

**Important:** 
- Replace `[YOUR_HOST]` with the actual host from Internal Database URL
- Use the **Internal Database URL** host (not external), as it's faster and more secure
- Port is usually `5432` for PostgreSQL

### Step 4: Redeploy

After setting environment variables, **Save** and **Manual Deploy** your service.

## Quick Reference

| Render Property | Example Value | JDBC Format |
|----------------|---------------|-------------|
| Internal URL | `postgresql://user:pass@host:5432/db` | `jdbc:postgresql://host:5432/db` |
| Host | `dpg-xxxxx-a.oregon-postgres.render.com` | Same |
| Port | `5432` | Same |
| Database | `deep_search_db` | Same |

## Verification

After deployment, check logs for:
- ✅ `HikariPool-1 - Start completed`
- ✅ `Using dialect: org.hibernate.dialect.PostgreSQLDialect`
- ❌ No `'url' must start with "jdbc"` error
- ❌ No `Connection to localhost:5432 refused` error

## Alternative: Use External Database URL

If you prefer to use the external URL (for local development), use the **External Database URL** instead, but still convert it to JDBC format:

```
External: postgresql://user:pass@host:5432/db?sslmode=require
JDBC: jdbc:postgresql://host:5432/db?sslmode=require
```

