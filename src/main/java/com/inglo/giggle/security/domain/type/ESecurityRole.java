package com.inglo.giggle.security.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ESecurityRole {

    USER("유학생", "USER", "ROLE_USER"),
    OWNER("점주", "OWNER", "ROLE_OWNER")

    ;

    private final String koName;
    private final String enName;
    private final String securityName;

    public static ESecurityRole fromString(String value) {
        return switch (value.toUpperCase()) {
            case "USER" -> USER;
            case "OWNER" -> OWNER;
            default -> throw new IllegalArgumentException("Security Role이 잘못되었습니다.");
        };
    }
}
