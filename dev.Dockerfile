FROM openjdk:17-slim

WORKDIR /app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/application.jar ./app.jar

CMD ["java","-jar","./app.jar","--spring.profiles.active=dev"]