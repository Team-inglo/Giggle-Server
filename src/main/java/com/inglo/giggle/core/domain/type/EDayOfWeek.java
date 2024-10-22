package com.inglo.giggle.core.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EDayOfWeek {
    WEEKDAYS("평일", "WEEKDAYS"),
    WEEKEND("주말", "WEEKEND"),
    MONDAY("월요일", "MONDAY"),
    TUESDAY("화요일", "TUESDAY"),
    WEDNESDAY("수요일", "WEDNESDAY"),
    WENSDAY("목요일", "WENSDAY"),
    FRIDAY("금요일", "FRIDAY"),
    SATURDAY("토요일", "SATURDAY"),
    SUNDAY("일요일", "SUNDAY"),
    NEGOCIABLE("협의가능", "NEGOCIABLE")
    ;

    private final String krName;
    private final String enName;

    public static EDayOfWeek fromString(String value) {
        return switch (value.toUpperCase()) {
            case "WEEKDAYS" -> WEEKDAYS;
            case "WEEKEND" -> WEEKEND;
            case "MONDAY" -> MONDAY;
            case "TUESDAY" -> TUESDAY;
            case "WEDNESDAY" -> WEDNESDAY;
            case "WENSDAY" -> WENSDAY;
            case "FRIDAY" -> FRIDAY;
            case "SATURDAY" -> SATURDAY;
            case "SUNDAY" -> SUNDAY;
            case "NEGOCIABLE" -> NEGOCIABLE;
            default -> throw new IllegalArgumentException("요일이 잘못되었습니다.");
        };
    }
}
