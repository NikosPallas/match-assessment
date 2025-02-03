FROM openjdk:17-jdk-slim
WORKDIR JAR_FILE=target/*.jar
COPY target/MatchAssessment-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]