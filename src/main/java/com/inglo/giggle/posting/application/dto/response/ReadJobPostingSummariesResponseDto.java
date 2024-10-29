package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.JobPosting;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadJobPostingSummariesResponseDto extends SelfValidating<ReadJobPostingSummariesResponseDto> {

    @NotNull
    @JsonProperty("icon_img_url")
    private final String iconImgUrl;

    @NotNull
    @JsonProperty("company_name")
    private final String companyName;

    @NotNull
    @JsonProperty("title")
    private final String title;

    @JsonProperty("tags")
    private final Tags tags;

    @JsonProperty("summaries")
    private final Summaries summaries;

    @Builder
    public ReadJobPostingSummariesResponseDto(
            String iconImgUrl,
            String companyName,
            String title,
            Tags tags,
            Summaries summaries
    ) {
        this.iconImgUrl = iconImgUrl;
        this.companyName = companyName;
        this.title = title;
        this.tags = tags;
        this.summaries = summaries;

        this.validateSelf();
    }

    public static ReadJobPostingSummariesResponseDto fromEntity(
            JobPosting jobPosting
    ) {
        return ReadJobPostingSummariesResponseDto.builder()
                .iconImgUrl(jobPosting.getOwner().getProfileImgUrl())
                .companyName(jobPosting.getOwner().getCompanyName())
                .title(jobPosting.getTitle())
                .tags(Tags.fromEntity(jobPosting))
                .summaries(
                        Summaries.of(
                                jobPosting
                        ))
                .build();
    }

    @Getter
    public static class Tags extends SelfValidating<Tags> {

        @NotNull
        @JsonProperty("is_recruiting")
        private final Boolean isRecruiting;

        @NotNull
        @JsonProperty("visa")
        private final String visa;

        @NotNull
        @JsonProperty("job_category")
        private final String jobCategory;

        @Builder
        public Tags(Boolean isRecruiting, String visa, String jobCategory) {
            this.isRecruiting = isRecruiting;
            this.visa = visa;
            this.jobCategory = jobCategory;

            this.validateSelf();
        }

        public static Tags fromEntity(
                JobPosting jobPosting
        ) {
            return Tags.builder()
                    .isRecruiting(jobPosting.getRecruitmentDeadLine().isAfter(LocalDate.now()))
                    .visa(jobPosting.getVisa().toString())
                    .jobCategory(jobPosting.getJobCategory().toString())
                    .build();
        }
    }

    @Getter
    public static class Summaries extends SelfValidating<Summaries> {

        @NotNull
        @JsonProperty("address")
        private final String address;

        @NotNull
        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @NotNull
        @JsonProperty("work_period")
        private final String workPeriod;

        @NotNull
        @JsonProperty("work_days_per_week")
        private final String workDaysPerWeek;

        @Builder
        public Summaries(String address, Integer hourlyRate, String workPeriod, String workDaysPerWeek) {
            this.address = address;
            this.hourlyRate = hourlyRate;
            this.workPeriod = workPeriod;
            this.workDaysPerWeek = workDaysPerWeek;

            this.validateSelf();
        }

        public static Summaries of(
                JobPosting jobPosting
        ) {
            return Summaries.builder()
                    .address(jobPosting.getAddress().getAddressName())
                    .hourlyRate(jobPosting.getHourlyRate())
                    .workPeriod(jobPosting.getWorkPeriod().toString())
                    .workDaysPerWeek(jobPosting.getWorkDaysPerWeekToString())
                    .build();
        }
    }
}
