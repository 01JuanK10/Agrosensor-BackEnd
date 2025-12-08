## FASE 1: BUILDER
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ src/

RUN mvn clean package -DskipTests

## FASE 2: RUNTIME
FROM eclipse-temurin:21-jre-alpine AS runtime

RUN apk update && apk add --no-cache shadow curl && rm -rf /var/cache/apk/*

RUN groupadd -r spring && useradd -r -g spring spring

WORKDIR /app
RUN chown spring:spring /app 
USER spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar app.jar"]
