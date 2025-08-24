# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests clean package

# Runtime stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app will run on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
