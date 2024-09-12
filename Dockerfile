#FROM openjdk:17-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# Use an official Maven image as the base image
FROM maven:3.8.4-openjdk-17-slim AS build
# Set the working directory in the container
WORKDIR /serenity-broker
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM openjdk:17-jre-slim
# Set the working directory in the container
ARG JAR_FILE=target/*.jar
# Copy the built JAR file from the previous stage to the container
COPY ${JAR_FILE} app.jar
# Set the command to run the application
CMD ["java", "-jar", "app.jar"]