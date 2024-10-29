package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EDayOfWeek {
    MONDAY("월요일", "MONDAY"),
    TUESDAY("화요일", "TUESDAY"),
    WEDNESDAY("수요일", "WEDNESDAY"),
    THURSDAY("목요일", "THURSDAY"),
    FRIDAY("금요일", "FRIDAY"),
    SATURDAY("토요일", "SATURDAY"),
    SUNDAY("일요일", "SUNDAY"),
    NEGOTIABLE("협의가능", "NEGOTIABLE")
    ;

    private final String krName;
    private final String enName;

    public static EDayOfWeek fromString(String value) {
        return switch (value.toUpperCase()) {
            case "MONDAY" -> MONDAY;
            case "TUESDAY" -> TUESDAY;
            case "WEDNESDAY" -> WEDNESDAY;
            case "THURSDAY" -> THURSDAY;
            case "FRIDAY" -> FRIDAY;
            case "SATURDAY" -> SATURDAY;
            case "SUNDAY" -> SUNDAY;
            case "NEGOTIABLE" -> NEGOTIABLE;
            default -> throw new IllegalArgumentException("요일이 잘못되었습니다.");
        };
    }
}
