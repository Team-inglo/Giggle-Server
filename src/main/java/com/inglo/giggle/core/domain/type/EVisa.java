package com.inglo.giggle.core.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EVisa {
    D_2_1("전문학사", "D-2-2"),
    D_2_2("학사과정", "D-2-2"),
    D_2_3("석사과정", "D-2-3"),
    D_2_4("박사과정", "D-2-4"),
    D_2_6("교환학생", "D-2-6"),
    D_2_7("일-학습연계유학", "D-2-7"),
    D_2_8("방문학생", "D-2-8"),
    D_4_1("한국어연수", "D-4-1"),
    D_4_7("외국어연수", "D-4-7"),
    F_2("거주", "F-2")

    ;

    private final String krName;
    private final String enName;
}
