# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-slim AS build
WORKDIR /home/gradle/project
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean build --no-daemon -x test

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]