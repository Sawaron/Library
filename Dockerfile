# Stage 1 — build
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

COPY src ./src

RUN mvn -q -DskipTests package

# Stage 2 — run
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]