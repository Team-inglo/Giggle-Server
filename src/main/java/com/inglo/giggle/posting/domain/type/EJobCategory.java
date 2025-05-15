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
    BUSINESS_MANAGEMENT("사업관리", "BUSINESS_MANAGEMENT"),
    OFFICE_ACCOUNTING("경영/회계/사무", "OFFICE_ACCOUNTING"),
    FINANCE_INSURANCE("금융/보험", "FINANCE_INSURANCE"),
    EDUCATION_RESEARCH("교육/자연/사회과학", "EDUCATION_RESEARCH"),
    LAW_PUBLIC_SAFETY("법률/경찰/소방/교도/국방", "LAW_PUBLIC_SAFETY"),
    HEALTHCARE("보건/의료", "HEALTHCARE"),
    SOCIAL_WORK_RELIGION("사회복지/종교", "SOCIAL_WORK_RELIGION"),
    ARTS_MEDIA("문화/예술/디자인/방송", "ARTS_MEDIA"),
    DRIVING_DELIVERY("운전/운송", "DRIVING_DELIVERY"),
    SALES("영업판매", "SALES"),
    SECURITY_CLEANING("경비/청소", "SECURITY_CLEANING"),
    HOSPITALITY_LEISURE("이용/숙박/여행/오락/스포츠", "HOSPITALITY_LEISURE"),
    FOOD_SERVICE("음식서비스", "FOOD_SERVICE"),
    CONSTRUCTION("건설", "CONSTRUCTION"),
    MACHINERY("기계", "MACHINERY"),
    MATERIALS("재료", "MATERIALS"),
    CHEMISTRY_BIO("화학/바이오", "CHEMISTRY_BIO"),
    TEXTILES_FASHION("섬유/의복", "TEXTILES_FASHION"),
    ELECTRONICS("전기/전자", "ELECTRONICS"),
    IT_TELECOMMUNICATIONS("정보통신", "IT_TELECOMMUNICATIONS"),
    FOOD_PROCESSING("식품가공", "FOOD_PROCESSING"),
    PRINTING_CRAFT("인쇄/목재/가구/공예", "PRINTING_CRAFT"),
    ENVIRONMENT_SAFETY("환경/에너지/안전", "ENVIRONMENT_SAFETY"),
    AGRICULTURE_FISHERIES("농립어업", "AGRICULTURE_FISHERIES");
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
            // V2
            case "BUSINESS_MANAGEMENT" -> BUSINESS_MANAGEMENT;
            case "OFFICE_ACCOUNTING" -> OFFICE_ACCOUNTING;
            case "FINANCE_INSURANCE" -> FINANCE_INSURANCE;
            case "EDUCATION_RESEARCH" -> EDUCATION_RESEARCH;
            case "LAW_PUBLIC_SAFETY" -> LAW_PUBLIC_SAFETY;
            case "HEALTHCARE" -> HEALTHCARE;
            case "SOCIAL_WORK_RELIGION" -> SOCIAL_WORK_RELIGION;
            case "ARTS_MEDIA" -> ARTS_MEDIA;
            case "DRIVING_DELIVERY" -> DRIVING_DELIVERY;
            case "SALES" -> SALES;
            case "SECURITY_CLEANING" -> SECURITY_CLEANING;
            case "HOSPITALITY_LEISURE" -> HOSPITALITY_LEISURE;
            case "FOOD_SERVICE" -> FOOD_SERVICE;
            case "CONSTRUCTION" -> CONSTRUCTION;
            case "MACHINERY" -> MACHINERY;
            case "MATERIALS" -> MATERIALS;
            case "CHEMISTRY_BIO" -> CHEMISTRY_BIO;
            case "TEXTILES_FASHION" -> TEXTILES_FASHION;
            case "ELECTRONICS" -> ELECTRONICS;
            case "IT_TELECOMMUNICATIONS" -> IT_TELECOMMUNICATIONS;
            case "FOOD_PROCESSING" -> FOOD_PROCESSING;
            case "PRINTING_CRAFT" -> PRINTING_CRAFT;
            case "ENVIRONMENT_SAFETY" -> ENVIRONMENT_SAFETY;
            case "AGRICULTURE_FISHERIES" -> AGRICULTURE_FISHERIES;
            default -> throw new IllegalArgumentException("직종이 잘못되었습니다.");
        };
    }
}
