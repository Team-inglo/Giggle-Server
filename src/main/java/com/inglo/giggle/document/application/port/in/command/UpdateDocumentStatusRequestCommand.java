package com.inglo.giggle.document.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateDocumentStatusRequestCommand extends SelfValidating<UpdateDocumentStatusRequestCommand> {

    @NotNull(message = "계정 ID는 필수입니다.")
    private final UUID accountId;

    @NotNull(message = "문서 ID는 필수입니다.")
    private final Long documentId;

    @NotNull(message = "재요청 사유는 필수입니다.")
    @Size(min = 1, max = 100, message = "재요청 사유는 1자 이상 100자 이하로 입력해주세요.")
    private final String reason;

    public UpdateDocumentStatusRequestCommand(
            UUID accountId,
            Long documentId,
            String reason
    ) {
        this.accountId = accountId;
        this.documentId = documentId;
        this.reason = reason;

        this.validateSelf();
    }
}
