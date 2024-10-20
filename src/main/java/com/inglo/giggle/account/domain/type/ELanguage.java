package com.inglo.giggle.account.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ELanguage {
    KOREAN("한국어", "KOREAN"),
    ENGLISH("영어", "ENGLISH"),
    VIETNAMESE("베트남어", "VIETNAMESE"),
    TURKISH("터키어", "TURKISH"),
    RUSSIAN("러시아어", "RUSSIAN"),
    UZBEK("우즈베크어", "UZBEK"),
    JAPANESE("일본어", "JAPANESE"),
    CHINESE_SIMPLIFIED("중국어(간체)", "CHINESE_SIMPLIFIED"),
    CHINESE_TRADITIONAL("중국어(번체)", "CHINESE_TRADITIONAL")

    ;

    private final String krName;
    private final String enName;
}