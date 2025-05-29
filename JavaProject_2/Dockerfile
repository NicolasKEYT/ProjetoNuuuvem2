# 1) Build stage usando Maven oficial
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia apenas o pom e o src para aproveitar cache
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

# 2) Runtime stage
FROM eclipse-temurin:21-jdk-slim
WORKDIR /app

# Aqui copie o JAR gerado no builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Força binding em 0.0.0.0 para aceitar conexões externas
ENTRYPOINT ["java","-jar","app.jar","--server.address=0.0.0.0","--server.port=8080"]
