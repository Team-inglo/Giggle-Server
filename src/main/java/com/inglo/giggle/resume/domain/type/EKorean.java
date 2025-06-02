package com.inglo.giggle.resume.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EKorean {

    NONE("전혀 불가능", "NONE"),
    BASIC("기본적인 해석 가능", "BASIC"),
    COMMUNICATE("의사소통 가능", "COMMUNICATE"),
    WORKING("업무 능숙", "WORKING"),
    ADVANCED("고급 구사 가능", "ADVANCED"),
    NATIVE("모국어 수준", "NATIVE");

    private final String krName;
    private final String enName;

    public static EKorean fromString(String value) {
        return switch (value.toUpperCase()) {
            case "NONE" -> NONE;
            case "BASIC" -> BASIC;
            case "COMMUNICATE" -> COMMUNICATE;
            case "WORKING" -> WORKING;
            case "ADVANCED" -> ADVANCED;
            case "NATIVE" -> NATIVE;
            default -> throw new IllegalArgumentException("한국어 능력이 잘못되었습니다: " + value);
        };
    }
}
