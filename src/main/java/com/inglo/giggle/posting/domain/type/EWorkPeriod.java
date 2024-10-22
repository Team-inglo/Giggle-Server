package com.inglo.giggle.posting.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EWorkPeriod {
    ONE_DAY("1일", "ONE_DAY"),
    LESS_THAN_ONE_WEEK("1주 미만", "LESS_THAN_A_WEEK"),
    ONE_WEEK_TO_ONE_MONTH("1주일 ~ 1개월", "1_WEEK_TO_1_MONTH"),
    ONE_MONTH_TO_THREE_MONTHS("1개월 ~ 3개월", "1_MONTH_TO_3_MONTHS"),
    THREE_MONTHS_TO_SIX_MONTHS("3개월 ~ 6개월", "3_MONTHS_TO_6_MONTHS"),
    SIX_MONTHS_TO_ONE_YEAR("6개월 ~ 1년", "6_MONTHS_TO_1_YEAR"),
    MORE_THAN_ONE_YEAR("1년 이상", "MORE_THAN_1_YEAR")
    ;

    private final String krName;
    private final String enName;
}
