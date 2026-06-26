# ---------- Build Stage ----------
FROM eclipse-temurin:21 AS build

RUN apt-get update && apt-get install -y curl netcat-openbsd
WORKDIR /app

# Copy Maven files first
COPY pom.xml .
COPY mvnw .
COPY .mvn ./.mvn

# Download dependencies
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]