package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.utility.DateTimeUtil;
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

    public static ReadUserUserOwnerJobPostingDetailResponseDto from(
            UserOwnerJobPosting userOwnerJobPosting
    ) {

        int durationOfDays = 0;

        if (userOwnerJobPosting.getUpdatedAt() == null) {
            durationOfDays = (int) java.time.Duration.between(
                    userOwnerJobPosting.getCreatedAt(),
                    LocalDate.now().atStartOfDay()
            ).toDays();
        } else {
            durationOfDays = (int) java.time.Duration.between(
                    userOwnerJobPosting.getUpdatedAt(),
                    LocalDate.now().atStartOfDay()
            ).toDays();
        }

        return ReadUserUserOwnerJobPostingDetailResponseDto.builder()
                .title(userOwnerJobPosting.getJobPostingInfo().getTitle())
                .iconImgUrl(userOwnerJobPosting.getOwnerInfo().getProfileImgUrl())
                .addressName(userOwnerJobPosting.getJobPostingInfo().getAddressName())
                .durationOfDays(durationOfDays)
                .jobInfo(JobInfo.of(
                        userOwnerJobPosting.getJobPostingInfo().getHourlyRate(),
                        userOwnerJobPosting.getJobPostingInfo().getWorkPeriod().toString(),
                        userOwnerJobPosting.getJobPostingInfo().getWorkDayTimes()
                ))
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

        public static JobInfo of(
                Integer hourlyRate, String workPeriod, List<PostingWorkDayTime> workDayTimes
        ) {
            return JobInfo.builder()
                    .hourlyRate(hourlyRate)
                    .workPeriod(workPeriod)
                    .workDayTimes(
                            workDayTimes.stream()
                                    .map(WorkDayTime::from)
                                    .toList()
                    )
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

        public static WorkDayTime from(PostingWorkDayTime postingWorkDayTime) {
            return WorkDayTime.builder()
                    .dayOfWeek(postingWorkDayTime.getDayOfWeek().toString())
                    .workStartTime(postingWorkDayTime.getWorkStartTime() == null ? null : DateTimeUtil.convertLocalTimeToString(postingWorkDayTime.getWorkStartTime()))
                    .workEndTime(postingWorkDayTime.getWorkEndTime() == null ? null: DateTimeUtil.convertLocalTimeToString(postingWorkDayTime.getWorkEndTime()))
                    .build();
        }
    }
}
