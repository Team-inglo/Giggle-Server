package com.inglo.giggle.document.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EEmployeeStatus {
    TEMPORARY_SAVE("임시저장", "TEMPORARY_SAVE"),
    SUBMITTED("제출", "SUBMITTED"),
    BEFORE_CONFIRMATION("확인전", "BEFORE_CONFIRMATION"),
    REQUEST("요청", "REQUEST"),
    CONFIRMATION("확인", "CONFIRMATION")
    ;
    private final String krName;
    private final String enName;

    public static EEmployeeStatus fromString(String value) {
        return switch (value.toUpperCase()) {
            case "TEMPORARY_SAVE" -> TEMPORARY_SAVE;
            case "SUBMITTED" -> SUBMITTED;
            case "BEFORE_CONFIRMATION" -> BEFORE_CONFIRMATION;
            case "REQUEST" -> REQUEST;
            case "CONFIRMATION" -> CONFIRMATION;
            default -> throw new IllegalArgumentException("근로자 상태가 잘못되었습니다.");
        };
    }
}
