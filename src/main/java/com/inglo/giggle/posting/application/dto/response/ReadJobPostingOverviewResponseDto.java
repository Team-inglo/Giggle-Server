package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReadJobPostingOverviewResponseDto extends SelfValidating<ReadJobPostingDetailResponseDto> {

    @NotNull(message = "has_next는 null일 수 없습니다.")
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("job_posting_list")
    private final List<JobPostingOverviewDto> jobPostingList;

    @Builder
    public ReadJobPostingOverviewResponseDto(
            Boolean hasNext,
            List<JobPostingOverviewDto> jobPostingList
    ) {
        this.hasNext = hasNext;
        this.jobPostingList = jobPostingList;

        this.validateSelf();
    }

    public static ReadJobPostingOverviewResponseDto fromEntities(
            Page<JobPosting> jobPostingsPage,
            Account account
    ) {
        boolean hasNext = jobPostingsPage.hasNext();
        List<JobPostingOverviewDto> jobPostingList = jobPostingsPage.getContent().stream()
                .map(jobPosting -> JobPostingOverviewDto.fromEntities(jobPosting, account))
                .toList();

        return ReadJobPostingOverviewResponseDto.builder()
                .hasNext(hasNext)
                .jobPostingList(jobPostingList)
                .build();
    }

    @Getter
    public static class JobPostingOverviewDto extends SelfValidating<JobPostingOverviewDto> {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @NotNull(message = "is_book_marked일 수 없습니다.")
        @JsonProperty("is_book_marked")
        private final Boolean isBookMarked;

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

        @JsonProperty("recruitment_dead_line")
        private final String recruitmentDeadLine;

        @NotNull(message = "created_at은 null일 수 없습니다.")
        @JsonProperty("created_at")
        private final String createdAt;

        @Builder
        public JobPostingOverviewDto(
                Long id,
                Boolean isBookMarked,
                String iconImgUrl,
                String title,
                Summaries summaries,
                Tags tags,
                Integer hourlyRate,
                String recruitmentDeadLine,
                String createdAt
        ) {
            this.id = id;
            this.isBookMarked = isBookMarked;
            this.iconImgUrl = iconImgUrl;
            this.title = title;
            this.summaries = summaries;
            this.tags = tags;
            this.hourlyRate = hourlyRate;
            this.recruitmentDeadLine = recruitmentDeadLine;
            this.createdAt = createdAt;

            this.validateSelf();
        }

        public static JobPostingOverviewDto fromEntities(
                JobPosting jobPosting,
                Account account
        ) {
            if(account.getRole() == ESecurityRole.USER){
                if(jobPosting.getBookMarks().stream().anyMatch(
                        bookMark -> bookMark.getUser().getId().equals(account.getId()))
                ){
                    return JobPostingOverviewDto.builder()
                            .id(jobPosting.getId())
                            .isBookMarked(true)
                            .iconImgUrl(jobPosting.getOwner().getProfileImgUrl())
                            .title(jobPosting.getTitle())
                            .summaries(
                                    Summaries.fromEntity(
                                            jobPosting
                                    )
                            )
                            .tags(
                                    Tags.fromEntity(
                                            jobPosting
                                    )
                            )
                            .hourlyRate(jobPosting.getHourlyRate())
                            .recruitmentDeadLine(jobPosting.getRecruitmentDeadLine() == null ? "상시모집" : DateTimeUtil.convertLocalDateToString(jobPosting.getRecruitmentDeadLine()))
                            .createdAt(DateTimeUtil.convertLocalDateTimeToString(jobPosting.getCreatedAt()))
                            .build();
                }
            }

            return JobPostingOverviewDto.builder()
                    .id(jobPosting.getId())
                    .isBookMarked(false)
                    .iconImgUrl(jobPosting.getOwner().getProfileImgUrl())
                    .title(jobPosting.getTitle())
                    .summaries(
                            Summaries.fromEntity(
                                    jobPosting
                            )
                    )
                    .tags(
                            Tags.fromEntity(
                                    jobPosting
                            )
                    )
                    .hourlyRate(jobPosting.getHourlyRate())
                    .recruitmentDeadLine(jobPosting.getRecruitmentDeadLine() == null ? "상시모집" : DateTimeUtil.convertLocalDateToString(jobPosting.getRecruitmentDeadLine()))
                    .createdAt(DateTimeUtil.convertLocalDateTimeToString(jobPosting.getCreatedAt()))
                    .build();
        }
    }

    @Getter
    public static class Summaries extends SelfValidating<ReadGuestJobPostingOverviewsResponseDto.Summaries> {

        @NotNull(message = "address는 null일 수 없습니다.")
        @JsonProperty("address")
        private final String address;

        @NotNull(message = "work_period는 null일 수 없습니다.")
        @JsonProperty("work_period")
        private final String workPeriod;

        @NotNull(message = "work_days_per_week는 null일 수 없습니다.")
        @JsonProperty("work_days_per_week")
        private final String workDaysPerWeek;

        @Builder
        public Summaries(String address, String workPeriod, String workDaysPerWeek) {
            this.address = address;
            this.workPeriod = workPeriod;
            this.workDaysPerWeek = workDaysPerWeek;

            this.validateSelf();
        }

        public static Summaries fromEntity(JobPosting jobPosting) {
            return Summaries.builder()
                    .address(jobPosting.getAddress().getAddressName())
                    .workPeriod(jobPosting.getWorkPeriod().toString())
                    .workDaysPerWeek(jobPosting.getWorkDaysPerWeekToString())
                    .build();
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

        public static Tags fromEntity(JobPosting jobPosting) {
            return Tags.builder()
                    .isRecruiting(jobPosting.getRecruitmentDeadLine() == null || jobPosting.getRecruitmentDeadLine().isAfter(LocalDate.now()))
                    .visa(jobPosting.getVisa().toString())
                    .jobCategory(jobPosting.getJobCategory().toString())
                    .build();
        }
    }
}
