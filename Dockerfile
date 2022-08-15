FROM gradle:7.4.2-jdk8-alpine AS build
WORKDIR /
ADD build/libs/shortening-0.0.1-SNAPSHOT.jar //
EXPOSE 8080
ENTRYPOINT [ "java", "-Dspring.profiles.active=docker", "-jar", "/shortening-0.0.1-SNAPSHOT.jar"]