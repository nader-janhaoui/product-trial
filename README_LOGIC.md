# Backend Architecture & Implementation Guide

## Project Structure (Hexagonal Architecture)
- **Domain**: Core business logic and entities
- **Application**: Use case orchestration and ports
- **Infrastructure**: External adapters and frameworks

## Testing Strategy

### Domain Layer
- Fast, pure unit tests
- Test entities, value objects, and domain rules
- No external dependencies

### Application Layer
- Unit tests with mocks
- Test use case orchestration through ports
- Verify business flows

### Infrastructure Layer
- Persistence adapter: Integration tests with Testcontainers
- Web adapter: Controller tests with @WebMvcTest
- Test JSON mapping and HTTP endpoints

## Implementation Sequence

### Phase 1: Project Setup
1. **chore**: Initialize multi-module project (domain, application, infrastructure)
2. **test**: Add CI pipeline and core test dependencies (JUnit5, AssertJ, Mockito)
3. **feat(infra)**: Bootstrap Spring Boot app with health endpoint
4. **test(e2e)**: Implement walking skeleton test for /actuator/health

### Phase 2: Domain Implementation
1. **test(domain)**: Define Account and Money behaviors
2. **feat(domain)**: Implement Account and Money with invariants
3. **refactor(domain)**: Extract domain errors and value objects


Based on the requirements, here's a structured list of use cases and implementation plan for the hexagonal architecture backend:

## Domain Layer Use Cases

### Product Management
- Get all products
- Add new product (admin only)
- Update product (admin only)
- Delete product (admin only)

### User Management
- Create new user account
- Generate JWT token for login
- Validate admin permissions (admin@admin.com)

### Shopping Cart
- Create shopping cart for user
- Add product to cart
- Remove product from cart
- Update product quantity in cart
- Get cart contents

## Implementation Plan

### 1. Project Setup
- Initialize multi-module Maven project
- Set up domain, application, infrastructure modules
- Configure Spring Boot
- Add base dependencies (Spring Security, JWT, JPA)

### 2. Core Domain
- Implement Product entity
- Implement User entity  
- Implement ShoppingCart entity
- Define domain events and exceptions
- Add value objects (Money, Email, etc.)

### 3. Application Services
- Implement ProductService
- Implement UserService 
- Implement ShoppingCartService
- Add input/output ports
- Define service interfaces

### 4. Infrastructure
- Add JPA repositories
- Implement JWT authentication
- Add admin authorization filter
- Configure REST controllers
- Add persistence adapters

## Commit Strategy

### Project Setup
- chore: initialize multi-module project structure
- chore: add base dependencies and configurations

### Domain Layer
- feat(domain): add product entity and value objects
- feat(domain): add user entity and authentication logic
- feat(domain): add shopping cart aggregate
- test(domain): add domain unit tests

### Application Layer
- feat(app): implement product management use cases
- feat(app): add user registration and auth use cases
- feat(app): implement shopping cart management
- test(app): add application service tests

### Infrastructure Layer
- feat(infra): add JPA repositories and entities
- feat(infra): implement JWT authentication
- feat(infra): add REST API controllers
- feat(infra): configure admin authorization
- test(infra): add integration tests

### Documentation
- docs: add Swagger API documentation
- test: add Postman collection for API testing