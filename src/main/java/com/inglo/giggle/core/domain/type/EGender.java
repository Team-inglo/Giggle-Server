package com.inglo.giggle.core.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EGender {
    MALE("남성", "MALE"),
    FEMALE("여성", "FEMALE"),
    NONE("성별 무관", "NONE");

    private final String krName;
    private final String enName;
}
