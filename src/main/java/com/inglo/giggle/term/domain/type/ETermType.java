package com.inglo.giggle.term.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ETermType {
    PERSONAL_SERVICE_TERMS("개인 서비스 약관"),
    ENTERPRISE_SERVICE_TERMS("기업 서비스 약관"),
    LOCATION_BASED_TERMS("위치 기반 서비스 약관"),
    PRIVACY_POLICY("개인정보 처리방침");

    private final String description;

    public static ETermType fromString(String value) {
        return switch (value) {
            case "personal-service-terms" -> PERSONAL_SERVICE_TERMS;
            case "enterprise-service-terms" -> ENTERPRISE_SERVICE_TERMS;
            case "location-based-terms" -> LOCATION_BASED_TERMS;
            case "privacy-policy" -> PRIVACY_POLICY;
            default -> throw new IllegalArgumentException("약관 타입이 잘못되었습니다.");
        };
    }
}
