package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateAdditionalLanguageCommand extends SelfValidating<CreateAdditionalLanguageCommand> {

    @NotNull(message = "accountId는 필수 입력 값입니다.")
    private final UUID accountId;

    @Size(min = 1, max = 50, message = "언어 이름은 1자 이상 50자 이하로 입력 가능합니다.")
    @NotBlank(message = "언어 이름은 필수 입력 값입니다.")
    private final String languageName;

    @Max(value = 10, message = "언어 레벨은 10 이하로 입력 가능합니다.")
    @Min(value = 0, message = "언어 레벨은 0 이상으로 입력 가능합니다.")
    @NotNull(message = "언어 레벨은 필수 입력 값입니다.")
    private final Integer level;

    public CreateAdditionalLanguageCommand(UUID accountId, String languageName, Integer level) {
        this.accountId = accountId;
        this.languageName = languageName;
        this.level = level;
        this.validateSelf();
    }
}
