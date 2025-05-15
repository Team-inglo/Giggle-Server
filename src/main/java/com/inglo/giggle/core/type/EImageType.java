package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EImageType {
    USER_PROFILE_IMG("유저 프로필", "USER_PROFILE"),
    OWNER_PROFILE_IMG("고용주 프로필", "OWNER_PROFILE"),
    COMPANY_IMG("회사 이미지", "COMPANY_IMAGE"),
    BANNER_IMG("배너 이미지", "BANNER_IMAGE"),
    CAREER_IMG("커리어 이미지", "CAREER_IMAGE"),

    ;

    private final String krName;
    private final String enName;

    public static EImageType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "USER_PROFILE" -> USER_PROFILE_IMG;
            case "OWNER_PROFILE" -> OWNER_PROFILE_IMG;
            case "COMPANY_IMAGE" -> COMPANY_IMG;
            case "BANNER_IMAGE" -> BANNER_IMG;
            case "CAREER_IMAGE" -> CAREER_IMG;
            default -> throw new IllegalArgumentException("이미지 타입이 잘못되었습니다.");
        };
    }
}
