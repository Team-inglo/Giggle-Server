package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ENotificationType {

    USER("USER", "유저", "유저에게 보내는 알림"),
    OWNER("OWNER", "고용주", "고용주에게 보내는 알림")
    ;

    private final String enName;
    private final String krName;
    private final String description;

    public static ENotificationType findByEnName(String enName) {
        for (ENotificationType eNotificationType : values()) {
            if (eNotificationType.getEnName().equals(enName)) {
                return eNotificationType;
            }
        }
        return null;
    }
}
