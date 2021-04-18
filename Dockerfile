FROM openjdk:11-jdk-slim

WORKDIR /app

COPY ./ /app

ENTRYPOINT [ "./mvnw", "spring-boot:run" ]