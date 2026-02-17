# Quick Fix: JDBC URL Error on Render

## The Problem
Error: `Driver org.postgresql.Driver claims to not accept jdbcUrl, jdbc:postgresql://user:pass@host/db`

The JDBC URL has credentials embedded, which is incorrect. It should be:
- URL: `jdbc:postgresql://host:port/database` (NO credentials)
- Username: separate property
- Password: separate property

## The Fix (5 minutes)

### Step 1: Get Your Database Host

1. Go to Render Dashboard → Your PostgreSQL Database
2. Click **"Connect"** or **"Info"** tab
3. Find **"Internal Database URL"** - it looks like:
   ```
   postgresql://deepsearch:password@dpg-xxxxx-a.oregon-postgres.render.com:5432/deep_search_db
   ```
4. Copy the **host part**: `dpg-xxxxx-a.oregon-postgres.render.com`

### Step 2: Set Environment Variable in Web Service

1. Go to Render Dashboard → Your Web Service
2. Click **"Environment"** tab
3. Find or add `SPRING_DATASOURCE_URL`
4. Set it to (replace `[HOST]` with your host from Step 1):
   ```
   jdbc:postgresql://[HOST]:5432/deep_search_db
   ```
   
   **Example:**
   ```
   jdbc:postgresql://dpg-xxxxx-a.oregon-postgres.render.com:5432/deep_search_db
   ```

### Step 3: Verify Other Variables

Make sure these are set (they should be auto-set by render.yaml):
- ✅ `SPRING_DATASOURCE_USERNAME` = your database user
- ✅ `SPRING_DATASOURCE_PASSWORD` = your database password
- ✅ `SPRING_DATASOURCE_DRIVER_CLASS_NAME` = `org.postgresql.Driver`

### Step 4: Save and Redeploy

1. Click **"Save Changes"**
2. Go to **"Manual Deploy"** → **"Deploy latest commit"**

## Conversion Formula

**From Render's Internal Database URL:**
```
postgresql://user:pass@host:port/database
```

**To JDBC URL:**
```
jdbc:postgresql://host:port/database
```

**Remove:**
- `postgresql://` → replace with `jdbc:postgresql://`
- `user:pass@` → remove completely

**Keep:**
- `host:port/database` → use as is

## Example

**Render shows:**
```
postgresql://deepsearch:mypass123@dpg-abc123-a.oregon-postgres.render.com:5432/deep_search_db
```

**Set SPRING_DATASOURCE_URL to:**
```
jdbc:postgresql://dpg-abc123-a.oregon-postgres.render.com:5432/deep_search_db
```

**Set SPRING_DATASOURCE_USERNAME to:**
```
deepsearch
```

**Set SPRING_DATASOURCE_PASSWORD to:**
```
mypass123
```

## Verification

After redeploy, check logs for:
- ✅ `HikariPool-1 - Start completed`
- ✅ `Using dialect: org.hibernate.dialect.PostgreSQLDialect`
- ❌ No more "claims to not accept jdbcUrl" error

## Still Not Working?

1. **Double-check the URL format** - must start with `jdbc:postgresql://`
2. **No credentials in URL** - username/password must be separate
3. **Port is 5432** - default PostgreSQL port
4. **Database name matches** - should be `deep_search_db`
5. **Use Internal URL host** - not external, for better performance


