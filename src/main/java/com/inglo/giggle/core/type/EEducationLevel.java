package com.inglo.giggle.core.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EEducationLevel {

    BACHELOR("4년제", "BACHELOR"),
    ASSOCIATE("2년제", "ASSOCIATE"),
    HIGHSCHOOL("고졸", "HIGHSCHOOL"),
    MASTER("석사", "MASTER"),
    DOCTOR("박사", "DOCTOR"),
    NONE("무관", "NONE")

    ;

    private final String krName;
    private final String enName;

    public static EEducationLevel fromString(String value) {
        return switch (value.toUpperCase()) {
            case "BACHELOR" -> BACHELOR;
            case "ASSOCIATE" -> ASSOCIATE;
            case "HIGHSCOOL" -> HIGHSCHOOL;
            case "MASTER" -> MASTER;
            case "DOCTOR" -> DOCTOR;
            case "NONE" -> NONE;
            default -> throw new IllegalArgumentException("학력이 잘못되었습니다.");
        };
    }
}
