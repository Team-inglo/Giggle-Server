package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.type.EEmploymentType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    public static ReadJobPostingOverviewResponseDto of(
            Boolean hasNext,
            List<JobPosting> jobPostings,
            Map<Long, Integer> bookMarkCountMap
    ) {
        List<JobPostingOverviewDto> jobPostingList = jobPostings.stream()
                .map(jobPosting -> JobPostingOverviewDto.of(
                        jobPosting,
                        bookMarkCountMap)
                )
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

        @JsonProperty("representative_img_url")
        private final String representativeImgUrl;

        @JsonProperty("company_name")
        @NotNull(message = "company_name은 null일 수 없습니다.")
        private final String companyName;

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
                String representativeImgUrl,
                String title,
                String companyName,
                Summaries summaries,
                Tags tags,
                Integer hourlyRate,
                String recruitmentDeadLine,
                String createdAt
        ) {
            this.id = id;
            this.isBookMarked = isBookMarked;
            this.iconImgUrl = iconImgUrl;
            this.representativeImgUrl = representativeImgUrl;
            this.title = title;
            this.companyName = companyName;
            this.summaries = summaries;
            this.tags = tags;
            this.hourlyRate = hourlyRate;
            this.recruitmentDeadLine = recruitmentDeadLine;
            this.createdAt = createdAt;

            this.validateSelf();
        }

        public static JobPostingOverviewDto of(
                JobPosting jobPosting,
                Map<Long, Integer> bookMarkCountMap
        ) {
            return JobPostingOverviewDto.builder()
                    .id(jobPosting.getId())
                    .isBookMarked(bookMarkCountMap.getOrDefault(jobPosting.getId(), 0) > 0)
                    .iconImgUrl(jobPosting.getOwner().getProfileImgUrl())
                    .representativeImgUrl(!jobPosting.getCompanyImages().isEmpty() ? jobPosting.getCompanyImages().get(0).getImgUrl(): null)
                    .title(jobPosting.getTitle())
                    .companyName(jobPosting.getOwner().getCompanyName())
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

        @NotNull(message = "employment_type는 null일 수 없습니다.")
        @JsonProperty("employment_type")
        private final EEmploymentType employmentType;

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
        public Tags(EEmploymentType employmentType, Boolean isRecruiting, String visa, String jobCategory) {
            this.employmentType = employmentType;
            this.isRecruiting = isRecruiting;
            this.visa = visa;
            this.jobCategory = jobCategory;

            this.validateSelf();
        }

        public static Tags fromEntity(JobPosting jobPosting) {
            return Tags.builder()
                    .employmentType(jobPosting.getEmploymentType())
                    .isRecruiting(jobPosting.getRecruitmentDeadLine() == null || jobPosting.getRecruitmentDeadLine().isAfter(LocalDate.now()))
                    .visa(jobPosting.getVisa().toString())
                    .jobCategory(jobPosting.getJobCategory().toString())
                    .build();
        }
    }
}
