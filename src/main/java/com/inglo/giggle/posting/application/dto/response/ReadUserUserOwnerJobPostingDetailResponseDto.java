package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserUserOwnerJobPostingDetailResponseDto extends SelfValidating<ReadUserUserOwnerJobPostingDetailResponseDto> {

    @NotNull(message = "id는 null일 수 없습니다.")
    @JsonProperty("id")
    private final Long id;

    @NotBlank(message = "title은 null일 수 없습니다.")
    @JsonProperty("title")
    private final String title;

    @NotBlank(message = "company_name은 null일 수 없습니다.")
    @JsonProperty("company_name")
    private final String companyName;

    @NotBlank(message = "address_name은 null일 수 없습니다.")
    @JsonProperty("address_name")
    private final String addressName;

    @NotNull(message = "hourly_rate는 null일 수 없습니다.")
    @JsonProperty("hourly_rate")
    private final Integer hourlyRate;

    @NotNull(message = "work_days_per_week는 null이 될 수 없습니다.")
    @JsonProperty("work_days_per_week")
    private final String workDaysPerWeek;

    @NotNull(message = "work_period는 null일 수 없습니다.")
    @JsonProperty("work_period")
    private final String workPeriod;

    @NotBlank(message = "step은 null일 수 없습니다.")
    @JsonProperty("step")
    private final String step;

    @Builder
    public ReadUserUserOwnerJobPostingDetailResponseDto(
            Long id,
            String title,
            String companyName,
            String addressName,
            Integer hourlyRate,
            String workDaysPerWeek,
            String workPeriod,
            String step
    ) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
        this.addressName = addressName;
        this.hourlyRate = hourlyRate;
        this.workDaysPerWeek = workDaysPerWeek;
        this.workPeriod = workPeriod;
        this.step = step;

        validateSelf();
    }

    public static ReadUserUserOwnerJobPostingDetailResponseDto fromEntity(
            UserOwnerJobPosting userOwnerJobPosting
    ) {

        return ReadUserUserOwnerJobPostingDetailResponseDto.builder()
                .id(userOwnerJobPosting.getJobPosting().getId())
                .title(userOwnerJobPosting.getJobPosting().getTitle())
                .companyName(userOwnerJobPosting.getJobPosting().getOwner().getCompanyName())
                .addressName(userOwnerJobPosting.getJobPosting().getAddress().getAddressName())
                .hourlyRate(userOwnerJobPosting.getJobPosting().getHourlyRate())
                .workDaysPerWeek(userOwnerJobPosting.getJobPosting().getWorkDaysPerWeekToString())
                .workPeriod(userOwnerJobPosting.getJobPosting().getWorkPeriod().toString())
                .step(userOwnerJobPosting.getStep().name())
                .build();
    }
}
