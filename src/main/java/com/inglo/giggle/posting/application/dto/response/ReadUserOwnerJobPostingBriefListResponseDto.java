package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
public class ReadUserOwnerJobPostingBriefListResponseDto extends SelfValidating<ReadUserOwnerJobPostingBriefListResponseDto> {

    @NotNull(message = "has_next는 null일 수 없습니다.")
    @JsonProperty("has_next")
    private Boolean hasNext;

    @JsonProperty("ongoing_interviews_list")
    private final List<OngoingJobResponseDto> ongoingInterviewsList;

    @Builder
    public ReadUserOwnerJobPostingBriefListResponseDto(
            Boolean hasNext,
            List<OngoingJobResponseDto> ongoingInterviewsList
    ) {
        this.hasNext = hasNext;
        this.ongoingInterviewsList = ongoingInterviewsList;

        this.validateSelf();
    }

    public static ReadUserOwnerJobPostingBriefListResponseDto of(
            Page<UserOwnerJobPosting> userOwnerJobPostingPage
    ) {
        boolean hasNext = userOwnerJobPostingPage.hasNext();
        List<OngoingJobResponseDto> ongoingInterviewsList = userOwnerJobPostingPage.getContent().stream()
                .map(OngoingJobResponseDto::fromEntity)
                .toList();

        return ReadUserOwnerJobPostingBriefListResponseDto.builder()
                .hasNext(hasNext)
                .ongoingInterviewsList(ongoingInterviewsList)
                .build();
    }

    @Getter
    public static class OngoingJobResponseDto extends SelfValidating<OngoingJobResponseDto> {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

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

        @Builder
        public OngoingJobResponseDto(
                Long id,
                String iconImgUrl,
                String title,
                String addressName,
                String step
        ) {
            this.id = id;
            this.iconImgUrl = iconImgUrl;
            this.title = title;
            this.addressName = addressName;
            this.step = step;

            this.validateSelf();
        }

        public static OngoingJobResponseDto fromEntity(UserOwnerJobPosting userOwnerJobPosting) {
            return OngoingJobResponseDto.builder()
                    .id(userOwnerJobPosting.getId())
                    .iconImgUrl(userOwnerJobPosting.getOwner().getProfileImgUrl())
                    .title(userOwnerJobPosting.getJobPosting().getTitle())
                    .addressName(userOwnerJobPosting.getJobPosting().getAddress().getAddressName())
                    .step(userOwnerJobPosting.getStep().name())
                    .build();
        }
    }
}
