package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EVisa {
  
    D_2_1("전문학사", "D_2_1", "Associate Degree"),
    D_2_2("학사과정", "D_2_2", "Bachelor's Degree"),
    D_2_3("석사과정", "D_2_3", "Master's Degree"),
    D_2_4("박사과정", "D_2_4", "Doctoral Degree"),
    D_2_6("교환학생", "D_2_6", "Exchange Student"),
    D_2_7("일-학습연계유학", "D_2_7", "Internship"),
    D_2_8("방문학생", "D_2_8", "Visiting Student"),
    D_4_1("한국어연수", "D_4_1", "Korean Language Trainee"),
    D_4_7("외국어연수", "D_4_7", "Foreign Language Trainee"),
    D_2("일반 유학과정", "D_2", "-"),
    D_4("연수과정", "D_4", "-"),
    D_10("구직", "D_10", "전문직 취업 준비"),
    C_4("단기 취업", "C_4", "90일 이하 단기 근로"),
    F_2("거주", "F_2", "Residence"),
    F_4("단기 거주", "F_4", "90일 이하 단기 거주"),
    F_5("단기 관광", "F_5", "90일 이하 단기 관광"),
    F_6("단기 사업", "F_6", "90일 이하 단기 사업"),
    H_1("특정기술", "H_1", "특정기술"),

    ;

    private final String krName;
    private final String enName;
    private final String description;

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
            case "D_2" -> D_2;
            case "D_4" -> D_4;
            case "D_10" -> D_10;
            case "C_4" -> C_4;
            case "F_2" -> F_2;
            case "F_4" -> F_4;
            case "F_5" -> F_5;
            case "F_6" -> F_6;
            case "H_1" -> H_1;
            default -> throw new IllegalArgumentException("비자가 잘못되었습니다.");
        };
    }
}
