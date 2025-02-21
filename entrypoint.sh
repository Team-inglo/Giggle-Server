#!/bin/sh
# HTTP 프록시를 통해 SMTP 터널 생성
proxytunnel -p krmp-proxy.9rum.cc:3128 -d smtp.gmail.com:587 -a 2525 &

# 애플리케이션 실행
exec java -Dhttp.proxyHost=krmp-proxy.9rum.cc \
         -Dhttp.proxyPort=3128 \
         -Dhttps.proxyHost=krmp-proxy.9rum.cc \
         -Dhttps.proxyPort=3128 \
         -jar /app/app.jar \
         --spring.profiles.active=krampoline
