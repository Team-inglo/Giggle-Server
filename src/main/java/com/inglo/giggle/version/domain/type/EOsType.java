package com.inglo.giggle.version.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EOsType {

    IOS("IOS", "애플"),
    ANDROID("ANDROID", "안드로이드")

    ;

    private final String enName;
    private final String krName;
}
