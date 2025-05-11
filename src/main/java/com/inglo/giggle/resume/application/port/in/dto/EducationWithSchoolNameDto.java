package com.inglo.giggle.resume.application.port.in.dto;

import com.inglo.giggle.core.type.EEducationLevel;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EducationWithSchoolNameDto {
    private Long id;
    private EEducationLevel educationLevel;
    private String major;
    private Double gpa;
    private LocalDate enrollmentDate;
    private LocalDate graduationDate;
    private Integer grade;
    private final String schoolName;

    public EducationWithSchoolNameDto(
            Long id,
            EEducationLevel educationLevel,
            String major,
            Double gpa,
            LocalDate enrollmentDate,
            LocalDate graduationDate,
            Integer grade,
            String schoolName
    ) {
        this.id = id;
        this.educationLevel = educationLevel;
        this.major = major;
        this.gpa = gpa;
        this.enrollmentDate = enrollmentDate;
        this.graduationDate = graduationDate;
        this.grade = grade;
        this.schoolName = schoolName;
    }
}
