# Stage 1: Build
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copy gradle files
COPY build.gradle settings.gradle gradlew gradlew.bat ./
COPY gradle/ ./gradle/

# Copy source code
COPY src/ ./src/

# Build the application
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /app/build/libs/app.jar app.jar

# Set environment variables
ENV SERVER_PORT=8080
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Start application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
