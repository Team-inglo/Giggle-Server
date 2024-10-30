package com.inglo.giggle.account.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserDetailResponseDto extends SelfValidating<ReadUserDetailResponseDto> {

    @NotNull
    @JsonProperty("profile_img_url")
    private String profileImgUrl;

    @NotBlank(message = "first_name은 null일 수 없습니다.")
    @JsonProperty("first_name")
    private final String firstName;

    @NotBlank(message = "last_name은 null일 수 없습니다.")
    @JsonProperty("last_name")
    private final String lastName;

    @NotBlank(message = "birth는 null일 수 없습니다.")
    @JsonProperty("birth")
    private final String birth;

    @NotBlank(message = "gender는 null일 수 없습니다.")
    @JsonProperty("gender")
    private final String gender;

    @NotBlank(message = "nationality는 null일 수 없습니다.")
    @JsonProperty("nationality")
    private final String nationality;

    @NotNull(message = "visa는 null일 수 없습니다.")
    @JsonProperty("visa")
    private final String visa;

    @NotBlank(message = "phone_number는 null일 수 없습니다.")
    @JsonProperty("phone_number")
    private final String phoneNumber;

    @Builder
    public ReadUserDetailResponseDto(
            String profileImgUrl,
            String firstName,
            String lastName,
            String birth,
            String gender,
            String nationality,
            String visa,
            String phoneNumber
    ) {
        this.profileImgUrl = profileImgUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.gender = gender;
        this.nationality = nationality;
        this.visa = visa;
        this.phoneNumber = phoneNumber;

        this.validateSelf();
    }

    public static ReadUserDetailResponseDto fromEntity(User user) {
        return ReadUserDetailResponseDto.builder()
                .profileImgUrl(user.getProfileImgUrl())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birth(user.getBirth().toString())
                .gender(user.getGender().name())
                .nationality(user.getNationality())
                .visa(user.getVisa().name())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
