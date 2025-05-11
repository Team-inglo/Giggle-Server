package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateAdditionalLanguageCommand extends SelfValidating<UpdateAdditionalLanguageCommand> {

    @NotNull(message = "accountId는 필수 입력 값입니다.")
    private final UUID accountId;

    @NotNull(message = "additionalLanguageSkillId는 필수 입력 값입니다.")
    @Positive(message = "additional_language_skill_id는 양수여야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "additional_language_skill_id는 최대값을 초과할 수 없습니다.")
    private final Long additionalLanguageSkillId;

    @Size(min = 1, max = 50, message = "언어 이름은 1자 이상 50자 이하로 입력 가능합니다.")
    @NotBlank(message = "언어 이름은 필수 입력 값입니다.")
    private final String languageName;

    @NotNull(message = "언어 레벨은 필수 입력 값입니다.")
    @Max(value = 10, message = "언어 레벨은 10 이하로 입력 가능합니다.")
    @Min(value = 0, message = "언어 레벨은 0 이상으로 입력 가능합니다.")
    private final Integer level;

    public UpdateAdditionalLanguageCommand(
            UUID accountId,
            Long additionalLanguageSkillId,
            String languageName,
            Integer level
    ) {
        this.accountId = accountId;
        this.additionalLanguageSkillId = additionalLanguageSkillId;
        this.languageName = languageName;
        this.level = level;
        this.validateSelf();
    }
}
