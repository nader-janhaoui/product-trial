# Repository Testing Strategy

This document describes the testing strategy for the repository layer of the application.

## Testing Approach

We use a multi-layered approach to testing our repository implementations:

1. **Contract Tests**: Framework-agnostic tests that define the expected behavior of any repository implementation.
2. **Integration Tests**: Tests that verify the repository implementation against a real database.
3. **Transaction Tests**: Tests that demonstrate and verify transactional behavior.

## Test Types

### Contract Tests

The `ProductRepositoryContractTest` is an abstract test class that defines a contract for repository implementations. It includes tests for:

- Saving and retrieving products
- Finding products by ID
- Updating existing products
- Deleting products
- Checking for existence by code
- Retrieving all products

Any repository implementation must extend this class and implement the abstract methods to fulfill the contract.

### Integration Tests

`PostgresProductRepositoryIT` is an integration test that extends the contract test and verifies that our PostgreSQL implementation correctly fulfills the contract.

### Specific Implementation Tests

`PostgresProductRepositoryTest` contains specific tests for the PostgreSQL implementation that go beyond the contract.

### Transaction Tests

`PostgresProductRepositoryTransactionTest` demonstrates and verifies the transactional behavior of our repository. It ensures that:

- Changes in a transaction are visible within the transaction
- Changes are rolled back if the transaction is not committed

## Infrastructure

We use Testcontainers to run tests against a real PostgreSQL database. This ensures that:

1. We test against the same database technology used in production
2. We avoid test/production drift that can occur with in-memory databases
3. Our tests are more reliable and representative of real-world behavior

## Running the Tests

All tests can be run with Maven:

```bash
mvn test
```

Transaction tests use Spring's transactional test support to automatically roll back changes after each test, ensuring test isolation.