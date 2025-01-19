# Stage 1: Build the application
FROM maven:3.8.6-openjdk-11 AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package

# Stage 2: Run the application
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the packaged WAR file from the builder stage
COPY --from=builder /app/target/todo-1.0-SNAPSHOT.war /app/todo-1.0-SNAPSHOT.war

# Specify the command to
CMD ["java", "-jar", "/app/todo-1.0-SNAPSHOT.war"]