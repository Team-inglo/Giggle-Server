package com.inglo.giggle.security.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ESecurityRole {

    USER("유학생", "USER", "ROLE_USER"),
    OWNER("점주", "OWNER", "ROLE_OWNER"),
    ADMIN("관리자", "ADMIN", "ROLE_ADMIN"),
    GUEST("손님", "GUEST", "ROLE_GUEST")
    ;

    private final String koName;
    private final String enName;
    private final String securityName;

    public static ESecurityRole fromString(String value) {
        return switch (value.toUpperCase()) {
            case "USER" -> USER;
            case "OWNER" -> OWNER;
            case "ADMIN" -> ADMIN;
            case "GUEST" -> GUEST;
            default -> throw new IllegalArgumentException("Security Role이 잘못되었습니다.");
        };
    }
}
