# Use a Maven image to build the Spring Boot app
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only what is needed to download dependencies/caches
COPY pom.xml .
COPY src ./src

# Build the application (skip tests to speed up)
RUN mvn -B -DskipTests package

# Runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]