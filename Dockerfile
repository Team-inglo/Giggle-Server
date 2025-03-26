# Stage 1: 빌드 단계
FROM gradle:8.12-jdk17 AS build

ENV GRADLE_OPTS="-Dhttp.proxyHost=krmp-proxy.9rum.cc -Dhttp.proxyPort=3128 -Dhttps.proxyHost=krmp-proxy.9rum.cc -Dhttps.proxyPort=3128"

WORKDIR /app
# 모든 소스 코드를 컨테이너로 복사합니다.
COPY . .

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > ./gradle/gradle-wrapper.properties

RUN gradle wrapper

# gradlew에 실행 권한 부여
RUN chmod +x gradlew

# Gradle을 이용해 애플리케이션을 빌드합니다.
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: 실행 단계
FROM openjdk:17-slim
WORKDIR /app
# 빌드 단계에서 생성된 jar 파일을 최종 이미지로 복사합니다.
COPY --from=build /app/build/libs/application.jar ./app.jar

# 애플리케이션 실행 명령어
CMD ["java", "-Dhttp.proxyHost=krmp-proxy.9rum.cc", "-Dhttp.proxyPort=3128","-Dhttps.proxyHost=krmp-proxy.9rum.cc", "-Dhttps.proxyPort=3128", "-Dhttp.nonProxyHosts=kapi.kakao.com", "-Dhttps.nonProxyHosts=kapi.kakao.com", "-jar", "./app.jar", "--spring.profiles.active=krampoline"]