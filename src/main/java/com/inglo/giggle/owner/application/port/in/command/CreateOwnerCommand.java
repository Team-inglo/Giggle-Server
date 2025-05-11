package com.inglo.giggle.owner.application.port.in.command;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOwnerCommand extends SelfValidating<CreateOwnerCommand> {

    @NotNull(message = "이메일은 필수입니다.")
    private final String email;

    @NotNull(message = "프로필 이미지는 필수입니다.")
    private final String profileImgUrl;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private final String phoneNumber;

    @NotBlank(message = "회사/점포명을 입력해주세요.")
    private final String companyName;

    @NotBlank(message = "이름을 입력해주세요.")
    private final String ownerName;

    @NotBlank(message = "사업자 등록번호를 입력해주세요.")
    private final String companyRegistrationNumber;

    @NotNull(message = "마케팅 정보 활용 동의 여부를 입력해주세요.")
    private final Boolean marketingAllowed;

    @NotNull(message = "알림 수신 동의 여부를 입력해주세요.")
    private final Boolean notificationAllowed;

    @Valid
    private final Address address;

    public CreateOwnerCommand(
            String email,
            String profileImgUrl,
            String phoneNumber,
            String companyName,
            String ownerName,
            String companyRegistrationNumber,
            Boolean marketingAllowed,
            Boolean notificationAllowed,
            Address address
    ) {
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.ownerName = ownerName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.marketingAllowed = marketingAllowed;
        this.notificationAllowed = notificationAllowed;
        this.address = address;
        this.validateSelf();
    }

}
