## FASE 1: BUILDER
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ src/

RUN mvn clean package -DskipTests

## FASE 2: RUNTIME LIGERO Y SEGURO
FROM eclipse-temurin:21-jre-alpine AS runtime

RUN apk update && apk add --no-cache shadow curl && rm -rf /var/cache/apk/*

RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app
RUN chown spring:spring /app 
USER spring

COPY --from=builder /app/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=dev
ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]