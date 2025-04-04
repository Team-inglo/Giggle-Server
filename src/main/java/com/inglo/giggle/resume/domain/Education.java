package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EVisa;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Education extends BaseDomain {
    private Long id;
    private EEducationLevel educationLevel;
    private String major;
    private Double gpa;
    private LocalDate enrollmentDate;
    private LocalDate graduationDate;
    private Integer grade;
    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private UUID resumeId;
    private Long schoolId;

    @Builder
    public Education(
            Long id,
            EEducationLevel educationLevel,
            String major,
            Double gpa,
            LocalDate enrollmentDate,
            LocalDate graduationDate,
            Integer grade,
            UUID resumeId,
            Long schoolId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.id = id;
        this.educationLevel = educationLevel;
        this.major = major;
        this.gpa = gpa;
        this.enrollmentDate = enrollmentDate;
        this.graduationDate = graduationDate;
        this.grade = grade;
        this.resumeId = resumeId;
        this.schoolId = schoolId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static EEducationLevel getEducationLevelByVisa(EVisa visa) {
        switch (visa) {
            case D_2_1 -> {
                return EEducationLevel.ASSOCIATE;
            }
            case D_2_2 -> {
                return EEducationLevel.BACHELOR;
            }
            case D_2_3 -> {
                return EEducationLevel.MASTER;
            }
            case D_2_4 -> {
                return EEducationLevel.DOCTOR;
            }
            default -> {
                return EEducationLevel.HIGHSCHOOL;
            }
        }
    }

    public void checkValidation(UUID accountId) {
        if (!resumeId.equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void updateSelf(
            EEducationLevel educationLevel,
            String major,
            Double gpa,
            LocalDate enrollmentDate,
            LocalDate graduationDate,
            Integer grade,
            Long schoolId
    ) {
        this.educationLevel = educationLevel;
        this.schoolId = schoolId;
        this.major = major;
        this.gpa = gpa;
        this.enrollmentDate = enrollmentDate;
        this.graduationDate = graduationDate;
        this.grade = grade;
    }
}
