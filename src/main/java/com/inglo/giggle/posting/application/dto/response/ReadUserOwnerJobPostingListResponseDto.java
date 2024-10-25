package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReadUserOwnerJobPostingListResponseDto extends SelfValidating<ReadUserOwnerJobPostingListResponseDto> {

    @NotNull(message = "has_next는 null일 수 없습니다.")
    @JsonProperty("has_next")
    private Boolean hasNext;

    @JsonProperty("job_posting_list")
    private final List<UserAppliedJobDto> jobPostingList;

    @Builder
    public ReadUserOwnerJobPostingListResponseDto(
            Boolean hasNext,
            List<UserAppliedJobDto> jobPostingList
    ) {
        this.hasNext = hasNext;
        this.jobPostingList = jobPostingList;

        this.validateSelf();
    }

    // UserOwnerJobPosting 엔티티를 UserAppliedJobDto로 변환하는 메서드 추가
    public static ReadUserOwnerJobPostingListResponseDto of(
            Page<UserOwnerJobPosting> userOwnerJobPostingPage
    ) {
        boolean hasNext = userOwnerJobPostingPage.hasNext();
        List<UserAppliedJobDto> jobPostingList = userOwnerJobPostingPage.getContent().stream()
                .map(UserAppliedJobDto::fromEntity)
                .collect(Collectors.toList());

        return ReadUserOwnerJobPostingListResponseDto.builder()
                .hasNext(hasNext)
                .jobPostingList(jobPostingList)
                .build();
    }

    @Getter
    public static class UserAppliedJobDto extends SelfValidating<UserAppliedJobDto> {

        @NotBlank(message = "job_posting_id는 null일 수 없습니다.")
        @JsonProperty("job_posting_id")
        private final Long jobPostingId;

        @NotBlank(message = "user_owner_job_posting_id는 null일 수 없습니다.")
        @JsonProperty("user_owner_job_posting_id")
        private final Long userOwnerJobPostingId;

        @NotBlank(message = "icon_img_url은 null일 수 없습니다.")
        @JsonProperty("icon_img_url")
        private final String iconImgUrl;

        @NotBlank(message = "title은 null일 수 없습니다.")
        @JsonProperty("title")
        private final String title;

        @NotBlank(message = "address_name은 null일 수 없습니다.")
        @JsonProperty("address_name")
        private final String addressName;

        @NotBlank(message = "step은 null일 수 없습니다.")
        @JsonProperty("step")
        private final String step;

        @NotBlank(message = "hourly_rate은 null일 수 없습니다.")
        @JsonProperty("hourly_rate")
        private final Integer hourlyRate;

        @NotBlank(message = "duration_of_days는 null일 수 없습니다.")
        @JsonProperty("duration_of_days")
        private final Integer durationOfDays;

        @Builder
        public UserAppliedJobDto(
                Long jobPostingId,
                Long userOwnerJobPostingId,
                String iconImgUrl,
                String title,
                String addressName,
                String step,
                Integer hourlyRate,
                Integer durationOfDays
        ) {
            this.jobPostingId = jobPostingId;
            this.userOwnerJobPostingId = userOwnerJobPostingId;
            this.iconImgUrl = iconImgUrl;
            this.title = title;
            this.addressName = addressName;
            this.step = step;
            this.hourlyRate = hourlyRate;
            this.durationOfDays = durationOfDays;

            this.validateSelf();
        }

        public static UserAppliedJobDto fromEntity(UserOwnerJobPosting userOwnerJobPosting) {
            int durationOfDays = (int) java.time.Duration.between(
                    userOwnerJobPosting.getUpdatedAt().atStartOfDay(),
                    LocalDate.now().atStartOfDay()
            ).toDays();

            return UserAppliedJobDto.builder()
                    .userOwnerJobPostingId(userOwnerJobPosting.getId())
                    .jobPostingId(userOwnerJobPosting.getJobPosting().getId())
                    .iconImgUrl(userOwnerJobPosting.getOwner().getProfileImgUrl())
                    .title(userOwnerJobPosting.getJobPosting().getTitle())
                    .addressName(userOwnerJobPosting.getJobPosting().getAddress().getAddressName())
                    .step(userOwnerJobPosting.getStep().name())
                    .hourlyRate(userOwnerJobPosting.getJobPosting().getHourlyRate())
                    .durationOfDays(durationOfDays)
                    .build();
        }
    }
}