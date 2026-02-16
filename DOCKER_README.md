# Docker Setup for Deep Search

This guide explains how to run the Deep Search application using Docker.

## Prerequisites

- Docker installed (version 20.10 or later)
- Docker Compose installed (version 2.0 or later)

## Quick Start

### Option 1: Using Docker Compose (Recommended)

This will start both the MySQL database and the Spring Boot application:

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

2. **Start MySQL database:**
   ```bash
   docker run -d \
     --name deep-search-mysql \
     -e MYSQL_ROOT_PASSWORD=root \
     -e MYSQL_DATABASE=deep_search_db \
     -p 3306:3306 \
     -v mysql_data:/var/lib/mysql \
     mysql:8.0
   ```

3. **Run the application:**
   ```bash
   docker run -d \
     --name deep-search-app \
     -p 8080:8080 \
     -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/deep_search_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true \
     -e SPRING_DATASOURCE_USERNAME=root \
     -e SPRING_DATASOURCE_PASSWORD=root \
     --link deep-search-mysql:mysql \
     deep-search:latest
   ```

## Environment Variables

You can customize the application using these environment variables:

- `SPRING_DATASOURCE_URL`: JDBC URL for MySQL (default: jdbc:mysql://mysql:3306/deep_search_db...)
- `SPRING_DATASOURCE_USERNAME`: Database username (default: root)
- `SPRING_DATASOURCE_PASSWORD`: Database password (default: root)
- `SERVER_PORT`: Application port (default: 8080)

## Accessing the Application

- **Application**: http://localhost:8080
- **MySQL**: localhost:3306
  - Username: `root`
  - Password: `root`
  - Database: `deep_search_db`

## Health Check

The Dockerfile includes a health check. To verify the application is running:

```bash
docker ps
```

You should see the container status as "healthy" after startup.

## Troubleshooting

### Application won't start

1. Check if MySQL is running and healthy:
   ```bash
   docker-compose ps
   ```

2. Check application logs:
   ```bash
   docker-compose logs app
   ```

3. Verify database connection:
   ```bash
   docker-compose exec mysql mysql -uroot -proot -e "SHOW DATABASES;"
   ```

### Port already in use

If port 8080 or 3306 is already in use, modify the ports in `docker-compose.yml`:

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

