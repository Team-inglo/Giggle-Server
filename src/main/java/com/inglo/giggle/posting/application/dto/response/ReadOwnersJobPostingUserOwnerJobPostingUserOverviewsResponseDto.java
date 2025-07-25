package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
public class ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto extends SelfValidating<ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto> {

    @NotNull
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("applicant_list")
    private final List<ApplicantOverviewDto> applicantList;

    @Builder
    private ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto(Boolean hasNext, List<ApplicantOverviewDto> applicantList) {
        this.hasNext = hasNext;
        this.applicantList = applicantList;

        this.validateSelf();
    }

    public static ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto of(
            List<ApplicantOverviewDto> applicantList,
            boolean hasNext
    ) {
        return ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto.builder()
                .hasNext(hasNext)
                .applicantList(applicantList)
                .build();
    }

    @Getter
    public static class ApplicantOverviewDto extends SelfValidating<ApplicantOverviewDto> {

        @NotNull
        @JsonProperty("id")
        private final Long id;

        @NotNull
        @JsonProperty("resume_id")
        private final UUID resumeId;

        @NotNull
        @JsonProperty("profile_img_url")
        private final String profileImgUrl;

        @NotNull
        @JsonProperty("name")
        private final String name;

        @JsonProperty("nationality")
        private final String nationality;

        @NotNull
        @JsonProperty("gender")
        private final String gender;

        @NotNull
        @JsonProperty("visa")
        private final String visa;

        @NotNull
        @JsonProperty("school_name")
        private final String schoolName;

        @NotNull
        @JsonProperty("duration_of_days")
        private final Integer durationOfDays;

        @NotNull
        @JsonProperty("step")
        private final String step;

        @Builder
        private ApplicantOverviewDto(
                Long id,
                UUID resumeId,
                String profileImgUrl,
                String name,
                String nationality,
                String gender,
                String visa,
                String schoolName,
                Integer durationOfDays,
                String step
        ) {
            this.id = id;
            this.resumeId = resumeId;
            this.profileImgUrl = profileImgUrl;
            this.name = name;
            this.nationality = nationality;
            this.gender = gender;
            this.visa = visa;
            this.schoolName = schoolName;
            this.durationOfDays = durationOfDays;
            this.step = step;

            this.validateSelf();
        }

        public static ApplicantOverviewDto of(
                UserOwnerJobPosting userOwnerJobPosting,
                String schoolName
        ) {

            User user = userOwnerJobPosting.getUser();

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

            return ApplicantOverviewDto.builder()
                    .id(userOwnerJobPosting.getId())
                    .resumeId(user.getId())
                    .profileImgUrl(user.getProfileImgUrl())
                    .name(user.getName())
                    .nationality(user.getNationality() != null ? user.getNationality().getEnName() : null)
                    .gender(user.getGender().toString())
                    .visa(user.getVisa().toString())
                    .schoolName(schoolName)
                    .durationOfDays(durationOfDays)
                    .step(userOwnerJobPosting.getStep().name())
                    .build();
        }
    }
}
