FROM krmp-d2hub-idock.9rum.cc/goorm/openjdk:17-jdk-slim

WORKDIR /app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/application.jar ./app.jar

CMD ["java","-jar","./app.jar","--spring.profiles.active=krampoline"]