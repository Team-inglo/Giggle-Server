package com.inglo.giggle.resume.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EVisa;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
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

    /* -------------------------------------------- */
    /* Nested Class ------------------------------- */
    /* -------------------------------------------- */
    private SchoolInfo schoolInfo;

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
            SchoolInfo schoolInfo
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
        this.schoolInfo = schoolInfo;
    }
    @Getter
    public static class SchoolInfo {
        private Long id;
        private String schoolName;
        private Boolean isMetropolitan;
        private String instituteName;
        private String coordinatorName;
        private String coordinatorPhoneNumber;
        private Address address;

        @Builder
        public SchoolInfo(Long id, String schoolName, Boolean isMetropolitan, String instituteName, String coordinatorName, String coordinatorPhoneNumber, Address address) {
            this.id = id;
            this.schoolName = schoolName;
            this.isMetropolitan = isMetropolitan;
            this.instituteName = instituteName;
            this.coordinatorName = coordinatorName;
            this.coordinatorPhoneNumber = coordinatorPhoneNumber;
            this.address = address;
        }
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
            Long schoolId,
            String major,
            Double gpa,
            LocalDate enrollmentDate,
            LocalDate graduationDate,
            Integer grade
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
