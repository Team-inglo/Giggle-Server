package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.UpdateDocumentStatusRequestCommand;

@UseCase
public interface UpdateUserDocumentStatusRequestUseCase {

    /**
     * 서류 재검토 요청하기
     */
    void execute(UpdateDocumentStatusRequestCommand command);
}
