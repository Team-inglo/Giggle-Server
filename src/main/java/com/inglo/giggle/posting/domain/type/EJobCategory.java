package com.inglo.giggle.posting.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EJobCategory {
    GENERAL_INTERPRETATION_TRANSLATION("일반통역/번역", "GENERAL_INTERPRETATION_TRANSLATION"),
    FOOD_SERVICE_ASSISTANT("음식업보조", "FOOD_SERVICE_ASSISTANT"),
    GENERAL_ADMINISTRATIVE_SUPPORT("일반사무보조", "GENERAL_ADMINISTRATIVE_SUPPORT"),
    ENGLISH_KIDS_CAFE("영어키즈카페", "ENGLISH_KIDS_CAFE"),
    GENERAL_CAFE("일반카페", "GENERAL_CAFE"),
    PART_TIME_WORK("시간제 & 계절제 근로", "PART_TIME_WORK"),
    TOUR_GUIDE_AND_DUTY_FREE_ASSISTANT("관광안재보조및면세점판매보조", "TOUR_GUIDE_AND_DUTY_FREE_ASSISTANT"),
    MANUFACTURING("제조업", "MANUFACTURING"),

    // V2
    FOOD_SERVICE("외식/음료", "FOOD_SERVICE"),
    STORE_MANAGEMENT("매장관리/판매", "STORE_MANAGEMENT"),
    SERVICE("서비스", "SERVICE"),
    OFFICE_WORK("사무직", "OFFICE_WORK"),
    CUSTOMER_SALES("고객상담/리서치/영업", "CUSTOMER_SALES"),
    PRODUCTION_CONSTRUCTION("생산/건설/노무", "PRODUCTION_CONSTRUCTION"),
    IT_TECH("IT/기술", "IT_TECH"),
    DESIGN("디자인", "DESIGN"),
    MEDIA("미디어", "MEDIA"),
    DRIVING_DELIVERY("운전/배달", "DRIVING_DELIVERY"),
    HEALTHCARE_RESEARCH("병원/간호/연구", "HEALTHCARE_RESEARCH"),
    EDUCATION("교육/강사", "EDUCATION")

    ;


    private final String krName;
    private final String enName;

    public static EJobCategory fromString(String value) {
        for (EJobCategory category : EJobCategory.values()) {
            if (category.getEnName().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("직종이 잘못되었습니다: " + value);
    }
}
