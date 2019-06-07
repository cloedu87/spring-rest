FROM openjdk:11.0.3-jdk-slim-stretch
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]