package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.JobPosting;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
public class ReadJobPostingSummariesResponseDto extends SelfValidating<ReadJobPostingSummariesResponseDto> {

    @NotNull(message = "iconImgUrl 은(는) 필수 입력값입니다.")
    @JsonProperty("icon_img_url")
    private final String iconImgUrl;

    @NotNull(message = "companyName 은(는) 필수 입력값입니다.")
    @JsonProperty("company_name")
    private final String companyName;

    @NotNull(message = "title 은(는) 필수 입력값입니다.")
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

        @NotNull(message = "isRecruiting 은(는) 필수 입력값입니다.")
        @JsonProperty("is_recruiting")
        private final Boolean isRecruiting;

        @NotNull(message = "visa 은(는) 필수 입력값입니다.")
        @JsonProperty("visa")
        private final Set<EVisa> visa;

        @NotNull(message = "jobCategory 은(는) 필수 입력값입니다.")
        @JsonProperty("job_category")
        private final String jobCategory;

        @Builder
        public Tags(Boolean isRecruiting, Set<EVisa> visa, String jobCategory) {
            this.isRecruiting = isRecruiting;
            this.visa = visa;
            this.jobCategory = jobCategory;

            this.validateSelf();
        }

        public static Tags fromEntity(
                JobPosting jobPosting
        ) {
            return Tags.builder()
                    .isRecruiting(jobPosting.getRecruitmentDeadLine() == null || jobPosting.getRecruitmentDeadLine().isAfter(LocalDate.now()))
                    .visa(jobPosting.getVisa())
                    .jobCategory(jobPosting.getJobCategory().toString())
                    .build();
        }
    }

    @Getter
    public static class Summaries extends SelfValidating<Summaries> {

        @NotNull(message = "address 은(는) 필수 입력값입니다.")
        @JsonProperty("address")
        private final String address;

        @NotNull(message = "hourlyRate 은(는) 필수 입력값입니다.")
        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @NotNull(message = "workPeriod 은(는) 필수 입력값입니다.")
        @JsonProperty("work_period")
        private final String workPeriod;

        @NotNull(message = "workDaysPerWeek 은(는) 필수 입력값입니다.")
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
