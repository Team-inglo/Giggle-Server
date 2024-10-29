package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EVisa {
    D_2_1("전문학사", "D_2_1"),
    D_2_2("학사과정", "D_2_2"),
    D_2_3("석사과정", "D_2_3"),
    D_2_4("박사과정", "D_2_4"),
    D_2_6("교환학생", "D_2_6"),
    D_2_7("일-학습연계유학", "D_2_7"),
    D_2_8("방문학생", "D_2_8"),
    D_4_1("한국어연수", "D_4_1"),
    D_4_7("외국어연수", "D_4_7"),
    F_2("거주", "F_2"),

    D_2("일반 유학과정", "D_2"),
    D_4("연수과정", "D_4")

    ;

    private final String krName;
    private final String enName;

    public static EVisa fromString(String value) {
        return switch (value.toUpperCase()) {
            case "D_2_1" -> D_2_1;
            case "D_2_2" -> D_2_2;
            case "D_2_3" -> D_2_3;
            case "D_2_4" -> D_2_4;
            case "D_2_6" -> D_2_6;
            case "D_2_7" -> D_2_7;
            case "D_2_8" -> D_2_8;
            case "D_4_1" -> D_4_1;
            case "D_4_7" -> D_4_7;
            case "F_2" -> F_2;
            default -> throw new IllegalArgumentException("비자가 잘못되었습니다.");
        };
    }
}
