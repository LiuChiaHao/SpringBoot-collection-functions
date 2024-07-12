# Use the official image as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the executable JAR file from the local filesystem to the image
COPY target/Collection-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8443

# Command to run the executable JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
