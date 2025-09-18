# Docker Setup for PostgreSQL

This project includes a Docker Compose configuration for setting up a PostgreSQL database for the product-trial application.

## Prerequisites

- Docker and Docker Compose installed on your machine
- Java 21 or higher
- Maven

## Running the Database

1. From the root directory of the project, run:

```bash
docker-compose up -d
```

This will start a PostgreSQL container with the following configuration:
- Port: 5432 (mapped to the host)
- Database name: product_trial
- Username: postgres
- Password: postgres

2. To stop the database:

```bash
docker-compose down
```

If you want to completely remove the volumes as well:

```bash
docker-compose down -v
```

## Database Schema

The database schema is automatically created when the container starts up, using Flyway migrations located in:
`backend/infrastructure/src/main/resources/db/migration/`

## Connecting to the Database

You can connect to the database using any PostgreSQL client with these details:
- Host: localhost
- Port: 5432
- Database: product_trial
- Username: postgres
- Password: postgres

## Application Configuration

The application is configured to connect to this database using the settings in:
`backend/boot/src/main/resources/application.yml`