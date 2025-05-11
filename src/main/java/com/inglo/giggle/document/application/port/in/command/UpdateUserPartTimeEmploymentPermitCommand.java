package com.inglo.giggle.document.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateUserPartTimeEmploymentPermitCommand extends SelfValidating<UpdateUserPartTimeEmploymentPermitCommand> {

    @NotNull(message = "계정 ID는 필수입니다.")
    private final UUID accountId;

    @NotNull(message = "문서 ID는 필수입니다.")
    private final Long documentId;

    @NotNull(message = "first name 을 입력해주세요.")
    @Size(min = 1, max = 50, message = "first name은 50자 이하로 입력해주세요.")
    private final String firstName;

    @NotNull(message = "last name 을 입력해주세요.")
    @Size(min = 1, max = 100, message = "last name은 100자 이하로 입력해주세요.")
    private final String lastName;


    @NotNull(message = "전공을 입력해주세요.")
    @Size(min = 1, max = 50, message = "전공은 50자 이하로 입력해주세요.")
    private final String major;


    @NotNull(message = "이수학기 수를 입력해주세요.")
    @Min(value = 0, message = "이수학기는 최소 0학기부터 입력 가능합니다.")
    @Max(value = 12, message = "이수학기는 최대 12학기까지 입력 가능합니다.")
    private final Integer termOfCompletion;


    @NotNull(message = "핸드폰 번호를 입력해주세요.")
    @Size(min = 10, max = 20, message = "핸드폰 번호는 10자 이상 20자 이하로 입력해주세요.")
    private final String phoneNumber;


    @NotNull(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 320, message = "이메일은 320자 이하로 입력해주세요.")
    private final String email;

    public UpdateUserPartTimeEmploymentPermitCommand(
            UUID accountId,
            Long documentId,
            String firstName,
            String lastName,
            String major,
            Integer termOfCompletion,
            String phoneNumber,
            String email
    ) {
        this.accountId = accountId;
        this.documentId = documentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.termOfCompletion = termOfCompletion;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.validateSelf();
    }

}
