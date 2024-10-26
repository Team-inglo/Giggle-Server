package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadGuestJobPostingOverviewsResponseDto extends SelfValidating<ReadGuestJobPostingOverviewsResponseDto> {

    @NotNull(message = "has_next는 null일 수 없습니다.")
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("job_posting_list")
    private final List<JobPostingOverviewDto> jobPostingList;

    @Builder
    public ReadGuestJobPostingOverviewsResponseDto(
            Boolean hasNext,
            List<JobPostingOverviewDto> jobPostingList
    ) {
        this.hasNext = hasNext;
        this.jobPostingList = jobPostingList;

        this.validateSelf();
    }

    @Getter
    public static class JobPostingOverviewDto extends SelfValidating<JobPostingOverviewDto> {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @NotNull(message = "icon_img_url은 null일 수 없습니다.")
        @JsonProperty("icon_img_url")
        private final String iconImgUrl;

        @NotNull(message = "title은 null일 수 없습니다.")
        @JsonProperty("title")
        private final String title;

        @NotNull(message = "summaries는 null일 수 없습니다.")
        @JsonProperty("summaries")
        private final Summaries summaries;

        @NotNull(message = "tags는 null일 수 없습니다.")
        @JsonProperty("tags")
        private final Tags tags;

        @NotNull(message = "hourly_rate는 null일 수 없습니다.")
        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @NotNull(message = "recruitment_dead_line은 null일 수 없습니다.")
        @JsonProperty("recruitment_dead_line")
        private final String recruitmentDeadLine;

        @NotNull(message = "created_at은 null일 수 없습니다.")
        @JsonProperty("created_at")
        private final String createdAt;

        @Builder
        public JobPostingOverviewDto(
                Long id,
                String iconImgUrl,
                String title,
                Summaries summaries,
                Tags tags,
                Integer hourlyRate,
                String recruitmentDeadLine,
                String createdAt
        ) {
            this.id = id;
            this.iconImgUrl = iconImgUrl;
            this.title = title;
            this.summaries = summaries;
            this.tags = tags;
            this.hourlyRate = hourlyRate;
            this.recruitmentDeadLine = recruitmentDeadLine;
            this.createdAt = createdAt;

            this.validateSelf();
        }
    }

    @Getter
    public static class Summaries extends SelfValidating<Summaries> {

        @NotNull(message = "address는 null일 수 없습니다.")
        @JsonProperty("address")
        private final String address;

        @NotNull(message = "work_period는 null일 수 없습니다.")
        @JsonProperty("work_period")
        private final String workPeriod;

        @NotNull(message = "work_days_per_week는 null일 수 없습니다.")
        @JsonProperty("work_days_per_week")
        private final Integer workDaysPerWeek;

        @Builder
        public Summaries(String address, String workPeriod, Integer workDaysPerWeek) {
            this.address = address;
            this.workPeriod = workPeriod;
            this.workDaysPerWeek = workDaysPerWeek;

            this.validateSelf();
        }
    }

    @Getter
    public static class Tags extends SelfValidating<Tags> {

        @NotNull(message = "is_recruiting는 null일 수 없습니다.")
        @JsonProperty("is_recruiting")
        private final Boolean isRecruiting;

        @NotNull(message = "visa는 null일 수 없습니다.")
        @JsonProperty("visa")
        private final String visa;

        @NotNull(message = "job_category는 null일 수 없습니다.")
        @JsonProperty("job_category")
        private final String jobCategory;

        @Builder
        public Tags(Boolean isRecruiting, String visa, String jobCategory) {
            this.isRecruiting = isRecruiting;
            this.visa = visa;
            this.jobCategory = jobCategory;

            this.validateSelf();
        }
    }
}
