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
    MANUFACTURING("제조업", "MANUFACTURING")
    ;


    private final String krName;
    private final String enName;

    public static EJobCategory fromString(String value) {
        return switch (value.toUpperCase()) {
            case "GENERAL_INTERPRETATION_TRANSLATION" -> GENERAL_INTERPRETATION_TRANSLATION;
            case "FOOD_SERVICE_ASSISTANT" -> FOOD_SERVICE_ASSISTANT;
            case "GENERAL_ADMINISTRATIVE_SUPPORT" -> GENERAL_ADMINISTRATIVE_SUPPORT;
            case "ENGLISH_KIDS_CAFE" -> ENGLISH_KIDS_CAFE;
            case "GENERAL_CAFE" -> GENERAL_CAFE;
            case "PART_TIME_WORK" -> PART_TIME_WORK;
            case "TOUR_GUIDE_AND_DUTY_FREE_ASSISTANT" -> TOUR_GUIDE_AND_DUTY_FREE_ASSISTANT;
            case "MANUFACTURING" -> MANUFACTURING;
            default -> throw new IllegalArgumentException("직종이 잘못되었습니다.");
        };
    }
}
