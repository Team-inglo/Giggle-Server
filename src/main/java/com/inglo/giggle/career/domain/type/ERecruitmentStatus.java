package com.inglo.giggle.career.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERecruitmentStatus {

    PRE_RECRUITMENT("PRE_RECRUITMENT", "모집전"),
    RECRUITMENT("RECRUITMENT", "모집중"),
    CLOSED("CLOSED", "모집완료"),

    ;

    private final String enName;
    private final String koName;

    public static ERecruitmentStatus from(String enName) {
        for (ERecruitmentStatus status : values()) {
            if (status.enName.equals(enName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid recruitment status: " + enName);
    }
}
