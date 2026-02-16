# Render Deployment Troubleshooting

## Error: "failed to read dockerfile: read .../src: is a directory"

This error occurs when Docker tries to read a directory instead of the Dockerfile. Here are solutions:

## Solution 1: Fix render.yaml (Recommended)

Make sure your `render.yaml` has:

```yaml
services:
  - type: web
    name: deep-search-api
    runtime: docker
    dockerfilePath: Dockerfile  # No ./ prefix, just the filename
    dockerContext: .
```

**Key points:**
- `dockerfilePath: Dockerfile` (not `./Dockerfile` or `Dockerfile/`)
- `dockerContext: .` (current directory)

## Solution 2: Manual Dashboard Configuration

If not using `render.yaml`, configure in Render dashboard:

1. **Root Directory**: Leave **EMPTY** (not `.` or `src`)
2. **Dockerfile Path**: `Dockerfile` (exactly this, no path prefix)
3. **Docker Context**: `.` (period only)

## Solution 3: Verify File Structure

Ensure your repository structure is:

```
deep-search/
├── Dockerfile          ← Must be here (root level)
├── pom.xml
├── src/
│   └── main/
│       └── java/
├── render.yaml
└── ...
```

**Verify Dockerfile exists:**
```bash
ls -la Dockerfile
```

Should show: `-rw-r--r-- ... Dockerfile`

## Solution 4: Check for Hidden Characters

Sometimes invisible characters cause issues. Recreate the Dockerfile path:

1. In Render dashboard, try:
   - `Dockerfile` (no quotes, no spaces)
   - Or try: `./Dockerfile`

2. If using render.yaml, ensure no trailing spaces:
   ```yaml
   dockerfilePath: Dockerfile
   ```

## Solution 5: Alternative render.yaml Format

Try this alternative format:

```yaml
services:
  - type: web
    name: deep-search-api
    runtime: docker
    dockerfilePath: ./Dockerfile
    dockerContext: ./
    plan: free
    envVars:
      - key: SPRING_DATASOURCE_URL
        value: ${DATABASE_URL}
      - key: SPRING_DATASOURCE_USERNAME
        value: ${DB_USERNAME}
      - key: SPRING_DATASOURCE_PASSWORD
        value: ${DB_PASSWORD}
      - key: SERVER_PORT
        value: 8080
```

## Solution 6: Use Absolute Path (Last Resort)

If nothing works, try specifying the full path:

```yaml
dockerfilePath: /opt/render/project/Dockerfile
dockerContext: /opt/render/project
```

## Common Mistakes:

❌ **Wrong:**
```yaml
dockerfilePath: ./Dockerfile/
dockerfilePath: src/Dockerfile
dockerfilePath: Dockerfile/
dockerContext: src
```

✅ **Correct:**
```yaml
dockerfilePath: Dockerfile
dockerContext: .
```

## Verify Your Setup:

1. **Check Dockerfile location:**
   ```bash
   pwd
   ls -la | grep Dockerfile
   ```

2. **Check render.yaml syntax:**
   ```bash
   cat render.yaml
   ```

3. **Test Dockerfile locally:**
   ```bash
   docker build -t test-deep-search .
   ```

## If Still Failing:

1. **Remove render.yaml** and configure manually in dashboard
2. **Check Render build logs** for more specific errors
3. **Verify repository connection** - make sure Render can see all files
4. **Try creating a new service** with fresh configuration

## Quick Fix Checklist:

- [ ] Dockerfile is in root directory
- [ ] `dockerfilePath: Dockerfile` (no `./` or `/`)
- [ ] `dockerContext: .` (period only)
- [ ] Root Directory is empty in dashboard
- [ ] All files committed and pushed to repository
- [ ] No special characters in paths

