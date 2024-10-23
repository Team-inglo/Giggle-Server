package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EGender {
    MALE("남성", "MALE"),
    FEMALE("여성", "FEMALE"),
    NONE("성별 무관", "NONE");

    private final String krName;
    private final String enName;

    public static EGender fromString(String value) {
        return switch (value.toUpperCase()) {
            case "MALE" -> MALE;
            case "FEMALE" -> FEMALE;
            case "NONE" -> NONE;
            default -> throw new IllegalArgumentException("성별이 잘못되었습니다.");
        };
    }
}
