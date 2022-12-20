FROM openjdk:18-jdk-alpine
COPY target/Users-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "/app.jar"]