package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EDayOfWeek {
    WEEKDAYS("평일", "WEEKDAYS", 0),
    WEEKEND("주말", "WEEKEND", 1),
    MONDAY("월", "MONDAY", 2),
    TUESDAY("화", "TUESDAY", 3),
    WEDNESDAY("수", "WEDNESDAY", 4),
    THURSDAY("목", "THURSDAY", 5),
    FRIDAY("금", "FRIDAY", 6),
    SATURDAY("토", "SATURDAY", 7),
    SUNDAY("일", "SUNDAY", 8),
    NEGOCIABLE("협의가능", "NEGOCIABLE", 9)
    ;

    private final String krName;
    private final String enName;
    private final Integer order;

    public static EDayOfWeek fromString(String value) {
        return switch (value.toUpperCase()) {
            case "WEEKDAYS" -> WEEKDAYS;
            case "WEEKEND" -> WEEKEND;
            case "MONDAY" -> MONDAY;
            case "TUESDAY" -> TUESDAY;
            case "WEDNESDAY" -> WEDNESDAY;
            case "THURSDAY" -> THURSDAY;
            case "FRIDAY" -> FRIDAY;
            case "SATURDAY" -> SATURDAY;
            case "SUNDAY" -> SUNDAY;
            case "NEGOCIABLE" -> NEGOCIABLE;
            default -> throw new IllegalArgumentException("요일이 잘못되었습니다.");
        };
    }
}
