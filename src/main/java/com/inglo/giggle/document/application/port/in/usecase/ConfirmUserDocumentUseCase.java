package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.ConfirmUserDocumentCommand;

@UseCase
public interface ConfirmUserDocumentUseCase {

    /**
     * (유학생) 서류 컨펌하기
     */
    void execute(ConfirmUserDocumentCommand command);
}
