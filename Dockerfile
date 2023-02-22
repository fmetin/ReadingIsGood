# Base image
FROM openjdk:17-jdk-alpine

# Copy application jar file and other files
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app.jar"]
