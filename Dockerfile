# Stage 1: Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:resolve

# Copy the rest of the source code and build the application JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage (using lightweight JRE)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy only the compiled JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]