package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EMajor {

    BUSINESS_MANAGEMENT("사업관리", "Business Management"),
    OFFICE_ACCOUNTING("경영/회계/사무", "Office & Accounting"),
    FINANCE_INSURANCE("금융/보험", "Finance & Insurance"),
    EDUCATION_RESEARCH("교육/자연/사회과학", "Education & Research"),
    LAW_PUBLIC_SAFETY("법률/경찰/소방/교도/국방", "Law & Public Safety"),
    HEALTHCARE("보건/의료", "Healthcare"),
    SOCIAL_WORK_RELIGION("사회복지/종교", "Social Work & Religion"),
    ARTS_MEDIA("문화/예술/디자인/방송", "Arts & Media"),
    DRIVING_DELIVERY("운전/운송", "Driving & Delivery"),
    SALES("영업판매", "Sales"),
    SECURITY_CLEANING("경비/청소", "Security & Cleaning"),
    HOSPITALITY_LEISURE("이용/숙박/여행/오락/스포츠", "Hospitality & Leisure"),
    FOOD_SERVICE("음식서비스", "Food Service"),
    CONSTRUCTION("건설", "Construction"),
    MACHINERY("기계", "Machinery"),
    MATERIALS("재료", "Materials"),
    CHEMISTRY_BIO("화학/바이오", "Chemistry & Bio"),
    TEXTILES_FASHION("섬유/의복", "Textiles & Fashion"),
    ELECTRONICS("전기/전자", "Electronics"),
    IT_TELECOMMUNICATIONS("정보통신", "IT & Telecommunications"),
    FOOD_PROCESSING("식품가공", "Food Processing"),
    PRINTING_CRAFT("인쇄/목재/가구/공예", "Printing & Craft"),
    ENVIRONMENT_SAFETY("환경/에너지/안전", "Environment & Safety"),
    AGRICULTURE_FISHERIES("농립어업", "Agriculture & Fisheries");

    ;

    private final String krName;
    private final String enName;

    public static EMajor fromString(String value) {
        for (EMajor major : EMajor.values()) {
            if (major.name().equalsIgnoreCase(value.toUpperCase())) {
                return major;
            }
        }
        throw new IllegalArgumentException("전공이 잘못되었습니다.");
    }
}
