package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.UpdateUserDocumentStatusSubmissionCommand;

@UseCase
public interface UpdateUserDocumentStatusSubmissionUseCase {

    /**
     * (유학생) 서류 제출하기
     */
    void execute(UpdateUserDocumentStatusSubmissionCommand command);
}
