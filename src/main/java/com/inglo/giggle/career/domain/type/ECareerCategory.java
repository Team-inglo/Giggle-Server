package com.inglo.giggle.career.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ECareerCategory {
    ACTIVITY("대외활동", "Activity"),
    PROGRAM("교육", "Program"),
    CONTEST("공모전", "Contest"),
    CLUB("동아리", "Club");

    private final String krName;
    private final String enName;

    public static ECareerCategory fromString(String value) {
        return switch (value.toUpperCase()) {
            case "ACTIVITY" -> ACTIVITY;
            case "PROGRAM" -> PROGRAM;
            case "CONTEST" -> CONTEST;
            case "CLUB" -> CLUB;
            default -> throw new IllegalArgumentException("잘못된 커리어 타입입니다.");
        };
    }
}
