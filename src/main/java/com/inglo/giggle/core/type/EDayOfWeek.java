package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EDayOfWeek {
    MONDAY("월요일", "MONDAY", 0),
    TUESDAY("화요일", "TUESDAY", 1),
    WEDNESDAY("수요일", "WEDNESDAY", 2),
    THURSDAY("목요일", "THURSDAY", 3),
    FRIDAY("금요일", "FRIDAY", 4),
    SATURDAY("토요일", "SATURDAY", 5),
    SUNDAY("일요일", "SUNDAY", 6),
    NEGOTIABLE("협의가능", "NEGOTIABLE", 7)
    ;

    private final String krName;
    private final String enName;
    private final Integer order;


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
