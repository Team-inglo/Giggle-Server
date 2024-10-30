package com.inglo.giggle.posting.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EWorkPeriod {
    ONE_DAY("1일", "ONE_DAY"),
    LESS_THAN_ONE_WEEK("1주 미만", "LESS_THAN_ONE_WEEK"),
    ONE_WEEK_TO_ONE_MONTH("1주일 ~ 1개월", "ONE_WEEK_TO_ONE_MONTH"),
    ONE_MONTH_TO_THREE_MONTHS("1개월 ~ 3개월", "ONE_MONTH_TO_THREE_MONTHS"),
    THREE_MONTHS_TO_SIX_MONTHS("3개월 ~ 6개월", "THREE_MONTHS_TO_SIX_MONTHS"),
    SIX_MONTHS_TO_ONE_YEAR("6개월 ~ 1년", "SIX_MONTHS_TO_ONE_YEAR"),
    MORE_THAN_ONE_YEAR("1년 이상", "MORE_THAN_ONE_YEAR")
    ;

    private final String krName;
    private final String enName;

    public static EWorkPeriod fromString(String value) {
        return switch (value.toUpperCase()) {
            case "ONE_DAY" -> ONE_DAY;
            case "LESS_THAN_ONE_WEEK" -> LESS_THAN_ONE_WEEK;
            case "ONE_WEEK_TO_ONE_MONTH" -> ONE_WEEK_TO_ONE_MONTH;
            case "ONE_MONTH_TO_THREE_MONTHS" -> ONE_MONTH_TO_THREE_MONTHS;
            case "THREE_MONTHS_TO_SIX_MONTHS" -> THREE_MONTHS_TO_SIX_MONTHS;
            case "SIX_MONTHS_TO_ONE_YEAR" -> SIX_MONTHS_TO_ONE_YEAR;
            case "MORE_THAN_ONE_YEAR" -> MORE_THAN_ONE_YEAR;
            default -> throw new IllegalArgumentException("근무기간이 잘못되었습니다.");
        };
    }
}
