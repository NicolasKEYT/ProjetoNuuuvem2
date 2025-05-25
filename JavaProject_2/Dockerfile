# Dockerfile

FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn ./.mvn
COPY pom.xml .

# Copy the source code
COPY src ./src

# Install Maven
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Package the application
RUN ./mvnw package -DskipTests

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/JavaProject-0.0.1-SNAPSHOT.jar"]