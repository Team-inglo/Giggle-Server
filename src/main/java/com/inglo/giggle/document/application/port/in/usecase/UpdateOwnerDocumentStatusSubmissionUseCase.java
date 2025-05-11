package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.UpdateOwnerDocumentStatusSubmissionCommand;

@UseCase
public interface UpdateOwnerDocumentStatusSubmissionUseCase {

    /**
     * (고용주) 서류 제출하기
     */
    void execute(UpdateOwnerDocumentStatusSubmissionCommand command);
}
