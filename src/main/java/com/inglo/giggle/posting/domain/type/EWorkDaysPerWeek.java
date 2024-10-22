package com.inglo.giggle.posting.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EWorkDaysPerWeek {

    ONE_DAY("1일", "ONE_DAY"),
    TWO_DAYS("2일", "TWO_DAYS"),
    THREE_DAYS("3일", "THREE_DAYS"),
    FOUR_DAYS("4일", "FOUR_DAYS"),
    FIVE_DAYS("5일", "FIVE_DAYS"),
    SIX_DAYS("6일", "SIX_DAYS"),
    ALL("매일", "ALL")
    ;
    private final String krName;
    private final String enName;

    public static EWorkDaysPerWeek fromString(String value) {
        return switch (value.toUpperCase()) {
            case "ONE_DAY" -> ONE_DAY;
            case "TWO_DAYS" -> TWO_DAYS;
            case "THREE_DAYS" -> THREE_DAYS;
            case "FOUR_DAYS" -> FOUR_DAYS;
            case "FIVE_DAYS" -> FIVE_DAYS;
            case "SIX_DAYS" -> SIX_DAYS;
            case "ALL" -> ALL;
            default -> throw new IllegalArgumentException("근무일수가 잘못되었습니다.");
        };
    }
}
