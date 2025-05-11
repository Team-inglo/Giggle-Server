package com.inglo.giggle.user.application.port.in.command;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.user.domain.type.ELanguage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateUserCommand extends SelfValidating<CreateUserCommand> {

    @NotBlank(message = "이메일은 null일 수 없습니다.")
    private final String email;

    @NotBlank(message = "profile_img_url은 null일 수 없습니다.")
    private String profileImgUrl;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private final String phoneNumber;

    @NotBlank(message = "first name을 입력해주세요.")
    private final String firstName;

    @NotBlank(message = "last name을 입력해주세요.")
    private final String lastName;

    @NotNull(message = "성별을 선택해주세요.")
    private final EGender gender;

    private final String nationality;

    @NotNull(message = "사용 언어 선택은 필수입니다.")
    private final ELanguage language;

    private final LocalDate birth;

    @NotNull(message = "비자를 입력해주세요.")
    private final EVisa visa;

    @NotNull(message = "마케팅 정보 활용 동의 여부를 입력해주세요.")
    private final Boolean marketingAllowed;

    @NotNull(message = "알림 수신 동의 여부를 입력해주세요.")
    private final Boolean notificationAllowed;

    @Valid
    private final Address address;

    public CreateUserCommand(
            String email,
            String profileImgUrl,
            String phoneNumber,
            String firstName,
            String lastName,
            EGender gender,
            String nationality,
            ELanguage language,
            LocalDate birth,
            EVisa visa,
            Boolean marketingAllowed,
            Boolean notificationAllowed,
            Address address
    ) {
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.nationality = nationality;
        this.language = language;
        this.birth = birth;
        this.visa = visa;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.address = address;
        this.validateSelf();
    }
}
