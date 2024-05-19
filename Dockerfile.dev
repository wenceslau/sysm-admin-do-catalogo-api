# build stage
# Use an official Maven image as the base
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and the source code to the container
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK image as the base for the final image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/infrastructure/build/application.jar ./application.jar

# Specify the command to run the application
CMD java -jar application.jar
