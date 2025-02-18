# Stage 1: 빌드 단계
FROM krmp-d2hub-idock.9rum.cc/goorm/gradle:8.8-jdk17 AS build
WORKDIR /app
# 모든 소스 코드를 컨테이너로 복사합니다.
COPY . .

# gradlew에 실행 권한 부여
RUN chmod +x gradlew

# Gradle을 이용해 애플리케이션을 빌드합니다.
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: 실행 단계
FROM krmp-d2hub-idock.9rum.cc/goorm/openjdk:17-jdk-slim
WORKDIR /app
# 빌드 단계에서 생성된 jar 파일을 최종 이미지로 복사합니다.
COPY --from=build /app/build/libs/application.jar ./app.jar

# 애플리케이션 실행 명령어
CMD ["java", "-jar", "./app.jar", "--spring.profiles.active=krampoline"]
