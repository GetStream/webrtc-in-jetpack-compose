# Use the official Gradle 6.6.1 image with JDK 17 as the build environment
FROM gradle:6.6.1-jdk11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Set JVM compatibility to 17 in the build process
RUN gradle clean build -Dorg.gradle.java.home=/opt/java/openjdk -x test --no-daemon

# Use the official OpenJDK 17 runtime as the runtime environment
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR from the build environment
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Set environment variables if needed (e.g., for ports)
ENV PORT=8080

# Run the application
CMD ["java", "-jar", "app.jar"]
