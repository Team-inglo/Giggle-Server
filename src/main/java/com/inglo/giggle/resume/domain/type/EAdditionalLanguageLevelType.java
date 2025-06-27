package com.inglo.giggle.resume.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EAdditionalLanguageLevelType {

    NOT_ABLE_TO_USE_AT_ALL("전혀 사용할 수 없음", "NOT_ABLE_TO_USE_AT_ALL"),
    CAN_UNDERSTAND_BASIC_WORDS("기초 단어 이해 가능", "CAN_UNDERSTAND_BASIC_WORDS"),
    BASIC_CONVERSATION_POSSIBLE("기본 대화 가능", "BASIC_CONVERSATION_POSSIBLE"),
    COMFORTABLE_FOR_WORK("업무에 무리 없음", "COMFORTABLE_FOR_WORK"),
    FLUENT("유창함", "FLUENT"),
    NATIVE_OR_NEAR_NATIVE("모국어 수준", "NATIVE_OR_NEAR_NATIVE");

    private final String krName;
    private final String enName;

    public static EAdditionalLanguageLevelType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "NOT_ABLE_TO_USE_AT_ALL" -> NOT_ABLE_TO_USE_AT_ALL;
            case "CAN_UNDERSTAND_BASIC_WORDS" -> CAN_UNDERSTAND_BASIC_WORDS;
            case "BASIC_CONVERSATION_POSSIBLE" -> BASIC_CONVERSATION_POSSIBLE;
            case "COMFORTABLE_FOR_WORK" -> COMFORTABLE_FOR_WORK;
            case "FLUENT" -> FLUENT;
            case "NATIVE_OR_NEAR_NATIVE" -> NATIVE_OR_NEAR_NATIVE;
            default -> throw new IllegalArgumentException("언어 수준 값이 잘못되었습니다: " + value);
        };
    }
}
