# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim AS build
WORKDIR /home/gradle/project
COPY . .
RUN ./gradlew clean build --no-daemon

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jre-slim
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]