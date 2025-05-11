package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.UpdateUserIntegratedApplicationCommand;

@UseCase
public interface UpdateUserIntegratedApplicationUseCase {

    /**
     * (유학생) 통합신청서 수정하기
     */
    void execute(UpdateUserIntegratedApplicationCommand command);
}
