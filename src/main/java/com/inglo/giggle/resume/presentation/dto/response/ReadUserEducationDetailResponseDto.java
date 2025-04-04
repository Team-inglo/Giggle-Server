package com.inglo.giggle.resume.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.resume.application.vo.EducationWithSchoolDto;
import com.inglo.giggle.school.domain.School;
import lombok.Builder;

public class ReadUserEducationDetailResponseDto extends SelfValidating<ReadUserEducationDetailResponseDto> {
    @JsonProperty("education_level")
    private final String educationLevel;

    @JsonProperty("major")
    private final String major;

    @JsonProperty("gpa")
    private final Double gpa;

    @JsonProperty("start_date")
    private final String startDate;

    @JsonProperty("end_date")
    private final String endDate;

    @JsonProperty("grade")
    private final Integer grade;

    @JsonProperty("school")
    private final SchoolDto schoolDto;

    @Builder
    public ReadUserEducationDetailResponseDto(String educationLevel, String major, Double gpa, String startDate, String endDate, Integer grade, SchoolDto schoolDto) {
        this.educationLevel = educationLevel;
        this.major = major;
        this.gpa = gpa;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grade = grade;
        this.schoolDto = schoolDto;
        this.validateSelf();
    }

    public static class SchoolDto {
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("name")
        private final String name;

        @Builder
        public SchoolDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public static SchoolDto from(School school) {
            return SchoolDto.builder()
                    .id(school.getId())
                    .name(school.getSchoolName())
                    .build();
        }
    }

    public static ReadUserEducationDetailResponseDto from(EducationWithSchoolDto educationWithSchoolDto) {
        return ReadUserEducationDetailResponseDto.builder()
                .educationLevel(educationWithSchoolDto.education().getEducationLevel().toString())
                .major(educationWithSchoolDto.education().getMajor())
                .gpa(educationWithSchoolDto.education().getGpa())
                .startDate(DateTimeUtil.convertLocalDateToString(educationWithSchoolDto.education().getEnrollmentDate()))
                .endDate(educationWithSchoolDto.education().getGraduationDate() != null ? DateTimeUtil.convertLocalDateToString(educationWithSchoolDto.education().getGraduationDate()) : null)
                .grade(educationWithSchoolDto.education().getGrade())
                .schoolDto(SchoolDto.from(educationWithSchoolDto.school()))
                .build();
    }

}
