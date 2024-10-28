package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReadUserUserOwnerJobPostingDetailResponseDto extends SelfValidating<ReadUserUserOwnerJobPostingDetailResponseDto> {

    @NotBlank(message = "title은 null일 수 없습니다.")
    @JsonProperty("title")
    private final String title;

    @NotBlank(message = "icon_img_url은 null일 수 없습니다.")
    @JsonProperty("icon_img_url")
    private final String iconImgUrl;

    @NotBlank(message = "address_name은 null일 수 없습니다.")
    @JsonProperty("address_name")
    private final String addressName;

    @NotNull(message = "duration_of_days는 null일 수 없습니다.")
    @JsonProperty("duration_of_days")
    private final Integer durationOfDays;

    @NotNull(message = "job_info는 null일 수 없습니다.")
    @JsonProperty("job_info")
    private final JobInfo jobInfo;

    @NotBlank(message = "step은 null일 수 없습니다.")
    @JsonProperty("step")
    private final String step;

    @Builder
    public ReadUserUserOwnerJobPostingDetailResponseDto(
            String title,
            String iconImgUrl,
            String addressName,
            Integer durationOfDays,
            JobInfo jobInfo,
            String step
    ) {
        this.title = title;
        this.iconImgUrl = iconImgUrl;
        this.addressName = addressName;
        this.durationOfDays = durationOfDays;
        this.jobInfo = jobInfo;
        this.step = step;

        this.validateSelf();
    }

    public static ReadUserUserOwnerJobPostingDetailResponseDto fromEntity(
            UserOwnerJobPosting userOwnerJobPosting
    ) {

        int durationOfDays = (int) java.time.Duration.between(
                userOwnerJobPosting.getUpdatedAt().atStartOfDay(),
                LocalDate.now().atStartOfDay()
        ).toDays();

        return ReadUserUserOwnerJobPostingDetailResponseDto.builder()
                .title(userOwnerJobPosting.getJobPosting().getTitle())
                .iconImgUrl(userOwnerJobPosting.getOwner().getProfileImgUrl())
                .addressName(userOwnerJobPosting.getJobPosting().getAddress().getAddressName())
                .durationOfDays(durationOfDays)
                .jobInfo(JobInfo.fromEntity(userOwnerJobPosting.getJobPosting()))
                .step(userOwnerJobPosting.getStep().name())
                .build();
    }

    @Getter
    @Builder
    public static class JobInfo {

        @NotNull(message = "hourly_rate는 null일 수 없습니다.")
        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @NotNull(message = "work_period는 null일 수 없습니다.")
        @JsonProperty("work_period")
        private final String workPeriod;

        @NotNull(message = "work_day_times는 null일 수 없습니다.")
        @JsonProperty("work_day_times")
        private final List<WorkDayTime> workDayTimes;

        public static JobInfo fromEntity(
                JobPosting jobPosting
        ) {
            return JobInfo.builder()
                    .hourlyRate(jobPosting.getHourlyRate())
                    .workPeriod(jobPosting.getWorkPeriod().toString())
                    .workDayTimes(jobPosting.getWorkDayTimes().stream()
                            .map(WorkDayTime::fromEntity)
                            .toList())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class WorkDayTime {

        @NotBlank(message = "day_of_week는 null일 수 없습니다.")
        @JsonProperty("day_of_week")
        private final String dayOfWeek;

        @NotBlank(message = "work_start_time은 null일 수 없습니다.")
        @JsonProperty("work_start_time")
        private final String workStartTime;

        @NotBlank(message = "work_end_time은 null일 수 없습니다.")
        @JsonProperty("work_end_time")
        private final String workEndTime;

        public static WorkDayTime fromEntity(PostingWorkDayTime postingWorkDayTime) {
            return WorkDayTime.builder()
                    .dayOfWeek(postingWorkDayTime.getDayOfWeek().toString())
                    .workStartTime(postingWorkDayTime.getWorkStartTime().toString())
                    .workEndTime(postingWorkDayTime.getWorkEndTime().toString())
                    .build();
        }
    }
}
