# Multi-stage build for Spring Boot application
FROM eclipse-temurin:21-jdk-alpine AS builder

# Install Maven
RUN apk add --no-cache maven

# Set working directory
WORKDIR /app

# Copy Maven files for dependency resolution
COPY pom.xml .
COPY application/pom.xml application/
COPY boot/pom.xml boot/
COPY domain/pom.xml domain/
COPY infrastructure/pom.xml infrastructure/

# Download dependencies (this layer will be cached if pom.xml files don't change)
RUN mvn dependency:go-offline -B || true

# Copy source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests -B

# Debug: List JAR files and their sizes, and check boot target contents
RUN find /app -name "*.jar" -type f -exec ls -lh {} \; && \
    echo "Contents of boot/target:" && \
    ls -la /app/boot/target/

# Runtime stage
FROM eclipse-temurin:21-jre-alpine AS runtime

# Create app user for security
RUN addgroup -g 1001 -S appuser && \
    adduser -u 1001 -S appuser -G appuser

# Set working directory
WORKDIR /app

# Copy any JAR file from boot target directory
COPY --from=builder /app/boot/target/*.jar ./

# Setup the application JAR and change ownership
RUN ls -lh ./*.jar && \
    mv ./*.jar app.jar && \
    ls -lh app.jar && \
    chown -R appuser:appuser /app

# Switch to non-root user
USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]