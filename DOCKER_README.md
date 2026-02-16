# Docker Setup for Deep Search

This guide explains how to run the Deep Search application using Docker.

## Prerequisites

- Docker installed (version 20.10 or later)
- Docker Compose installed (version 2.0 or later)

## Quick Start

### Option 1: Using Docker Compose (Recommended)

This will start both the PostgreSQL database and the Spring Boot application:

```bash
docker-compose up -d
```

To view logs:
```bash
docker-compose logs -f app
```

To stop:
```bash
docker-compose down
```

To stop and remove volumes (database data):
```bash
docker-compose down -v
```

### Option 2: Build and Run Manually

1. **Build the Docker image:**
   ```bash
   docker build -t deep-search:latest .
   ```

2. **Start PostgreSQL database:**
   ```bash
   docker run -d \
     --name deep-search-postgres \
     -e POSTGRES_DB=deep_search_db \
     -e POSTGRES_USER=postgres \
     -e POSTGRES_PASSWORD=postgres \
     -p 5432:5432 \
     -v postgres_data:/var/lib/postgresql/data \
     postgres:15-alpine
   ```

3. **Run the application:**
   ```bash
   docker run -d \
     --name deep-search-app \
     -p 8080:8080 \
     -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/deep_search_db \
     -e SPRING_DATASOURCE_USERNAME=postgres \
     -e SPRING_DATASOURCE_PASSWORD=postgres \
     -e SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver \
     --link deep-search-postgres:postgres \
     deep-search:latest
   ```

## Environment Variables

You can customize the application using these environment variables:

- `SPRING_DATASOURCE_URL`: JDBC URL for PostgreSQL (default: jdbc:postgresql://postgres:5432/deep_search_db)
- `SPRING_DATASOURCE_USERNAME`: Database username (default: postgres)
- `SPRING_DATASOURCE_PASSWORD`: Database password (default: postgres)
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME`: Database driver (default: org.postgresql.Driver)
- `SPRING_JPA_DATABASE_PLATFORM`: Hibernate dialect (default: org.hibernate.dialect.PostgreSQLDialect)
- `SERVER_PORT`: Application port (default: 8080)

## Accessing the Application

- **Application**: http://localhost:8080
- **PostgreSQL**: localhost:5432
  - Username: `postgres`
  - Password: `postgres`
  - Database: `deep_search_db`

## Health Check

The Dockerfile includes a health check. To verify the application is running:

```bash
docker ps
```

You should see the container status as "healthy" after startup.

## Troubleshooting

### Application won't start

1. Check if PostgreSQL is running and healthy:
   ```bash
   docker-compose ps
   ```

2. Check application logs:
   ```bash
   docker-compose logs app
   ```

3. Verify database connection:
   ```bash
   docker-compose exec postgres psql -U postgres -d deep_search_db -c "\dt"
   ```

### Port already in use

If port 8080 or 5432 is already in use, modify the ports in `docker-compose.yml`:

```yaml
ports:
  - "8081:8080"  # Use 8081 instead of 8080
```

## Development

To rebuild after code changes:

```bash
docker-compose build app
docker-compose up -d app
```

Or rebuild everything:

```bash
docker-compose up -d --build
```

