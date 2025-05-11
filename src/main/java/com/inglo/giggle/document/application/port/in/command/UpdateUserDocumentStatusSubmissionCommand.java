package com.inglo.giggle.document.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateUserDocumentStatusSubmissionCommand extends SelfValidating<UpdateUserDocumentStatusSubmissionCommand> {

    @NotNull(message = "accountId 는 필수입니다.")
    private final UUID accountId;

    @NotNull(message = "documentId 는 필수입니다.")
    private final Long documentId;

    public UpdateUserDocumentStatusSubmissionCommand(UUID accountId, Long documentId) {
        this.accountId = accountId;
        this.documentId = documentId;
        this.validateSelf();
    }
}
