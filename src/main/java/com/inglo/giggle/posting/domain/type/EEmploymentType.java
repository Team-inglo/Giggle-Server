package com.inglo.giggle.posting.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EEmploymentType {
    PARTTIME("아르바이트", "PARTTIME"),
    INTERNSHIP("인턴", "INTERNSHIP")
    ;

    private final String krName;
    private final String enName;

    public static EEmploymentType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "PARTTIME" -> PARTTIME;
            case "INTERNSHIP" -> INTERNSHIP;
            default -> throw new IllegalArgumentException("고용형태가 잘못되었습니다.");
        };
    }
}
