package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.user.domain.type.ELanguage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SignUpDefaultUserCommand extends SelfValidating<SignUpDefaultUserCommand> {

    @NotNull(message = "임시 토큰은 필수입니다.")
    private final String temporaryToken;

    @Valid
    private final AddressRequestDto address;

    @NotNull(message = "마케팅 정보 활용 동의 여부를 입력해주세요.")
    private final Boolean marketingAllowed;

    @NotNull(message = "알림 수신 동의 여부를 입력해주세요.")
    private final Boolean notificationAllowed;

    @NotNull(message = "사용 언어 선택은 필수입니다.")
    private final ELanguage language;

    @NotNull(message = "약관 동의는 필수입니다.")
    private final List<String> termTypes;

    @NotBlank(message = "first name을 입력해주세요.")
    private final String firstName;

    @NotBlank(message = "last name을 입력해주세요.")
    private final String lastName;

    @NotNull(message = "성별을 선택해주세요.")
    private final EGender gender;

    private final LocalDate birth;

    private final String nationality;

    @NotNull(message = "비자를 입력해주세요.")
    private final EVisa visa;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private final String phoneNumber;

    public SignUpDefaultUserCommand(
            String temporaryToken,
            AddressRequestDto address,
            Boolean marketingAllowed,
            Boolean notificationAllowed,
            ELanguage language,
            List<String> termTypes,
            String firstName,
            String lastName,
            EGender gender,
            LocalDate birth,
            String nationality,
            EVisa visa,
            String phoneNumber
    ) {
        this.temporaryToken = temporaryToken;
        this.address = address;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.language = language;
        this.termTypes = termTypes;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birth = birth;
        this.nationality = nationality;
        this.visa = visa;
        this.phoneNumber = phoneNumber;
    }
}
