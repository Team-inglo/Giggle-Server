package com.inglo.giggle.user.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminUserDetailResult extends SelfValidating<ReadAdminUserDetailResult> {

    @JsonProperty("email")
    private final String email;

    @NotNull
    @JsonProperty("profile_img_url")
    private String profileImgUrl;

    @NotBlank(message = "first_name은 null일 수 없습니다.")
    @JsonProperty("first_name")
    private final String firstName;

    @NotBlank(message = "last_name은 null일 수 없습니다.")
    @JsonProperty("last_name")
    private final String lastName;

    @JsonProperty("birth")
    private final String birth;

    @NotBlank(message = "gender는 null일 수 없습니다.")
    @JsonProperty("gender")
    private final String gender;

    @JsonProperty("nationality")
    private final String nationality;

    @NotNull(message = "visa는 null일 수 없습니다.")
    @JsonProperty("visa")
    private final String visa;

    @NotBlank(message = "phone_number는 null일 수 없습니다.")
    @JsonProperty("phone_number")
    private final String phoneNumber;

    @JsonProperty("address")
    private final AddressResponseDto address;

    @Builder
    public ReadAdminUserDetailResult(
            String email,
            String profileImgUrl,
            String firstName,
            String lastName,
            String birth,
            String gender,
            String nationality,
            String visa,
            String phoneNumber,
            AddressResponseDto address
    ) {
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.gender = gender;
        this.nationality = nationality;
        this.visa = visa;
        this.phoneNumber = phoneNumber;
        this.address = address;

        this.validateSelf();
    }

    public static ReadAdminUserDetailResult of(
            String email,
            String profileImgUrl,
            String firstName,
            String lastName,
            String birth,
            String gender,
            String nationality,
            String visa,
            String phoneNumber,
            AddressResponseDto address
    ) {
        return ReadAdminUserDetailResult.builder()
                .email(email)
                .profileImgUrl(profileImgUrl)
                .firstName(firstName)
                .lastName(lastName)
                .birth(birth)
                .gender(gender)
                .nationality(nationality)
                .visa(visa)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }
}
