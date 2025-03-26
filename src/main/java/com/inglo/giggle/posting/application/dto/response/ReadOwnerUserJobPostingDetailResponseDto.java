package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.school.domain.School;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReadOwnerUserJobPostingDetailResponseDto extends SelfValidating<ReadOwnerUserJobPostingDetailResponseDto> {

    @NotNull(message = "profile_img_url은 null이 될 수 없습니다.")
    @JsonProperty("profile_img_url")
    private final String profileImgUrl;

    @NotNull(message = "name은 null이 될 수 없습니다.")
    @JsonProperty("name")
    private final String name;

    @NotNull(message = "nationality는 null이 될 수 없습니다.")
    @JsonProperty("nationality")
    private final String nationality;

    @NotNull(message = "gender는 null이 될 수 없습니다.")
    @JsonProperty("gender")
    private final String gender;

    @NotNull(message = "visa는 null이 될 수 없습니다.")
    @JsonProperty("visa")
    private final String visa;

    @NotNull(message = "school_name은 null이 될 수 없습니다.")
    @JsonProperty("school_name")
    private final String schoolName;

    @NotNull(message = "duration_of_days는 null이 될 수 없습니다.")
    @JsonProperty("duration_of_days")
    private final Integer durationOfDays;

    @NotNull(message = "step은 null이 될 수 없습니다.")
    @JsonProperty("step")
    private final String step;

    @Builder
    public ReadOwnerUserJobPostingDetailResponseDto(
            String profileImgUrl,
            String name,
            String nationality,
            String gender,
            String visa,
            String schoolName,
            Integer durationOfDays,
            String step
    ) {
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

    public static final String DASH = " - ";

    public static ReadOwnerUserJobPostingDetailResponseDto fromEntities(
            UserOwnerJobPosting userOwnerJobPosting,
            School school
            ) {

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

        return ReadOwnerUserJobPostingDetailResponseDto.builder()
                .profileImgUrl(userOwnerJobPosting.getUser().getProfileImgUrl())
                .name(userOwnerJobPosting.getUser().getName())
                .nationality(userOwnerJobPosting.getUser().getNationality() != null ? userOwnerJobPosting.getUser().getNationality() : null)
                .gender(userOwnerJobPosting.getUser().getGender().toString())
                .visa(userOwnerJobPosting.getUser().getVisa().toString())
                .schoolName(school == null ? DASH : school.getSchoolName())
                .durationOfDays(durationOfDays)
                .step(userOwnerJobPosting.getStep().toString())
                .build();
    }
}
