FROM openjdk:21-slim

WORKDIR /app

RUN mkdir -p certs

# jar file version to match the pom version
ARG JAR_VERSION=0.0.1

COPY target/channel-request-processor-${JAR_VERSION}.jar /app/app.jar
COPY src/main/resources/application.yml /app/application.yml
COPY certs/sample.p12 /app/certs/sample.p12

CMD ["java", "-jar", "app.jar"]

