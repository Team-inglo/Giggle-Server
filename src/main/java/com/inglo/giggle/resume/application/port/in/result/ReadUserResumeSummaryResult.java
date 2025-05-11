package com.inglo.giggle.resume.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class ReadUserResumeSummaryResult extends SelfValidating<ReadUserResumeSummaryResult> {

    @NotNull(message = "grade는 null일 수 없습니다.")
    @JsonProperty("grade")
    private final Integer grade;

    @NotNull(message = "gpa는 null일 수 없습니다.")
    @JsonProperty("gpa")
    private final Double gpa;

    @NotNull(message = "topik_level은 null일 수 없습니다.")
    @JsonProperty("topik_level")
    private final Integer topikLevel;

    @NotNull(message = "kiip_level은 null일 수 없습니다.")
    @JsonProperty("kiip_level")
    private final Integer kiipLevel;

    @NotNull(message = "sejong_level은 null일 수 없습니다.")
    @JsonProperty("sejong_level")
    private final Integer sejongLevel;

    @NotNull(message = "weekend_work_hour은 null일 수 없습니다.")
    @JsonProperty("weekend_work_hour")
    private final Integer weekendWorkHour;

    @NotNull(message = "weekday_work_hour은 null일 수 없습니다.")
    @JsonProperty("weekday_work_hour")
    private final Integer weekdayWorkHour;

    @NotNull(message = "is_topik_4_or_more은 null일 수 없습니다.")
    @JsonProperty("is_language_skill_4_or_more")
    private final Boolean isLanguageSkill4OrMore;

    @JsonProperty("school_id")
    private final Long schoolId;

    @Builder
    public ReadUserResumeSummaryResult(
            Integer grade,
            Double gpa,
            Integer topikLevel,
            Integer kiipLevel,
            Integer sejongLevel,
            Integer weekendWorkHour,
            Integer weekdayWorkHour,
            Boolean isLanguageSkill4OrMore,
            Long schoolId
    ) {
        this.grade = grade;
        this.gpa = gpa;
        this.topikLevel = topikLevel;
        this.kiipLevel = kiipLevel;
        this.sejongLevel = sejongLevel;
        this.weekendWorkHour = weekendWorkHour;
        this.weekdayWorkHour = weekdayWorkHour;
        this.isLanguageSkill4OrMore = isLanguageSkill4OrMore;
        this.schoolId = schoolId;

        this.validateSelf();
    }

    public static final String WEEKEND_WORK_HOURS = "weekendWorkHours";
    public static final String WEEKDAY_WORK_HOURS = "weekdayWorkHours";

    public static ReadUserResumeSummaryResult of(
            Integer grade,
            Double gpa,
            Integer topikLevel,
            Integer kiipLevel,
            Integer sejongLevel,
            Map<String, Integer> workHours,
            Long schoolId
    ) {
        return ReadUserResumeSummaryResult.builder()
                .grade(grade)
                .gpa(gpa)
                .topikLevel(topikLevel)
                .kiipLevel(kiipLevel)
                .sejongLevel(sejongLevel)
                .weekendWorkHour(workHours.get(WEEKEND_WORK_HOURS))
                .weekdayWorkHour(workHours.get(WEEKDAY_WORK_HOURS))
                .isLanguageSkill4OrMore(topikLevel >= 4 && kiipLevel >= 4)
                .schoolId(schoolId)
                .build();
    }


    // education 이 null 일 때
    public static ReadUserResumeSummaryResult of(
            Integer topikLevel,
            Integer kiipLevel,
            Integer sejongLevel
    ) {
        return ReadUserResumeSummaryResult.builder()
                .grade(0)
                .gpa(0.0)
                .topikLevel(topikLevel)
                .kiipLevel(kiipLevel)
                .sejongLevel(sejongLevel)
                .weekendWorkHour(0)
                .weekdayWorkHour(0)
                .isLanguageSkill4OrMore(topikLevel >= 4 && kiipLevel >= 4)
                .schoolId(null)
                .build();
    }
}
