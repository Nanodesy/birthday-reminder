FROM openjdk:17-jdk-slim
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

WORKDIR /app
COPY ./build/libs/*.jar ./app.jar

ENTRYPOINT java -jar app.jar