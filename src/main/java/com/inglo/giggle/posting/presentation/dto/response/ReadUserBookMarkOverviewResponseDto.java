package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
public class ReadUserBookMarkOverviewResponseDto extends SelfValidating<ReadUserBookMarkOverviewResponseDto> {

    @NotNull(message = "has_next는 null이 될 수 없습니다.")
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("job_posting_list")
    private final List<JobPostingOverviewDto> jobPostingList;

    @Builder
    public ReadUserBookMarkOverviewResponseDto(Boolean hasNext, List<JobPostingOverviewDto> jobPostingList) {
        this.hasNext = hasNext;
        this.jobPostingList = jobPostingList;

        this.validateSelf();
    }

    public static ReadUserBookMarkOverviewResponseDto of(Page<BookMark> bookMarkPage) {
        List<JobPostingOverviewDto> jobPostingList = bookMarkPage.getContent().stream()
                .map(bookMark ->
                        JobPostingOverviewDto.of(
                                bookMark.getJobPostingInfo()
                        )
                ).toList();

        return ReadUserBookMarkOverviewResponseDto.builder()
                .hasNext(bookMarkPage.hasNext())
                .jobPostingList(jobPostingList)
                .build();
    }

    @Getter
    public static class JobPostingOverviewDto extends SelfValidating<JobPostingOverviewDto> {

        @NotNull(message = "id는 null이 될 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @NotNull(message = "icon_img_url은 null이 될 수 없습니다.")
        @JsonProperty("icon_img_url")
        private final String iconImgUrl;

        @NotNull(message = "title은 null이 될 수 없습니다.")
        @JsonProperty("title")
        private final String title;

        @JsonProperty("summaries")
        private final Summaries summaries;

        @JsonProperty("tags")
        private final Tags tags;

        @NotNull(message = "hourly_rate는 null이 될 수 없습니다.")
        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @JsonProperty("recruitment_dead_line")
        private final String recruitmentDeadLine;

        @NotNull(message = "created_at은 null이 될 수 없습니다.")
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

        public static JobPostingOverviewDto of(BookMark.JobPostingInfo jobPostingInfo) {
            return JobPostingOverviewDto.builder()
                    .id(jobPostingInfo.getId())
                    .iconImgUrl(jobPostingInfo.getOwnerInfo().getProfileImgUrl())
                    .title(jobPostingInfo.getTitle())
                    .summaries(Summaries.of(jobPostingInfo))
                    .tags(Tags.of(jobPostingInfo))
                    .hourlyRate(jobPostingInfo.getHourlyRate())
                    .recruitmentDeadLine(jobPostingInfo.getRecruitmentDeadLine() == null ? "상시모집" : DateTimeUtil.convertLocalDateToString(jobPostingInfo.getRecruitmentDeadLine()))
                    .createdAt(DateTimeUtil.convertLocalDateTimeToString(jobPostingInfo.getCreatedAt()))
                    .build();
        }

        @Getter
        public static class Summaries extends SelfValidating<Summaries> {

            @NotNull(message = "address는 null이 될 수 없습니다.")
            @JsonProperty("address")
            private final String address;

            @NotNull(message = "work_period는 null이 될 수 없습니다.")
            @JsonProperty("work_period")
            private final EWorkPeriod workPeriod;

            @NotNull(message = "work_days_per_week는 null이 될 수 없습니다.")
            @JsonProperty("work_days_per_week")
            private final String workDaysPerWeek;

            @Builder
            public Summaries(String address, EWorkPeriod workPeriod, String workDaysPerWeek) {
                this.address = address;
                this.workPeriod = workPeriod;
                this.workDaysPerWeek = workDaysPerWeek;

                this.validateSelf();
            }

            public static Summaries of(BookMark.JobPostingInfo jobPostingInfo) {
                return Summaries.builder()
                        .address(jobPostingInfo.getAddress().getAddressName())
                        .workPeriod(jobPostingInfo.getWorkPeriod())
                        .workDaysPerWeek(jobPostingInfo.getWorkDaysPerWeek())
                        .build();
            }
        }

        @Getter
        public static class Tags extends SelfValidating<Tags> {

            @NotNull(message = "is_recruiting는 null이 될 수 없습니다.")
            @JsonProperty("is_recruiting")
            private final Boolean isRecruiting;

            @NotNull(message = "visa는 null이 될 수 없습니다.")
            @JsonProperty("visa")
            private final Set<EVisa> visa;

            @NotNull(message = "job_category는 null이 될 수 없습니다.")
            @JsonProperty("job_category")
            private final EJobCategory jobCategory;

            @Builder
            public Tags(Boolean isRecruiting, Set<EVisa> visa, EJobCategory jobCategory) {
                this.isRecruiting = isRecruiting;
                this.visa = visa;
                this.jobCategory = jobCategory;

                this.validateSelf();
            }

            public static Tags of(BookMark.JobPostingInfo jobPostingInfo) {
                return Tags.builder()
                        .isRecruiting(jobPostingInfo.getRecruitmentDeadLine() == null || jobPostingInfo.getRecruitmentDeadLine().isAfter(LocalDate.now()))
                        .visa(jobPostingInfo.getVisa())
                        .jobCategory(jobPostingInfo.getJobCategory())
                        .build();
            }
        }
    }
}
