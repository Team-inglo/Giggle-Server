package com.inglo.giggle.document.application.port.in.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.port.in.command.CreateUserIntegratedApplicationCommand;

@UseCase
public interface CreateUserIntegratedApplicationUseCase {

    /**
     * (유학생) 통합 지원서 생성하기
     */
    void execute(CreateUserIntegratedApplicationCommand command);
}
