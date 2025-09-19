# Backend Application

A modern backend application built with Spring Boot using Clean Architecture principles and a multimodule Maven structure.

## Table of Contents

- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Docker Support](#docker-support)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)

## Architecture

This application follows **Clean Architecture** principles with a **Hexagonal Architecture** (Ports and Adapters) pattern, ensuring separation of concerns and maintainability.

### Key Architectural Principles

- **Domain-Driven Design (DDD)**: Core business logic is isolated in the domain layer
- **Dependency Inversion**: Dependencies point inward toward the domain
- **Framework Independence**: Business logic is not coupled to Spring Boot
- **Testability**: Each layer can be tested independently

### Layers

```
┌─────────────────────────────────────────┐
│            Infrastructure              │  ← Controllers, Repositories, External APIs
├─────────────────────────────────────────┤
│             Application                │  ← Use Cases, Application Services
├─────────────────────────────────────────┤
│               Domain                   │  ← Entities, Value Objects, Domain Services
└─────────────────────────────────────────┘
```

## Project Structure

```
backend/
├── application/          # Application layer (Use Cases)
│   ├── src/main/java/
│   └── src/test/java/
├── boot/                # Spring Boot configuration and main class
│   ├── src/main/java/
│   └── src/main/resources/
├── domain/              # Domain layer (Business Logic)
│   ├── src/main/java/
│   └── src/test/java/
└── infrastructure/      # Infrastructure layer (Adapters)
    ├── src/main/java/
    ├── src/main/resources/
    └── src/test/java/
```

### Module Responsibilities

#### 🏗️ **boot**
- Main Spring Boot application class
- Configuration files (`application.yml`)
- Dependency injection configuration
- Application startup and shutdown logic

#### 🎯 **domain**
- Core business entities and value objects
- Domain services and business rules
- Domain events and specifications
- No external dependencies (pure Java)

#### 🔧 **application**
- Use cases and application services
- Command and query handlers
- Application-level validation
- Orchestrates domain objects

#### 🌐 **infrastructure**
- REST controllers and API endpoints
- Database repositories and JPA entities
- External service integrations
- Configuration and security

## Prerequisites

- **Java 21** or higher
- **Maven 3.6** or higher
- **Docker** (optional, for containerized deployment)
- **PostgreSQL** (if running without Docker)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/product-trial.git
cd product-trial/backend
```

### 2. Build the Project

```bash
# Clean and compile all modules
mvn clean compile

# Build with tests
mvn clean install

# Skip tests for faster build
mvn clean install -DskipTests
```

## Running the Application

### Option 1: Local Development with PostgreSQL Container

1. **Start PostgreSQL using Docker**:
   ```bash
   # Start only PostgreSQL container
   docker-compose up -d postgres
   
   # Verify the container is running
   docker ps
   ```

2. **Run the application directly on your machine**:
   ```bash
   # From the root backend directory
   mvn spring-boot:run -pl boot

   # Or run the JAR directly
   java -jar boot/target/boot-0.1.0.jar
   ```

3. **Access the application**:
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - Health Check: `http://localhost:8080/actuator/health`

### Option 2: Local Development with Native PostgreSQL

1. **Start PostgreSQL** and create the database:
   ```bash
   # Start PostgreSQL service (macOS with Homebrew)
   brew services start postgresql
   
   # Or start PostgreSQL (Linux/Windows)
   sudo systemctl start postgresql
   
   # Create the database
   createdb -U postgres product_trial
   
   # Or using psql
   psql -U postgres -c "CREATE DATABASE product_trial;"
   ```

2. **Configure database connection** (optional):
   ```bash
   # Set environment variables
   export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/productdb
   export SPRING_DATASOURCE_USERNAME=postgres
   export SPRING_DATASOURCE_PASSWORD=your_password
   ```

3. **Run the application**:
   ```bash
   # From the root backend directory
   mvn spring-boot:run -pl boot

   # Or run the JAR directly
   java -jar boot/target/boot-0.1.0.jar
   ```

4. **Access the application**:
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - Health Check: `http://localhost:8080/actuator/health`

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/product_trial` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `password` |

## Testing

### Unit Tests

```bash
# Run all tests
mvn test

# Run tests for specific module
mvn test -pl domain
mvn test -pl application
mvn test -pl infrastructure
```

### Integration Tests

```bash
# Run integration tests
mvn verify

# Run with test profile
mvn test -Dspring.profiles.active=test
```

### Test Coverage

```bash
# Generate test coverage report
mvn clean verify jacoco:report
```

## Docker Support

### Using Docker for PostgreSQL Only

```bash
# Start PostgreSQL container only
docker-compose up -d postgres

# Stop PostgreSQL container
docker-compose stop postgres

# Check container status
docker ps
```

### PostgreSQL Container Details

- **Port**: 5432 (mapped to host)
- **Database**: product_trial
- **Username**: postgres
- **Password**: password
- **Data Persistence**: Volume mounted at `./docker/postgres-data`

### Docker Compose Configuration

See the `docker-compose.yml` file for PostgreSQL configuration. The application itself should be run directly on your machine, not in a Docker container for local development.

## API Documentation

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products` | List all products |
| `GET` | `/api/products/{id}` | Get product by ID |
| `POST` | `/api/products` | Create new product |
| `PUT` | `/api/products/{id}` | Update product |
| `DELETE` | `/api/products/{id}` | Delete product |

### Example Requests

```bash
# Get all products
curl -X GET http://localhost:8080/api/products

# Create a product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Sample Product", "price": 29.99}'
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Write unit tests for new features
- Maintain Clean Architecture boundaries

## Troubleshooting

### Common Issues

#### Database Connection Error
```
FATAL: database "product_trial" does not exist
```
**Solution**: Create the database manually:
```bash
createdb -U postgres product_trial
```

#### Port Already in Use
```
Port 8080 was already in use
```
**Solution**: Kill the process or use a different port:
```bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9

# Or run on different port
mvn spring-boot:run -pl boot -Dserver.port=8081
```

#### Permission Denied (Docker)
```
Permission denied while trying to connect to Docker daemon
```
**Solution**: Add user to docker group:
```bash
sudo usermod -aG docker $USER
newgrp docker
```

---

For more detailed information, see the [DOCKER-README.md](DOCKER-README.md) file.

