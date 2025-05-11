package com.inglo.giggle.document.application.port.in.command;

import com.inglo.giggle.core.dto.AddressRequestDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateUserStandardLaborContractCommand extends SelfValidating<CreateUserStandardLaborContractCommand> {

    @NotNull(message = "계정 ID는 필수입니다.")
    private final UUID accountId;

    @NotNull(message = "지원 ID는 필수입니다.")
    private final Long userOwnerJobPostingId;

    @NotNull(message = "first name 을 입력해주세요.")
    @Size(min = 1, max = 50, message = "first name은 50자 이하로 입력해주세요.")
    private final String firstName;

    @NotNull(message = "last name 을 입력해주세요.")
    @Size(min = 1, max = 100, message = "last name은 100자 이하로 입력해주세요.")
    private final String lastName;

    @NotNull(message = "주소를 입력해주세요.")
    @Valid
    private final AddressRequestDto address;

    @NotNull(message = "전화번호를 입력해주세요.")
    @Size(min = 10, max = 20, message = "전화번호는 10자 이상, 20자 이하로 입력해주세요.")
    private final String phoneNumber;

    @NotNull(message = "서명을 입력해주세요.")
    private final String signatureBase64;

    public CreateUserStandardLaborContractCommand(
            UUID accountId,
            Long userOwnerJobPostingId,
            String firstName,
            String lastName,
            AddressRequestDto address,
            String phoneNumber,
            String signatureBase64
    ) {
        this.accountId = accountId;
        this.userOwnerJobPostingId = userOwnerJobPostingId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.signatureBase64 = signatureBase64;

        this.validateSelf();
    }
}
