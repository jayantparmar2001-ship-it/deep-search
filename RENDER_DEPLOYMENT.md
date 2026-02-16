# Render Deployment Guide for Deep Search

## Error Fix: "no such file or directory" on Render

If you're getting the error:
```
invalid local: resolve : lstat /opt/render/project/src/deep-search: no such file or directory
```

This means Render is looking for your project in the wrong directory. Here's how to fix it:

## Solution 1: Using render.yaml (Recommended)

1. Make sure `render.yaml` is in the root of your repository
2. Connect your repository to Render
3. Render will automatically detect and use `render.yaml`

## Solution 2: Fix Render Dashboard Settings

If you're configuring manually in the Render dashboard:

### For Web Service (API):

1. **Root Directory**: Leave this **EMPTY** or set to `.` (not `src/deep-search`)
   - Render expects the project root, not a subdirectory
   - The Dockerfile is already in the root

2. **Dockerfile Path**: Set to `Dockerfile` (or `./Dockerfile`)
   - This should be relative to the root directory

3. **Docker Context**: Set to `.` (current directory)
   - This tells Docker where to find files for `COPY` commands

4. **Build Command**: Leave empty (Dockerfile handles this)

5. **Start Command**: Leave empty (Dockerfile handles this)

### Environment Variables:

Set these in the Render dashboard:

```
SPRING_DATASOURCE_URL=jdbc:mysql://your-db-host:3306/deep_search_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=your-username
SPRING_DATASOURCE_PASSWORD=your-password
SERVER_PORT=8080
```

## Solution 3: If Your Project is in a Subdirectory

If your repository structure is:
```
your-repo/
  ├── src/
  │   └── deep-search/    <- Your project is here
  │       ├── Dockerfile
  │       ├── pom.xml
  │       └── src/
  └── other-files/
```

Then in Render dashboard:
- **Root Directory**: `src/deep-search`
- **Dockerfile Path**: `Dockerfile`
- **Docker Context**: `src/deep-search`

## Common Issues:

### Issue 1: Root Directory is Wrong
- ❌ Wrong: `src/deep-search` (if project is at root)
- ✅ Correct: `.` or leave empty

### Issue 2: Dockerfile Path is Wrong
- ❌ Wrong: `src/deep-search/Dockerfile`
- ✅ Correct: `Dockerfile` (if root directory is set correctly)

### Issue 3: Docker Context is Wrong
- ❌ Wrong: `src/deep-search`
- ✅ Correct: `.` (same as root directory)

## Verify Your Setup:

1. **Check your repository structure**:
   ```
   deep-search/
   ├── Dockerfile          ← Should be here
   ├── pom.xml             ← Should be here
   ├── src/                 ← Should be here
   └── render.yaml         ← Optional but recommended
   ```

2. **In Render Dashboard**, verify:
   - Root Directory: `.` or empty
   - Dockerfile Path: `Dockerfile`
   - Docker Context: `.`

## Database Setup on Render:

1. Create a **PostgreSQL** or **MySQL** database in Render
2. Note the connection details
3. Update environment variables with the correct database URL

For PostgreSQL (Render's default):
```
SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/deep_search_db?sslmode=require
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
```

For MySQL:
```
SPRING_DATASOURCE_URL=jdbc:mysql://your-db-host:3306/deep_search_db?useSSL=true&serverTimezone=UTC
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
```

## Quick Checklist:

- [ ] Root Directory is `.` or empty
- [ ] Dockerfile Path is `Dockerfile`
- [ ] Docker Context is `.`
- [ ] Dockerfile exists in the root
- [ ] Environment variables are set
- [ ] Database is created and connected

## Still Having Issues?

1. Check Render build logs for more details
2. Verify your repository is connected correctly
3. Make sure all files are committed and pushed to your repository
4. Check that the Dockerfile uses relative paths (which it does)

