package com.inglo.giggle.document.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EInsurance {
    EMPLOYMENT_INSURANCE("고용보험", "EMPLOYMENT_INSURANCE"),
    WORKERS_COMPENSATION_INSURANCE("산재보험", "WORKERS_COMPENSATION_INSURANCE"),
    NATIONAL_PENSION("국민연금", "NATIONAL_PENSION"),
    HEALTH_INSURANCE("건강보험", "HEALTH_INSURANCE")
    ;
    private final String krName;
    private final String enName;

    public static EInsurance fromString(String value) {
        return switch (value.toUpperCase()) {
            case "EMPLOYMENT_INSURANCE" -> EMPLOYMENT_INSURANCE;
            case "WORKERS_COMPENSATION_INSURANCE" -> WORKERS_COMPENSATION_INSURANCE;
            case "NATIONAL_PENSION" -> NATIONAL_PENSION;
            case "HEALTH_INSURANCE" -> HEALTH_INSURANCE;
            default -> throw new IllegalArgumentException("보험종류가 잘못되었습니다.");
        };
    }
}