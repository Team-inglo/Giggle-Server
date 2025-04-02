package com.inglo.giggle.posting.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.JobPosting;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReadOwnerJobPostingOverviewsResponseDto extends SelfValidating<ReadOwnerJobPostingOverviewsResponseDto> {

    @NotNull(message = "has_next는 null일 수 없습니다.")
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("job_posting_list")
    private final List<JobPostingOverviewDto> jobPostingList;

    @Builder
    public ReadOwnerJobPostingOverviewsResponseDto(
            Boolean hasNext,
            List<JobPostingOverviewDto> jobPostingList
    ) {
        this.hasNext = hasNext;
        this.jobPostingList = jobPostingList;

        this.validateSelf();
    }

    public static ReadOwnerJobPostingOverviewsResponseDto of(
            Page<JobPosting> userOwnerJobPostingPage,
            Owner owner
    ) {
        boolean hasNext = userOwnerJobPostingPage.hasNext();
        List<JobPostingOverviewDto> jobPostingOverviewDtoList = userOwnerJobPostingPage.getContent().stream()
                .map(jobPosting -> JobPostingOverviewDto.fromEntity(jobPosting, owner))
                .toList();

        return ReadOwnerJobPostingOverviewsResponseDto.builder()
                .hasNext(hasNext)
                .jobPostingList(jobPostingOverviewDtoList)
                .build();
    }

    @Getter
    public static class JobPostingOverviewDto extends SelfValidating<JobPostingOverviewDto> {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @NotNull(message = "icon_img_url은 null일 수 없습니다.")
        @JsonProperty("icon_img_url")
        private final String iconImgUrl;

        @NotNull(message = "company_name은 null일 수 없습니다.")
        @JsonProperty("company_name")
        private final String companyName;

        @NotNull(message = "title은 null일 수 없습니다.")
        @JsonProperty("title")
        private final String title;

        @NotNull(message = "address_name은 null일 수 없습니다.")
        @JsonProperty("address_name")
        private final String addressName;

        @NotNull(message = "hourly_rate는 null일 수 없습니다.")
        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @NotNull(message = "duration_of_days는 null일 수 없습니다.")
        @JsonProperty("duration_of_days")
        private final Integer durationOfDays;

        @Builder
        public JobPostingOverviewDto(
                Long id,
                String iconImgUrl,
                String companyName,
                String title,
                String addressName,
                Integer hourlyRate,
                Integer durationOfDays
        ) {
            this.id = id;
            this.iconImgUrl = iconImgUrl;
            this.companyName = companyName;
            this.title = title;
            this.addressName = addressName;
            this.hourlyRate = hourlyRate;
            this.durationOfDays = durationOfDays;

            this.validateSelf();
        }

        public static JobPostingOverviewDto fromEntity(
                JobPosting jobPosting,
                Owner owner
        ) {

            int durationOfDays = (int) Duration.between(
                    jobPosting.getCreatedAt(),
                    LocalDateTime.now()
            ).toDays();

            return JobPostingOverviewDto.builder()
                    .id(jobPosting.getId())
                    .iconImgUrl(owner.getProfileImgUrl())
                    .companyName(owner.getCompanyName())
                    .title(jobPosting.getTitle())
                    .addressName(jobPosting.getAddress().getAddressName())
                    .hourlyRate(jobPosting.getHourlyRate())
                    .durationOfDays(durationOfDays)
                    .build();
        }
    }
}
