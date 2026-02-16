# Fix: Database Connection Issue on Render

## Problem
The application is trying to connect to `localhost:5432` instead of the Render database, causing:
```
Connection to localhost:5432 refused
```

## Root Cause
The environment variables from the Render database aren't being properly injected or the connection string format is incorrect.

## Solution 1: Manual Environment Variable Configuration (Recommended)

If `render.yaml` isn't working, configure manually in Render dashboard:

1. **Go to your Web Service** in Render dashboard
2. **Navigate to Environment** tab
3. **Add these environment variables:**

```
SPRING_DATASOURCE_URL = [Get from Database → Connect → Internal Database URL]
SPRING_DATASOURCE_USERNAME = [Get from Database → Connect → User]
SPRING_DATASOURCE_PASSWORD = [Get from Database → Connect → Password]
SPRING_DATASOURCE_DRIVER_CLASS_NAME = org.postgresql.Driver
SPRING_JPA_DATABASE_PLATFORM = org.hibernate.dialect.PostgreSQLDialect
SERVER_PORT = 8080
```

**To get the database connection details:**
1. Go to your PostgreSQL database service
2. Click on "Connect" or "Info"
3. Copy the **Internal Database URL** (format: `postgresql://user:pass@host:port/dbname`)
4. Convert it to JDBC format: `jdbc:postgresql://host:port/dbname`

**Example conversion:**
- Render Internal URL: `postgresql://deepsearch:password@dpg-xxxxx-a/deep_search_db`
- JDBC URL: `jdbc:postgresql://dpg-xxxxx-a:5432/deep_search_db`

## Solution 2: Fix render.yaml

The current `render.yaml` should work, but if it doesn't, try this alternative:

```yaml
services:
  - type: web
    name: deep-search-api
    runtime: docker
    dockerfilePath: Dockerfile
    dockerContext: .
    plan: free
    envVars:
      - key: DATABASE_URL
        fromDatabase:
          name: deep-search-db
          property: connectionString
      - key: SPRING_DATASOURCE_URL
        # Convert DATABASE_URL to JDBC format
        value: jdbc:postgresql://${DATABASE_URL#postgresql://}
      - key: SPRING_DATASOURCE_USERNAME
        fromDatabase:
          name: deep-search-db
          property: user
      - key: SPRING_DATASOURCE_PASSWORD
        fromDatabase:
          name: deep-search-db
          property: password
      - key: SPRING_DATASOURCE_DRIVER_CLASS_NAME
        value: org.postgresql.Driver
      - key: SPRING_JPA_DATABASE_PLATFORM
        value: org.hibernate.dialect.PostgreSQLDialect
      - key: SERVER_PORT
        value: 8080
    healthCheckPath: /

databases:
  - name: deep-search-db
    databaseName: deep_search_db
    user: deepsearch
    plan: free
    postgresMajorVersion: 15
```

## Solution 3: Verify Database is Created and Linked

1. **Check if database exists:**
   - Go to Render dashboard → Databases
   - Verify `deep-search-db` exists and is running

2. **Link database to web service:**
   - Go to your Web Service
   - In the Environment tab, check if database is linked
   - If not, manually add the connection variables

3. **Verify database name matches:**
   - Database name in render.yaml: `deep_search_db`
   - Should match the actual database name

## Solution 4: Use Render's Internal Database URL Format

Render provides connection strings in format: `postgresql://user:pass@host:port/dbname`

You need to convert this to JDBC format: `jdbc:postgresql://host:port/dbname`

**Manual conversion steps:**
1. Get Internal Database URL from Render (e.g., `postgresql://user:pass@dpg-xxx-a.oregon-postgres.render.com:5432/deep_search_db`)
2. Extract host and port: `dpg-xxx-a.oregon-postgres.render.com:5432`
3. Extract database name: `deep_search_db`
4. Format as JDBC: `jdbc:postgresql://dpg-xxx-a.oregon-postgres.render.com:5432/deep_search_db`

## Quick Fix Checklist

- [ ] Database service is created and running
- [ ] Database name matches: `deep_search_db`
- [ ] Environment variables are set in Web Service
- [ ] `SPRING_DATASOURCE_URL` is in JDBC format (starts with `jdbc:postgresql://`)
- [ ] Username and password are correct
- [ ] Web service is linked to database (if using render.yaml)
- [ ] Redeployed after making changes

## Testing the Connection

After setting environment variables, check the logs:

```bash
# Look for successful connection messages:
HikariPool-1 - Start completed.
HHH000400: Using dialect: org.hibernate.dialect.PostgreSQLDialect
```

If you still see `localhost:5432`, the environment variables aren't being read. Double-check:
1. Variables are saved in Render dashboard
2. Service has been redeployed
3. No typos in variable names

## Alternative: Use Application Properties Override

If environment variables still don't work, you can create a `application-render.properties` file:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

And set the profile in Render:
```
SPRING_PROFILES_ACTIVE=render
```

