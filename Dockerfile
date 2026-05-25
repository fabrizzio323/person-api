FROM eclipse-temurin:21-jdk
ARG JAR_FILE=/target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]