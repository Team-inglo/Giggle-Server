package com.inglo.giggle.term.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.term.domain.type.ETermType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateAdminTermCommand extends SelfValidating<CreateAdminTermCommand> {

    @NotNull(message = "version은 필수값입니다.")
    private final Double version;

    @NotNull(message = "content은 필수값입니다.")
    private final String content;

    @NotNull(message = "term_type은 필수값입니다.")
    private final ETermType termType;

    public CreateAdminTermCommand(Double version, String content, ETermType termType) {
        this.version = version;
        this.content = content;
        this.termType = termType;

        this.validateSelf();
    }
}
