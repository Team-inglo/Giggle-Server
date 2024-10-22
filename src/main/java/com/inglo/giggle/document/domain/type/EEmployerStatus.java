package com.inglo.giggle.document.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EEmployerStatus {
    TEMPORARY_SAVE("임시저장", "TEMPORARY_SAVE"),
    SUBMITTED("제출", "SUBMITTED"),
    REWRITING("재작성", "REWRITING"),
    CONFIRMATION("확인", "CONFIRMATION")
    ;
    private final String krName;
    private final String enName;

    public static EEmployerStatus fromString(String value) {
        return switch (value.toUpperCase()) {
            case "TEMPORARY_SAVE" -> TEMPORARY_SAVE;
            case "SUBMITTED" -> SUBMITTED;
            case "REWRITING" -> REWRITING;
            case "CONFIRMATION" -> CONFIRMATION;
            default -> throw new IllegalArgumentException("고용주 상태가 잘못되었습니다.");
        };
    }
}
