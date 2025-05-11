package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class SignUpDefaultOwnerCommand extends SelfValidating<SignUpDefaultOwnerCommand> {

    private final MultipartFile file;

    @NotNull(message = "임시 토큰은 필수입니다.")
    private final String temporaryToken;

    @Valid
    private final AddressRequestDto address;

    @NotNull(message = "마케팅 정보 활용 동의 여부를 입력해주세요.")
    private final Boolean marketingAllowed;

    @NotNull(message = "알림 수신 동의 여부를 입력해주세요.")
    private final Boolean notificationAllowed;

    @NotNull(message = "약관 동의는 필수입니다.")
    private final List<String> termTypes;

    @NotBlank(message = "회사/점포명을 입력해주세요.")
    private final String companyName;

    @NotBlank(message = "이름을 입력해주세요.")
    private final String ownerName;

    @NotBlank(message = "사업자 등록번호를 입력해주세요.")
    private final String companyRegistrationNumber;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private final String phoneNumber;

    public SignUpDefaultOwnerCommand(
            MultipartFile file,
            String temporaryToken,
            AddressRequestDto address,
            Boolean marketingAllowed,
            Boolean notificationAllowed,
            List<String> termTypes,
            String companyName,
            String ownerName,
            String companyRegistrationNumber,
            String phoneNumber
    ) {
        this.file = file;
        this.temporaryToken = temporaryToken;
        this.address = address;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.termTypes = termTypes;
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.phoneNumber = phoneNumber;
        this.validateSelf();
    }
}
