# ==========================================
# Stage 1: Build & Run JUnit Tests
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Cache Maven dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy project source code
COPY src ./src

# Compile source code and execute all JUnit test cases
RUN mvn clean package -B

# ==========================================
# Stage 2: Lightweight Runtime Environment
# ==========================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-privileged user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy executable jar from builder stage
COPY --from=builder /app/target/student-grade-calculator-1.0.0.jar app.jar

# Run the Student Grade Calculator application
ENTRYPOINT ["java", "-jar", "app.jar"]
