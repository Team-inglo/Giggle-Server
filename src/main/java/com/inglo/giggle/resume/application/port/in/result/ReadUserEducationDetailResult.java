package com.inglo.giggle.resume.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EEducationLevel;
import lombok.Builder;

public class ReadUserEducationDetailResult extends SelfValidating<ReadUserEducationDetailResult> {
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
    public ReadUserEducationDetailResult(String educationLevel, String major, Double gpa, String startDate, String endDate, Integer grade, SchoolDto schoolDto) {
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

        public static SchoolDto of(Long schoolId, String schoolName) {
            return SchoolDto.builder()
                    .id(schoolId)
                    .name(schoolName)
                    .build();
        }
    }

    public static ReadUserEducationDetailResult of(
            EEducationLevel educationLevel,
            String major,
            Double gpa,
            String startDate,
            String endDate,
            Integer grade,
            Long schoolId,
            String schoolName
    ) {
        return ReadUserEducationDetailResult.builder()
                .educationLevel(educationLevel.toString())
                .major(major)
                .gpa(gpa)
                .startDate(startDate)
                .endDate(endDate)
                .grade(grade)
                .schoolDto(SchoolDto.of(schoolId, schoolName))
                .build();
    }

}
